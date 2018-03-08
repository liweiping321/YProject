package com.road.fire.fighting.skill.container;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.road.fire.cfg.provider.SkillCfgProvider;
import com.road.fire.entity.cfg.MonsterCfg;
import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.SkillFactory;

/***
 * 英雄技能容器
 * @author lip.li
 *
 */
public class MonsterSkillContainer  extends SkillContainer<Monster>  {
	
	public MonsterSkillContainer(Monster owner, BaseGame game) {
		super(owner, game);
		 
	}
    @Override
    public void init() {
    	MonsterCfg monsterCfg=owner.getMonsterCfg();
    	if(monsterCfg.getCommonSkillId()!=null){
    		SkillCfg skillCfg=SkillCfgProvider.getInstance().getConfigVoByKey(monsterCfg.getCommonSkillId());
    		if(null!=skillCfg){
    			ActiveSkill<?> activeSkil= SkillFactory.getInstace().createActiveSkill(skillCfg, owner, this);
    			addActiveSkill(monsterCfg.getCommonSkillId(), activeSkil);
    		}
    		
    	}
    	
    	Set<Integer> skillIds=monsterCfg.getSkillIds();
    	if(!CollectionUtils.isEmpty(skillIds)){
    		for(Integer skillId:skillIds){
    			SkillCfg skillCfg=SkillCfgProvider.getInstance().getConfigVoByKey(skillId);
    			if(null!=skillCfg){
    				ActiveSkill<?> activeSkil=SkillFactory.getInstace().createActiveSkill(skillCfg, owner, this);
    				addActiveSkill(skillId, activeSkil);
    			}
        		
    		}
    	}
    	
    	
    }
    
    @Override
    public void onDieTriggerSKill(int skillId) {
    	SkillCfg skillCfg=SkillCfgProvider.getInstance().getConfigVoByKey(skillId);
    	
    	ActiveSkill<?>  activeSkill=SkillFactory.getInstace().createActiveSkill(skillCfg, owner, this);
    	activeSkill.onSpread(owner.getFrameTime());
    }
}
