package com.road.fire.handler.game;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto.PBSkillStartReq;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.OnlinePlayer;

/**
 *  使用技能 615
 *  @since  2017-03-20 12:03:00 
 */
@Handler(code=MsgCode.SkillStartReq)
public class SkillStartHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        PBSkillStartReq req = request.parseParams(PBSkillStartReq.newBuilder());
    }
}