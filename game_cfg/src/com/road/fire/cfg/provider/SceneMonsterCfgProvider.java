package com.road.fire.cfg.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
import com.game.cfg.entity.PointCfg;
import com.road.fire.entity.cfg.SceneMonsterCfg;

/**
 *@author 工具生成
 *@date 2017-05-04 10:07:39 
 */
public class SceneMonsterCfgProvider extends BaseCfgEntiyProvider<Integer,SceneMonsterCfg> {
	
	private Map<Integer,Map<Integer,SceneMonsterCfg>> sceneMonsterCfgGroups=new HashMap<>();

	@Override
	public void loadEnd() {
		
		Map<Integer,Map<Integer,SceneMonsterCfg>> tempCfgGroups=new HashMap<>();
		for(SceneMonsterCfg value:mapData.values()){
			Map<Integer,SceneMonsterCfg> sceneMonsterCfgs= tempCfgGroups.get(value.getSceneId());
			if(null==sceneMonsterCfgs){
				sceneMonsterCfgs=new HashMap<>();
				tempCfgGroups.put(value.getSceneId(), sceneMonsterCfgs);
			}
			
			sceneMonsterCfgs.put(value.getSceneMonsterId(), value);
		}
		
		sceneMonsterCfgGroups=tempCfgGroups;
		
	}
	@Override
	public void afterReload(SceneMonsterCfg value) {
	 
		value.setImpassePointCfgs(getPointCfgs(value.getImpassePoints()));
		
		value.setMovePathPointCfgs(getPointCfgs(value.getMovePath()));
		
		List<PointCfg> offsetPoints=getPointCfgs(value.getDropOffsetPoints());
		List<PointCfg> dropPoints=new ArrayList<PointCfg>();
		
		dropPoints.add(new PointCfg(value.getX(), value.getY()));
		for(PointCfg offsetPoint:offsetPoints){
			dropPoints.add(new PointCfg(value.getX()+offsetPoint.getX(), value.getY()+offsetPoint.getY()));
		}
		value.setDropPointCfgs(dropPoints);
	}
 
	public Class<?> getCfgClass(){
		
		return SceneMonsterCfg.class;
	}
	
	public Collection<SceneMonsterCfg> getSceneMonsterCfgs(int sceneId){
		Map<Integer,SceneMonsterCfg> monsterCfgs= sceneMonsterCfgGroups.get(sceneId);
		if(monsterCfgs==null){
			return new ArrayList<>();
		}
		return monsterCfgs.values();
	}

	private static final SceneMonsterCfgProvider instance=new SceneMonsterCfgProvider();

	public static SceneMonsterCfgProvider getInstance() {
		return instance;
	}
	
	
}
