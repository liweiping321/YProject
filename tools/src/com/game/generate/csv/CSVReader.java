package com.game.generate.csv;

import com.csvreader.CsvReader;
import com.game.generate.dao.MyProperties;
import com.game.http.HttpClient;
import com.game.http.entity.HttpRequestMsg;
import com.game.http.entity.HttpResponseMsg;
import com.game.utils.DecodeUtil;
import com.game.utils.LogUtils;
import com.game.utils.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author jianpeng.zhang
 * @since 2017/3/1.
 */
public class CSVReader {
	// private static final Logger LOG =LogManager.getLogger(CSVReader.class);
	private MyProperties prop = null;
	private File tableSqlFile;
	private File operatorSqlFile;

	public CSVReader() {
		init();
	}

	private void init() {
		try {
			prop = new MyProperties("config/csv_reader_config.properties");
			String save = prop.getProperty("outputPath");
			if (StringUtils.isEmpty(save)) {
				save = "outputData";
			}

			tableSqlFile = Paths.get(save, "tableSql.sql").toFile();
			operatorSqlFile = Paths.get(save, "operatorSql.sql").toFile();

			if (!tableSqlFile.getParentFile().exists()) {
				tableSqlFile.getParentFile().mkdirs();
			}

			if (tableSqlFile.exists()) {
				tableSqlFile.delete();
			}
			if (operatorSqlFile.exists()) {
				operatorSqlFile.delete();
			}

		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
		}

	}

