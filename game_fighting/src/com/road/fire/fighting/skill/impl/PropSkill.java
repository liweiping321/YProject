package com.road.fire.fighting.skill.impl;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.container.SkillContainer;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropSkill extends ActiveSkill {

	public PropSkill(SkillCfg skillCfg,  Living<?>  owner,
			SkillContainer  skillContainer) {
		super(skillCfg, owner, skillContainer);
	 
	}
	
	@Override
	public void onFinish() {
		super.onFinish();
		skillContainer.addActiveSkill(getIndex(), null);
		
		owner.sendMsg(MsgCode.SkillUpdateResp, FtMsgUtil.builderSkillUpdateResp(owner));
		
	}
	@Override
	public Living<?> assistSelectTarget() {
		 return null;
	}
}
