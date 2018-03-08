package com.game.db.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.db.base.handle.DefaultHandler;
import com.game.db.base.handle.HandlerManger;
import com.game.db.base.handle.IPropertyMapHandle;
import com.game.db.base.handle.ListHandler;
import com.game.db.base.handle.MapHandler;
import com.game.db.base.handle.SetHandler;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 */
@SuppressWarnings("rawtypes")
public class Property<V> {

	private static final Logger logger =LogManager.getLogger(Property.class);

	private String propertyName;
	private String columnName;
	private EntityType type;
	private boolean persistence;

	private Field field;

	private Class<V> handleClazz;

	private IPropertyMapHandle<V> handle;

	private String splitRex;
	private String splitRex1;

	public Class<V> getHandleClazz() {
		return handleClazz;
	}

	public void setHandleClazz(Class<V> handleClazz) {
		this.handleClazz = handleClazz;
	}

	public boolean isPersistence() {
		return persistence;
	}

	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getSplitRex() {
		return splitRex;
	}

	public void setSplitRex(String splitRex) {
		this.splitRex = splitRex;
	}

	public String getSplitRex1() {
		return splitRex1;
	}

	public void setSplitRex1(String splitRex1) {
		this.splitRex1 = splitRex1;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public Object readValue(Object bean) {
		try {
			return field.get(bean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void writeValue(Object bean, Object value) {
		try {
			field.set(bean, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void fillPreparedStatement(PreparedStatement ps, V value, int index) throws SQLException {

		if (value == null) {
			int sqlType = Types.NULL;
			ps.setNull(index, sqlType);
		} else {
			handle.set(ps, index, value);

		}
	}

	public void parseResultSet(Object bean, ResultSet rs) throws SQLException {
		V value = handle.get(rs, columnName);
		writeValue(bean, value);

	}

	@SuppressWarnings("unchecked")
	public void initPropertyHandler(Field field) {
		Class<?> fieldType = field.getType();
		Type type = field.getGenericType();
		Type[] actualTypes = null;
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			actualTypes = pType.getActualTypeArguments();
		}
		// 自定义处理器
		if (handleClazz != DefaultHandler.class) {
			if (fieldType == List.class || fieldType == Set.class) {
				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().createHandler(handleClazz,
						(Class) actualTypes[0]);
			} else if (fieldType == Map.class) {

				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().createHandler(handleClazz,
						(Class) actualTypes[0], (Class) actualTypes[1]);
			} else {
				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().createHandler(handleClazz, fieldType);
			}
		} else {
			// 默认处理器
			if (fieldType == List.class) {
				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().createHandler(ListHandler.class,
						(Class) actualTypes[0], splitRex);
			} else if (fieldType == Set.class) {
				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().createHandler(SetHandler.class,
						(Class) actualTypes[0], splitRex);
			} else if (fieldType == Map.class) {
				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().createHandler(MapHandler.class,
						(Class) actualTypes[0], (Class) actualTypes[1], splitRex, splitRex1);
			} else {
				handle = (IPropertyMapHandle<V>) HandlerManger.getInstance().getHander(fieldType);
			}
		}

		this.field = field;
		this.field.setAccessible(true);
	}
}
