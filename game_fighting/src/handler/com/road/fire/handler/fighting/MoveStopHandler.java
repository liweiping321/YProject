package com.road.fire.handler.fighting;

import com.game.ai.fsm.FsmState;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBMoveStopReq;
import com.road.fire.FtMsgUtil;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;

/**
 *  停止移动 709
 *  @since  2017-05-04 06:04:36 
 */
@Handler(code=MsgCode.MoveStopReq)
public class MoveStopHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
		PBMoveStopReq req = request.parseParams(PBMoveStopReq.newBuilder());

		hero.setX(req.getStopX());
		hero.setY(req.getStopY());
		 
		hero.stopMove();
		
    }
}