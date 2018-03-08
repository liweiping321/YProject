/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java -db 属性映射处理器
 * 
 * @author lip.li
 * @date 2017年1月5日
 */
public interface IPropertyMapHandle<V> {
	/**
	 * 从结果集获取数据
	 * 
	 * @param rs
	 *            结果集
	 */
	V get(ResultSet rs, String columnName) throws SQLException;

	/**
	 * 数据预设置
	 * 
	 * @param ps
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	void set(PreparedStatement ps, int index, V value) throws SQLException;
}
