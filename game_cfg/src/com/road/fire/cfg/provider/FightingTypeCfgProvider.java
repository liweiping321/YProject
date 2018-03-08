package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.FightingTypeCfg;

/**
 *@author 工具生成
 *@date 2017-06-02 02:52:39 
 */
public class FightingTypeCfgProvider extends BaseCfgEntiyProvider<Integer,FightingTypeCfg> {
	
 
	@Override
	public void afterReload(FightingTypeCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return FightingTypeCfg.class;
	}

	private static final FightingTypeCfgProvider instance=new FightingTypeCfgProvider();

	public static FightingTypeCfgProvider getInstance() {
		return instance;
	}
	
	
}
