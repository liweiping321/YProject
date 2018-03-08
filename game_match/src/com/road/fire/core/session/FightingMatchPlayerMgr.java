package com.road.fire.core.session;

import java.util.Set;

import org.apache.mina.util.ConcurrentHashSet;

/**
 *战斗服连接Session管理
 * @author lip.li
 *
 */
public class FightingMatchPlayerMgr {
    
	
	public  Set<MatchPlayer> matchPlayers = new ConcurrentHashSet<MatchPlayer>();
	
	
	public void addPlayer(MatchPlayer matchPlayer){
		matchPlayers.add(matchPlayer);
	}
	
	public void removePlayer(MatchPlayer matchPlayer){
		matchPlayers.remove(matchPlayer);
	}
	
	private static final FightingMatchPlayerMgr instance=new FightingMatchPlayerMgr();

	public static FightingMatchPlayerMgr getInstance() {
		return instance;
	}
	
	
}
