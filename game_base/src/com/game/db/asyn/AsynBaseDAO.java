package com.game.db.asyn;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.ArrayUtils;

import com.game.db.base.AbstractEntityBean;
import com.game.db.base.BaseDAO;
import com.game.executor.GameTask;

/**
 * 数据异步入库操作
 * @author lip.li
 */
public class AsynBaseDAO<PK, V  extends AbstractEntityBean<?, ?>> extends BaseDAO<PK, V> {
	
	public static final AsynBaseDAO<?, ?> ins=new AsynBaseDAO<>();

	private static final AsynDbQueueManager QUEUE_MANAGER=AsynDbQueueManager.getInstance();
	
	public void asynInsert(AsynObj player, final V insertObj) {
		Objects.requireNonNull(insertObj);
		
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				insert(insertObj);
			}
		});
		
	
	}
	public void asynInsert(final V insertObj) {
		
		Objects.requireNonNull(insertObj);
		
		QUEUE_MANAGER.addTask(insertObj.getClass().getName(), new GameTask() {
			public void doRun() {
				insert(insertObj);
			}
		});
	}

	public void asynDelete(AsynObj player, final V deleteObj) {
	 
		Objects.requireNonNull(deleteObj);
		
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				delete(deleteObj);
			}
		});
	}
	
	public void asynDelete(final V deleteObj) {
	 
		Objects.requireNonNull(deleteObj);
		
		QUEUE_MANAGER.addTask(deleteObj.getClass().getName(), new GameTask() {
			public void doRun() {
				delete(deleteObj);
			}
		});
	}
	
	 
	
	public void asynBatchExecuteSql(final String sql,final List<Object[]> params) {
	 	
		QUEUE_MANAGER.addTask(sql, new GameTask() {
			public void doRun() {
				executeBatchUpdateSql(sql, params);
			}
		});
	}
	
	
	public void asynBatchInsertt(AsynObj player, final List<V> insertObjs) {
		
		Objects.requireNonNull(insertObjs);
		
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				batchInsert(insertObjs);
			}
		});
	}
	
	public void asynBatchInsert(final List<V> insertObjs) {
		 
		QUEUE_MANAGER.addTask(insertObjs.get(0).getClass().getName(), new GameTask() {
			public void doRun() {
				batchInsert(insertObjs);
			}
		});
	}
	
	public void asynBatchUpdate(AsynObj player, final List<V> updateObjs,final String ... propertyNames) {
	 
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				if(ArrayUtils.isEmpty(propertyNames)){
					batchUpdateByPrimaryKey(updateObjs);
				}else{
					batchUpdatePartByPrimaryKey(updateObjs, propertyNames);
				}
			}
		});
	}

	public void asynBatchUpdate(final List<V> updateObjs,final String ... propertyNames) {
		
		QUEUE_MANAGER.addTask(updateObjs.get(0).getClass().getName(), new GameTask() {
			public void doRun() {
				if(ArrayUtils.isEmpty(propertyNames)){
					batchUpdateByPrimaryKey(updateObjs);
				}else{
					batchUpdatePartByPrimaryKey(updateObjs, propertyNames);
				}
			}
		});
	}
	public void asynDeleteByWhereSql(AsynObj player, final Class<V> clazz, final String whereSql) {
		
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				deleteByWhereSql(clazz, whereSql);
			}
		});
	}

	public void asynUpdate(AsynObj player, final V updateObj, final String... propertyNames) {
	 
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				if(ArrayUtils.isEmpty(propertyNames)){
					updateByPrimaryKey(updateObj);
				}else{
					updatePartByPrimaryKey(updateObj, propertyNames);
				}
			}
		});
	}
	public void asynUpdate(final V updateObj, final String... propertyNames) {
		Objects.requireNonNull(updateObj);
		
		QUEUE_MANAGER.addTask(updateObj.getClass().getName(), new GameTask() {
			public void doRun() {
				if(ArrayUtils.isEmpty(propertyNames)){
					updateByPrimaryKey(updateObj);
				}else{
					updatePartByPrimaryKey(updateObj, propertyNames);
				}
			}
		});
	}
	
	public void asynDeleteByPrimaryKey(AsynObj player, final Class<V> clazz, final PK primaryKey) {
		 
		QUEUE_MANAGER.addTask(player, new GameTask() {
			public void doRun() {
				 deleteByPrimaryKey(clazz, primaryKey);
			}
		});
	}

	public void asynExecuteSql(final String sql,final Object ...param) {
	 
		QUEUE_MANAGER.addTask(sql, new GameTask() {
			public void doRun() {
				 executeUpdateSql(sql, param);
			}
		});
	}


}
