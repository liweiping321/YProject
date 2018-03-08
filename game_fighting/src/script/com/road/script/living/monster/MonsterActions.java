package com.road.script.living.monster;

import java.util.HashMap;
import java.util.Map;

import com.road.fire.fighting.phy.living.Monster;
import com.road.script.living.monster.action.MonsterAction;
/**
 * 怪物Action列表
 * @author lip.li
 *
 */
public class MonsterActions {

	private Map<String,MonsterAction> actionMap=new HashMap<String,MonsterAction>();
	
	
	
	
	public void execute(Monster monster,Class<? extends MonsterAction> clazz){
		MonsterAction monsterAction=  actionMap.get(clazz.getName());
		if(null!=monsterAction){
			monsterAction.onExcute(monster);
		}
	}
	
	public void register(String clazzName) throws Exception{
		
		 MonsterAction  monsterAction=(MonsterAction) Class.forName(clazzName).newInstance();
		
		 actionMap.put(clazzName,monsterAction );
	}
	
	public void registerAll()throws Exception{
		register("com.road.script.living.monster.action.InitMonsterPointAction");
	}
	
	
	private static final MonsterActions ins=new MonsterActions();

	public static MonsterActions getIns() {
		return ins;
	}
	
	
}
