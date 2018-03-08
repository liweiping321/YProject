package com.road.fire.handler.game;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto.PBSkillInterruptReq;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.OnlinePlayer;

/**
 *  打断技能 641
 *  @since  2017-03-20 02:37:53 
 */
@Handler(code=MsgCode.SkillInterruptReq)
public class SkillInterruptHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        PBSkillInterruptReq req = request.parseParams(PBSkillInterruptReq.newBuilder());
    }
}