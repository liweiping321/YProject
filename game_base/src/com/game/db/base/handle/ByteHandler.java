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
public class ByteHandler implements IPropertyMapHandle<Byte> {

	@Override
	public Byte get(ResultSet rs, String columnName) throws SQLException {
		return rs.getByte(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Byte value) throws SQLException {
		ps.setByte(index, value);
	}

}
