package com.road.fire.core.session;

import org.apache.mina.core.session.IoSession;

import com.game.core.net.session.NetSession;

/**
 * 战斗服连接匹配服务器session
 * @author lip.li
 *
 */
public class Fighting2MatchPlayer extends NetSession {
	public static final String  Key="Fighting2Match";

	public Fighting2MatchPlayer(IoSession session) {
		super(session);
		session.setAttribute(Key, this);
		
	}
	public static Fighting2MatchPlayer getPlayerSession(IoSession session) {
		return (Fighting2MatchPlayer) session.getAttribute(Key);
	}
	
	
	@Override
	public void removeIOSessionMap() {
		if (client != null) {
			
			client.removeAttribute(Key);
			
			super.removeIOSessionMap();
		}

	}
  
}
