package com.road.fire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.MinaHandler;
import com.game.core.net.SocketServer;
import com.game.core.net.StrictCodecFactory;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.db.asyn.AsynDbQueueManager;
import com.game.db.base.BaseDAO;
import com.game.db.ds.DruidDataSourceFactory;
import com.game.db.id.IDGenerate;
import com.game.line.LogicLineManager;
import com.game.utils.PropertyConfigUtil;
import com.match.MatchFactory;
import com.road.fire.cfg.CfgEntityProviders;
import com.road.fire.cfg.provider.CfgGameidProvider;
import com.road.fire.consts.config.AppConfig;
import com.road.fire.core.http.WebServer;
import com.road.fire.fighting.GameMgr;
import com.road.fire.player.OnlinePlayer;

/**
 * 
 * @author weiping.li
 * 
 */
public class GameServer {

	private static final Logger logger =LogManager.getLogger(GameServer.class);
	
	private  SocketServer socketServer;
	
	private LogicLineManager<OnlinePlayer> lineManager;
 
	private GameServer() {
	}

	public void startUp() {
		
		try {
			//初始化配置文件
			PropertyConfigUtil.init("com.road.fire.consts.config");

			//初始化数据源
			DruidDataSourceFactory.getInstance().initDruidDataSource("config/jdbc.properties");
			
			//DAO注册数据源
			BaseDAO.register(DruidDataSourceFactory.getInstance().getDataSource());
			
			//配置数据初始化
			CfgEntityProviders.getInstance().init();
			
			//初始化处理指令
			RequestHandlerCenter.getInstance().init("com.road.fire.handler");
			
			//初始化数据库异步队列
			AsynDbQueueManager.getInstance().init(AppConfig.dataSavePeriod, AppConfig.dbQueueSize);
			
			//ID生成器初始化
			long gameZoneId=CfgGameidProvider.getInstance().getCurrentGameId().getGameZoneId() * 1000000000000L;
			IDGenerate.getInstance().init(gameZoneId);

			// 匹配线程池初始化
			MatchFactory.init();

            int cpuCount=Runtime.getRuntime().availableProcessors();
			//初始化逻辑分线程
			lineManager=new LogicLineManager<OnlinePlayer>();
			lineManager.startUp(cpuCount);
			
			//初始化网络连接
			socketServer=new SocketServer();
			socketServer.start(AppConfig.socketPort, AppConfig.minaThreadCount, new MinaHandler(), new StrictCodecFactory() );
			//启动战斗分线
			GameMgr.getInstance().start(cpuCount);
			//启动jetty
			WebServer.getInstance().startHttp();
			//注册WebHandler
			WebServer.getInstance().register("com.road.fire.web");

           
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void shutDown() {
		
		lineManager.shutDown();
		
		DruidDataSourceFactory.getInstance().getDataSource().close();
		
		socketServer.stop();
	}
	
	public LogicLineManager<OnlinePlayer> getLineManager() {
		return lineManager;
	}

	private static final GameServer instance = new GameServer();

	public static GameServer getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		GameServer.getInstance().startUp();
	}

}
