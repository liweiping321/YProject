package com.road.fire.handler.fighting;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;
import com.game.protobuf.MsgCode;

import com.game.protobuf.fighting.FightingProto.PBTalentUpLevelReq;

/**
 *  天赋升级 751
 *  @since  2017-06-06 11:30:58 
 */
@Handler(code=MsgCode.TalentUpLevelReq)
public class TalentUpLevelHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBTalentUpLevelReq req = request.parseParams(PBTalentUpLevelReq.newBuilder());
        
        hero.getTalentManager().upLevel(req.getTalentId());
		//发送升级成功消息
        hero.sendMsg(MsgCode.TalentUpLevelSucResp);
    }
}