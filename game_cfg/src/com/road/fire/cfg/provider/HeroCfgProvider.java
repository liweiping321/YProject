package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.HeroCfg;

/**
 *@author 工具生成
 *@date 2017-05-04 10:07:39 
 */
public class HeroCfgProvider extends BaseCfgEntiyProvider<Integer,HeroCfg> {
	
 
	@Override
	public void afterReload(HeroCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return HeroCfg.class;
	}

	private static final HeroCfgProvider instance=new HeroCfgProvider();

	public static HeroCfgProvider getInstance() {
		return instance;
	}
	
	
}
