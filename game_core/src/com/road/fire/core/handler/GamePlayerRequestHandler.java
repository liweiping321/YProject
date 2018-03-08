package com.road.fire.core.handler;


import com.game.core.net.Request;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.session.GamePlayer;
import com.game.core.net.session.NetSession;

/**
 * 
 * @author lip.li
 *
 */
public abstract class GamePlayerRequestHandler extends RequestHandler {
 
	 @Override
	public void handle(NetSession session, Request request) throws Exception {
		 
		 doHandle((GamePlayer)session, request);
		 
		
	}
	public abstract void doHandle(GamePlayer gamePlayer,Request request)throws Exception;
	
	
}
