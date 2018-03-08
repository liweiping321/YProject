package com.road.fire.cfg.provider;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SceneEffectCfg;

/**
 *@author 工具生成
 *@date 2017-04-25 02:37:38 
 */
public class SceneEffectCfgProvider extends BaseCfgEntiyProvider<Integer,SceneEffectCfg> {
	
 
	@Override
	public void afterReload(SceneEffectCfg value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return SceneEffectCfg.class;
	}

	private static final SceneEffectCfgProvider instance=new SceneEffectCfgProvider();

	public static SceneEffectCfgProvider getInstance() {
		return instance;
	}
	
	
}
