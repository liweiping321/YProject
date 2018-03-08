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
public class IntegerHandler implements IPropertyMapHandle<Integer> {

	@Override
	public Integer get(ResultSet rs, String columnName) throws SQLException {

		return rs.getInt(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Integer value) throws SQLException {
		ps.setInt(index, value);
	}

}
