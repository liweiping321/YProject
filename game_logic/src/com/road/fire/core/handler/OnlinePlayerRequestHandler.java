package com.road.fire.core.handler;

import com.game.core.net.Request;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.session.NetSession;
import com.road.fire.player.OnlinePlayer;

/**
 * 
 * @author lip.li
 *
 */
public abstract class OnlinePlayerRequestHandler extends RequestHandler {
	 

	@Override
	public void handle(NetSession session, Request request) throws Exception {
		OnlinePlayer onlinePlayer = (OnlinePlayer) session.getCurrentPlayer(OnlinePlayer.class);
		if (onlinePlayer == null) {
			LOG.error("role authority not validate!");
			return;
		}
		
		synchronized (onlinePlayer) {
			 doHandle(onlinePlayer,request);
		}
		 
		 
	    
	}

	public abstract void doHandle(OnlinePlayer role, Request request) throws Exception;
}
