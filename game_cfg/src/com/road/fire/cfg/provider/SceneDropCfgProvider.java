package com.road.fire.cfg.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SceneDropCfg;

/**
 *@author 工具生成
 *@date 2017-05-17 06:18:58 
 */
public class SceneDropCfgProvider extends BaseCfgEntiyProvider<Integer,SceneDropCfg> {
	
	private Map<Integer,Set<SceneDropCfg>> sceneDropGroups=new HashMap<>();
	 
	@Override
	public void loadEnd() {
		Map<Integer,Set<SceneDropCfg>> tempDropGroups=new HashMap<>();
		
		for(SceneDropCfg value:mapData.values()){
			Set<SceneDropCfg> sceneDropCfgs= tempDropGroups.get(value.getSceneId());
			if(null==sceneDropCfgs){
				sceneDropCfgs=new HashSet<>();
				tempDropGroups.put(value.getSceneId(), sceneDropCfgs);
			}
			
			sceneDropCfgs.add(value);
		}
		
		sceneDropGroups=tempDropGroups;
	}
	@Override
	public void afterReload(SceneDropCfg value) {
		
		
	}
	

	public Collection<SceneDropCfg> getSceneDropCfgs(int sceneId){
		return sceneDropGroups.get(sceneId);
	}

	public Class<?> getCfgClass(){
		
		return SceneDropCfg.class;
	}

	private static final SceneDropCfgProvider instance=new SceneDropCfgProvider();

	public static SceneDropCfgProvider getInstance() {
		return instance;
	}
	
	
}
