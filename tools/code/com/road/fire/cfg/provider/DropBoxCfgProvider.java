package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.DropBoxCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class DropBoxCfgProvider extends BaseCfgEntiyProvider<Integer,DropBoxCfg> {
	
 
	@Override
	public void afterReload(DropBoxCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return DropBoxCfg.class;
	}

	private static final DropBoxCfgProvider instance=new DropBoxCfgProvider();

	public static DropBoxCfgProvider getInstance() {
		return instance;
	}
	
	
}
