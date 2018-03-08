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
public class LongHandler implements IPropertyMapHandle<Long> {

	@Override
	public Long get(ResultSet rs, String columnName) throws SQLException {

		return rs.getLong(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Long value) throws SQLException {
		ps.setLong(index, value);
	}

}
