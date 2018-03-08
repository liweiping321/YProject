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
import com.road.fire.core.session.Logic2MatchPlayer;
import com.road.fire.match.Logic2MatchClient;
 

public class Logic2MatchMinaHandler implements IoHandler {
	
	private static final Logger logger =LogManager.getLogger(Logic2MatchMinaHandler.class);
	 
	
	private Logic2MatchClient matchClient;
	
	public Logic2MatchMinaHandler(Logic2MatchClient matchClient){
		this.matchClient=matchClient;
	}

	

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
	 
		Logic2MatchPlayer logic2MatchPlayer = Logic2MatchPlayer.getPlayerSession(session);
		if (logic2MatchPlayer == null) {
			return;
		}
		logic2MatchPlayer.setLastTime(System.currentTimeMillis());
 
		RequestHandler requestMsgHandler = RequestHandlerCenter.getInstance().getHandler(request.getCode());
		if (requestMsgHandler == null) {
			logger.error("can't find MsgHandler code={}",request.getCode());
			return;
		}
		if (!requestMsgHandler.isEnable()) {
			logger.error("MsgHandler be closed code={}",request.getCode());
			return;
		}
	 
		requestMsgHandler.handle(logic2MatchPlayer, request);
		 
	}

	@Override
	public void messageSent(IoSession session, Object paramObject) throws Exception {
		 
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	
		Logic2MatchPlayer matchPlayer = Logic2MatchPlayer.getPlayerSession(session);
		if(matchPlayer!=null){
			matchPlayer.removeIOSessionMap();
			matchClient.setMatchPlayer(null);
		}
		
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		Logic2MatchPlayer logic2MatchPlayer=new Logic2MatchPlayer(session);
		matchClient.setMatchPlayer(logic2MatchPlayer);
		logger.info("连接匹配服:"+logic2MatchPlayer.getAddress());
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
