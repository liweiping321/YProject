package com.road.fire.handler.fighting;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;
import com.game.protobuf.MsgCode;

import com.game.protobuf.fighting.FightingProto.PBMoveStopReq;

/**
 *  停止移动 709
 *  @since  2017-06-06 12:01:57 
 */
@Handler(code=MsgCode.MoveStopReq)
public class MoveStopHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBMoveStopReq req = request.parseParams(PBMoveStopReq.newBuilder());
    }
}