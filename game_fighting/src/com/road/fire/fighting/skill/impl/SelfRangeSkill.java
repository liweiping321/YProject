package com.road.fire.fighting.skill.impl;

import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.computer.SelfRangeMany;
import com.road.fire.fighting.skill.container.SkillContainer;

/**
 * 
 * @author lip.li
 *
 */
 
public class SelfRangeSkill extends PropSkill{

	public SelfRangeSkill(SkillCfg skillCfg, Living<?> owner,
			SkillContainer<?> skillContainer) {
		super(skillCfg, owner, skillContainer);
		 
	}
	@Override
	public void onSpread(long now) {
		SelfRangeMany rangeMany=	new SelfRangeMany(owner, this,false,false);
		rangeMany.run();
	}
	@Override
	public void lockTarget() {
	}
}
	
 
