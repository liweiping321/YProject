package com.road.fire.player;

import com.google.protobuf.GeneratedMessage;
import com.road.fire.consts.PlayerStatus;
import com.road.fire.core.session.FightingPlayer;
import com.road.fire.entity.business.PlayerData;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.match.PlayerMatchInfo;

public class PlayerInfo {
	 
	private PlayerData playerData;
	
	private FightingPlayer player;
	/**玩家状态*/
	private PlayerStatus status=PlayerStatus.idle;
	
	private PlayerMatchInfo matchInfo;
	
	private BaseGame baseGame;
	
	private Hero hero;
	

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		if(status==PlayerStatus.idle){
		   clearGameInfo();
		}
		this.status = status;
	}
	
	private void clearGameInfo() {
		 matchInfo=null;
		 baseGame=null;
		 hero=null;
		
	}

	public PlayerMatchInfo getMatchInfo() {
		return matchInfo;
	}

	public void setMatchInfo(PlayerMatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}

	public BaseGame getBaseGame() {
		return baseGame;
	}

	public void setBaseGame(BaseGame baseGame) {
		this.baseGame = baseGame;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}

	public FightingPlayer getPlayer() {
		return player;
	}

	public void setPlayer(FightingPlayer player) {
		this.player = player;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}
	

	
	
	
	
	public void sendMsg(int code) {
		 if(player!=null){
			 player.sendMsg(code);
		 }
		
	}

	public void sendMsg(int code, GeneratedMessage msg) {
		 if(player!=null){
			 player.sendMsg(code,msg);
		 }
		
	}
	public void executeRequest() {
		 if(player!=null){
			 player.executeRequest();
		 }
		
	}
	
	public boolean isOnline(){
		if(player==null){
			return false;
		}
		
		return player.isOpen();
	
	}
	 
	public String getPlayerName(){
		return playerData.getNickname();
	}
	
}
