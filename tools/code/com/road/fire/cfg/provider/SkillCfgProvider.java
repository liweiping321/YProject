package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SkillCfg;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:24 
 */
public class SkillCfgProvider extends BaseCfgEntiyProvider<Integer,SkillCfg> {
	
 
	@Override
	public void afterReload(SkillCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return SkillCfg.class;
	}

	private static final SkillCfgProvider instance=new SkillCfgProvider();

	public static SkillCfgProvider getInstance() {
		return instance;
	}
	
	
}