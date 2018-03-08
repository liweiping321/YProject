package com.road.fire.fighting.skill.container;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.SkillCfgProvider;
import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.entity.cfg.WeaponCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.SkillFactory;

/***
 * 英雄技能容器
 * @author lip.li
 *
 */
public class HeroSkillContainer extends SkillContainer<Hero> {
	
	public HeroSkillContainer(Hero owner, BaseGame game) {
		super(owner, game);
		 
	}
    @Override
    public void init() {
    	//初始化武器技能
    	WeaponCfg weaponCfg=owner.getWeaponCfg();

		ActiveSkill<?> activeSkill = SkillFactory.getInstace().createActiveSkill(weaponCfg.getSkillCfg(), owner, this);
	    activeSkill.setWeaponSkill(true);
	    
	    addActiveSkill(0, activeSkill); 
	    addActiveSkill(1, null);
	    addActiveSkill(2, null);
	    addActiveSkill(3, null);
		 
    }
    
    @Override
    public void addPropSkill(int skillId) {
    	int index =getFreeIndex();
    	
    	if(index<0){
    		return ;
    	}
    	
    	SkillCfg skillCfg=SkillCfgProvider.getInstance().getConfigVoByKey(skillId);
 		if(null!=skillCfg){
			ActiveSkill<?> activeSkil= SkillFactory.getInstace().createActiveSkill(skillCfg, owner, this);
			addActiveSkill(index, activeSkil);
		}
		
    	owner.sendMsg(MsgCode.SkillUpdateResp, FtMsgUtil.builderSkillUpdateResp(owner));
    }
    /**获取空技能槽*/
	private int getFreeIndex() {
		 for(int index=1;index<=3;index++){
			ActiveSkill<?> activeSkill= activeSkillMap.get(index);
			if(null==activeSkill){
				return index;
			}
		 }
		return -1;
	}
	
	@Override
	public boolean hasFreeIndex() {
		 for(int index=1;index<=3;index++){
			 ActiveSkill<?> activeSkill= activeSkillMap.get(index);
				if(null==activeSkill){
					return true;
				}
		 }
		 return false;
	}
}
