package com.road.fire.handler.fighting;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBFaceDirReq;
import com.road.fire.FtMsgUtil;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;

/**
 *  同步朝向 719
 *  @since  2017-05-09 05:19:36 
 */
@Handler(code=MsgCode.FaceDirReq)
public class FaceDirHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBFaceDirReq req = request.parseParams(PBFaceDirReq.newBuilder());
        hero.setFaceDir(req.getFaceDir());
        hero.broadcastMsg(MsgCode.FaceDirResp,FtMsgUtil.builderFaceDirResp(hero), hero);
    }
}