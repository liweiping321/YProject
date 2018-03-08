package com.game.ai.fsm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 状态机管理器
 * @author lip.li
 *
 */
public abstract class StateMachineImpl<T> implements IStateMachine<T> {
	private static Logger LOG=LogManager.getLogger(StateMachineImpl.class);
	
	protected T owner;
	/**
	 * 主状态机 集合
	 */
	protected Map<Integer, IState<T>> stateMap = new HashMap<>();
	/**
	 * 是否已经初始化
	 */
	protected boolean init = false;
	/**
	 * 当前状态
	 */
	protected IState<T> currentState;
	/**
	 * 被禁止的状态
	 */
	protected Map<Integer, Boolean> banMainStateMap = new HashMap<>();

	public StateMachineImpl(T owner) {
		this.owner = owner;
	}

	@Override
	public abstract void initFSM();
	
	public void addState(IState<T> state){
		stateMap.put(state.getState().getId(), state);
	}

	@Override
	public IState<T> getCurrMainState() {
		return currentState;
	}

	@Override
	public T getOwer() {
		return owner;
	}

	@Override
	public boolean switchState(FsmState mainState) {
		if(isBanState(mainState))
		{
			return false;
		}
		IState<T> state = stateMap.get(mainState.getId());
		if (state == null) {
			return false;
		}
		if (currentState == state) {
			return false;
		}
		if (currentState != null) {
			currentState.onExit();
		}
		currentState = state;
		state.onBegin();
		return true;
	}

	@Override
	public void update(long now) {
		if (currentState != null) {
			try {
				currentState.onUpdate(now);
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * 添加拒绝的主状态
	 * 
	 * @param state
	 */
	public void addBanState(FsmState state)
	{
		banMainStateMap.put(state.getId(), true);
	}
	/**
	 * 移除拒绝的主状态
	 * 
	 * @param state
	 * @return
	 */
	public void removeBanState(FsmState state)
	{
		banMainStateMap.remove(state.getId());
	}
	/**
	 * 验证 状态是否被拒绝
	 * 
	 * @param state
	 * @return
	 */
	public boolean isBanState(FsmState state){
		Boolean isban=banMainStateMap.get(state.getId());
		if(isban==null||isban==false)
		{
			return false;
		}
		return true;
	}
	@Override
	public void release() {
		owner=null;
		Collection<IState<T>> collection=stateMap.values();
		for (IState<T> state:collection) {
			state.destory();
		}
		stateMap.clear();
		currentState=null;
		banMainStateMap.clear();
	}

}
