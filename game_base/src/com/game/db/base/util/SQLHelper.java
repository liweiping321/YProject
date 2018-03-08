
package com.game.db.base.util;

import java.util.Collection;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 */
public class SQLHelper {

	/**
	 * 根据表名和列名称得到插入语句
	 * 
	 * @param table
	 * @param names
	 * @return
	 */
	public static StringBuilder getInsertSQL(String table, Collection<String> names) {
		StringBuilder sql = new StringBuilder("INSERT INTO `").append(table).append("` (");
		for (String name : names) {
			sql.append("`").append(name + "`,");
		}
		sql.setLength(sql.length() - 1);
		sql.append(") VALUES (");
		for (String name : names) {
			sql.append("?,");
		}
		sql.setLength(sql.length() - 1);
		sql.append(")");
		return sql;
	}

	/**
	 * 根据表名和列名称得到更新语句
	 * 
	 * @param table
	 * @param names
	 * @return
	 */
	public static StringBuilder getUpdateSQL(String tableName, Collection<String> names) {
		StringBuilder sql = new StringBuilder("UPDATE `").append(tableName).append("` SET  ");
		for (String name : names) {
			sql.append("`").append(name + "`=?,");
		}
		sql.setLength(sql.length() - 1);
		sql.append(" WHERE ");

		return sql;
	}

	public static StringBuilder getDeleteSQL(String tableName) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(tableName).append(" WHERE ");
		return sql;
	}

	/**
	 * @param tableName
	 * @param names
	 * @return
	 */
	public static StringBuilder getSelectSQL(String tableName, Collection<String> names) {
		StringBuilder sql = new StringBuilder();
		for (String name : names) {
			sql.append("`").append(name).append("`,");
		}
		sql.setLength(sql.length() - 1);
		sql.append(" FROM `").append(tableName).append("`");
		sql.append(" WHERE 1 =1 ");
		return sql;

	}

	/**
	 * @param tableName
	 * @return
	 */
	public static StringBuilder getCountSQL(String tableName) {
		StringBuilder sql = new StringBuilder("SELECT COUNT(1) from `").append(tableName).append("` WHERE 1=1 ");
		return sql;
	}

}
