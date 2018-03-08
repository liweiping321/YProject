package com.road.fire.fighting.ai.move;

import com.game.cfg.entity.ConfigsCfg;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Hero;

/**
 * 角色移动控制器
 * @author lip.li
 *
 * @param <Hero>
 */
 
public class HeroMoveController extends MoveController<Hero> {

	public HeroMoveController(Hero owner) {
		super(owner);
	 
	}

	@Override
	public void setMoveInfo(MoveInfo moveInfo) {
	
		super.setMoveInfo(moveInfo);
		
		moveInfo.setMoveTime(ConfigsCfg.moveTime);
		
		owner.broadcastMsg(MsgCode.MoveResp,FtMsgUtil.builderMoveResp(moveInfo, owner), owner);
	}
}
