package com.road.fire.match;

import java.util.HashSet;
import java.util.Set;

import com.game.core.net.ClientConnector;
import com.road.fire.core.session.Logic2MatchPlayer;

/**
 * 逻辑服务连接匹配服客户端
 * @author lip.li
 *
 */
public class Logic2MatchClient {
	
	private int serverId;
	/**匹配服务器所支持的战斗类型*/
	private Set<Integer> fightingTypeIds=new HashSet<Integer>();
	 
	private ClientConnector connector;
	
	private Logic2MatchPlayer matchPlayer;

	public ClientConnector getConnector() {
		return connector;
	}

	public void setConnector(ClientConnector connector) {
		this.connector = connector;
	}

	public Logic2MatchPlayer getMatchPlayer() {
		return matchPlayer;
	}

	public void setMatchPlayer(Logic2MatchPlayer matchPlayer) {
		this.matchPlayer = matchPlayer;
	}

	public Set<Integer> getFightingTypeIds() {
		return fightingTypeIds;
	}

	public void setFightingTypeIds(Set<Integer> fightingTypeIds) {
		this.fightingTypeIds = fightingTypeIds;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	
	
	

}
