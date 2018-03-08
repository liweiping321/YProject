/**
 * 
 */
package com.game.db.base.handle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

import com.game.db.base.util.DBCheckUtil;

/**
 * @author lip.li
 * @param <V>
 * @date 2017年1月5日
 */
public class MapHandler<K, V> implements IPropertyMapHandle<Map<K, V>> {

	private String splitRex;
	private String splitRex1;
	private Class<K> keyType;

	private Class<V> valueType;

	public MapHandler(Class<K> keyType, Class<V> valueType, String splitRex, String splitRex1) {
		this.keyType = keyType;
		this.valueType = valueType;
		this.splitRex = splitRex;
		this.splitRex1 = splitRex1;
	}

	public MapHandler() {
	}

	@Override
	public Map<K, V> get(ResultSet rs, String columnName) throws SQLException {
		Map<K, V> map = new HashMap<K, V>();
		String value = StringUtils.trim(rs.getString(columnName));
		if (!StringUtils.isEmpty(value)) {
			String[] mapValueArray = StringUtils.split(value, splitRex);
			;
			for (String mapValueItem : mapValueArray) {
				String[] items = StringUtils.split(mapValueItem, splitRex1);
				if (items.length != 2) {
					throw new SQLException(
							"columnName [" + columnName + "] value [" + value + "] parse format error !");
				}
				K k = keyType.cast(ConvertUtils.convert(items[0], keyType));
				V v = valueType.cast(ConvertUtils.convert(items[1], valueType));
				map.put(k, v);
			}

		}
		return map;
	}

	@Override
	public void set(PreparedStatement ps, int index, Map<K, V> values) throws SQLException {
		StringBuilder builder = new StringBuilder();
		if (values != null && values.size() > 0) {
			for (Map.Entry<K, V> entry : values.entrySet()) {

				DBCheckUtil.checkStringSplit(keyType, entry.getKey(), splitRex1, splitRex);
				DBCheckUtil.checkStringSplit(valueType, entry.getValue(), splitRex1, splitRex);

				builder.append(entry.getKey()).append(splitRex1).append(entry.getValue()).append(splitRex);
			}
			builder.deleteCharAt(builder.length() - 1);
		}
		ps.setString(index, builder.toString());
	}

}
