/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class StringHandler implements IPropertyMapHandle<String> {

	@Override
	public String get(ResultSet rs, String columnName) throws SQLException {

		String value = rs.getString(columnName);
		value = StringUtils.trim(value);
		return value;
	}

	@Override
	public void set(PreparedStatement ps, int index, String value) throws SQLException {
		ps.setString(index, value);
	}

}
