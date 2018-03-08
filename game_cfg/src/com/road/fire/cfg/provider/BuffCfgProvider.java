package com.road.fire.cfg.provider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.BuffCfg;
import com.road.fire.entity.cfg.EffectCfg;

/**
 *@author 工具生成
 *@date 2017-04-25 02:37:37 
 */
public class BuffCfgProvider extends BaseCfgEntiyProvider<Integer,BuffCfg> {
	
 
	@Override
	public void afterReload(BuffCfg value) {
		List<Integer> effectIds=value.getEffects();
		if(!CollectionUtils.isEmpty(effectIds)){
			for(Integer effectId:effectIds){
				EffectCfg effectCfg=EffectCfgProvider.getInstance().getConfigVoByKey(effectId);
				value.getEffectCfgs().add(effectCfg);
			}
		}
	}
	
	public Class<?> getCfgClass(){
		
		return BuffCfg.class;
	}

	private static final BuffCfgProvider instance=new BuffCfgProvider();

	public static BuffCfgProvider getInstance() {
		return instance;
	}
	
	
}
