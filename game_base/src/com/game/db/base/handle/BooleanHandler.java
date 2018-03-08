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
public class BooleanHandler implements IPropertyMapHandle<Boolean> {

	@Override
	public Boolean get(ResultSet rs, String columnName) throws SQLException {
		return rs.getBoolean(columnName);
	}

	@Override
	public void set(PreparedStatement ps, int index, Boolean value) throws SQLException {
		ps.setBoolean(index, value);
	}

}
