package com.road.fire.handler.fighting;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;
import com.game.protobuf.MsgCode;

import com.game.protobuf.fighting.FightingProto.PBLoadingProcessReq;

/**
 *  加载进度 703
 *  @since  2017-06-06 12:01:57 
 */
@Handler(code=MsgCode.LoadingProcessReq)
public class LoadingProcessHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBLoadingProcessReq req = request.parseParams(PBLoadingProcessReq.newBuilder());
    }
}