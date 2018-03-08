package com.road.fire.player;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.Response;
import com.game.core.net.session.NetSession;
import com.game.db.asyn.AsynObj;
import com.google.protobuf.GeneratedMessage;
import com.road.fire.core.line.LogicLine;
import com.road.fire.module.IModule;

/**
 * 在线玩家
 * @author jason.lin
 *
 */
public abstract class BaseOnlinePlayer extends AsynObj implements Serializable{
	
	private static final Logger LOG =LogManager.getLogger(BaseOnlinePlayer.class);
	
	private static final long serialVersionUID = 1L;

	// 玩家id
	protected long playerId;
	
	protected String playerName;

	protected NetSession gamePlayer;
	 

 
	protected LogicLine<?> logicLine;
	// 模块列表
	protected Map<Class<?>, IModule> moduleMap = new LinkedHashMap<>();
	
	// 模块初始化
	public abstract void initModules();

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	/**
	 * 添加模块
	 */
	public void addModule(IModule module){
		moduleMap.put(module.getClass(), module);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getModule(Class<T> T){
		return (T)moduleMap.get(T);
	}
	/**
	 * 加载数据
	 */
	public boolean loadDB(){
		for(IModule module : moduleMap.values()){
			try {
				module.loadDB();
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 每日重置
	 */
	public boolean dayReset(){
		for(IModule module : moduleMap.values()){
			try {
				module.dayReset();
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 数据加载之后
	 */
	public boolean afterLoadDB(){
		for(IModule module : moduleMap.values()){
			try {
				module.afterLoadDB();
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 上线处理
	 */
	public boolean online(){
		for(IModule module : moduleMap.values()){
			try {
				module.online();
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				return false;
			}
		}
		return true;
	}
	/**
	 * 数据入库
	 */
	public boolean save(){
		for(IModule module : moduleMap.values()){
			try {
				module.save();
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				return false;
			}
		}
		return true;
	}
	
	 
	public void logout(){
		
	}
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public NetSession getGamePlayer() {
		return gamePlayer;
	}

	public void setGamePlayer(NetSession gamePlayer) {
		this.gamePlayer = gamePlayer;
	}

	public static String getChannelAccountKey(String channel, String areaId, String uid){
		return String.format("%s_%s_%s", channel, areaId, uid);
	}

	
	public void setLogicLine(LogicLine<?> line) {
		 this.logicLine=line;
		
	}

	public void sendMsg(int code,GeneratedMessage protoMsg) {
		if(null==gamePlayer){
			return ;
		}
		gamePlayer.sendMsg(code, protoMsg);
	}
	
	public void sendMsg(int code) {
		if(null==gamePlayer){
			return ;
		}
		 gamePlayer.sendMsg(code);
	}
	
	public void sendMsg(Response msg) {
		if(null==gamePlayer){
			return ;
		}
		 gamePlayer.sendMsg(msg);
	}
	@Override
	public long getModId() {
		return playerId; 
	}
}
