package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.TalentCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:22 
 */
public class TalentCfgProvider extends BaseCfgEntiyProvider<Integer,TalentCfg> {
	
 
	@Override
	public void afterReload(TalentCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return TalentCfg.class;
	}

	private static final TalentCfgProvider instance=new TalentCfgProvider();

	public static TalentCfgProvider getInstance() {
		return instance;
	}
	
	
}
