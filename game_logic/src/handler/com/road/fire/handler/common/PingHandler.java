package com.road.fire.handler.common;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.common.CommonProto.PBPingReq;
import com.game.protobuf.common.CommonProto.PBPingResp;
import com.road.fire.core.handler.GamePlayerRequestHandler;
import com.road.fire.core.session.GamePlayer;

/**
 *  心跳客户端请求 1
 *  @since  2017-03-09 01:10:03 
 */
@Handler(code=MsgCode.PingReq)
public class PingHandler extends GamePlayerRequestHandler {

    @Override
    public void doHandle(GamePlayer player, Request request) throws Exception {
        PBPingReq req = request.parseParams(PBPingReq.newBuilder());
        
        
        PBPingResp.Builder pingResp= PBPingResp.newBuilder();
        pingResp.setClientTick(req.getClientTick());
        pingResp.setServerTick(System.currentTimeMillis());
        
        player.sendMsg(MsgCode.PingResp, pingResp.build());
    }
}