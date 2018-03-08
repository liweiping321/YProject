package com.road.fire.fighting.talent.impl;

import com.road.fire.entity.cfg.TalentCfg;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.talent.BaseTalent;
/**
 * 属性类天赋
 * @author lip.li
 *
 */
public class PropTalent extends BaseTalent {

	private int changeValue=0;
	
	
	public PropTalent(Hero owner, int talentId) {
		super(owner, talentId);
		 
	}

	@Override
	public void onStart(long now) {
		TalentCfg talentCfg=getTalentCfg();
		changeValue=talentCfg.getPropValue();
		
		owner.getPropertyManager().addIntValue(talentCfg.getDetailType(), changeValue);
		owner.getPropertyManager().sendUpdatePropertyValue(true);
	}
 
	@Override
	public void onStop(long now) {
		TalentCfg talentCfg=getTalentCfg();
		
		owner.getPropertyManager().subIntValue(talentCfg.getDetailType(), changeValue);
		
		owner.getPropertyManager().sendUpdatePropertyValue(true);

	}

	 

}
