package com.road.fire.fighting;

import com.game.fighting.AbstractGameMgr;
/**
 * 游戏管理类
 * @author lip.li
 *
 */
public class GameMgr extends AbstractGameMgr {

	
	private static final   GameMgr instance=new GameMgr();

	public static GameMgr getInstance() {
		return instance;
	}
	
}
