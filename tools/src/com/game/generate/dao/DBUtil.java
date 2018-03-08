package com.game.generate.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
 
/**
 * 
 * @author lip.li
 * @date 2017年1月9日
 */
public class DBUtil {
	
    private final Configuration cfg;
    
    private final MyProperties prop;
    
    private final Connection connection;
    
    public final File providers=new File("code/providers.txt");
    
	public MyProperties getProp() {
		return prop;
	}


	public DBUtil()throws Exception{
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File("model"));
		} catch (IOException e) {
		 
			e.printStackTrace();
		}
		prop=new MyProperties("config/dao_config.properties");
		
		connection=getConnection();
	}
	 

	public  Connection getConnection() throws ClassNotFoundException, SQLException {
 
		return getConnection(prop.getProperty("db.url"),  prop.getProperty("db.username"),
				prop.getProperty("db.password"));
	}

	public  Connection getConnection(String jdbcString,
			String username, String password)
			throws ClassNotFoundException, SQLException {

		Class.forName(prop.getProperty("db.Driver"));
		return  DriverManager.getConnection(jdbcString, username, password);
	}

	public  String table2pojo(String tableName,
			String path, boolean isCreateFile,String classPackage) throws Exception {
		
		String className=getClassName(tableName);
		
		classPackage=getClassPackage(tableName,classPackage);
		
		Connection connection=getConnection();
		
		List<ColumnInfoBean> columnInfoBeans=getColumnInfos(connection, tableName);
		
		String keyType=getPrimaryKeyType(columnInfoBeans);
		
		Set<String> importTypes=getImportTypes(columnInfoBeans);
		 
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("imports", importTypes);
		rootMap.put("keyType", keyType);
		rootMap.put("className", className);
		rootMap.put("table", tableName);
		rootMap.put("package", classPackage);
		rootMap.put("props", columnInfoBeans);
		rootMap.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ").format(new Date()));
		
		createFile(tableName, classPackage, className, rootMap,"DbPojo.java");
		
		if(className.endsWith("Cfg")){
			createFile(tableName, "com.road.fire.cfg.provider", className+"Provider", rootMap,"CfgProvider.java");
			List<String> lines=Lists.newArrayList("register("+className+"Provider.getInstance());");
			FileUtils.writeLines(providers,lines , true);
		}
		return null;
	}


	private void createFile(String tableName, String classPackage,
			String className, Map<String, Object> rootMap,String templateName) throws IOException,
			FileNotFoundException, UnsupportedEncodingException {
		Template template = cfg.getTemplate(templateName);
		 
	
		try {
			File file = new File( "code" + File.separator+classPackage.replaceAll("\\.", "/")+File.separator + className + ".java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out=new FileOutputStream(file);  
		    BufferedWriter   rd   =   new BufferedWriter(new OutputStreamWriter(out,"utf-8"));  

			template.process(rootMap,rd);
			System.out.println("生成代码成功! table="+tableName+",class="+classPackage+"."+className);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	private String getClassPackage(String tableName, String classPackage) {
		if(tableName.startsWith("t_u_")){
			classPackage=classPackage+".business";
		}else if(tableName.startsWith("t_log_")){
			classPackage=classPackage+".log";
		}else if(tableName.startsWith("t_s_")){
			classPackage=classPackage+".cfg";
		} 
		return classPackage;
	}


	private String getClassName(String tableName) {
		String tempTableName=tableName;
		if(tableName.startsWith("t_u_")){
			tempTableName=tempTableName.replace( "t_u_","")+"Data";
		}else if(tableName.startsWith("t_log_")){
			tempTableName=tempTableName.replace("t_","")+"Log";
		}else if(tableName.startsWith("t_s_")){
			tempTableName=tempTableName.replace("t_s_","")+"Cfg";
		} 
		return StringUtil.tableToClassName(tempTableName);
	}


	/**
	 * @param columnInfoBeans
	 * @return
	 */
	private  Set<String> getImportTypes(List<ColumnInfoBean> columnInfoBeans) {
		Set<String> importTypes=new HashSet<String>();
		for(ColumnInfoBean infoBean:columnInfoBeans){
			String importType=TypeUtils.getImportType(infoBean.getJavaType());
			if(!StringUtil.isEmpty(importType)){
				importTypes.add(importType);
			}
		}
		return importTypes;
	}

	/**
	 * @param columnInfoBeans
	 * @return
	 */
	private  String getPrimaryKeyType(List<ColumnInfoBean> columnInfoBeans) {
		 for(ColumnInfoBean columnInfoBean:columnInfoBeans){
			 if(columnInfoBean.isPrimarykey()){
				return TypeUtils.primitive2Wrapper(TypeUtils.primitive2Wrapper(columnInfoBean.getJavaType()));
			 }
		 }
		return "Object";
	}

	private  List<ColumnInfoBean> getColumnInfos(Connection connection, String tableName)
			throws SQLException {
		String sql = "select * from " + tableName + " where 1 <> 1";
		Set<String> keys = getPrimaryKeys(connection, tableName);
		PreparedStatement   ps = connection.prepareStatement(sql);
		ResultSet  rs = ps.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		List<ColumnInfoBean> columnInfoBeans = new ArrayList<ColumnInfoBean>();
		for (int i = 1; i <= columnCount; i++) {
			String columnName = md.getColumnName(i);
			int sqlType = md.getColumnType(i);
			String  columnComment="";
			boolean generatedKey=md.isAutoIncrement(i);
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet columnSet = databaseMetaData.getColumns(null, null, tableName, null);
			 
			while(columnSet.next()){
				if(columnSet.getString("COLUMN_NAME").equals(columnName)){
					 columnComment = columnSet.getString("REMARKS");
				}    
			}
			boolean primaryKey=keys.contains(columnName);
			ColumnInfoBean columnInfoBean = new ColumnInfoBean(columnName, columnComment, sqlType,generatedKey,primaryKey);
			columnInfoBeans.add(columnInfoBean);
		}
		
		return columnInfoBeans;
	}

	private  Set<String> getPrimaryKeys(Connection connection, String tableName) throws SQLException {
		Set<String> keys=new HashSet<String>();
		ResultSet primaryKeySet= connection.getMetaData().getPrimaryKeys(null, null, tableName);
		while(primaryKeySet.next()){
			keys.add(primaryKeySet.getString("COLUMN_NAME"));
		}
		return keys;
	}
 

	public static void main(String[] args) throws Exception{
		
		DBUtil dbUtil=new DBUtil();
		String nameStr=dbUtil.getProp().getProperty("table.name");
		String[] nameArr=nameStr.split(",");
		dbUtil.providers.delete();
		for(String tableName:nameArr){
			try{
				dbUtil.table2pojo(StringUtils.trim(tableName), "code", true,dbUtil.getProp().getProperty("package.name"));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
}
