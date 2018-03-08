package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.LevelExpCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:22 
 */
public class LevelExpCfgProvider extends BaseCfgEntiyProvider<Integer,LevelExpCfg> {
	
 
	@Override
	public void afterReload(LevelExpCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return LevelExpCfg.class;
	}

	private static final LevelExpCfgProvider instance=new LevelExpCfgProvider();

	public static LevelExpCfgProvider getInstance() {
		return instance;
	}
	
	
}
