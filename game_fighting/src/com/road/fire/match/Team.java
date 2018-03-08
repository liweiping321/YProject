package com.road.fire.match;

import java.util.ArrayList;
import java.util.List;

import com.road.fire.consts.PlayerStatus;

/**
 * 
 * @author lip.li
 *
 */
public class Team {
  
	private int cmpType;
	
	private List<PlayerMatchInfo> matchInfos;
	
	public Team(int cmpType,int teamNum) {
		super();
		this.cmpType = cmpType;
		this.matchInfos=new ArrayList<>(teamNum);
	}

	public int getCmpType() {
		return cmpType;
	}

	public void setCmpType(int cmpType) {
		this.cmpType = cmpType;
	}

	public List<PlayerMatchInfo> getMatchInfos() {
		return matchInfos;
	}

	public void setMatchInfos(List<PlayerMatchInfo> matchInfos) {
		this.matchInfos = matchInfos;
	}

	public void addMatchPlayer(PlayerMatchInfo matchPlayer) {
		matchInfos.add(matchPlayer);
		
	}
	
	public int calculateAVGElo() {
		if (matchInfos.size() == 0) {
			return 0;
		}

		int sum = 0;
		for (PlayerMatchInfo player : matchInfos) {
			sum += player.getElo();
		}
		return sum / matchInfos.size();
	}

	public void setPlayerStatus(PlayerStatus playerStatus) {
		for (PlayerMatchInfo player : matchInfos) {
			player.getPlayerInfo().setStatus(playerStatus);
		}
	}

}
