package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.HeroLevelCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class HeroLevelCfgProvider extends BaseCfgEntiyProvider<Integer,HeroLevelCfg> {
	
 
	@Override
	public void afterReload(HeroLevelCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return HeroLevelCfg.class;
	}

	private static final HeroLevelCfgProvider instance=new HeroLevelCfgProvider();

	public static HeroLevelCfgProvider getInstance() {
		return instance;
	}
	
	
}
