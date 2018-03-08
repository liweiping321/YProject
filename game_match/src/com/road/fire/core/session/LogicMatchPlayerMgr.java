package com.road.fire.core.session;

import java.util.Set;

import org.apache.mina.util.ConcurrentHashSet;

/**
 * 逻辑服连接Session管理
 * @author lip.li
 *
 */
public class LogicMatchPlayerMgr {
	public  Set<MatchPlayer> matchPlayers = new ConcurrentHashSet<MatchPlayer>();
}
