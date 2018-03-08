package com.road.fire.core.line;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.road.fire.player.BaseOnlinePlayer;
 
public class LineOnlinePlayerManager<Player extends BaseOnlinePlayer> {
	
	private Map<Long, Player>  onlinePlayers=new ConcurrentHashMap<>();
	
	private Map<String,Player> nameOnlinePlayers=new ConcurrentHashMap<>();
	 
	public Player getPlayerById(Long	 playerId){
		return onlinePlayers.get(playerId);
	}
	public Player getPlayerByName(String playerName){
		return nameOnlinePlayers.get(playerName);
	}
	public void addPlayer(Player player){
		onlinePlayers.put(player.getPlayerId(), player);
		nameOnlinePlayers.put(player.getPlayerName(), player);
	 
	}
	public Player removePlayerById(long playerId){
		Player onlinePlayer= onlinePlayers.remove(playerId);
		 if(onlinePlayer!=null){
			 nameOnlinePlayers.remove(onlinePlayer.getPlayerName());
		 }
		 return onlinePlayer;
	}
	
	public void removePlayer(Player player){
		onlinePlayers.remove(player.getPlayerId());
		nameOnlinePlayers.remove(player.getPlayerName());
	}

	public int getOnlineCount() {
		return  onlinePlayers.size();
	}
	
	public Collection<Player> getAllPlayers(){
		return onlinePlayers.values();
	}
}
