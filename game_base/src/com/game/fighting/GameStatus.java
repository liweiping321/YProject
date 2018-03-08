package com.game.fighting;
/**
 * 游戏状态
 * @author lip.li
 *
 */
public enum GameStatus {

	init(1),//初始状态
	loading(2),//加载中
	playerReady(3),//玩家进入游戏，准备中
	fighting(4),//游戏战斗中
	computing (5),//结算中
	gameOver(6);//游戏结束
	

	private int value;

	private GameStatus(int value){
		this.value=value;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	
}
