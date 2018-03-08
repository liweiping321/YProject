package com.road.fire.fighting.ai.fsm.hero;

import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.StateMachineImpl;
import com.road.fire.fighting.phy.living.Hero;

/**
 * 英雄状态机
 * 
 * @author lip.li
 */
public class HeroStateMachine extends StateMachineImpl<Hero> {

	public HeroStateMachine(Hero owner) {
		super(owner);
		initFSM();
	}

	@Override
	public void initFSM() {
		if (init) {
			return;
		}
		init = true;
		
		addState(new HeroIdleState(this, owner));
		addState(new HeroDieState(this, owner));
		addState(new HeroCityReliveState(this, owner));
		addState(new HeroMoveState(this, owner));
		
		currentState=stateMap.get(FsmState.idle.getId());
		currentState.onBegin();
	}

}
