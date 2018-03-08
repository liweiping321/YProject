package com.road.fire.fighting.effect;

import com.road.fire.entity.cfg.EffectCfg;
import com.road.fire.fighting.phy.living.Living;

public class NoMoveEffect extends BaseEffect {

	public NoMoveEffect(EffectCfg effectCfg) {
		super(effectCfg);

	}

	@Override
	public void onStart() {
		Living<?> owner=buff.getTarget();
		
		owner.stopMove();
		
		
	}

	@Override
	protected void onStop() {
		 

	}

}
