package com.road.fire.fighting.talent.impl;

import com.game.property.PropertyConstants;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.entity.cfg.TalentCfg;
import com.road.fire.fighting.consts.HurtType;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.skill.CastResult;
import com.road.fire.fighting.talent.BaseTalent;
/**
 * 回血类天赋
 * @author lip.li
 *
 */
public class AddHpTalent extends BaseTalent {

	 
	
	public AddHpTalent(Hero owner, int talentId) {
		super(owner, talentId);
		 
	}

	@Override
	public void onStart(long now) {
		 onUpdate(now);
	}
	@Override
	public void onUpdate(long now) {
		if(owner.isDie()){
			return ;
		}

		TalentCfg talentCfg = getTalentCfg();
		if(talentCfg.getDetailType()==PropertyConstants.CurrHp){
			 
			int addHp= owner.addHp(talentCfg.getPropValue(), true);
			
			if(addHp>0){
				
				CastResult castResult=new CastResult(owner,owner,HurtType.ResultTypeCure,addHp);
				//给释放者发送伤害
				owner.sendMsg(MsgCode.SkillResultResp, FtMsgUtil
						.builderSkillResultResp(owner.getPhyId(), castResult));
			}
		}
 
	}

	@Override
	public void onStop(long now) {
		 
		
	}

	 

}
