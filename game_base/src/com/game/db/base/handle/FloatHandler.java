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
public class FloatHandler implements IPropertyMapHandle<Float> {

	@Override
	public Float get(ResultSet rs, String columnName) throws SQLException {

		return rs.getFloat(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Float value) throws SQLException {
		ps.setFloat(index, value);
	}

}
