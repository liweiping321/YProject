package com.road.fire.fighting.ai.fsm.monster;
 

import com.game.ai.fsm.BaseState;
import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.IStateMachine;
import com.road.fire.fighting.phy.living.Monster;

/**
 * 英雄移动状态
 * @author lip.li
 *
 */
public  class MonsterMoveState extends BaseState<Monster> {
 
	public MonsterMoveState(IStateMachine<Monster> fsm, Monster owner) {
		super(fsm, owner);
		 
	}

	@Override
	public void onBegin() {

	}

	@Override
	public void onExit() {
		owner.getMoveController().stopMove();
	}

	@Override
	public void onUpdate(long now) {
		
		//到达目的地，停止移动
		if(owner.getMoveController().checkArrived()){
			owner.switchState(FsmState.idle);
			return;
		}
	 
	}

	@Override
	public FsmState getState() {
		 
		return FsmState.move;
	}
	
	
	 
}
