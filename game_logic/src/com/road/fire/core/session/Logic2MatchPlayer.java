package com.road.fire.core.session;

import org.apache.mina.core.session.IoSession;

import com.game.core.net.session.NetSession;

/**
 * 
 * 逻辑服连接匹配服务器session
 * @author lip.li
 *
 */
public class Logic2MatchPlayer extends NetSession {
	public static final String  Key="Logic2Match";

	public Logic2MatchPlayer(IoSession session) {
		super(session);
		session.setAttribute(Key, this);
		
	}
	public static Logic2MatchPlayer getPlayerSession(IoSession session) {
		return (Logic2MatchPlayer) session.getAttribute(Key);
	}
	
	
	@Override
	public void removeIOSessionMap() {
		if (client != null) {
			
			client.removeAttribute(Key);
			
			super.removeIOSessionMap();
		}

	}
  
}
