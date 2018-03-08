
package com.game.db.base;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 * @param <PK>
 * @param <V>
 */
public abstract class AbstractEntityBean<PK, V>  implements IEntityBean<PK> {
	@JSONField(serialize=false)
	public String getPrimaryKeyName() {
		EntityType<V> type = EntityType.getType(this.getClass());
		return type.getKeyName();
	}
	@JSONField(serialize=false)
	public String getTableName() {
		EntityType<V> type = EntityType.getType(this.getClass());

		return type.getTableName();
	}

	@SuppressWarnings("unchecked")
	@JSONField(serialize=false)
	public PK getPrimaryKeyValue() {
		EntityType<V> type = EntityType.getType(this.getClass());
		Property<?> keyProperty = type.getKeyProperty();
		if (keyProperty != null) {
			return (PK) keyProperty.readValue(this);
		}
		return null;
	}

	public boolean equals(boolean value1, boolean value2) {
		return value1 == value2;
	}

	public boolean equals(byte value1, byte value2) {
		return value1 == value2;
	}

	public boolean equals(short value1, short value2) {
		return value1 == value2;
	}

	public boolean equals(char value1, char value2) {
		return value1 == value2;
	}

	public boolean equals(int value1, int value2) {
		return value1 == value2;
	}

	public boolean equals(long value1, long value2) {
		return value1 == value2;
	}

	public boolean equals(float value1, float value2) {
		return value1 == value2;
	}

	public boolean equals(double value1, double value2) {
		return value1 == value2;
	}

	public boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}

	public boolean equals(Object obj1, Object obj2) {
		return ObjectUtils.equals(obj1, obj2);
	}
    @Override
    public String toString() {
    	 return JSON.toJSONString(this);
    }
}
