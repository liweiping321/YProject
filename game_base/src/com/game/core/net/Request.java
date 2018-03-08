package com.game.core.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.cfg.entity.ConfigsCfg;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.executor.ExecutorUtils;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;

public class Request extends CommonMessage {
	protected static final Logger LOG = LogManager.getLogger(Request.class);
	private long timestamp;// 请求时间

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public static Request valueOf(short cmd) {
		Request request = new Request();
		request.setCode(cmd);
		return request;
	}

	public static Request valueOf(short cmd, int playerId) {
		Request request = new Request();
		request.setCode(cmd);
		request.setPlayerId(playerId);
		return request;
	}

	public static Request valueOf(short cmd, GeneratedMessage message) {
		Request request = valueOf(cmd);
		request.setBody(message.toByteArray());
		return request;
	}

	public static Request valueOf(short cmd, int playerId, GeneratedMessage message) {
		Request request = valueOf(cmd, playerId);
		request.setBody(message.toByteArray());
		return request;
	}

	public static Request valueOf(short cmd, byte[] params) {
		return valueOf(cmd, 0, params);
	}

	public static Request valueOf(short cmd, int playerId, byte[] params) {
		Request request = new Request();
		request.setCode(cmd);
		request.setPlayerId(playerId);
		request.setBody(params);
		request.setTimestamp(System.currentTimeMillis());
		return request;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T parseParams(GeneratedMessage.Builder builder) throws InvalidProtocolBufferException {
		final Object message = builder.mergeFrom(getBody()).buildPartial();

		if (ConfigsCfg.showMsg) {

			RequestHandler handler = RequestHandlerCenter.getInstance().getHandler(getCode());
			if (handler == null) {
				return  (T) message;
			}
			if (handler.isSingleThread()) {
				ExecutorUtils.getIns().execute(new Runnable() {
					public void run() {
						LOG.info("收到消息 code:{},content:{}", getCode(),
								JsonFormat.printToString((GeneratedMessage) message));
					}
				});
			} else {
				LOG.info("收到消息 code:{},content:{}", getCode(), JsonFormat.printToString((GeneratedMessage) message));
			}

		}

		return (T) message;
	}
}
