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
public class ShortHandler implements IPropertyMapHandle<Short> {

	@Override
	public Short get(ResultSet rs, String columnName) throws SQLException {
		return rs.getShort(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Short value) throws SQLException {
		ps.setShort(index, value);
	}

}
