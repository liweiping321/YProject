package com.road.fire.module;

import java.io.Serializable;

/**
 * 模块接口
 * @author jason.lin
 *
 */
public interface IModule extends Serializable{
	
	/**
	 * 缓存加载数据
	 */
	void loadCache();
	/**
	 * 加载数据
	 */
	void loadDB();
	/**
	 * 加载完数据后续处理
	 */
	void afterLoadDB();
	/**
	 * 上线
	 */
	void online();
	
	/**
	 * 初始化客户端数据
	 */
	void initClientData();
	
	/**周期执行*/
	void update(long now);
	
	/**
	 * 每日重置
	 */
	void dayReset();
	
	/**
	 * 退出登录
	 */
	void logout();
	
	/**
	 * 数据入库
	 */
	void save();
	

	
	
	
	
}
