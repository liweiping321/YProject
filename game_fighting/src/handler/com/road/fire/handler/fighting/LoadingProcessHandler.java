package com.road.fire.handler.fighting;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBLoadingProcessReq;
import com.road.fire.FtMsgUtil;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;

/**
 *  加载进度 703
 *  @since  2017-05-04 06:04:36 
 */
@Handler(code=MsgCode.LoadingProcessReq)
public class LoadingProcessHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBLoadingProcessReq req = request.parseParams(PBLoadingProcessReq.newBuilder());
        
        hero.setLoadingProcess(req.getProcess());
        
        hero.broadcastMsg(MsgCode.LoadingProcessResp, FtMsgUtil.builderLoadingProcessResp(hero));
       
    }
}