	private void loadCSV2Db() {
		List<File> sourceFiles = getCsvFileList(prop.getProperty("readPath"));
		FileInputStream fis = null;

		ArrayList<String> needReloadFile = new ArrayList<>();
		// BufferedReader reader = null;
		for (File file : sourceFiles) {
			try {
				fis = new FileInputStream(file);
				String fileName = file.getName();

				CsvReader readers = new CsvReader(fis, Charset.forName(DecodeUtil.guessFileEncoding(file)));

				readers.readRecord();
				String[] columnNames = replaceBom(readers.getValues());
				readers.readRecord();
				String[] columnTypes = readers.getValues();
				readers.readRecord();
				String[] columnComments = readers.getValues();

				String tableName = MySqlUtil.getTableName(fileName.substring(0, fileName.indexOf(".")));

				switch (Integer.parseInt(prop.getProperty("operatorMode"))) {
				case 1:
					MySqlUtil.dropTable(tableName, tableSqlFile, prop.getBoolean("isExecuteSql", false));
					MySqlUtil.createTable(tableName, columnNames, columnTypes, columnComments, tableSqlFile,
							prop.getBoolean("isExecuteSql", false));
					break;
				case 2:
					MySqlUtil.clearTableData(tableName, operatorSqlFile, prop.getBoolean("isExecuteSql", false));
					loadData2Db(readers, tableName, columnNames, columnTypes);
					needReloadFile.add(tableName);
					break;
				case 3:
					MySqlUtil.dropTable(tableName, tableSqlFile, prop.getBoolean("isExecuteSql", false));
					MySqlUtil.createTable(tableName, columnNames, columnTypes, columnComments, tableSqlFile,
							prop.getBoolean("isExecuteSql", false));
					loadData2Db(readers, tableName, columnNames, columnTypes);
					needReloadFile.add(tableName);
					break;
				default:
					break;
				}
			} catch (Exception e) {

				LogUtils.error(e.getMessage(), e);
				LogUtils.error(String.format("文件 %s 操作失败", file.getAbsolutePath()));

			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						LogUtils.error(e.getMessage(), e);
					}
				}
			}
		}

		if (Integer.parseInt(prop.getProperty("operatorMode")) != 1 && prop.getBoolean("isExecuteSql", false)
				&& prop.getBoolean("isReload", false)) {
			try {
				if (needReloadFile.isEmpty()) {
					return;
				}
				LogUtils.info("请求调用服务器数据更新方法");
				HttpRequestMsg<List<String>> httpRequestMsg = new HttpRequestMsg<>();
				httpRequestMsg.setBody(needReloadFile);
				HttpResponseMsg responseMsg = HttpClient.post(prop.getProperty("url"), httpRequestMsg);
				if (responseMsg != null) {
					LogUtils.info("status=" + responseMsg.getStatus() + ", errorCode=" + responseMsg.getErrorCode()
							+ ", message=" + responseMsg.getBody().toString());
				} else {
					LogUtils.info("请求服务器异常");
				}
			} catch (Exception e) {
				LogUtils.info("请求服务器异常", e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 过滤 utf-8 bom格式开头的标记
	 */
	private String[] replaceBom(String[] source) {
		ArrayList<String> list = new ArrayList<>();
		for (String s : source) {
			list.add(s.replaceAll("" + (char) 65279, ""));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 读取数据添加到数据库中
	 *
	 * @param reader
	 *            源数据的CsvReader，从中读取数据
	 * @param columnNames
	 *            列名数组
	 * @param columnTypes
	 *            列的类型的数组
	 * @param tableName
	 *            插入表格的名称
	 */
	private void loadData2Db(CsvReader reader, String tableName, String[] columnNames, String[] columnTypes)
			throws IOException, SQLException {
		LogUtils.debug("开始插入数据操作，" + tableName);
		HashSet<Integer> numIndex = new HashSet<>();
		HashSet<Integer> stringIndex = new HashSet<>();
		// 判断哪些值需要加引号
		for (int i = 0; i < columnTypes.length; i++) {
			if ("date".equals(columnTypes[i].trim().toLowerCase())
					|| "string".equals(columnTypes[i].trim().toLowerCase())) {
				stringIndex.add(i);
			} else if ("int".equals(columnTypes[i].trim().toLowerCase())
					|| "float".equals(columnNames[i].trim().toLowerCase())) {
				numIndex.add(i);
			}
		}
		// 列名的首字母大写
		StringBuilder nameSb = new StringBuilder();
		for (String name : columnNames) {
			if (name.length() > 0) {
				nameSb.append("`").append(name.substring(0, 1).toUpperCase()).append(name.substring(1)).append("`,");
			} else {
				nameSb.append(name);
			}
		}
		nameSb.delete(nameSb.length() - 1, nameSb.length());

		StringBuilder sb = new StringBuilder();
		int count = 0;

		while (reader.readRecord()) {
			count++;
			sb.append("(");
			for (int i = 0; i < reader.getColumnCount(); i++) {
				if (stringIndex.contains(i)) {
					sb.append("'").append(reader.get(i)).append("',");
				} else {
					if (numIndex.contains(i) && StringUtil.isEmpty(reader.get(i))) {
						sb.append(0);
					}
					sb.append(reader.get(i)).append(",");
				}
			}
			sb.insert(sb.length() - 1, ")");

			if (count >= 10000) {
				sb.delete(sb.length() - 1, sb.length());
				MySqlUtil.insertValue(tableName, nameSb.toString(), sb.toString(), operatorSqlFile,
						prop.getBoolean("isExecuteSql", false));
				sb = new StringBuilder();
			}
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
			MySqlUtil.insertValue(tableName, nameSb.toString(), sb.toString(), operatorSqlFile,
					prop.getBoolean("isExecuteSql", false));
		}
	}

	/**
	 * 得到传入路径里所有的csv文件
	 */
	private List<File> getCsvFileList(String root) {
		List<File> result = new ArrayList<>();
		LogUtils.error("root -------- =" + root);
		File sourceFile = new File(root);
		if (!sourceFile.exists()) {
			LogUtils.error("文件不存在。path=" + sourceFile.getAbsolutePath());
			return result;
		}

		if (sourceFile.isDirectory()) {
			if (sourceFile.listFiles() != null) {
				for (File file : sourceFile.listFiles()) {
					if (file.isFile() && file.getName().endsWith(".csv")) {
						result.add(file);
					} else if (file.isDirectory()) {
						result.addAll(getCsvFileList(file.getAbsolutePath()));
					}
				}
			}
		} else if (sourceFile.isFile() && sourceFile.getName().endsWith(".csv")) {
			result.add(sourceFile);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		CSVReader reader = new CSVReader();
		try {
			reader.loadCSV2Db();
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
		}

	}
}
