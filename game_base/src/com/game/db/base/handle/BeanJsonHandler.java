/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;

/**
 * @author lip.li
 * @date 2017年1月5日
 */
public class BeanJsonHandler<V> implements IPropertyMapHandle<V> {

	private Class<V> type;

	public BeanJsonHandler(Class<V> type) {
		this.type = type;
	}

	@Override
	public V get(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		if (!StringUtils.isEmpty(value)) {
			V bean = JSON.parseObject(value, type);
			return bean;
		}
		return null;
	}

	@Override
	public void set(PreparedStatement ps, int index, V value) throws SQLException {
		String valueString = "";
		if (value != null) {
			valueString = JSON.toJSONString(value);
		}

		ps.setString(index, valueString);
	}

}
