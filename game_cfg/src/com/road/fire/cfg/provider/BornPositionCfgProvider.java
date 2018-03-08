package com.road.fire.cfg.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.BornPositionCfg;

/**
 *@author 工具生成
 *@date 2017-05-04 10:07:38 
 */
public class BornPositionCfgProvider extends BaseCfgEntiyProvider<Integer,BornPositionCfg> {
	
	private Map<Integer,Map<Integer,List<BornPositionCfg>>>  sceneBornPositions=new HashMap<>();
	 
	@Override
	public void loadEnd() {
		Map<Integer,Map<Integer,List<BornPositionCfg>>>  tempBornPositions=new HashMap<>();
		for(BornPositionCfg value:mapData.values()){
			Map<Integer,List<BornPositionCfg>>  cmpBornPositions=tempBornPositions.get(value.getSceneId());
			if(null==cmpBornPositions){
				cmpBornPositions=new HashMap<>();
				tempBornPositions.put(value.getSceneId(), cmpBornPositions);
			}
			
			List<BornPositionCfg> positionCfgs=cmpBornPositions.get(value.getCampType());
			if(null==positionCfgs){
				positionCfgs=new ArrayList<>();
				cmpBornPositions.put(value.getCampType(), positionCfgs);
			}
			
			positionCfgs.add(value);
		}
		sceneBornPositions=tempBornPositions;
	}
	@Override
	public void afterReload(BornPositionCfg value) {
		
		
	}
	
	public Class<?> getCfgClass(){
		
		return BornPositionCfg.class;
	}
	
	public Map<Integer, List<BornPositionCfg>> getBornPositions(int sceneId) {
		 
		return sceneBornPositions.get(sceneId);
	}

	private static final BornPositionCfgProvider instance=new BornPositionCfgProvider();

	public static BornPositionCfgProvider getInstance() {
		return instance;
	}

	public BornPositionCfg getBornPositions(int sceneId, int cmpType, int index) {
		 
		return sceneBornPositions.get(sceneId).get(cmpType).get(index);
	}

	
	
	
}
