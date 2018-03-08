package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.DropTemplateCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
public class DropTemplateCfgProvider extends BaseCfgEntiyProvider<Integer,DropTemplateCfg> {
	
 
	@Override
	public void afterReload(DropTemplateCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return DropTemplateCfg.class;
	}

	private static final DropTemplateCfgProvider instance=new DropTemplateCfgProvider();

	public static DropTemplateCfgProvider getInstance() {
		return instance;
	}
	
	
}
