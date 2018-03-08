package com.road.fire.core.handler;


import com.game.core.net.Request;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.session.NetSession;
import com.road.fire.core.session.FightingPlayer;

/**
 * 
 * @author lip.li
 *
 */
public abstract class FightingPlayerRequestHandler extends RequestHandler {
 
	 @Override
	public void handle(NetSession session, Request request) throws Exception {
		 
		 doHandle((FightingPlayer)session, request);
		 
		
	}
	public abstract void doHandle(FightingPlayer gamePlayer,Request request)throws Exception;
	
	
}
