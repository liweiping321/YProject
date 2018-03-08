package com.road.fire.fighting.talent.impl;

import com.road.fire.entity.cfg.TalentCfg;
import com.road.fire.fighting.buff.BaseBuff;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.talent.BaseTalent;
/**
 * BUFF类型天赋
 * @author lip.li
 *
 */
public class BuffTalent extends BaseTalent {
 
	private BaseBuff baseBuff;
	
	public BuffTalent(Hero owner, int talentId) {
		super(owner, talentId);
	
	}

	@Override
	public void onStart(long now) {
	 
		TalentCfg talentCfg=getTalentCfg();
		
		owner.getBuffContainer().addBuff(talentCfg.getDetailType());
	}

	@Override
	public void onStop(long now) {
		
		owner.getBuffContainer().removeBuff(baseBuff);

	}



}
