package com.road.fire.fighting.effect;

import com.road.fire.entity.cfg.EffectCfg;
import com.road.fire.fighting.consts.EffectConsts;

/**
 * effect工厂
 */
public class EffectFactory {

	public static BaseEffect createEffect(EffectCfg  effectCfg) {
		switch (effectCfg.getEffectType()) {
			case EffectConsts.TypeNotAttack:
				return new NoAttackEffect(effectCfg);
			case EffectConsts.TypeNotMove:
				return new NoMoveEffect(effectCfg);
			case EffectConsts.TypeAddHp:
				return new AddHpEffect(effectCfg);
		default:
			return null;
		}
	}

}
