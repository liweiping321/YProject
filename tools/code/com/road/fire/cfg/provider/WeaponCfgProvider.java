package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.WeaponCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:24 
 */
public class WeaponCfgProvider extends BaseCfgEntiyProvider<Integer,WeaponCfg> {
	
 
	@Override
	public void afterReload(WeaponCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return WeaponCfg.class;
	}

	private static final WeaponCfgProvider instance=new WeaponCfgProvider();

	public static WeaponCfgProvider getInstance() {
		return instance;
	}
	
	
}
