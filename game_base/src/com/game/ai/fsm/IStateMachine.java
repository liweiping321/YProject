package com.game.ai.fsm;


/**
 * 状态机管理接口
 * @author zss
 *
 */
public interface IStateMachine<T> {
	/**
	 * 初始化状态机
	 */
	public void initFSM();
   /**
    * 获得当前状态
    * @return
    */
	public IState<T> getCurrMainState();
	/***
	 * 得到状态机得拥有者
	 * @return
	 */
	public T getOwer();
	/**
	 * 切换状态
	 * @param state
	 * @return
	 */
    public boolean switchState(FsmState state);
    /**
     * 帧频更新
     * @param now
     */
    public void update(long now);
    /**
     * 添加拒绝的主状态
     * @param state
     */
    public void addBanState(FsmState state);
    /**
     * 移除拒绝的主状态
     * @param state
     * @return
     */
    public void removeBanState(FsmState state);
    /**
     * 验证 状态是否被拒绝
     * @param state
     * @return
     */
    public boolean isBanState(FsmState state);
    /**
     * 释放资源
     */
    public void release();
}
