package com.game.core.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.game.utils.IPUtil;

public class SocketServer {
	
	private static final Logger logger =LogManager.getLogger(SocketServer.class);
    
	private  Runnable stopTask;
	
	public SocketServer(){
		
	}
	
	public SocketServer(Runnable stopTask){
		this.stopTask=stopTask;
	}



	final IoAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);

	public void start(int port, int minaThreadCount, IoHandler handler, ProtocolCodecFactory factory) throws Exception {
		configSession(acceptor);
//		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(factory));
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter(Executors.newFixedThreadPool(minaThreadCount)));
//		acceptor.getFilterChain().addLast("executor", new ExecutorFilter(Executors.newCachedThreadPool()));
		acceptor.setHandler(handler);

		InetSocketAddress address = new InetSocketAddress(port);
		acceptor.bind(address);
		logger.error("server start! " + IPUtil.getHostAddress() + ":" + address.getPort());
	}

	private void configSession(IoAcceptor acceptor) {
		DefaultSocketSessionConfig sessionConfig = (DefaultSocketSessionConfig) acceptor.getSessionConfig();
		sessionConfig.setSoLinger(0);
		sessionConfig.setKeepAlive(true);
		sessionConfig.setReuseAddress(true);
		sessionConfig.setTcpNoDelay(true);
		sessionConfig.setSendBufferSize(1024);
		sessionConfig.setReadBufferSize(1024);
		sessionConfig.setBothIdleTime(15);
		sessionConfig.setIdleTime(IdleStatus.READER_IDLE, 10);// 单位秒
		sessionConfig.setWriteTimeout(15000);

	}

	public void stop() {
		if(stopTask!=null){
			stopTask.run();
		}
	}
}
