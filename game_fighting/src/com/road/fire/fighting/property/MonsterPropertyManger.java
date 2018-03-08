package com.road.fire.fighting.property;

import com.game.property.PropertyConstants;
import com.road.fire.entity.cfg.MonsterCfg;
import com.road.fire.fighting.phy.living.Monster;
/**
 * 英雄属性管理器
 * @author lip.li
 *
 */
public class MonsterPropertyManger extends FightingPropertyManger<Monster>  {

	public MonsterPropertyManger(Monster owner) {
		super(owner);
		
		
	}

	@Override
	public void initializePropertyValue() {
		  MonsterCfg monsterCfg=owner.getMonsterCfg();

		  setIntValue(PropertyConstants.MaxHp,monsterCfg.getMaxHp());
		  setIntValue(PropertyConstants.CurrHp,getIntValue(PropertyConstants.MaxHp));
		  setIntValue(PropertyConstants.Attack,monsterCfg.getAttack());
		  setIntValue(PropertyConstants.Defense,monsterCfg.getDefense());
		  setIntValue(PropertyConstants.MoveSpeed,monsterCfg.getMoveSpeed());
		  setIntValue(PropertyConstants.CritRate,monsterCfg.getCritRate());
		  setIntValue(PropertyConstants.OpposeCrit,monsterCfg.getOpposeCrit());
		  setIntValue(PropertyConstants.BreachPower,monsterCfg.getBreachPower());
 
	}
}
