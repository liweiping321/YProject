package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.BuffCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class BuffCfgProvider extends BaseCfgEntiyProvider<Integer,BuffCfg> {
	
 
	@Override
	public void afterReload(BuffCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return BuffCfg.class;
	}

	private static final BuffCfgProvider instance=new BuffCfgProvider();

	public static BuffCfgProvider getInstance() {
		return instance;
	}
	
	
}
