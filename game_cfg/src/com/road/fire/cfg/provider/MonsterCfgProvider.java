package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.MonsterCfg;

/**
 *@author 工具生成
 *@date 2017-05-04 10:07:39 
 */
public class MonsterCfgProvider extends BaseCfgEntiyProvider<Integer,MonsterCfg> {
	
 
	@Override
	public void afterReload(MonsterCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return MonsterCfg.class;
	}

	private static final MonsterCfgProvider instance=new MonsterCfgProvider();

	public static MonsterCfgProvider getInstance() {
		return instance;
	}
	
	
}
