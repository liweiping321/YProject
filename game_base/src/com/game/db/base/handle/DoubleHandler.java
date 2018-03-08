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
public class DoubleHandler implements IPropertyMapHandle<Double> {

	@Override
	public Double get(ResultSet rs, String columnName) throws SQLException {

		return rs.getDouble(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Double value) throws SQLException {
		ps.setDouble(index, value);
	}

}
