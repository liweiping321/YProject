/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class TimeStampHandler implements IPropertyMapHandle<Timestamp> {

	@Override
	public Timestamp get(ResultSet rs, String columnName) throws SQLException {

		return rs.getTimestamp(columnName);

	}

	@Override
	public void set(PreparedStatement ps, int index, Timestamp value) throws SQLException {
		ps.setTimestamp(index, value);
	}

}
