/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class TimeHandler implements IPropertyMapHandle<Time> {

	@Override
	public Time get(ResultSet rs, String columnName) throws SQLException {

		return rs.getTime(columnName);

	}

	@Override
	public void set(PreparedStatement ps, int index, Time value) throws SQLException {
		ps.setTime(index, value);
	}

}
