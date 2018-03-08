package com.game.cfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.game.cfg.entity.PointCfg;
import com.game.consts.Splitable;
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.BaseDAO;
import com.game.db.base.EntityType;

/**
 * 
 * @author lip.li
 */
public abstract class BaseCfgEntiyProvider<Key, V extends AbstractEntityBean<Key, ?>> {

	protected static final Logger logger = LogManager.getLogger(BaseCfgEntiyProvider.class);

	protected Map<Key, V> mapData = new LinkedHashMap<Key, V>();

	public boolean isFirstLoad = true;

	protected BaseCfgEntiyProvider() {

	}

	public Collection<V> getConfigDatas() {
		return mapData.values();
	}

	public V getConfigVoByKey(Key key) {
		return mapData.get(key);
	}

	public abstract void afterReload(V value);

	public void beforeReload() {
	
	}

	@SuppressWarnings("unchecked")
	public void reLoad() {

		List<V> values = (List<V>) BaseDAO.ins.queryList(getCfgClass());
		
		beforeReload();
		for (V v : values) {
			if (mapData.containsKey(v.getPrimaryKeyValue())) {
				V oldV = mapData.get(v.getPrimaryKeyValue());
				try {
					afterReload(v);
					BeanUtils.copyProperties(oldV, v);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				mapData.put(v.getPrimaryKeyValue(), v);
				afterReload(mapData.get(v.getPrimaryKeyValue()));
			}
			
		}

	}

	public String getName() {
		return EntityType.getType(getCfgClass()).getTableName();
	}

	public abstract Class<?> getCfgClass();

	public boolean isExist(Key key) {
		return mapData.containsKey(key);
	}

	/**
	 * 加载完成
	 */
	public void loadEnd() {
		isFirstLoad = false;
	}

	public Map<Key, V> getMapData() {
		return mapData;
	}

	protected void printlnCheckInfo(String fieldName, Object value,String otherFile) {
		logger.info("表中的{}：{}在{}中不存在", getName(), fieldName, value, otherFile);

	}

	protected void printlnNotValidate(String fieldName, Object value) {
		logger.info("表中的{}：{}不合法数据", getName(), fieldName, value);
	}
	
	public void printLoadOver() {
		logger.info("表{}：成功加载 {}条记录", getName(),mapData.size());

	}
	
	public List<PointCfg> getPointCfgs(String pointStr){
		
		List<PointCfg> pointCfgs=new ArrayList<>();
		if(!StringUtils.isEmpty(pointStr)){
			String [] items=pointStr.split(Splitable.HUO);
			if(!ArrayUtils.isEmpty(items)){
				for(String item:items){
					PointCfg pointCfg=new PointCfg(item, Splitable.XIAHUAXIAN);
					pointCfgs.add(pointCfg);
				}
			}
		}
		return pointCfgs;
		
	}

}
