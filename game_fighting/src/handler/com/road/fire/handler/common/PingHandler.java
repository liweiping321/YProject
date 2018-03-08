package com.road.fire.handler.common;

import com.game.consts.GameConsts;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.session.FightingPlayer;
import com.road.fire.core.handler.FightingPlayerRequestHandler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.common.CommonProto.PBPingReq;
import com.game.protobuf.common.CommonProto.PBPingResp;

/**
 *  心跳客户端请求 1
 *  @since  2017-05-04 05:42:19 
 */
@Handler(code=MsgCode.PingReq,singleThread=false)
public class PingHandler extends FightingPlayerRequestHandler {

    @Override
    public void doHandle(FightingPlayer player, Request request) throws Exception {
        PBPingReq req = request.parseParams(PBPingReq.newBuilder());
      

        PBPingResp.Builder pingResp= PBPingResp.newBuilder();
        pingResp.setClientTick(req.getClientTick());
        pingResp.setServerTick(GameConsts.GlobalCurrTime);
        player.setLastTime(GameConsts.GlobalCurrTime);
        player.sendMsg(MsgCode.PingResp, pingResp.build());
   
    }
}