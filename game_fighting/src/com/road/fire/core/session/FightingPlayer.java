package com.road.fire.core.session;

import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.ConcurrentHashSet;

import com.game.core.net.session.NetSession;

/**
 * 战斗服连接session
 * 
 * @author lip.li
 * 
 */
public class FightingPlayer extends NetSession {

	public static final String Key = "fighting";

	public static final Set<FightingPlayer> matchPlayers = new ConcurrentHashSet<FightingPlayer>();

	public FightingPlayer(IoSession session) {
		super(session);
		session.setAttribute(Key, this);
		matchPlayers.add(this);

	}

	public static FightingPlayer getPlayerSession(IoSession session) {
		return (FightingPlayer) session.getAttribute(Key);
	}

}
