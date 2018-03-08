/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class DefaultHandler implements IPropertyMapHandle<Object> {

	@Override
	public Object get(ResultSet rs, String columnName) throws SQLException {

		return rs.getObject(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Object value) throws SQLException {
		ps.setObject(index, value);
	}

}
