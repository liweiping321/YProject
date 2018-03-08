package com.road.script.living.monster.composite;

import com.road.fire.entity.cfg.MonsterCfg;
import com.road.fire.entity.cfg.SceneMonsterCfg;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.phy.scene.GameScene;
import com.road.fire.fighting.phy.scene.MapPoint;
import com.road.script.living.monster.MonsterActions;
import com.road.script.living.monster.action.InitMonsterPointAction;
/**
 * 箱子怪物
 * @author lip.li
 *
 */
public class BoxAction extends MonsterCompositeAction {

	@Override
	public void onInit(Monster monster) {
		//初始化怪物暂用的地图点
		MonsterActions.getIns().execute(monster, InitMonsterPointAction.class);
		 
	}

	 

}
