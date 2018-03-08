package com.road.fire.fighting.ai.fsm.hero;
 

import com.game.ai.fsm.BaseState;
import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.IStateMachine;
import com.game.cfg.entity.ConfigsCfg;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Hero;

/**
 * 英雄原地复活
 * @author lip.li
 *
 */
public  class HeroCityReliveState extends BaseState<Hero> {
	
	private long endTime=0l;
	public HeroCityReliveState(IStateMachine<Hero> fsm, Hero owner) {
		super(fsm, owner);
		 
	}

	@Override
	public void onBegin() {
		endTime=owner.getFrameTime()+ConfigsCfg.getCityReliveTime();
		//发送回城复活倒计时
		owner.sendMsg(MsgCode.HeroReLiveTimeResp,FtMsgUtil.builderHeroReLiveTimeResp(owner));
	}

	@Override
	public void onExit() {
     
	}

	@Override
	public void onUpdate(long now) {
		if(now>endTime){//回城复活时间到
			owner.onCityRelive();
			owner.switchState(FsmState.idle);
		}
	}

	@Override
	public FsmState getState() {
	 
		return FsmState.cityRelive;
	}
	 
}
