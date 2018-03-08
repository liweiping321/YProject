package com.road.fire.fighting.effect;

import com.road.fire.entity.cfg.EffectCfg;
import com.road.fire.fighting.phy.living.Living;

public class NoAttackEffect extends BaseEffect {

	public NoAttackEffect(EffectCfg effectCfg) {
		super(effectCfg);

	}

	@Override
	public void onStart() {
		Living<?> owner=buff.getTarget();
		
	   owner.getSkillContainer().stopSkill();
		
	}

	@Override
	protected void onStop() {
		 

	}

}
