package com.road.fire.fighting.skill;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.consts.SkillConsts;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.container.SkillContainer;
import com.road.fire.fighting.skill.impl.GrenadeSkill;
import com.road.fire.fighting.skill.impl.QuickShotSkill;
import com.road.fire.fighting.skill.impl.SelfRangeSkill;

public class SkillFactory {
	private static final Logger LOG=LogManager.getLogger(SkillFactory.class);
	
	private Map<Integer,Class<?>>  skillClazzMap=new HashMap<>();
	
	private SkillFactory(){
		init();
	}
	
	private void init(){
		skillClazzMap.put(SkillConsts.TypeQuickShot,QuickShotSkill.class);
		skillClazzMap.put(SkillConsts.TypeGrenade, GrenadeSkill.class);
		skillClazzMap.put(SkillConsts.TypeSelfRange, SelfRangeSkill.class);
		
	}
	
	
	public ActiveSkill<?> createActiveSkill(SkillCfg skillCfg, Living<?> owner,
			SkillContainer<?> skillContainer){
		
		Class<?> skillClazz=skillClazzMap.get(skillCfg.getType());
		
		ActiveSkill<?> activeSkill=null;
		try {
			activeSkill = (ActiveSkill<?>) skillClazz.getConstructor(SkillCfg.class,Living.class,SkillContainer.class).newInstance(skillCfg,owner,skillContainer);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			 
			LOG.error(e.getMessage(),e);
		};
	
		return activeSkill;
	}
	

	private static final SkillFactory instace=new SkillFactory();

	public static SkillFactory getInstace() {
		return instace;
	}
	
	
}
