package com.road.fire.fighting.ai.fsm.hero;
 

import com.game.ai.fsm.BaseState;
import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.IStateMachine;
import com.road.fire.fighting.phy.living.Hero;

/**
 * 英雄死亡状态
 * @author lip.li
 *
 */
public  class HeroDieState extends BaseState<Hero> {
	 
	public HeroDieState(IStateMachine<Hero> fsm, Hero owner) {
		super(fsm, owner);
		 
	}

	@Override
	public void onBegin() {
		
		 owner.onDead();
		//切换到复活状态
		 owner.switchState(FsmState.cityRelive);
	}

	@Override
	public void onExit() {
     
	}

	@Override
	public void onUpdate(long now) {
		 
	}

	@Override
	public FsmState getState() {
	 
		return FsmState.die;
	}
	 
}
