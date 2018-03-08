package com.game.core.net;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.game.core.net.handler.RequestHandlerCenter;


/**
 * 连接客户端基类
 * 
 * @author jason.lin
 */
public class ClientConnector {
	
	private static final Logger LOG =LogManager.getLogger(ClientConnector.class);
	
	protected InetSocketAddress currentAddress = null;
	protected IoConnector connector = null;
	
	private String host;
	
	private int port;
	
	private IoHandler handler;
	
	private ProtocolCodecFactory factory;
 
	public ClientConnector(String host, int port, IoHandler handler,
			ProtocolCodecFactory factory) {
		super();
		this.host = host;
		this.port = port;
		this.handler = handler;
		this.factory = factory;
	}



	/**
	 * 建立连接
	 * @param host
	 * @param port
	 * @return
	 */
	public ConnectFuture connectTo() {
		ConnectFuture connectFuture;
		try {
			connector = new NioSocketConnector();

			// 添加编码解码
			initConnector(connector, factory, handler);

			// 开始连接
			currentAddress = new InetSocketAddress(host, port);
			connectFuture = connector.connect(currentAddress);
			return connectFuture;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(String.format("error host %s:%d", host, port) , e);
		}
		return null;
	}

	/**
	 * 初始编码解码器
	 * @param connector
	 * @param factory
	 * @param handler
	 */
	public void initConnector(IoConnector connector, ProtocolCodecFactory factory, IoHandler handler) {
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(factory));
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getSessionConfig().setReadBufferSize(102400);
		connector.setHandler(handler);
	}
}
