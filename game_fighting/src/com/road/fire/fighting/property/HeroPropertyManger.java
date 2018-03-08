package com.road.fire.fighting.property;

import com.game.property.PropertyConstants;
import com.road.fire.cfg.provider.HeroLevelCfgProvider;
import com.road.fire.entity.cfg.HeroCfg;
import com.road.fire.entity.cfg.HeroLevelCfg;
import com.road.fire.entity.cfg.WeaponCfg;
import com.road.fire.fighting.phy.living.Hero;
/**
 * 英雄属性管理器
 * @author lip.li
 *
 */
public class HeroPropertyManger extends FightingPropertyManger<Hero> {

	public HeroPropertyManger(Hero owner) {
		super(owner);
		
		
	}

	@Override
	public void initializePropertyValue() {
		  HeroCfg heroCfg= owner.getHeroCfg();
		  WeaponCfg weaponCfg=owner.getWeaponCfg();
		  HeroLevelCfg levelCfg=HeroLevelCfgProvider.getInstance().getHeroLeveLCfg(heroCfg.getHeroId(),heroCfg.getLevel());
		  
		  setIntValue(PropertyConstants.Level, heroCfg.getLevel());
		  setIntValue(PropertyConstants.CurrExp, 0);
		  setIntValue(PropertyConstants.MaxHp,levelCfg.getMaxHp());
		  setIntValue(PropertyConstants.CurrHp,getIntValue(PropertyConstants.MaxHp));
		  setIntValue(PropertyConstants.Attack,levelCfg.getAttack());
		  setIntValue(PropertyConstants.Defense,levelCfg.getDefense());
		  setIntValue(PropertyConstants.MoveSpeed,levelCfg.getMoveSpeed());
		  setIntValue(PropertyConstants.MoveReduce,levelCfg.getMoveReduce());
		  setIntValue(PropertyConstants.ReloadTime,weaponCfg.getReloadTime());
		  setIntValue(PropertyConstants.ClipSize,weaponCfg.getClipSize());
		  setIntValue(PropertyConstants.currClipSize,weaponCfg.getClipSize());
		  setIntValue(PropertyConstants.BreachPower,weaponCfg.getBreachPower());
 
	}

	public void upLevel(HeroLevelCfg oldLevel, HeroLevelCfg levelCfg) {
		 setIntValue(PropertyConstants.Level, levelCfg.getLevel());
		 setIntValue(PropertyConstants.CurrExp, 0);
		
		 subIntValue(PropertyConstants.MaxHp,oldLevel.getMaxHp());
		 subIntValue(PropertyConstants.Attack,oldLevel.getAttack());
		 subIntValue(PropertyConstants.Defense,oldLevel.getDefense());
		 subIntValue(PropertyConstants.MoveSpeed,oldLevel.getMoveSpeed());
		 subIntValue(PropertyConstants.MoveReduce,oldLevel.getMoveReduce());
		 
		 addIntValue(PropertyConstants.MaxHp,levelCfg.getMaxHp());
		 addIntValue(PropertyConstants.Attack,levelCfg.getAttack());
		 addIntValue(PropertyConstants.Defense,levelCfg.getDefense());
		 addIntValue(PropertyConstants.MoveSpeed,levelCfg.getMoveSpeed());
		 addIntValue(PropertyConstants.MoveReduce,levelCfg.getMoveReduce());
		
	}
	
	
}
