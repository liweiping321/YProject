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
public  class MonsterDieState extends BaseState<Monster> {
	 
	private long endTime=0l;
	public MonsterDieState(IStateMachine<Monster> fsm, Monster owner) {
		super(fsm, owner);
		 
	}

	@Override
	public void onBegin() {
		endTime=owner.getFrameTime()+owner.getMonsterCfg().getDieTime();
		
		onUpdate(owner.getFrameTime());
	}

	@Override
	public void onExit() {
     
	}

	@Override
	public void onUpdate(long now) {
		 if(now>endTime){
			 
			 owner.onDead();
			 
			 owner.switchState(FsmState.idle);
		 }
	}

	@Override
	public FsmState getState() {
	 
		return FsmState.die;
	}
	 
}
