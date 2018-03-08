package com.game.ai.fsm;


/**
 * 状态机接口
 * @author lip.li
 */
public interface IState<T> {
	/**
	 * 当进入此AI状态时触发
	 */
	public void onBegin();
	/**
	 * 当需要更新时触发
	 * @param now TODO
	 */
	public void onUpdate(long now);
	/**
	 * 当退出此AI状态时触发
	 */
	public void onExit();
	/**
	 * 状态标志
	 * @return
	 */
	public FsmState getState();
	
	public void destory();
}
