package com.road.fire.fighting.phy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.road.fire.fighting.phy.living.Bullet;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.phy.living.drop.KillDropGoods;
import com.road.fire.fighting.phy.living.drop.SceneDropGoods;

/**
 * 生物对象管理器
 * @author lip.li
 *
 */
@SuppressWarnings("unchecked")
public class LivingManager {
	
    private Map<Integer,Map<Class<?>,Map<Integer,Living<?>>>>   livingGroupObjs=new HashMap<>();
    
    private Map<Integer,Living<?>> allLivingObjs=new ConcurrentHashMap<>();
    
    private Map<Class<?>,Map<Integer,Living<?>>> clazzLivingObjs=new HashMap<>();
    
    
	public LivingManager (int cmpNum){
		 init(cmpNum);
	}
	
	
	private void init(int cmpNum) {
		if(cmpNum<=0){
			return ;
		}
		for(int cmpType=0;cmpType<=cmpNum;cmpType++){
			
			Map<Class<?>, Map<Integer, Living<?>>> campLivingObjs = initCampLiving();
			
			livingGroupObjs.put(cmpType, campLivingObjs);
		}
		
		clazzLivingObjs=initCampLiving();
	}
	/**初始阵营生物集合*/
	private Map<Class<?>, Map<Integer, Living<?>>> initCampLiving() {
		Map<Class<?>,Map<Integer,Living<?>>>  campLivingObjs=new HashMap<>(8);
		campLivingObjs.put(Hero.class, new ConcurrentHashMap<Integer, Living<?>>(1));
		campLivingObjs.put(Monster.class, new ConcurrentHashMap<Integer, Living<?>>(1));
    	campLivingObjs.put(Bullet.class, new ConcurrentHashMap<Integer, Living<?>>(1));
    	campLivingObjs.put(SceneDropGoods.class, new ConcurrentHashMap<Integer, Living<?>>(1));
    	campLivingObjs.put(KillDropGoods.class, new ConcurrentHashMap<Integer, Living<?>>(1));
    	
		return campLivingObjs;
	}
	/**添加生物*/
	public void addLiving(Living<?> living) {
		livingGroupObjs.get(living.getCampType()).get(living.getClass()).put(living.getPhyId(), living);
		allLivingObjs.put(living.getPhyId(), living);
		clazzLivingObjs.get(living.getClass()).put(living.getPhyId(), living);
	}
	/**移除生物*/
	public boolean removeLiving(Living<?> living) {
		allLivingObjs.remove(living.getPhyId());
		clazzLivingObjs.get(living.getClass()).remove(living.getPhyId());
		livingGroupObjs.get(living.getCampType()).get(living.getClass()).remove(living.getPhyId());
	
		return true;
	}
	/**根据类型查找生物*/
	public <T extends Living<?>> T getLiving(Class<T> clazz,int id) {
		return clazz.cast(livingGroupObjs.get(id));
	}
 

	public <T extends Living<?>>  Collection<T> getLivings(Class<T> clazz) {
		
		 return  (Collection<T>) clazzLivingObjs.get(clazz).values();
	}

	public  Collection<Living<?>> getLivings() {
		
		 return  allLivingObjs.values();
	}
	
	
	public  <T extends Living<?>>  Collection<T> getCampLivings(Class<T> clazz,int campType) {

		 return    (Collection<T>) livingGroupObjs.get(campType).get(clazz).values();
	}

	public boolean containsLiving(Living<?> living) {
		return allLivingObjs.containsKey(living.getPhyId());
	}
	
	public int getLivingCount(Class<?> clazz) {
		return clazzLivingObjs.get(clazz).size();
	}
	
	public int getCampLivingCount(Class<?> clazz,int campType) {
		return livingGroupObjs.get(campType).get(clazz).size();
	}
	

	public void clear() {
		livingGroupObjs.clear();
		allLivingObjs.clear(); 
		clazzLivingObjs.clear();
	}


	public Map<Integer, Map<Class<?>, Map<Integer, Living<?>>>> getLivingGroupObjs() {
		return livingGroupObjs;
	}
 
	public Map<Integer, Living<?>> getAllLivingObjs() {
		return allLivingObjs;
	}
 
	public Map<Class<?>, Map<Integer, Living<?>>> getClazzLivingObjs() {
		return clazzLivingObjs;
	}

 
	

}
