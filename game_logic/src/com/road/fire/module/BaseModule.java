package com.road.fire.module;

import com.road.fire.player.BaseOnlinePlayer;

/**
 * 模块基类
 * @author jason.lin
 */
public abstract class BaseModule<T extends BaseOnlinePlayer> implements IModule{
	private static final long serialVersionUID = 1L;
	protected T player;
	
	public BaseModule(T player){
		this.player = player;
	}
}
