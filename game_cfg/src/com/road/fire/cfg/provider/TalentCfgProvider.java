package com.road.fire.cfg.provider;

import java.util.HashMap;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.TalentCfg;

/**
 *@author 工具生成
 *@date 2017-06-05 04:38:40 
 */
public class TalentCfgProvider extends BaseCfgEntiyProvider<Integer,TalentCfg> {
	
	Map<Integer,Map<Integer,TalentCfg>> heroTalentGrups=new HashMap<Integer,Map<Integer,TalentCfg>>();
	
	@Override
	public void afterReload(TalentCfg value) {
	 
	}
		
	@Override
	public void loadEnd() {
		Map<Integer,Map<Integer,TalentCfg>> tempTalentGroups=new HashMap<Integer,Map<Integer,TalentCfg>>();
	   
		for(TalentCfg talentCfg:mapData.values()){
			Map<Integer,TalentCfg> heroTalentCfgs=tempTalentGroups.get(talentCfg.getHeroId());
			if(heroTalentCfgs==null){
				heroTalentCfgs=new HashMap<>();
				
				tempTalentGroups.put(talentCfg.getHeroId(), heroTalentCfgs);
			}
			
			heroTalentCfgs.put(talentCfg.getTalentId(), talentCfg);
			
			TalentCfg nextTalent=mapData.get(talentCfg.getNextTalentId());
			if(nextTalent!=null){
				nextTalent.setPreTalentId(talentCfg.getTalentId());
			}
	   }
		heroTalentGrups=tempTalentGroups;
	}
	
	
	public Map<Integer,TalentCfg> getHeroTalentCfgs(int heroId){
		
		return heroTalentGrups.get(heroId);
	}
	public Class<?> getCfgClass(){
		
		return TalentCfg.class;
	}

	private static final TalentCfgProvider instance=new TalentCfgProvider();

	public static TalentCfgProvider getInstance() {
		return instance;
	}
	
	
}
