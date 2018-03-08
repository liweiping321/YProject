
package com.game.core.net.handler;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.core.net.session.NetSession;
import com.game.utils.ClassUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lip.li
 *
 */
public class RequestHandlerCenter {

	private static final Logger LOG = LogManager.getLogger(RequestHandlerCenter.class);

	private Map<Integer, RequestHandler> handlerMap;

	public RequestHandlerCenter() {
		handlerMap = new HashMap<Integer, RequestHandler>();
	}

	public void handle(NetSession session, Request request) throws Exception {
		RequestHandler handler = getHandler(request.getCode());
		if (handler != null) {
			handler.handle(session, request);
		}
	}

	public RequestHandler getHandler(int msgCode) {
		RequestHandler requestMsgHandler = handlerMap.get(msgCode);
		if (requestMsgHandler == null) {
			LOG.error("can't find message handler :{}", msgCode);

		}
		return requestMsgHandler;
	}

	public Map<Integer, RequestHandler> getHandlerMap() {
		return handlerMap;
	}

	public void init(String handlerPack) throws Exception {

		List<Class<?>> classSet = ClassUtil.getClasses(handlerPack);
		if (CollectionUtils.isEmpty(classSet)) {
			LOG.info("Attention !!! no cmd path:{} " + handlerPack);
			return;
		}

		for (Class<?> clazz : classSet) {

			Handler handler = clazz.getAnnotation(Handler.class);
			if (null == handler) {
				continue;
			}

			RequestHandler requestHandler = initHandler(clazz, handler);
			if (handlerMap.put(requestHandler.getMsgCode(), requestHandler) != null) {
				throw new IllegalClassFormatException(
						"协议号已经被使用：code=" + requestHandler.getMsgCode() + ",handler=" + clazz.getName());
			}
		}
	}

	private RequestHandler initHandler(Class<?> clazz, Handler handler) throws Exception {
		RequestHandler requestHandler = (RequestHandler) clazz.newInstance();
		requestHandler.setEnable(true);
		requestHandler.setMsgCode(handler.code());
		requestHandler.setMsgIntervalTime(handler.intervalTime());
		requestHandler.setNeedAuth(handler.needAuth());
		requestHandler.setSingleThread(handler.singleThread());
		return requestHandler;
	}

	@Override
	public String toString() {
		return "RequestMsgHandlerAdapter [handlerMap=" + handlerMap + "]";
	}

	private static final RequestHandlerCenter instance = new RequestHandlerCenter();

	public static RequestHandlerCenter getInstance() {
		return instance;
	}

}
