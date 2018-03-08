package com.road.fire.handler.fighting;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.consts.EffectConsts;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.skill.SkillContext;
import com.game.protobuf.MsgCode;

import com.game.protobuf.fighting.FightingProto.PBSkillUseReq;

/**
 *  使用技能 725
 *  @since  2017-05-11 09:48:09 
 */
@Handler(code=MsgCode.SkillUseReq)
public class SkillUseHandler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        PBSkillUseReq req = request.parseParams(PBSkillUseReq.newBuilder());
        
        if(hero.isSkillRigid()||hero.isSkillReleaseIn()){//用户正在释放技能中
        	return ;
        }
        
        if(hero.hasEffectType(EffectConsts.IdNotAttack)){//不能攻击
        	return ;
        }
        //技能槽技能为空
        if(!hero.getSkillContainer().hasSkill(req.getIndex())){
        	return ;
        }
         
        SkillContext skillContext=new SkillContext(req.getIndex(),req.getSkillAngle(),req.getStartX(),req.getStartY(),req.getTargetX(),req.getTargetY());
        
        hero.getSkillContainer().useSkill(skillContext);
        
    }
}