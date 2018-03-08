package com.game.generate.proto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.generate.dao.StringUtil;
import com.game.utils.PropertyConfigUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jianpeng.zhang
 * @since 2017/3/6.
 */
public class HandlerFactory {
	private final Configuration cfg;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");

	/**
	 * 存放用于生成ProtoConfig.xml的数据
	 */
	private HashMap<String, ModuleInfo> moduleInfoMap = null;
	/**
	 * 存放用于生成 MsgCode.java 的数据
	 */
	private TreeMap<Integer, MsgFieldInfo> codeMap = null;

	/** 截取引号里的内容 */
	private Pattern pattern = Pattern.compile("\"([^\"]*?)\"");

	public HandlerFactory() {
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File("model"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扫描指定文件夹下的.proto文件，并对文件的名字，文件的内容进行校验。校验通过会生成相应的handler类
	 */
	private ArrayList<File> scanProto() throws IOException {
		ArrayList<File> successFile = new ArrayList<>();
		File dir = new File(HandlerConfig.getProtoFilePath());
		Collection<File> files = FileUtils.listFiles(dir, new String[] { "proto" }, true);

		codeMap = new TreeMap<>();
		moduleInfoMap = new HashMap<>();

		ArrayList<String> failList = new ArrayList<>();
		ArrayList<String> warnList = new ArrayList<>();

		for (File file : files) {

			String[] ss = file.getName().split("\\.");

			String moduleName = ss[0].substring(0, 1).toLowerCase() + ss[0].substring(1);
			if (!moduleInfoMap.containsKey(moduleName)) {
				moduleInfoMap.put(moduleName, new ModuleInfo(moduleName));
			}

			try {
				List<String> lines = FileUtils.readLines(file, Charset.forName("utf-8"));
				String packageName = "";
				String className = "";

				ArrayList<Map<String, Object>> templateParams = new ArrayList<>();

				/** 分开存储每个.proto文件里的常量，并按顺序罗列在生成的MsgCode.java里 */
				MsgFieldInfo fieldInfos = new MsgFieldInfo();
				TreeMap<Integer, MsgFieldInfo> treeMap = new TreeMap<>();

				List<MsgBean> msgBeanList = new ArrayList<>();

				for (String line : lines) {
					if (StringUtil.isEmpty(packageName) || StringUtil.isEmpty(className)) {
						if (line.contains("java_package")) {
							Matcher matcher = pattern.matcher(line);
							if (!matcher.find()) {
								throw new Exception(
										getExceptionFormat(line, lines.indexOf(line), "option java_package附近有误"));
							}
							packageName = matcher.group(1);
						} else if (line.contains("java_outer_classname")) {
							Matcher matcher = pattern.matcher(line);
							if (!matcher.find()) {
								throw new Exception(getExceptionFormat(line, lines.indexOf(line),
										"option java_outer_classname附近有误"));
							}
							className = matcher.group(1);
						} else if (line.replaceAll("\\s*|\t|\r|\n", "").startsWith("//@module")) {
							JSONObject jsonObject = JSON.parseObject(line.substring(line.indexOf("@module") + 7));
							if (!moduleInfoMap.containsKey(jsonObject.getString("name"))) {
								throw new Exception(getExceptionFormat(line, lines.indexOf(line), "name 不跟文件匹配"));
							}
							ModuleInfo module = moduleInfoMap.get(jsonObject.getString("name"));
							module.setModuleDesc(jsonObject.getString("desc"));

							String range = jsonObject.getString("codeRange");// [1,500]
							fieldInfos.setComment(module.getModuleDesc() + "。消息号范围：" + range);

							range = StringUtils.substringBetween(range, "[", "]");
							String[] number = range.split(",");
							if (number.length != 2) {
								throw new Exception(getExceptionFormat(line, lines.indexOf(line), "codeRange 个数只能为2个"));
							}
							module.setMinCode(Integer.parseInt(number[0]));
							module.setMaxCode(Integer.parseInt(number[1]));

							fieldInfos.setCode(module.getMinCode()); //表示本文件起始的code，用于排序
							fieldInfos.setFieldName(file.getName());

						}
					} else if (line.replaceAll("\\s*|\t|\r|\n", "").startsWith("//@msg")) {
						MsgBean bean = JSON.parseObject(line.substring(line.indexOf("@msg") + 4), MsgBean.class);

						if (StringUtil.isEmpty(bean.getDesc())) {
							throw new Exception(getExceptionFormat(line, lines.indexOf(line), "desc 不能为空"));
						}
						if (!moduleInfoMap.get(moduleName).inRange(bean.getCode())) {
							throw new Exception(getExceptionFormat(line, lines.indexOf(line), "code 指不在范围内"));
						}
						if (codeMap.containsKey(bean.getCode())) {
							throw new Exception(getExceptionFormat(line, lines.indexOf(line), "code 有重复"));
						}
						if (StringUtil.isEmpty(bean.getName())) {
							throw new Exception(getExceptionFormat(line, lines.indexOf(line), "name 不能为空"));
						}
						if (StringUtil.isEmpty(bean.getTemplate()) && bean.getCode() % 2 != 0) {
							throw new Exception(getExceptionFormat(line, lines.indexOf(line), "template 不能为空"));
						}

						if (!StringUtil.isEmpty(bean.getMsgBody()))
						{
							bean.setBodyClazzName(packageName + "." + className + "$" + bean.getMsgBody());
						}
						bean.setType(bean.getCode() % 2 == 1 ? "UP" : "DOWN");

						MsgFieldInfo fieldInfo = new MsgFieldInfo();
						fieldInfo.setCode(bean.getCode());
						fieldInfo.setComment(bean.getDesc() + " " + bean.getCode());
						fieldInfo.setFieldName(bean.getName() + (bean.getCode() % 2 != 0 ? "Req" : "Resp"));
						treeMap.put(bean.getCode(), fieldInfo);
						msgBeanList.add(bean);

						String handlerName = bean.getName();
						List<String> imports = new ArrayList<>();
						String sentence = "";
						if (!StringUtil.isEmpty(bean.getMsgBody())) {
							imports.add(packageName + "." + className + "." + bean.getMsgBody());
							sentence = bean.getMsgBody() + " req = request.parseParams(" + bean.getMsgBody()
									+ ".newBuilder());";
						}

						Map<String, Object> rootMap = new HashMap<>();
						rootMap.put("package",
								HandlerConfig.getHandlerOutputPath().replaceAll("/", ".") + "." + moduleName);
						rootMap.put("imports", imports);
						rootMap.put("comment", fieldInfo.getComment());
						rootMap.put("code", bean.getCode());
						rootMap.put("handlerName", handlerName);
						rootMap.put("template", bean.getTemplate());
						rootMap.put("sentence", sentence);
						rootMap.put("date", sdf.format(new Date()));
						templateParams.add(rootMap);
					} else {
						String oldLine = line;
						line = line.replaceAll("\\s*|\t|\r|\n", "");
						if (line.startsWith("optional") || line.startsWith("required") || line.startsWith("repeated")) {
							String[] strings = line.split("//");
							if (strings.length >= 2 && !StringUtil.isEmpty(strings[1])) {
								continue;
							}
							throw new Exception(getExceptionFormat(oldLine, lines.indexOf(oldLine), "该行没有注释，生成失败"));
						}
					}
				}

				if (templateParams.size() == 0) {
					warnList.add(file.getName());
					continue;
				}
				moduleInfoMap.get(moduleName).setBeanList(msgBeanList);

				fieldInfos.setFieldList(new ArrayList<>(treeMap.values()));	//使用treeMap是用于排序
				codeMap.put(fieldInfos.getCode(), fieldInfos);

				//生成handler文件
				for (Map<String, Object> rootMap : templateParams) {
					File javaFile = Paths.get("code", HandlerConfig.getHandlerOutputPath(), moduleName,
							rootMap.get("handlerName")+"Handler" + ".java").toFile();
					if (!javaFile.getParentFile().exists()) {
						javaFile.getParentFile().mkdirs();
					}
					if (rootMap.get("template") != null) {
						OutputStream out = new FileOutputStream(javaFile);
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
						cfg.getTemplate(rootMap.get("template") + ".java").process(rootMap, bw);
						System.out.println("生成代码成功! className=" + rootMap.get("handlerName"));
					}
				}
				successFile.add(file);
			} catch (Exception e) {
				failList.add(file.getName());
				System.out.println(file.getName() + " 异常");
				System.out.println(e.getMessage());
			}
		}

		if (failList.size() > 0) {
			System.out.println("构建失败的proto文件有：" + failList.toString());
		}
		if (warnList.size() > 0) {
			System.out.println("没有handler生成的proto文件：" + warnList.toString());
		}

		return successFile;
	}

	private String getExceptionFormat(String line, int index, String msg) {
		return line + " : " + (index + 1) + "行, " + msg;
	}

	/**
	 * 把所有proto文件内容合并到一个txt文件里
	 */
	private void merge(ArrayList<File> files) throws Exception {
		File mergeFile = Paths.get(HandlerConfig.getOutPutPath(), "merge.txt").toFile();
		if (mergeFile.exists()) {
			mergeFile.delete();
		}
		for (File file : files) {
			FileUtils.writeStringToFile(mergeFile,
					"\n\n------------------------" + file.getName() + "------------------------------\n\n", true);

			FileUtils.writeLines(mergeFile, "utf8", ProtoMergeUtils.modify(file), true);
		}
		System.out.println("总共合并[" + files.size() + "]个文件!");
	}

	/**
	 * 生成协议配置文件
	 */
	private void buildXml() throws JAXBException {
		Root root = new Root();
		root.setInfos(new ArrayList<ModuleInfo>());
		ArrayList<ModuleInfo> moduleInfos = new ArrayList<>();
		if (moduleInfoMap != null) {
			for (ModuleInfo info : moduleInfoMap.values()) {
				if (info.getMaxCode() != info.getMinCode()) {
					moduleInfos.add(info);
				}
			}
		}
		root.setInfos(new ArrayList<>(moduleInfos));

		JAXBContext context = JAXBContext.newInstance(Root.class, ModuleInfo.class, MsgBean.class); // 获取上下文对象
		Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); // 设置编码字符集
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进

		File outFile = Paths.get(HandlerConfig.getOutPutPath(), "ProtoConfig.xml").toFile();
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		marshaller.marshal(root, outFile);
		System.out.println("生成ProtoConfig.xml成功");
	}

	private void createMsgCode() throws IOException, TemplateException {
		Template template = cfg.getTemplate("MsgPojo.java");

		Map<String, Object> rootMap = new HashMap<>();
		rootMap.put("package", HandlerConfig.getMsgCodePkgPath().replaceAll("/", "."));
		rootMap.put("moduleInfos", codeMap.values());
		rootMap.put("date", sdf.format(new Date()));

		File javaFile = Paths.get("code", HandlerConfig.getMsgCodePkgPath(), "MsgCode.java").toFile();
		if (!javaFile.getParentFile().exists()) {
			javaFile.getParentFile().mkdirs();
		}
		OutputStream out = new FileOutputStream(javaFile);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
		template.process(rootMap, bw);
		System.out.println("生成MsgCode.java成功!");
	}

	public static void main(String[] args) throws Exception {
		PropertyConfigUtil.init(HandlerConfig.class.getPackage());

		HandlerFactory factory = new HandlerFactory();
		try {
			ArrayList<File> files = factory.scanProto();
			factory.merge(files);
			factory.buildXml();
			factory.createMsgCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
