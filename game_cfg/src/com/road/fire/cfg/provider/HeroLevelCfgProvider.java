package com.road.fire.cfg.provider;

import java.util.HashMap;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.HeroLevelCfg;

/**
 *@author 工具生成
 *@date 2017-05-04 10:07:39 
 */
public class HeroLevelCfgProvider extends BaseCfgEntiyProvider<Integer,HeroLevelCfg> {
	
	private Map<Integer,Map<Integer,HeroLevelCfg>> heroLevelCfgs=new HashMap<>();
	
	@Override
	public void beforeReload() {
		heroLevelCfgs.clear();
	}
	@Override
	public void afterReload(HeroLevelCfg value) {
		Map<Integer,HeroLevelCfg> levelCfgs=heroLevelCfgs.get(value.getHeroId());
		if(null==levelCfgs){
			levelCfgs=new HashMap<>();
			heroLevelCfgs.put(value.getHeroId(), levelCfgs);
		}
		levelCfgs.put(value.getLevel(), value);
	}
	
	public Class<?> getCfgClass(){
		
		return HeroLevelCfg.class;
	}

	private static final HeroLevelCfgProvider instance=new HeroLevelCfgProvider();

	public static HeroLevelCfgProvider getInstance() {
		return instance;
	}
	
	/**获取英雄等级配置数据*/
	public HeroLevelCfg getHeroLeveLCfg(int heroId, int level) {
		return heroLevelCfgs.get(heroId).get(level);
	}
	
	
}
