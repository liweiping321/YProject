package com.road.fire.handler.fighting;

import com.game.ai.fsm.FsmState;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBMoveReq;
import com.road.fire.FtMsgUtil;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.ai.move.MoveInfo;
import com.road.fire.fighting.consts.EffectConsts;
import com.road.fire.fighting.phy.living.Hero;

/**
 *  位移消息 707
 *  @since  2017-05-04 06:04:36 
 */
@Handler(code=MsgCode.MoveReq,singleThread=false)
public class MoveHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBMoveReq req = request.parseParams(PBMoveReq.newBuilder());
        
        if(hero.isSkillRigid()){//用户释放技能正在僵直中
          hero.sendMsg(MsgCode.MoveStopResp,FtMsgUtil.builderMoveStopResp(hero));
        	return ;
        }
        
        if(hero.hasEffectType(EffectConsts.TypeNotMove)){
        	hero.broadcastMsg(MsgCode.MoveStopResp,FtMsgUtil.builderMoveStopResp(hero));
        	return ;
        }
        
        //TODO 后续需要增加校验逻辑,判断客户端是否使用外挂
        long startTime= hero.getFrameTime();

        MoveInfo moveInfo=new MoveInfo(startTime, req.getStartX(), req.getStartY(), req.getMoveDir(), req.getFaceDir());
        
        hero.getMoveController().setMoveInfo(moveInfo);
        
        hero.switchState(FsmState.move);
    }
}