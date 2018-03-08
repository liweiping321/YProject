package com.road.script.living.monster;

import java.util.HashMap;
import java.util.Map;

import com.road.fire.fighting.consts.MonsterConsts;
import com.road.script.living.monster.composite.BoxAction;
import com.road.script.living.monster.composite.MonsterCompositeAction;

/**
 * 怪物组合Action列表
 * @author lip.li
 *
 */
public class CompositeActions {
	
	private final  Map<Integer,MonsterCompositeAction> compositeActions=new HashMap<>();

	public void registerAll(){
		compositeActions.put(MonsterConsts.TypeBox, new BoxAction());
	}
	
	public MonsterCompositeAction getAction(int monsterType){
		return compositeActions.get( monsterType);
	}
	
	
	private static final CompositeActions ins=new CompositeActions();


	public static CompositeActions getIns() {
		return ins;
	}
	
	
}
