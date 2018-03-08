
package com.game.core.net.session;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.game.cfg.entity.ConfigsCfg;
import com.game.consts.GameConsts;
import com.game.core.net.Request;
import com.game.core.net.Response;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.executor.ExecutorUtils;
import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;

/**
 * 
 * @author lip.li
 *
 */
public abstract class NetSession {
	
	private static final Logger LOG =LogManager.getLogger(NetSession.class);
	
	private long sessionId;
	/**连接对像是否已经通过权限认证**/
	protected boolean isValidate;
	private volatile long lastTime ;// 心跳检测使用
	private long initTime ;
	
	private Object onlinePlayer;
	protected IoSession client;
	private String address;

	private ConcurrentLinkedQueue<Request> requests;
	
 
	
	private Map<String,Object> attributeObjects=new HashMap<String,Object>();
	
	public NetSession(IoSession session) {
		this.client = session;
		requests = new ConcurrentLinkedQueue<Request>();
		address = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
 
		lastTime = System.currentTimeMillis();
		initTime = System.currentTimeMillis();
	}
	public long getInitTime() {
		return initTime;
	}
	 
	public void setAttribute(String key,Object value){
		attributeObjects.put(key, value);
	}
	public Object getAttribute(String key){
		return attributeObjects.get(key);
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
 
	public boolean isValidate() {
		return isValidate;
	}
	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
 
 
	public boolean isBindGameObject() {
		return onlinePlayer != null;
	}
 
 
	public String getAddress() {
		return address;
	}

 
	public void setAddress(String ads) {
		address = ads;
	}

 
	public <T> void setCurrentPlayer(T onlinePlayer) {
		this.onlinePlayer = onlinePlayer;
	}

 
	public <T> T getCurrentPlayer(Class<T> castType) {
		if (onlinePlayer != null) {
			return castType.cast(onlinePlayer);
		}
		return null;
	}

	public boolean isOpen() {
		if (client == null || !client.isConnected() || client.isClosing()) {
			return false;
		}
		if(GameConsts.GlobalCurrTime-lastTime>GameConsts.LoseHeartTime){
			return false;
		}

		return true;
	}
	public void sendMsg(final Response msg) {
		if (!isOpen()) {
			return;
		}
		WriteFuture channelFuture = client.write(msg);
		channelFuture.addListener(new IoFutureListener<WriteFuture>() {
			@Override
			public void operationComplete(WriteFuture arg0) {
				LOG.info("sessionId:{},address{}:成功发送消息code:{}",getSessionId(),getAddress(), msg.getCode());
				 
			}
		});
	}
	
	public void sendMsg(final int code,final GeneratedMessage protoMsg) {
		if (!isOpen()) {
			return;
		}
		
		Response msg=Response.valueOf((short)code, protoMsg);
		if(ConfigsCfg.showMsg){
			ExecutorUtils.getIns().execute(new Runnable() {
				public void run() {
					LOG.info("开始发送消息 address:{},sessionId:{},code:{},content:{}",
							getAddress(), getSessionId(), code,
							JsonFormat.printToString(protoMsg));
				}
			});
		}
		
		
		sendMsg(msg);
	}
	
	public void sendMsg(int code) {
		if (!isOpen()) {
			return;
		}
		Response msg=Response.valueOf((short)code);
		LOG.info("开始发送消息 address:{},sessionId:{},code:{}",getAddress(),getSessionId(),code);
		sendMsg(msg);
	}
	 
	public void sendMsg(final List<Response> msgs) {
		if (!isOpen()) {
			return;
		}
		
		if(CollectionUtils.isEmpty(msgs)){
			return ;
		}
		
		WriteFuture channelFuture = client.write(msgs);
		channelFuture.addListener(new IoFutureListener<WriteFuture>() {
			@Override
			public void operationComplete(WriteFuture arg0) {
				LOG.info("{}:发送消息:{}条",getAddress(), msgs.size());
			}
		});
	}

 

 
	public void addRequest(Request request) {
		requests.offer(request);
		 
	}

 
	public void executeRequest() {
		
		Request request =  requests.poll();
		while (request != null) {
			RequestHandler requestHandler =RequestHandlerCenter.getInstance().getHandler(request.getCode());
			if (requestHandler != null) {
				try {
					requestHandler.handle(this, request);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage(), e);
				}
			}
			request =  requests.poll();
		}
	}

 
	public void clearRequests() {
		requests.clear();
	}

 
	public void removeIOSessionMap() {
		if (client != null) {
			clearRequests();
			onlinePlayer = null;

		}

	}

 
	public void close() {
		if (isOpen()) {
			client.closeNow();
		}else{
			logout();
			removeIOSessionMap();
		}
	}

 
 
	public void logout() {
	 
		
	}
	public <T> T getClient(Class<T> sessionType) {
		 
		return sessionType.cast(client) ;
	}
}
