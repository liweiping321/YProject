package com.game.ai.fsm;
 


/**
 * Base状态
 * @author lip.li
 *
 */
public abstract class BaseState<T> implements IState<T> {
	protected IStateMachine<T> fsm;
	protected T owner;
	public BaseState(IStateMachine<T> fsm, T owner) {
		this.fsm = fsm;
		this.owner =owner;
	}
	@Override
	public void onBegin() {

	}

	@Override
	public void onExit() {
     
	}

	@Override
	public void onUpdate(long now) {
	}
	
	public void destory()
	{
		owner=null;
		fsm=null;
	}
}
