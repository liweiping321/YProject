package com.road.fire.core.net;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteToClosedSessionException;

import com.game.core.net.Request;
import com.game.core.net.SessionKey;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.core.net.session.NetSession;
import com.road.fire.core.session.GamePlayer;
import com.road.fire.player.BaseOnlinePlayer;

public class LogicMinaHandler implements IoHandler {

	private static final Logger logger =LogManager.getLogger(LogicMinaHandler.class);
 

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {

		if (!throwable.getClass().equals(IOException.class) && throwable.getClass().getClass().equals(WriteToClosedSessionException.class)) {
			logger.error(throwable.getMessage(),throwable);
		} else {
			inputClosed(session);
		}
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		session.closeNow();
	}
 
	@Override
	public void messageReceived(IoSession session, Object paramObject) throws Exception {
		Request request = (Request) paramObject;
	 
		NetSession gamePlayer = GamePlayer.getPlayerSession(session);
		if (gamePlayer == null) {
			return;
		}
		gamePlayer.setLastTime(System.currentTimeMillis());
 
		RequestHandler requestMsgHandler = RequestHandlerCenter.getInstance().getHandler(request.getCode());
		if (requestMsgHandler == null) {
			logger.error("can't find MsgHandler code={}",request.getCode());
			return;
		}
		if (!requestMsgHandler.isEnable()) {
			logger.error("MsgHandler be closed code={}",request.getCode());
			return;
		}
	 
		if (gamePlayer.isValidate() || !requestMsgHandler.isNeedAuth()) {
			if (!requestMsgHandler.isSingleThread()) {
				try {
					requestMsgHandler.handle(gamePlayer, request);
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
				}
			} else {
				gamePlayer.addRequest(request);
			}
		}
		 
	}

	@Override
	public void messageSent(IoSession session, Object paramObject) throws Exception {
		 
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	
		GamePlayer gamePlayer = GamePlayer.getPlayerSession(session);
		if (gamePlayer != null) {
			BaseOnlinePlayer oninePlayer  = gamePlayer.getCurrentPlayer(BaseOnlinePlayer.class);
			if (oninePlayer != null) {
				oninePlayer.logout();
			} else {
				gamePlayer.removeIOSessionMap();
			}
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		GamePlayer gamePlayer=new GamePlayer(session);
		logger.info("远程地址连接:"+gamePlayer.getAddress());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
		if (session.getIdleCount(idleStatus) > 3) {
			// 发呆时间很久，强制断开
			session.setAttribute(SessionKey.ERROR, "发呆时间很久了，强制断开");
			// session.closeOnFlush();
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

}
