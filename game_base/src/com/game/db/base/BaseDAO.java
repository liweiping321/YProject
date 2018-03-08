/**
 *All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.game.db.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.game.db.base.util.DBCheckUtil;
import com.game.db.base.util.JdbcUtil;
 
/**
 * 数据库DAO基础操作类
 * @author lip.li
 *
 * @param <PK>
 * @param <V>
 */
@SuppressWarnings("rawtypes")
public class BaseDAO<PK, V extends AbstractEntityBean<?, ?>> {

	private static final JdbcUtil jdbcUtil = JdbcUtil.getInstance();
	

	public static  final   BaseDAO ins=new BaseDAO<>();
	
	private   static DataSource dataSource=null;
	
	protected BaseDAO(){
	}
	
	/**
	 * 服务器启动的时候调用
	 */
	public static void register(DataSource dataSource){
		BaseDAO.dataSource=dataSource;
	}

	/**
	 * 无数据中插入一条新纪录
	 * 
	 * @param insertObj
	 *            插入的新对象
	 * @return 返回对象的主键
	 * @throws SQLException
	 */
	public void insert(final V insertObj) {

		final EntityType<V> pType = EntityType.getType(insertObj.getClass());
		String sql = pType.getInsertSql();
		Operate<V> op = new Operate<V>(sql, dataSource) {
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				pType.fillPreparedStatement(ps, insertObj, pType.getInsertPropertyNames());
			}
			 
			@Override
			public void generatedKey(int key) {
				pType.setGenerateKey(insertObj,key);
			}
		};
		try {
			jdbcUtil.insert(op);
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
 
	}

