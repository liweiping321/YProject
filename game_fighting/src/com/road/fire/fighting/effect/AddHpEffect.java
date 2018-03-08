package com.road.fire.fighting.effect;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.entity.cfg.EffectCfg;
import com.road.fire.fighting.consts.HurtType;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.CastResult;
/**
 * 回血
 * @author lip.li
 *
 */
public class AddHpEffect extends BaseEffect {

	public AddHpEffect(EffectCfg effectCfg) {
		super(effectCfg);

	}

	@Override
	public void onStart() {
 
		
	}

	@Override
	public void update(long now) {
		
		Living<?> target=buff.getTarget();
		Living<?> source=buff.getSource();
		if(target.isDie()){
			return ;
		}
		int addHp= target.addHp(effectCfg.getParam1(), true);
		
		if(addHp>0){
			
			CastResult castResult=new CastResult(source,target,HurtType.ResultTypeCure,addHp);
			//给释放者发送伤害
			source.sendMsg(MsgCode.SkillResultResp, FtMsgUtil
					.builderSkillResultResp(source.getPhyId(), castResult));
		}
		
		   
		
		 
	}
	@Override
	protected void onStop() {
		 

	}

}
