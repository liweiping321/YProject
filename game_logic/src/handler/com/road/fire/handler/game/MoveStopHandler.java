package com.road.fire.handler.game;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto.PBMoveStopReq;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.OnlinePlayer;

/**
 *  移动停止请求 611
 *  @since  2017-03-09 02:26:39 
 */
@Handler(code=MsgCode.MoveStopReq)
public class MoveStopHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        PBMoveStopReq req = request.parseParams(PBMoveStopReq.newBuilder());
    }
}