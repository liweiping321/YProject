package com.road.fire.core.session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.ConcurrentHashSet;

import com.game.core.net.session.NetSession;

/**
 * 逻辑服连接session
 * @author lip.li
 *
 */
public class GamePlayer extends NetSession {
	
	public static final String  Key="palyer";
 
	public static final Set<GamePlayer> onlineGamePlayers = new ConcurrentHashSet<GamePlayer>();

	public static final Map<Long, GamePlayer> accountGamePlayers = new ConcurrentHashMap<Long, GamePlayer>();
 
	private boolean logout;
	
	
	public static GamePlayer getPlayerSession(IoSession session) {
		return (GamePlayer) session.getAttribute(Key);
	}

	public GamePlayer(IoSession session) {
		super(session);
		onlineGamePlayers.add(this);
		session.setAttribute(Key, this);
  
	}
 
 
	public void logout() {
		synchronized (this) {
			if (!logout) {
				logout = true;
				client.closeNow();
			}
		}

	}

 

	 

	@Override
	public void removeIOSessionMap() {
		if (client != null) {
			
			removeGamePlayer(getSessionId());
			onlineGamePlayers.remove(this);
			client.removeAttribute(Key);
			
			super.removeIOSessionMap();
		}

	}
 
	public static GamePlayer getGamePlayer(long accountId) {
		return accountGamePlayers.get(accountId);
	}

	public static void removeGamePlayer(long accountId) {
		accountGamePlayers.remove(accountId);
	}

	public static void putGamePlayer(long accountId, GamePlayer gamePlayer) {
		accountGamePlayers.put(accountId, gamePlayer);
		gamePlayer.setSessionId(accountId);
	}
  
}
