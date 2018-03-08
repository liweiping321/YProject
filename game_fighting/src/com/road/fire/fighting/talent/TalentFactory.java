package com.road.fire.fighting.talent;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.road.fire.cfg.provider.TalentCfgProvider;
import com.road.fire.entity.cfg.TalentCfg;
import com.road.fire.fighting.consts.TalentConsts;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.talent.impl.AddHpTalent;
import com.road.fire.fighting.talent.impl.BuffTalent;
import com.road.fire.fighting.talent.impl.PropTalent;
import com.road.fire.fighting.talent.impl.SkillTalent;

public class TalentFactory {
	private static final Logger LOG=LogManager.getLogger(TalentFactory.class);
	
	private Map<Integer,Class<?>>  talentClazzMap=new HashMap<>();
	
	private TalentFactory(){
		init();
	}
	
	private void init(){
		talentClazzMap.put(TalentConsts.TypeBuff,BuffTalent.class);
		talentClazzMap.put(TalentConsts.TypePropAdd, PropTalent.class);
		talentClazzMap.put(TalentConsts.TypeSkill, SkillTalent.class);
		talentClazzMap.put(TalentConsts.TypeAddHp, AddHpTalent.class);
		
	}
	
	
	public BaseTalent createTalent(int talentId,Hero hero){
		
		TalentCfg talentCfg=TalentCfgProvider.getInstance().getConfigVoByKey(talentId);
		
		Class<?> skillClazz=talentClazzMap.get(talentCfg.getType());
		
		BaseTalent baseTalent=null;
		try {
			baseTalent = (BaseTalent) skillClazz.getConstructor(Hero.class,int.class).newInstance(hero,talentId);
		} catch (Exception e) {
			 
			LOG.error(e.getMessage(),e);
		};
	
		return baseTalent;
	}
	

	private static final TalentFactory instace=new TalentFactory();

	public static TalentFactory getInstace() {
		return instace;
	}
	
	
}
