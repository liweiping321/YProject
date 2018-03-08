package com.road.fire.handler.common;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.session.FightingPlayer;
import com.road.fire.core.handler.FightingPlayerRequestHandler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.common.CommonProto.PBPingReq;

/**
 *  心跳客户端请求 1
 *  @since  2017-06-06 12:01:57 
 */
@Handler(code=MsgCode.PingReq)
public class PingHandler extends FightingPlayerRequestHandler {

    @Override
    public void doHandle(FightingPlayer player, Request request) throws Exception {
        PBPingReq req = request.parseParams(PBPingReq.newBuilder());
      
    }
}