/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.game.db.base.util.DBCheckUtil;

/**
 * @author lip.li
 * @param <V>
 * @date 2017年1月5日
 */
public class SetHandler<V> implements IPropertyMapHandle<Set<V>> {

	private String splitRex;

	private Class<V> type;

	public SetHandler(Class<V> type, String splitRex) {
		this.type = type;
		this.splitRex = splitRex;
	}

	public SetHandler() {
	}

	@Override
	public Set<V> get(ResultSet rs, String columnName) throws SQLException {
		String value = StringUtils.trim(rs.getString(columnName));
		Set<V> valueItems = new HashSet<V>();
		if (!StringUtils.isEmpty(value)) {
			String[] itemValueStrs = StringUtils.split(value, splitRex);

			for (String itemValueStr : itemValueStrs) {
				V itemValue = type.cast(ConvertUtils.convert(itemValueStr, type));
				valueItems.add(itemValue);
			}

		}

		return valueItems;
	}

	@Override
	public void set(PreparedStatement ps, int index, Set<V> values) throws SQLException {
		StringBuilder builder = new StringBuilder();
		if (CollectionUtils.isNotEmpty(values)) {
			for (V value : values) {

				DBCheckUtil.checkStringSplit(type, value, splitRex);

				builder.append(value).append(splitRex);
			}
			builder.deleteCharAt(builder.length() - 1);
		}
		ps.setString(index, builder.toString());
	}

}
