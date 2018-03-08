package com.road.fire.core.line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.game.core.net.Response;
import com.game.core.net.session.NetSession;
import com.road.fire.player.BaseOnlinePlayer;

public class LogicLine<Player extends BaseOnlinePlayer>{
   


	private LineOnlinePlayerManager<Player> onlineRoleManager;
	
	private LogicLineUpdateService<Player> updateService;

	private int id;
	
	public LogicLine(int id){
		 this.id=id;
	}
 
	public Player removePlayerById(long playerId) {
		Player  player=  onlineRoleManager.removePlayerById(playerId);
		 if(player!=null){
			 player.setLogicLine(null);
		 }
		 return player;
	}

 
	public Player getPlayerById(long playerId) {
	 
		return onlineRoleManager.getPlayerById(playerId);
	}

 
	public Player getPlayerByName(String playerName) {
	 
		return onlineRoleManager.getPlayerByName(playerName);
	}

 
	public int getOnlineCount() {
		 
		return onlineRoleManager.getOnlineCount();
	}

 
	public void broadcast(Response response) {
	    Collection<Player> onlinePlayers=	onlineRoleManager.getAllPlayers();
		for (Player onlinePlayer : onlinePlayers) {
			onlinePlayer.sendMsg(response);
		}
	}

 
	public Collection<Player> getAllPlayers() {
	 
		return onlineRoleManager.getAllPlayers();
	}
	 
	public void shutDown() {
		updateService.stop();
	}

 
	public void startUp(ScheduledThreadPoolExecutor executorScheduler) {
		onlineRoleManager=new LineOnlinePlayerManager<Player>();
		updateService=new LogicLineUpdateService<Player>(this,executorScheduler);
		updateService.start();
		
	}

 
	public boolean kickPlayerById(long playerId) {
	    BaseOnlinePlayer onlinePlayer=onlineRoleManager.getPlayerById(playerId);
	    if(onlinePlayer!=null){
	    	NetSession gamePlayer=onlinePlayer.getGamePlayer();
	    	if(gamePlayer!=null){
	    		gamePlayer.close();
	    	}
	    }
		return false;
	}
	
	public LineOnlinePlayerManager<Player> getOnlineRoleManager() {
		return onlineRoleManager;
	}

 
	public int getLineId() {
		return id;
	}

 
	public void killAllPlayers() {
		Collection<Player> onlineRoles=new ArrayList<>(onlineRoleManager.getAllPlayers());
		for (BaseOnlinePlayer onlineRole : onlineRoles) {
			NetSession gamePlayer=onlineRole.getGamePlayer();
			if(gamePlayer!=null){
				gamePlayer.close();
			}
		}
	}
 
	public void addPlayer(Player  player) {
		 onlineRoleManager.addPlayer(player);
		 player.setLogicLine(this);
	}
	 
	public void addFrameUpdateTask(Runnable task) {
		updateService.addTask(task);
	}
 
 
	public long getCurrentTime() {
	 
		return updateService.getCurrentTime();
	}
 
	public LogicLineUpdateService<Player> getLogicLineUpdateService() {
		return updateService;
	}
	

}
