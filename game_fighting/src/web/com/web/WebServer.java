package com.web;

import com.game.http.AbstractWebHandler;
import com.game.http.entity.RequestMapping;
import com.game.http.entity.UrlBean;
import com.game.utils.LogUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.MultipartConfigElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WebServer {

	/** 请求路径映射：key为具体映射 **/
	static Map<String, UrlBean> requestMap = new HashMap<>();

	private static WebServer instance=new WebServer();
	
	// 服务器
	private Server server = null;
	
	/**
	 * 启动http服务
	 * @throws Exception
	 */
	public void startHttp(int port, AbstractWebHandler webHandler) throws Exception {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setName("HttpThreadPool");
		threadPool.setMinThreads(1);
		threadPool.setMaxThreads(10);
		server = new Server(threadPool);
		ServerConnector http = new ServerConnector(server);
		http.setPort(port);
		http.setIdleTimeout(30000);
		http.setName("HttpConnector");
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/web");
		
		ServletHolder holder = new ServletHolder("HttpHandler", new HttpHandler());
		
		holder.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp", 250 * 1024 * 1024, 250 * 1024 * 1024, 250 * 1024 * 1024));
		context.addServlet(holder, "/*");
		server.setHandler(context);
		server.addConnector(http);
		server.start();
		
		register(webHandler);
		LogUtils.info("Web socket is starting : " + port);
	}

	public void stop() {
		if (server != null) {
			try {
				server.stop();
				LogUtils.error("Web service stopped");
			} catch (Exception e) {
				LogUtils.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * URL路径映射注册
	 */
	private static void register(AbstractWebHandler webHandler) {
		Class<?> clazz = webHandler.getClass();
		try {
			RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
			String name = "";
			// 类映射路径
			if (mapping != null) {
				name = mapping.value();
			}
			// 方法映射路径
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				mapping = method.getAnnotation(RequestMapping.class);
				if (mapping != null && !mapping.value().isEmpty()) {
					UrlBean urlBean = new UrlBean(webHandler, method.getName(), method);
					requestMap.put(name + mapping.value(), urlBean);
				}
			}
		} catch (SecurityException e) {
			LogUtils.error(e.getMessage(), e);
		}
	}

	public static WebServer getInstance() {
		return instance;
	}
	 
}
