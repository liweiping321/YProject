package com.road.fire.core.http;

import com.alibaba.druid.support.http.StatViewServlet;
import com.game.http.AbstractWebHandler;
import com.game.http.entity.RequestMapping;
import com.game.http.entity.UrlBean;
import com.game.utils.ClassUtil;
import com.game.utils.LogUtils;
import com.road.fire.consts.config.AppConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.MultipartConfigElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class WebServer {

	/** 请求路径映射：key为具体映射 **/
	public static Map<String, UrlBean> requestMap = new HashMap<String, UrlBean>();

	private static WebServer instance=new WebServer();

	private WebServer()
	{
	 
	}

	// 服务器
	private Server server = null;
	
	/**
	 * 启动http服务
	 * @throws Exception
	 */
	public void startHttp() throws Exception {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setName("HttpThreadPool");
		threadPool.setMinThreads(AppConfig.webMinThreads);
		threadPool.setMaxThreads(AppConfig.webMaxThreads);
		server = new Server(threadPool);
		ServerConnector http = new ServerConnector(server);
		http.setPort(AppConfig.webPort);
		http.setIdleTimeout(AppConfig.webIdleTimeOut);
		http.setName("HttpConnector");
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/web");
		ServletHolder holder = new ServletHolder("HttpHandler", new HttpDispatchHandler());
		holder.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp", 250 * 1024 * 1024, 250 * 1024 * 1024, 250 * 1024 * 1024));
		ServletHolder holder1 = new ServletHolder("DruidStatView", new StatViewServlet());
		context.addServlet(holder1, "/druid/*");
		context.addServlet(holder, "/*");
		server.setHandler(context);
		server.addConnector(http);
		server.start();
	
		LogUtils.info("Web socket is starting : " + AppConfig.webPort);
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

	public  void register(String packName){
		List<Class<?> > clazzs=ClassUtil.getClasses(packName);
		
		for(Class<?> clazz:clazzs){
			if(AbstractWebHandler.class.isAssignableFrom(clazz)){
				 register(clazz);
			}
		}
	}
	/**
	 * URL路径映射注册
	 */
	public  void register(Class<?> clazz) {
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
					UrlBean urlBean = new UrlBean((AbstractWebHandler)clazz.newInstance(), method.getName(), method);
					requestMap.put(name + mapping.value(), urlBean);
				}
			}
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
		}
	}

	public static WebServer getInstance() {
		return instance;
	}

}
