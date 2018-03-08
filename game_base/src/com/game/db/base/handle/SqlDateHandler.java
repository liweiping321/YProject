/**
 * 
 */
package com.game.db.base.handle;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class SqlDateHandler implements IPropertyMapHandle<Date> {

	@Override
	public Date get(ResultSet rs, String columnName) throws SQLException {

		return rs.getDate(columnName);

	}

	@Override
	public void set(PreparedStatement ps, int index, Date value) throws SQLException {
		ps.setDate(index, value);
	}

}
