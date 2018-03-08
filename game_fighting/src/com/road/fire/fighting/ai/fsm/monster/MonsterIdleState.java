package com.road.fire.fighting.ai.fsm.monster;
 

import com.game.ai.fsm.BaseState;
import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.IStateMachine;
import com.road.fire.fighting.phy.living.Monster;

/**
 * 英雄死亡状态
 * @author lip.li
 *
 */
public  class MonsterIdleState extends BaseState<Monster> {
 
	public MonsterIdleState(IStateMachine<Monster> fsm, Monster owner) {
		super(fsm, owner);
		 
	}

	@Override
	public void onBegin() {

	}

	@Override
	public void onExit() {
     
	}

	@Override
	public void onUpdate(long now) {
	}

	@Override
	public FsmState getState() {
		return FsmState.idle;
	}
	 
}
