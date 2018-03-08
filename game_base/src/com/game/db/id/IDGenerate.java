package com.game.db.id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.game.db.base.BaseDAO;
import com.game.db.base.EntityType;
@SuppressWarnings("unchecked")
public class IDGenerate {
	
	/**ID增长步长*/
	private static final long IdIncrStep=1000;
	
	private Map<Class<?>, AtomicLong> currentIds = new ConcurrentHashMap<>();
	
	private Map<String, Uniqueid> maxIds = new ConcurrentHashMap<>();
	
	 /**id前面为区号，即第 14-19位*/
	 private  long gameZoneId;;

 
	public IDGenerate( ) {

	}

	public static IDGenerate getInstance() {
		return instance;
	}

	public long getGenerateId(Class<?> clazz) {
		EntityType<?> entityType = EntityType.getType(clazz);
		String tableName = entityType.getTableName();
		Long currentId;
		synchronized (clazz) {
			if (!currentIds.containsKey(clazz)) {
				register(clazz);
			}
			currentId = currentIds.get(clazz).incrementAndGet();
			if (maxIds.get(tableName).getCurrentId() <= currentId) {
				Uniqueid uniqueid = new Uniqueid();
				uniqueid.setCurrentId(currentId + IdIncrStep);
				uniqueid.setTableName(tableName);
				if (BaseDAO.ins.updateByPrimaryKey(uniqueid) > 0) {
					maxIds.get(tableName).setCurrentId(currentId + IdIncrStep);
				}
			}
		}

		return currentId + gameZoneId;
	}

	/**
	 * 将当前内存里的数据保存到数据库里。
	 */
	public void saveData()
	{
		for (Class<?> clazz : currentIds.keySet())
		{
			EntityType<?> entityType = EntityType.getType(clazz);
			String tableName = entityType.getTableName();
			if (maxIds.containsKey(tableName))
			{
				maxIds.get(tableName).setCurrentId(currentIds.get(clazz).get());
			}
		}
		BaseDAO.ins.batchUpdateByPrimaryKey(new ArrayList<Uniqueid>(maxIds.values()));
	}

	public void init(long gameZoneId) {
		this.gameZoneId=gameZoneId;
		
		List<Uniqueid> uniqueIds = (List<Uniqueid>) BaseDAO.ins.queryList(Uniqueid.class);
		for (Uniqueid uniqueid : uniqueIds) {
			maxIds.put(uniqueid.getTableName(), uniqueid);
		}

		// 注册相应的表
		//register(Account.class);
	}

	private void register(Class<?> clazz) {
		EntityType<?> entityType = EntityType.getType(clazz);
		String tableName = entityType.getTableName();

		Uniqueid uniqueid = maxIds.get(tableName);
		if (uniqueid == null) {
			// 插入记录
			uniqueid = new Uniqueid();
			uniqueid.setCurrentId(IdIncrStep);
			uniqueid.setTableName(tableName);
			BaseDAO.ins.insert(uniqueid);
			
			currentIds.put(clazz, new AtomicLong(0));
			maxIds.put(uniqueid.getTableName(), uniqueid);
		}else{
			currentIds.put(clazz, new AtomicLong(uniqueid.getCurrentId()));
		}
	}
	 
	
	private static IDGenerate instance = new IDGenerate();

}