	/**
	 * 无数据中插入一条新纪录
	 * 
	 * @param insertObj
	 *            插入的新对象
	 * @return 返回对象的主键
	 * @throws SQLException
	 */
	public int batchInsert(List<V> insertObjs) {
		if(CollectionUtils.isEmpty(insertObjs))
		{
			return 0;
		}
		
		final EntityType<V> pType = EntityType.getType(insertObjs.get(0).getClass());
		String sql = pType.getInsertSql();
		Operate<V> op = new Operate<V>(sql, dataSource) {
			@Override
			public void fillBatchParam(PreparedStatement ps, V paramObject) throws SQLException {
				pType.fillPreparedStatement(ps, paramObject, pType.getInsertPropertyNames());
			}
		};
		int row=0;
		try {
			row=jdbcUtil.batchInsert(op,insertObjs);
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		return row;
	}

	/**
	 * 根据主键查找一个对象
	 * 
	 * @param clazz
	 *            对象的类型
	 * @param primaryKey
	 * @return 查找到的对象
	 * @throws SQLException
	 */
	public   V queryByPrimaryKey( Class<V> clazz, final PK primaryKey) {
		EntityType<V> pType = EntityType.getType(clazz);
		
		DBCheckUtil.checkPrimaryKey(pType);
		
		String sql = pType.getQueryByPrimaryKeySql();

		Operate<V> op = new Operate<V>(sql, dataSource,pType) {
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementByIndex(ps,  primaryKey,1);
			}
		};

		try {
			Object entry = jdbcUtil.queryUnique(op);
			if (entry != null) {
				V value= clazz.cast(entry);
				return value;
			}
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 条件查找一个对象(条件空，查找第一条记录)
	 * 
	 * @param clazz
	 *            对象的类型
	 * @param whereSql
	 *           (userid=? and itemId=?)查询条件必须带参数
	 * @return 查到的对象
	 * @throws SQLException
	 */
	public V queryOneByWhereSql(Class<V> clazz, String whereSql,final Object...params) {
		final EntityType<V> pType = EntityType.getType(clazz);

		final String sql = pType.getQueryOneByWhereSql(whereSql);

		Operate<V> op = new Operate<V>(sql, dataSource,pType){
			 
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};

		try {
			Object entry = jdbcUtil.queryUnique(op);
			if (entry != null) {
				V value= clazz.cast(entry);
				return value;
			}
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		return null;
	}
	
	public int queryCount(Class<V> clazz) {
		return queryCountByWhere(clazz, null);
	}
	/**
	 * 
	 * @param clazz 统计的类型
	 * @param whereSql (userid=? and itemId=?)查询条件必须带参数
	 * @param params 查询条件值
	 * @return
	 */
	public int queryCountByWhere(Class<V> clazz,String whereSql,final Object... params){
		EntityType<V> pType = EntityType.getType(clazz);
		
		String sql=pType.getQueryCountByWhereSql(whereSql);
		Operate<V> op = new Operate<V>(sql, dataSource){
			 
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};
	
		try {
			int value = jdbcUtil.queryInt(op);
			return value;
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
	}

	/**
	 * 查询总数
	 * @param sql(select count from table where id=? and userid=?)
	 * @param 参数 
	 */
	public int queryCountBySql(String sql,final Object... params){
		
		Operate<V> op = new Operate<V>(sql, dataSource){
		 
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};
		
		try {
			int value = jdbcUtil.queryInt(op);
			return value;
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
	}

	/**
	 * 条件查找多个对象(条件空查找所有的)
	 * 
	 * @param clazz
	 *            对象类型
	 * @param whereSql
	 *            查找条件
	 * @return 查找的对象的集合
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<V> queryListByWhereSql(Class<?> clazz, String whereSql,final Object... params) {
		EntityType<V> pType = EntityType.getType(clazz);

		String sql = pType.getQueryListByWhereSql(whereSql);

		Operate<V> op = new Operate<V>(sql, dataSource,pType){
			 
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};

		try {
			List<V> list = jdbcUtil.queryList(op);
			return list;
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
	}
	/**
	 * 查询表所有数据
	 * @param class1
	 * @return
	 */
	public List<?> queryList(Class<?> class1 ) {
		 return queryListByWhereSql(class1,null);
	}

	/**
	 * 保存对象到数据库中
	 * 
	 * @param updateObj
	 *            跟新的对象
	 * @return 保存结果（成功true,失败false）
	 * @throws SQLException
	 */
	public int updateByPrimaryKey(final V updateObj) {
		final EntityType<V> pType = EntityType.getType(updateObj.getClass());
		
		DBCheckUtil.checkPrimaryKey(pType);
		
		String updateSql = pType.getUpdateByPrimaryKeySql();
		 
		Operate<V> op = new Operate<V>(updateSql.toString(), dataSource) {
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				pType.fillPreparedStatement(ps, updateObj, pType.getUpdateAllPropertyNames());
				
				EntityType.fillPreparedStatementByIndex(ps, updateObj.getPrimaryKeyValue(), pType.getUpdateAllPropertyNames().length+1);
			}
		};

		int row;
		try {
			row = jdbcUtil.update(op);
		} catch (Exception e) {
			throw new DbException(op.getSql()+e.getMessage(),e);
		}
		return row;
	}
	
	/**
	 * 更新指定的属性值
	 * @param updateObj 
	 * @param propertyNames 要更新的数
	 * @return
	 */
	public int updatePartByPrimaryKey(final V updateObj,final String ... propertyNames) {
		if(ArrayUtils.isEmpty(propertyNames))
		{
			return 0;
		}
		
		final EntityType<V> pType = EntityType.getType(updateObj.getClass());
		
		DBCheckUtil.checkPrimaryKey(pType);
		
		String updateSql = pType.getUpdateByPrimaryKeySql(propertyNames);
		 
		Operate<V> op = new Operate<V>(updateSql.toString(), dataSource) {
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				pType.fillPreparedStatement(ps, updateObj, propertyNames);
				
				EntityType.fillPreparedStatementByIndex(ps, updateObj.getPrimaryKeyValue(),propertyNames.length+1);
			}
		};

		int row;
		try {
			row = jdbcUtil.update(op);
		} catch (Exception e) {
			throw new DbException(op.getSql()+e.getMessage(),e);
		}
		return row;
	}
	/**
	 * 主键作为查询条件批量更新
	 * @param updateObjs
	 * @return
	 */
	public int[] batchUpdateByPrimaryKey(List<V> updateObjs) {
	    if(CollectionUtils.isEmpty(updateObjs))
	    {
	    	return new int[0];
	    }
		final EntityType<V> pType = EntityType.getType(updateObjs.get(0).getClass());
		 
		DBCheckUtil.checkPrimaryKey(pType);
		
		String updateSql = pType.getUpdateByPrimaryKeySql();

		Operate<V> op = new Operate<V>(updateSql.toString(), dataSource) {
			@Override
			public void fillBatchParam(PreparedStatement ps, V paramObject) throws SQLException {
				pType.fillPreparedStatement(ps, paramObject, pType.getUpdateAllPropertyNames());
 
				EntityType.fillPreparedStatementByIndex(ps, paramObject.getPrimaryKeyValue(),
						pType.getUpdateAllPropertyNames().length + 1);
			}
		};

		try {
			int[] results = jdbcUtil.batchUpdate(op, updateObjs);
			return results;
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}

	}
	
	/**
	 * 主键作为查询条件批量更新部分属性
	 * @param updateObjs
	 * @return
	 */
	public int[] batchUpdatePartByPrimaryKey(List<V> updateObjs,final String ... propertyNames ) {
	    if(CollectionUtils.isEmpty(updateObjs)||ArrayUtils.isEmpty(propertyNames))
	    {
	    	return new int[0];
	    }
		final EntityType<V> pType = EntityType.getType(updateObjs.get(0).getClass());
		
		DBCheckUtil.checkPrimaryKey(pType);
		
		String updateSql = pType.getUpdateByPrimaryKeySql(propertyNames);

		Operate<V> op = new Operate<V>(updateSql.toString(), dataSource) {
			@Override
			public void fillBatchParam(PreparedStatement ps, V paramObject) throws SQLException {
				pType.fillPreparedStatement(ps, paramObject,propertyNames);
 
				EntityType.fillPreparedStatementByIndex(ps, paramObject.getPrimaryKeyValue(),
						propertyNames.length + 1);
			}
		};

		try {
			int[] results = jdbcUtil.batchUpdate(op, updateObjs);
			return results;
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}

	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param clazz
	 *            数据对应的类型
	 * @param keyValue
	 *            主键
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteByPrimaryKey(final Class<V> clazz, final PK primaryKey) {
		final EntityType<V> pType = EntityType.getType(clazz);
		
		DBCheckUtil.checkPrimaryKey(pType);
		
		String deleteSql = pType.getDeleteByPrimaryKeySql();
		Operate<V> op = new Operate<V>(deleteSql, dataSource) {

			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {

				EntityType.fillPreparedStatementByIndex(ps, primaryKey, 1);
			}
		};
		int row;
		try {
			row = jdbcUtil.update(op);
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		return row == 1;
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param deleteObj
	 *            要删除的对象
	 * @return 删除结果(成功true,失败false)
	 * @throws SQLException
	 */
	public boolean delete(final V deleteObj) {
		
		final EntityType<V> pType = EntityType.getType(deleteObj.getClass());
		
		DBCheckUtil.checkPrimaryKey(pType);
		
		String deleteSql = pType.getDeleteByPrimaryKeySql();
		Operate<V> op = new Operate<V>(deleteSql, dataSource) {
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementByIndex(ps, deleteObj.getPrimaryKeyValue(), 1);
			}
		};
		int row;
		try {
			row = jdbcUtil.update(op);
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		return row == 1;
	}

	
	/**
	 * 条件删除数据
	 * 
	 * @param clazz
	 *            数据的类型
	 * @param whereSql
	 *            删除数据的条件
	 * @return 成功删除数据的条数
	 * @throws SQLException
	 */
	public int deleteByWhereSql(Class<V> clazz, String whereSql,final Object ...params) {
		if(StringUtils.isEmpty(whereSql)){
			return 0;
		}
	    EntityType<V> pType = EntityType.getType(clazz);
        StringBuilder deleteSql=new StringBuilder(pType.getDeleteSql()).append(whereSql);
        Operate<V> op=new Operate<V>(deleteSql.toString(),dataSource){
        	public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
        };
        
        int row;
		try {
			row = jdbcUtil.update(op);
		} catch (Exception e) {
			throw new DbException(op.getSql()+e.getMessage(),e);
		}
    	return row;
	}
	/**
	 * 查询单个对象
	 * @param clazz
	 * @param sql
	 * @return
	 */
	public V queryOneBySql(final Class<V> clazz, String sql,final Object...params) {
		
		final EntityType<V> entityType= EntityType.getType(clazz);
		Operate<V> op=new Operate<V>(sql, dataSource,entityType){
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};
		
		try {
			Object entry = jdbcUtil.queryUnique(op);
			if (entry != null) {
				V value= clazz.cast(entry);
				return  value;
			}
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		
		return null;
		 
	}
	/**
	 * 查询列表
	 * @param clazz
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<V> queryListBySql(final Class<V> clazz, String sql,final Object ...params) {
		final EntityType<V> pType = EntityType.getType(clazz);
		Operate<V> op = new Operate<V>(sql, dataSource,pType){
			@Override
			public void fillParam(PreparedStatement ps) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};
		try {
			List<V> list =jdbcUtil.queryList(op);
			return list;
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
	}

	/**
	 * 执行更新
	 * @param sql
	 * @param params参数 （支持Map,Set,List,Bean）
	 * @return
	 */
	public int executeUpdateSql(String sql, final Object... params) {
		if(ArrayUtils.isEmpty(params))
		{
			return 0 ;
		}
		Operate<V> op = new Operate<V>(sql, dataSource) {

			public void fillParam(PreparedStatement ps) throws SQLException {

				EntityType.fillPreparedStatementValues(ps, params);
			}
		};

		int row;
		try {
			row = jdbcUtil.update(op);
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		return row;

	}

	/**
	 * 批量执行更新
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int[] executeBatchUpdateSql(String sql, List<Object[]> listParams) {
		if(CollectionUtils.isEmpty(listParams))
		{
			return null ;
		}
		Operate<Object[]> op = new Operate<Object[]>(sql, dataSource) {
			@Override
			public void fillBatchParam(PreparedStatement ps, Object[] params  ) throws SQLException {
				EntityType.fillPreparedStatementValues(ps, params);
			}
		};

		try {
			return  jdbcUtil.batchUpdate(op, listParams);
		} catch (Exception e) {
			throw new DbException(op.getSql() + e.getMessage(), e);
		}
		 
	}
 
}
