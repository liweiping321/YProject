/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class DateHandler implements IPropertyMapHandle<Date> {

	@Override
	public Date get(ResultSet rs, String columnName) throws SQLException {

		Timestamp timestamp = rs.getTimestamp(columnName);
		if (timestamp == null) {
			Calendar time = Calendar.getInstance();
			time.set(2000, 1, 1, 0, 0, 0);
			timestamp = new Timestamp(time.getTimeInMillis());
		}
		Date date = new Date(timestamp.getTime());
		return date;
	}

	@Override
	public void set(PreparedStatement ps, int index, Date value) throws SQLException {
		ps.setTimestamp(index, new Timestamp(((Date) value).getTime()));
	}
}
