package com.road.fire.handler.game;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto.PBLivingMoveReq;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.OnlinePlayer;

/**
 *  移动请求 609
 *  @since  2017-03-09 02:26:39 
 */
@Handler(code=MsgCode.LivingMoveReq)
public class LivingMoveHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        PBLivingMoveReq req = request.parseParams(PBLivingMoveReq.newBuilder());
    }
}