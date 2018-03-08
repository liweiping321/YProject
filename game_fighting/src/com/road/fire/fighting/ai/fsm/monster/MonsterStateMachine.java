package com.road.fire.fighting.ai.fsm.monster;

import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.StateMachineImpl;
import com.road.fire.fighting.phy.living.Monster;

/**
 * 怪物状态机
 * 
 * @author lip.li
 */
public class MonsterStateMachine extends StateMachineImpl<Monster> {

	public MonsterStateMachine(Monster owner) {
		super(owner);
		initFSM();
	}

	@Override
	public void initFSM() {
		if (init) {
			return;
		}
		init = true;
		
		addState(new MonsterIdleState(this, owner));
		addState(new MonsterDieState(this, owner));
		addState(new MonsterMoveState(this, owner));
		
		currentState=stateMap.get(FsmState.idle.getId());
		currentState.onBegin();
	}

}
