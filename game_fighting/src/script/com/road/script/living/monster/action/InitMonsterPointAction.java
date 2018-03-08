package com.road.script.living.monster.action;

import com.road.fire.entity.cfg.MonsterCfg;
import com.road.fire.entity.cfg.SceneMonsterCfg;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.phy.scene.GameScene;
import com.road.fire.fighting.phy.scene.MapPoint;

public class InitMonsterPointAction extends MonsterAction{

	@Override
	public void onExcute(Monster monster) {
		GameScene scene = monster.getScene();
		SceneMonsterCfg sceneMonsterCfg = monster.getSceneMonsterCfg();
		MonsterCfg monsterCfg = monster.getMonsterCfg();

		int x1 = scene.getTileX(sceneMonsterCfg.getX()
				- monsterCfg.getDamageRadius());
		int x2 = scene.getTileX(sceneMonsterCfg.getX()
				+ monsterCfg.getDamageRadius());

		int y1 = scene.getTileY(sceneMonsterCfg.getY()
				- monsterCfg.getDamageRadius());
		int y2 = scene.getTileY(sceneMonsterCfg.getY()
				+ monsterCfg.getDamageRadius());

		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				monster.getMonsterPoints().add(new MapPoint(x, y));
			}
		}
		
	}

}
