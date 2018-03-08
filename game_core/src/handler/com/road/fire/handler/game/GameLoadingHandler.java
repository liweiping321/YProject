package com.road.fire.handler.game;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto.PBGameLoadingReq;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.OnlinePlayer;

/**
 *  上报加载进度 603
 *  @since  2017-03-20 12:03:00 
 */
@Handler(code=MsgCode.GameLoadingReq)
public class GameLoadingHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        PBGameLoadingReq req = request.parseParams(PBGameLoadingReq.newBuilder());
    }
}