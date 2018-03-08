package com.game.ai.fsm;

/**
 * 
 * @author lip.li
 *
 */
public enum FsmState {
	/**
	 * 空闲
	 */
	idle(0),
	/**
	 * 死亡
	 */
	die(1),
	/**
	 * 移动
	 */
	move(2),
	/**
	 * 回城复活
	 */
	cityRelive(3);
	 
 
	
	private int id;
	private FsmState(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
}
