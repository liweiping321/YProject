package com.road.fire.handler.logic;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.session.FightingPlayer;
import com.road.fire.core.handler.FightingPlayerRequestHandler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.logic.LogicProto.PBLoginReq;

/**
 *  用户登录请求 501
 *  @since  2017-06-06 12:01:57 
 */
@Handler(code=MsgCode.LoginReq)
public class LoginHandler extends FightingPlayerRequestHandler {

    @Override
    public void doHandle(FightingPlayer player, Request request) throws Exception {
        PBLoginReq req = request.parseParams(PBLoginReq.newBuilder());
      
    }
}