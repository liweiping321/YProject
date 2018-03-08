package com.road.fire.core.handler;

import com.game.core.net.Request;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.session.NetSession;
import com.road.fire.player.PlayerInfo;

/**
 * 
 * @author lip.li
 *
 */
public abstract class OnlinePlayerRequestHandler extends RequestHandler {
	 

	@Override
	public void handle(NetSession session, Request request) throws Exception {
		PlayerInfo playerInfo = (PlayerInfo) session.getCurrentPlayer(PlayerInfo.class);
		if (playerInfo == null) {
			LOG.error("role authority not validate!");
			return;
		}
		
		synchronized (playerInfo) {
			 doHandle(playerInfo,request);
		}
		 
		 
	    
	}

	public abstract void doHandle(PlayerInfo playerInfo, Request request) throws Exception;
}
