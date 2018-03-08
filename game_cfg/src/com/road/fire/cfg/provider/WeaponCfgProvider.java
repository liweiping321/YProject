package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.entity.cfg.WeaponCfg;

/**
 *@author 工具生成
 *@date 2017-05-04 10:07:39 
 */
public class WeaponCfgProvider extends BaseCfgEntiyProvider<Integer,WeaponCfg> {
	
 
	@Override
	public void afterReload(WeaponCfg value) {
		SkillCfg skillCfg=SkillCfgProvider.getInstance().getConfigVoByKey(value.getSkillId());
		if(skillCfg==null){
			throw new RuntimeException("weapon cfg skill not exists ,weaponId:{"+value.getWeaponId()+"},skillId:{"+value.getSkillId()+"}");
		}
		
		value.setSkillCfg(skillCfg);
	}
	
	public Class<?> getCfgClass(){
		
		return WeaponCfg.class;
	}

	private static final WeaponCfgProvider instance=new WeaponCfgProvider();

	public static WeaponCfgProvider getInstance() {
		return instance;
	}
	
	
}
