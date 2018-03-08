package com.road.fire.handler.match;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.player.OnlinePlayer;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.MsgCode;
import com.game.protobuf.match.MatchProto.PBStartMatchReq;

/**
 *  请求撮合加入游戏 601
 *  @since  2017-06-06 12:01:57 
 */
@Handler(code=MsgCode.StartMatchReq)
public class StartMatchHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        PBStartMatchReq req = request.parseParams(PBStartMatchReq.newBuilder());
    }
}