package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SceneMonsterCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class SceneMonsterCfgProvider extends BaseCfgEntiyProvider<Integer,SceneMonsterCfg> {
	
 
	@Override
	public void afterReload(SceneMonsterCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return SceneMonsterCfg.class;
	}

	private static final SceneMonsterCfgProvider instance=new SceneMonsterCfgProvider();

	public static SceneMonsterCfgProvider getInstance() {
		return instance;
	}
	
	
}
