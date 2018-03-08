package com.road.fire.consts;
/**
 *
 * @author lip.li
 *
 */
public enum PlayerStatus {
	
	idle(1),//空闲状态
	matching(2),//匹配中
	loading(3),//游戏加载中
	game(4);//游戏中
	
	private int value;

	private PlayerStatus(int value){
		this.value=value;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	
}
