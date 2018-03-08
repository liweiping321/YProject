package com.road.fire.match;

import com.road.fire.consts.PlayerStatus;
import com.road.fire.entity.cfg.FightingTypeCfg;
import com.road.fire.fighting.consts.FightingConsts;
import com.road.fire.fighting.util.PhyIncrId;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
/**
 * 
 * @author lip.li
 *
 */
public class Room {
	
	private final int roomId;
		
	private final FightingTypeCfg fightigTypeCfg;
	
	private final Map<Integer,Team> teamMap;
	
	public Room(FightingTypeCfg fightigTypeCfg){
		this.fightigTypeCfg=fightigTypeCfg;
		this.roomId=PhyIncrId.incrAndGetPhyId();
		teamMap=new HashMap<>();
		
		initTeam();
	}

	private void initTeam() {
		for(int cmpi=1;cmpi<=fightigTypeCfg.getCmpNum();cmpi++){
			teamMap.put(cmpi, new Team(cmpi, fightigTypeCfg.getTeamNum()));
		}
		teamMap.put(FightingConsts.CampTypeMiddle, new Team(FightingConsts.CampTypeMiddle, fightigTypeCfg.getTeamNum()));
		
	}

	public FightingTypeCfg getFightignTypeCfg() {
		return fightigTypeCfg;
	}

	public Map<Integer, Team> getTeams() {
		return teamMap;
	}

	public void addMatchPlayer(int cmpType, PlayerMatchInfo matchPlayer) {
		teamMap.get(cmpType).addMatchPlayer(matchPlayer);
	}
	
	public TreeSet<Integer> getAvgElos() {
	
	    TreeSet<Integer> avlgElos=new TreeSet<Integer>();
		// 第0队不是玩家
		for (int i = 1; i<teamMap.size();i++)
		{
			int avlgElo=teamMap.get(i).calculateAVGElo();
			avlgElos.add(avlgElo);
		}
		return avlgElos;
	}

	public int getRoomId() {
		return roomId;
	}

	public FightingTypeCfg getFightigTypeCfg() {
		return fightigTypeCfg;
	}

	public Map<Integer, Team> getTeamMap() {
		return teamMap;
	}

	public void setPlayerStatus(PlayerStatus playerStatus) {
		   for(Team team:teamMap.values()){
			   team.setPlayerStatus(playerStatus);
		   }
		
	}
	
}
