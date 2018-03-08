package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SceneCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class SceneCfgProvider extends BaseCfgEntiyProvider<Integer,SceneCfg> {
	
 
	@Override
	public void afterReload(SceneCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return SceneCfg.class;
	}

	private static final SceneCfgProvider instance=new SceneCfgProvider();

	public static SceneCfgProvider getInstance() {
		return instance;
	}
	
	
}
