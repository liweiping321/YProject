/**
 * 
 */
package com.game.db.base.util;

import java.sql.SQLException;

import com.game.db.base.DbException;
import com.game.db.base.EntityType;

/**
 * @author lip.li
 * @date 2017年1月6日
 */
public class DBCheckUtil {

	/**
	 * 检查对象是否是字符串并包含字符分割符
	 * 
	 * @param clazz
	 * @param value
	 *            简单对象
	 * @param splitRex
	 * @throws SQLException
	 */
	public static void checkStringSplit(Class<?> clazz, final Object value, final String... splitRexs)
			throws SQLException {

		if (clazz == String.class) {
			String valueString = String.class.cast(value);
			for (String splitRex : splitRexs) {
				if (valueString.contains(splitRex)) {
					throw new SQLException("字符串不能带有 字符分割符 [" + splitRex + "]");
				}
			}

		}
	}

	/**
	 * @param deleteObj
	 * @param pType
	 */
	public static void checkPrimaryKey(EntityType<?> pType) {
		if (pType.getKeyProperty() == null) {
			throw new DbException("primaryKey not exits :clazz=" + pType.getRawClass().getName());
		}
		if (pType.isCompositeKey()) {
			throw new DbException("not Support CompositeKey :clazz=" + pType.getRawClass().getName());
		}
	}

}
