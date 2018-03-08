package com.road.fire.fighting.ai.fsm.hero;
 

import com.game.ai.fsm.BaseState;
import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.IStateMachine;
import com.game.cfg.entity.ConfigsCfg;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Hero;

/**
 * 英雄移动状态
 * @author lip.li
 *
 */
public  class HeroMoveState extends BaseState<Hero> {
 
	public HeroMoveState(IStateMachine<Hero> fsm, Hero owner) {
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
		if(ConfigsCfg.showCurPoint){
			owner.broadcastMsg(MsgCode.MoveCurPointResp, FtMsgUtil.builderMoveCurPointResp(owner));
		}
		//到达目的地，停止移动
		if(owner.getMoveController().checkArrived()){
			owner.switchState(FsmState.idle);
		}
	 
	}

	@Override
	public FsmState getState() {
		 
		return FsmState.move;
	}
	
	
	 
}
