/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.game.db.base.util.DBCheckUtil;

/**
 * List集合属性处理器
 * 
 * @author lip.li
 * @param <V>
 * @date 2017年1月5日
 */
public class ListHandler<V> implements IPropertyMapHandle<List<V>> {

	private String splitRex;

	private Class<V> type;

	public ListHandler(Class<V> type, String splitRex) {
		this.type = type;
		this.splitRex = splitRex;
	}

	@SuppressWarnings("unchecked")
	public ListHandler() {
	}

	@Override
	public List<V> get(ResultSet rs, String columnName) throws SQLException {
		List<V> valueItems = new LinkedList<V>();

		String value = StringUtils.trim(rs.getString(columnName));
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
	public void set(PreparedStatement ps, int index, List<V> values) throws SQLException {
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
