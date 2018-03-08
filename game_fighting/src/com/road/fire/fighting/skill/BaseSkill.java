package com.road.fire.fighting.skill;

import java.util.List;

import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.phy.living.Living;

/**
 * 技能基础类
 * 
 * @author lip.li
 * 
 */
public class BaseSkill<T extends Living<?>> {

	protected T owner;

	protected SkillCfg skillCfg;


	public BaseSkill(SkillCfg skillCfg, T owner) {
		this.skillCfg = skillCfg;
		this.owner = owner;
	}

	public int getSkillId() {
		return skillCfg.getSkillId();
	}

	public T getOwner() {
		return owner;
	}

	public void setOwner(T owner) {
		this.owner = owner;
	}

	public SkillCfg getSkillCfg() {
		return skillCfg;
	}

	public void setSkillCfg(SkillCfg skillCfg) {
		this.skillCfg = skillCfg;
	}
	
	public int getHurtType(){
		return skillCfg.getHurtType();
	}
	
	public List<Integer> getBuffs(){
		return skillCfg.getBuffs();
	}

}
