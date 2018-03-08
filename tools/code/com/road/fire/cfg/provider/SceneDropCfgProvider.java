package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SceneDropCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class SceneDropCfgProvider extends BaseCfgEntiyProvider<Integer,SceneDropCfg> {
	
 
	@Override
	public void afterReload(SceneDropCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return SceneDropCfg.class;
	}

	private static final SceneDropCfgProvider instance=new SceneDropCfgProvider();

	public static SceneDropCfgProvider getInstance() {
		return instance;
	}
	
	
}
