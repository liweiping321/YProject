package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.BornPositionCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class BornPositionCfgProvider extends BaseCfgEntiyProvider<Integer,BornPositionCfg> {
	
 
	@Override
	public void afterReload(BornPositionCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return BornPositionCfg.class;
	}

	private static final BornPositionCfgProvider instance=new BornPositionCfgProvider();

	public static BornPositionCfgProvider getInstance() {
		return instance;
	}
	
	
}
