package com.road.fire.cfg.provider;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.EffectCfg;

/**
 *@author 工具生成
 *@date 2017-04-25 02:37:37 
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
