
package com.game.db.base;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;
import com.game.db.base.handle.HandlerManger;
import com.game.db.base.handle.IPropertyMapHandle;
import com.game.db.base.util.SQLHelper;

import java.util.LinkedHashMap;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 * @param <V>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntityType<V> {
	private static final Logger LOG = LogManager.getLogger(EntityType.class);
	private Class<V> rawClass;
	private String tableName;
	private String keyName;

	private String selectAllSql;
	private String selectCountSql;
	private String deleteSql;

	private final Map<String, Property> propertyMap = new LinkedHashMap();

	private String[] updateAllPropertyNames = {};

	private String[] insertPropertyNames = {};

	private final List<Property> persistenceProperties = new ArrayList<Property>();

	private final Map<String, Property> columnMap = new LinkedHashMap();

	private final static Map<Class, EntityType> persistenceTypeMap = new ConcurrentHashMap<Class, EntityType>();

	private Map<String, String> sqlCache = new ConcurrentHashMap<String, String>();
	/** 自动生成key熟悉，一个表只有一个 */
	private Property generatedKeyProperty;

	private Property keyProperty;

	private boolean compositeKey;

	public static <V> EntityType<V> getType(Class clazz) {
		EntityType<V> persistenceType = persistenceTypeMap.get(clazz);
		if (persistenceType == null) {
			synchronized (clazz) {
				persistenceType = persistenceTypeMap.get(clazz);
				if (persistenceType == null) {
					persistenceType = new EntityType<V>();
					persistenceType.initType(clazz);
					persistenceType.initProperties();
					persistenceType.initSql();
					persistenceTypeMap.put(clazz, persistenceType);
				}
			}
		}
		return persistenceType;
	}

	public String[] getUpdateAllPropertyNames() {
		return updateAllPropertyNames;
	}

	public Map<String, Property> getPropertyMap() {
		return propertyMap;
	}

	public Map<String, Property> getColumnMap() {
		return columnMap;
	}

	public void setRawClass(Class<V> rawClass) {
		this.rawClass = rawClass;
	}

	/**
	 * @return the rawClass
	 */
	public Class<V> getRawClass() {
		return rawClass;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getSelectAllSql() {
		return selectAllSql;
	}

	public void setSelectAllSql(String selectAllSql) {
		this.selectAllSql = selectAllSql;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	public Property getProperty(String propertyName) {
		return propertyMap.get(propertyName);
	}

	public void setPropertyValue(Object bean, String propertyName, Object value) {
		Property property = propertyMap.get(propertyName);
		property.writeValue(bean, value);
	}

	public StringBuilder getUpdateSql(String[] propertyNames) {
		List<String> columns = new ArrayList<String>();
		for (String propertyName : propertyNames) {
			Property property = getProperty(propertyName);
			if (property == null) {
				LOG.error(this.rawClass.getName() + "不存在的字段名称:" + propertyName);
			}
			if (property.isPersistence()) {
				columns.add(property.getColumnName());
			}
		}
		return SQLHelper.getUpdateSQL(tableName, columns);
	}

	private void initType(Class clazz) {
		rawClass = clazz;
		EntityMap entityAnno = (EntityMap) clazz.getAnnotation(EntityMap.class);
		if (entityAnno == null) {
			LOG.info("not persistence entity");
			// throw new RuntimeException("not persistence entity");
		} else {
			tableName = entityAnno.table();
			if (StringUtils.isEmpty(tableName)) {
				LOG.info(clazz.getName() + " annotation not configure table");
				// throw new RuntimeException(clazz.getName() + " annotation not
				// configure table");
			}

		}

	}

	private void initProperties() {
		Field[] fields = rawClass.getDeclaredFields();
		for (Field field : fields) {
			Property property = new Property();
			String name = field.getName();
			property.setPropertyName(name);

			try {
				PropertyMap fieldAnno = rawClass.getDeclaredField(name).getAnnotation(PropertyMap.class);
				if (fieldAnno != null) {
					String columnName = fieldAnno.column();
					property.setHandleClazz(fieldAnno.handler());
					property.setSplitRex(fieldAnno.splitRex());
					property.setSplitRex1(fieldAnno.splitRex1());
					property.setColumnName(columnName.equals("") ? name : columnName);
					propertyMap.put(name, property);
					columnMap.put(property.getColumnName(), property);
					property.setPersistence(true);
					if (fieldAnno.generatedKey()) {
						if (generatedKeyProperty != null) {
							throw new DbException(rawClass.getName() + " can't set multi-generatedKey column");
						}
						generatedKeyProperty = property;
					}

					if (fieldAnno.primarykey()) {
						if (keyProperty != null) {
							compositeKey = true;
						}
						keyName = property.getColumnName();
						keyProperty = property;
					}

					property.initPropertyHandler(field);
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		List<String> tempUpdateProps = new ArrayList<String>();
		List<String> tempInsertProps = new ArrayList<String>();
		// 初始化持久化属性
		Collection<Property> properties = propertyMap.values();
		for (Property prop : properties) {
			if (prop.isPersistence()) {
				persistenceProperties.add(prop);

				if (!prop.getPropertyName().equals(keyName) && generatedKeyProperty != prop) {
					tempUpdateProps.add(prop.getPropertyName());
				}

				if (generatedKeyProperty != prop) {
					tempInsertProps.add(prop.getPropertyName());
				}
			}

		}

		updateAllPropertyNames = tempUpdateProps.toArray(new String[tempUpdateProps.size()]);
		insertPropertyNames = tempInsertProps.toArray(new String[tempInsertProps.size()]);

	}

	/**
	 * @return the compositeKey
	 */
	public boolean isCompositeKey() {
		return compositeKey;
	}

	/**
	 * @param compositeKey
	 *            the compositeKey to set
	 */
	public void setCompositeKey(boolean compositeKey) {
		this.compositeKey = compositeKey;
	}

	public Property getKeyProperty() {
		return keyProperty;
	}

	public void setKeyProperty(Property keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String[] getInsertPropertyNames() {
		return insertPropertyNames;
	}

	private void initSql() {
		List<String> columns = new ArrayList();
		Collection<Property> properties = propertyMap.values();
		for (Property persistenceProperty : properties) {
			if (persistenceProperty.isPersistence()) {
				columns.add(persistenceProperty.getColumnName());
			}
		}
		selectAllSql = SQLHelper.getSelectSQL(tableName, columns).toString();
		deleteSql = SQLHelper.getDeleteSQL(tableName).toString();
		selectCountSql = SQLHelper.getCountSQL(tableName).toString();
	}

	private List<Property> getPersistenceProperties() {
		return persistenceProperties;
	}

	/**
	 *
	 * @param ps
	 * @param values
	 * @throws SQLException
	 */
	public static void fillPreparedStatementValues(PreparedStatement ps, Object... values) throws SQLException {
		int index = 1;
		for (Object value : values) {
			IPropertyMapHandle handle = HandlerManger.getInstance().getHander(value.getClass());
			if (handle != null) {
				handle.set(ps, index, value);
			}

			index++;
		}
	}

	/**
	 *
	 * @param ps
	 * @param values
	 * @throws SQLException
	 */
	public static void fillPreparedStatementByIndex(PreparedStatement ps, Object value, int index) throws SQLException {
		IPropertyMapHandle handle = HandlerManger.getInstance().getHander(value.getClass());
		if (handle != null) {
			handle.set(ps, index, value);
		}
	}

	public void fillPreparedStatement(PreparedStatement ps, Object bean, String... propertyNames) throws SQLException {
		int index = 1;
		for (String propertyName : propertyNames) {
			Property property = getProperty(propertyName);
			if (property == null) {
				LOG.error("数据名为空:" + propertyName);
				continue;
			}

			if (property.isPersistence()) {
				Object value = property.readValue(bean);
				property.fillPreparedStatement(ps, value, index);
				index++;
			}
		}
	}

	public V parseResultSet(ResultSet rs) throws SQLException {
		V bean = null;
		try {
			bean = rawClass.newInstance();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		List<Property> properties = getPersistenceProperties();
		for (Property persistenceProperty : properties) {
			persistenceProperty.parseResultSet(bean, rs);
		}

		return bean;
	}

	public String getQueryByPrimaryKeySql() {
		String sql = sqlCache.get("QueryByPrimaryKey");
		if (sql == null) {
			sql = new StringBuilder("SELECT  ").append(selectAllSql).append("AND `").append(this.getKeyName())
					.append("`= ? limit 0,1").toString();
			sqlCache.put("QueryByPrimaryKey", sql);
		}

		return sql;
	}

	public String getQueryOneByWhereSql(String whereSql) {
		String sql = sqlCache.get("queryOneByWhere");
		if (sql == null) {
			sql = new StringBuilder("SELECT    ").append(selectAllSql).append(" AND ").toString();
			sqlCache.put("queryOneByWhere", sql);
		}
		if (StringUtils.isEmpty(whereSql)) {
			return sql.replaceAll("AND", "");
		}
		return sql + whereSql+"limit 0,1";

	}

	public String getQueryListByWhereSql(String whereSql) {
		String sql = sqlCache.get("queryListByWhere");
		if (sql == null) {
			sql = new StringBuilder("SELECT ").append(selectAllSql).append(" AND ").toString();
			sqlCache.put("queryListByWhere", sql);
		}
		if (StringUtils.isEmpty(whereSql)) {
			return sql.replaceAll("AND", "");
		}
		return sql + whereSql;
	}

	public String getDeleteByPrimaryKeySql() {
		String sql = sqlCache.get("deleteByPrimaryKey");
		if (sql == null) {
			sql = new StringBuilder(deleteSql).append(keyName).append("= ? ").toString();

			sqlCache.put("deleteByPrimaryKey", sql);
		}

		return sql;

	}

	/**
	 * @return
	 */
	public String getUpdateByPrimaryKeySql() {
		String sql = sqlCache.get("updateByPrimaryKey");
		if (sql == null) {
			sql = new StringBuilder(getUpdateSql(updateAllPropertyNames)).append(keyName).append("= ? ").toString();

			sqlCache.put("updateByPrimaryKey", sql);
		}
		return sql;
	}

	/**
	 * @return
	 */
	public String getUpdateByPrimaryKeySql(String... propertyNames) {
		String cacheKey = "updateByPrimaryKey-" + Arrays.toString(propertyNames);
		String sql = sqlCache.get(cacheKey);
		if (sql == null) {
			sql = new StringBuilder(getUpdateSql(propertyNames)).append(keyName).append("= ? ").toString();

			sqlCache.put(cacheKey, sql);
		}
		return sql;
	}

	public String getInsertSql() {
		String sql = sqlCache.get("insert");
		if (sql == null) {
			List<String> columns = new ArrayList<String>();
			for (String propertyName : insertPropertyNames) {
				Property property = getProperty(propertyName);
				if (property == null) {
					LOG.error(this.rawClass.getName() + "不存在的字段名称:" + propertyName);
				}
				if (property.isPersistence()) {
					columns.add(property.getColumnName());
				}
			}
			sql = SQLHelper.getInsertSQL(tableName, columns).toString();
			sqlCache.put("insert", sql);
		}

		return sql;
	}

	public void setGenerateKey(V insertObj, int generateKey) {
		if (generatedKeyProperty != null) {
			generatedKeyProperty.writeValue(insertObj, generateKey);
		}

	}

	/**
	 * @param whereSql
	 * @return
	 */
	public String getQueryCountByWhereSql(String whereSql) {
		if (StringUtils.isEmpty(whereSql)) {
			return selectCountSql;
		}

		String cacheKey = "QueryCountByWhere-" + whereSql;
		String sql = sqlCache.get(cacheKey);
		if (sql == null) {
			sql = SQLHelper.getCountSQL(tableName).append(" AND ").append(whereSql).toString();
			sqlCache.put(cacheKey, sql);
		}

		return sql;
	}

}
