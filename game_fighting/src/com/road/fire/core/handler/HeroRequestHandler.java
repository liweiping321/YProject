package com.road.fire.core.handler;

import com.game.core.net.Request;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.session.NetSession;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.player.PlayerInfo;

/**
 * 
 * @author lip.li
 *
 */
public abstract class HeroRequestHandler extends RequestHandler {
	 

	@Override
	public void handle(NetSession session, Request request) throws Exception {
		PlayerInfo playerInfo = (PlayerInfo) session.getCurrentPlayer(PlayerInfo.class);
		if (playerInfo == null) {
			LOG.error("role authority not validate!");
			return;
		}
		
		synchronized (playerInfo) {
			Hero hero=playerInfo.getHero();
			if(null!=hero && !hero.isDead()){
				doHandle(hero, request);
			}else{
				LOG.error("hero is null ,playerId:{},playerStatus:{}",playerInfo.getPlayerData().getUserID(),playerInfo.getStatus());
			}
		}
		 
		 
	    
	}

	public abstract void doHandle(Hero hero, Request request) throws Exception;
}
