package com.road.fire.cfg.provider;

import java.util.HashMap;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.LevelExpCfg;

/**
 *@author 工具生成
 *@date 2017-06-01 06:00:03 
 */
public class LevelExpCfgProvider extends BaseCfgEntiyProvider<Integer,LevelExpCfg> {
	
	private Map<Integer,Map<Integer,LevelExpCfg>> levelExpCfgGroups=new HashMap<>();
	@Override
	public void afterReload(LevelExpCfg value) {
	
	}
	@Override
	public void loadEnd() {
		Map<Integer,Map<Integer,LevelExpCfg>> tempLevelExpCfgGroups=new HashMap<>();
		
		for(LevelExpCfg expCfg:mapData.values()){
			
			Map<Integer,LevelExpCfg> levelExpCfgs= tempLevelExpCfgGroups.get(expCfg.getType());
			if(levelExpCfgs==null){
				levelExpCfgs=new HashMap<>();
				tempLevelExpCfgGroups.put(expCfg.getType(), levelExpCfgs);
			}
			
			levelExpCfgs.put(expCfg.getLevel(), expCfg);
			
		}
		levelExpCfgGroups=tempLevelExpCfgGroups;
	}
	
	public Class<?> getCfgClass(){
		
		return LevelExpCfg.class;
	}
	
	public LevelExpCfg getLevelExpCfg(int type, Integer level) {
		Map<Integer,LevelExpCfg> levelExpCfgs= levelExpCfgGroups.get(type);
		if(levelExpCfgs!=null){
			return levelExpCfgs.get(level);
		}
		return null;
	}

	private static final LevelExpCfgProvider instance=new LevelExpCfgProvider();

	public static LevelExpCfgProvider getInstance() {
		return instance;
	}

	
	
	
}
