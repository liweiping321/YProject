package com.game.generate.csv;

import com.game.generate.dao.MyProperties;
import com.game.utils.LogUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author jianpeng.zhang
 * @since 2017/3/1.
 */
public class MySqlUtil {
	private static final Logger LOG =LogManager.getLogger(CSVReader.class);
	private static String varcharSql;

	private static HashSet<String> keywordSet = new HashSet<>();


	static {
		try {
			MyProperties prop = new MyProperties("config/csv_reader_config.properties");
			MyProperties properties = new MyProperties("config/mysql_keyword.properties");
			String[] keywords = properties.getProperty("keyword").split(",");
			Collections.addAll(keywordSet, keywords);

			varcharSql = "` varchar(" + prop.getProperty("maxLength") + ") NOT NULL ";
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * 传入来的 columnNames，columnTypes， columnComments
	 * 用逗号做分割符。而且要一一对应。所有传入的值不能为null。
	 *
	 * @param tableName
	 *            表名
	 * @param columnNames
	 *            列名
	 * @param columnTypes
	 *            列的类型
	 * @param columnComments
	 *            列的注释
	 */
	public static void createTable(String tableName, String[] columnNames, String[] columnTypes,
			String[] columnComments, File outFile, boolean isExecute) throws Exception {
		LogUtils.debug("开始创建表格，" + tableName);
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < columnNames.length; i++) {

			if (keywordSet.contains(columnNames[i].toUpperCase()))
			{
				LogUtils.warn("warming：表 " + tableName + " 的列含有关键字：" + columnNames[i]);
			}

			sb.append("`")
					.append(columnNames[i].length() > 0 ? columnNames[i].substring(0, 1).toUpperCase() : columnNames[i])
					.append(columnNames[i].length() > 1 ? columnNames[i].substring(1) : "")
					.append(getRelationSqlType(columnTypes[i])).append(" COMMENT '").append((columnComments[i]))
					.append("',");
		}
		sb.append(" PRIMARY KEY (").append(columnNames[0]).append(") ");

		String createTableSql = "create TABLE %s(%s) ENGINE=InnoDB CHARACTER set utf8;\n";
		String executeSql = String.format(createTableSql, tableName, sb.toString());
		FileUtils.write(outFile, executeSql, "utf-8", true);
		LogUtils.debug("生成建表语句完成 " + tableName);
		if (isExecute) {
			JdbcManager.getInstance().getConnect().prepareStatement(executeSql).execute();
			LogUtils.debug("创建表格成功 " + tableName);
		}
	}

	public static void dropTable(String tableName, File outFile, boolean isExecute) throws SQLException, IOException {
		String sql = "drop table if EXISTS " + tableName + ";\n";
		FileUtils.write(outFile, sql, "utf-8", true);
		if (isExecute) {
			JdbcManager.getInstance().getConnect().prepareStatement(sql).execute();
		}
	}

	public static void clearTableData(String tableName, File outFile, boolean isExecute)
			throws SQLException, IOException {
		String sql = "delete from " + tableName + ";\n";
		FileUtils.write(outFile, sql, "utf-8", true);
		if (isExecute) {
			JdbcManager.getInstance().getConnect().prepareStatement(sql).execute();
		}
	}

	public static void insertValue(String table, String columnKeys, String columnValues, File outFile,
			boolean isExecute) throws SQLException, IOException {
		String insertIntoTableSql = "INSERT INTO %s(%s) VALUES %s ;\n";
		String sql = String.format(insertIntoTableSql, table, columnKeys, columnValues);
		FileUtils.write(outFile, sql, "utf-8", true);
		LogUtils.debug("生成插入语句完成 " + table);
		if (isExecute) {
			JdbcManager.getInstance().getConnect().prepareStatement(sql).execute();
			LogUtils.debug("插入数据成功 " + table);
		}
	}

	public static String getTableName(String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append("t_s_");

		for (String word : fileName.replaceAll("[A-Z]", " $0").split(" ")) {
			if (!StringUtils.isEmpty(word)) {
				sb.append(word.toLowerCase()).append("_");
			}
		}
		sb.delete(sb.length() - 1, sb.length());

		return sb.toString();
	}

	private static String getRelationSqlType(String type) throws Exception {
		type = type.trim().toLowerCase();
		switch (type) {
		case "int":
			return "` int(11) NOT NULL ";
		case "bool":
			return "` tinyint(1) NOT NULL ";
		case "float":
			return "` decimal(11,2) NOT NULL ";
		case "date":
			return "` datetime NOT NULL ";
		case "string":
			return varcharSql;
		default:
			throw new Exception("数据库列没有该类型 type=" + type);
		}
	}

}
