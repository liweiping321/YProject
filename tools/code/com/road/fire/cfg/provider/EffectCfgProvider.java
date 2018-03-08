package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.EffectCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class EffectCfgProvider extends BaseCfgEntiyProvider<Integer,EffectCfg> {
	
 
	@Override
	public void afterReload(EffectCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return EffectCfg.class;
	}

	private static final EffectCfgProvider instance=new EffectCfgProvider();

	public static EffectCfgProvider getInstance() {
		return instance;
	}
	
	
}
