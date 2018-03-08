package com.road.fire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.SocketServer;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.core.net.server.ServerCodecFactory;
import com.game.db.base.BaseDAO;
import com.game.db.ds.DruidDataSourceFactory;
import com.game.utils.PropertyConfigUtil;
import com.road.fire.cfg.MatchCfgProviders;
import com.road.fire.consts.config.MatchConfig;
import com.road.fire.core.net.MatchMinaHandler;
 


 
public class MatchServer {
    private static final Logger logger =LogManager.getLogger(MatchServer.class);
	
	private  SocketServer socketServer;
	
	

	public void startUp() {
		
		try {
			//初始化配置文件
			PropertyConfigUtil.init("com.road.fire.consts.config");

			//初始化数据源
			DruidDataSourceFactory.getInstance().initDruidDataSource("config/jdbc.properties");
			
			//DAO注册数据源
			BaseDAO.register(DruidDataSourceFactory.getInstance().getDataSource());
			
			//配置数据初始化
			MatchCfgProviders.getInstance().init();
			
			//初始化处理指令
			RequestHandlerCenter.getInstance().init("com.road.fire.handler");
			
		 
			//初始化网络连接
			socketServer=new SocketServer();
			socketServer.start(MatchConfig.socketPort, MatchConfig.minaThreadCount, new MatchMinaHandler(), new ServerCodecFactory() );
	 
 
		 

           
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void shutDown() {
 
		DruidDataSourceFactory.getInstance().getDataSource().close();
		
		socketServer.stop();
	}
	
	private static final MatchServer instance=new MatchServer();
	
	public static MatchServer getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		MatchServer.getInstance().startUp();
	}
}
