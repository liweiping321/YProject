package com.road.fire.core.session;

import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.ConcurrentHashSet;

import com.game.core.net.session.NetSession;

/**
 * 匹配服连接session
 * @author lip.li
 *
 */
public class MatchPlayer extends NetSession {
	public static final String  Key="match";
	
	public static final Set<MatchPlayer> matchPlayers = new ConcurrentHashSet<MatchPlayer>();

	public MatchPlayer(IoSession session) {
		super(session);
		session.setAttribute(Key, this);
		matchPlayers.add(this);
		
	}
	public static MatchPlayer getPlayerSession(IoSession session) {
		return (MatchPlayer) session.getAttribute(Key);
	}
	
	
	@Override
	public void removeIOSessionMap() {
		if (client != null) {
			
			client.removeAttribute(Key);
			
			super.removeIOSessionMap();
			
			matchPlayers.remove(this);
			
			FightingMatchPlayerMgr.getInstance().removePlayer(this);
		}

	}
  
}
