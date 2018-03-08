package com.road.fire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.SocketServer;
import com.game.core.net.StrictCodecFactory;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.db.base.BaseDAO;
import com.game.db.ds.DruidDataSourceFactory;
import com.game.executor.ExecutorUtils;
import com.game.utils.PropertyConfigUtil;
import com.road.fire.cfg.FightingCfgProviders;
import com.road.fire.consts.config.FightingConfig;
import com.road.fire.core.net.FightingMinaHandler;
import com.road.fire.fighting.GameMgr;
import com.road.fire.fighting.phy.scene.MapDataMgr;
import com.road.fire.fighting.util.DistanceUtils;
import com.road.fire.match.MatchPoolFactory;
import com.road.script.living.monster.CompositeActions;
import com.road.script.living.monster.MonsterActions;
import com.road.script.living.monster.composite.MonsterCompositeAction;
import com.web.WebHandler;
import com.web.WebServer;



 
public class FightingServer {
    private static final Logger logger =LogManager.getLogger(FightingServer.class);

	private SocketServer socketServer;
	private WebServer webServer = new WebServer();

	public void startUp() {
		
		try {
			//初始化配置文件
			PropertyConfigUtil.init("com.road.fire.consts.config");

			//初始化数据源
			DruidDataSourceFactory.getInstance().initDruidDataSource("config/jdbc.properties");
			
			//DAO注册数据源
			BaseDAO.register(DruidDataSourceFactory.getInstance().getDataSource());
			//初始化距离计算公式
			DistanceUtils.initCache();
			
			//配置数据初始化
			FightingCfgProviders.getInstance().init();
			
			//初始化处理指令
			RequestHandlerCenter.getInstance().init("com.road.fire.handler");

			//初始化地图数据
			MapDataMgr.getInstance().init("config/map");
			
			//匹配池管理
			MatchPoolFactory.getInstance().init();
			//怪物action初始化
			MonsterActions.getIns().registerAll();
			//怪物组合action初始化
			CompositeActions.getIns().registerAll();
			
			//战斗管理器启动
			GameMgr.getInstance().start(1);
			//初始化通用线程池
			ExecutorUtils.getIns().init(1);
			
		 
			//初始化网络连接
			socketServer=new SocketServer();
			socketServer.start(FightingConfig.socketPort, FightingConfig.minaThreadCount, new FightingMinaHandler(), new StrictCodecFactory() );

			webServer.startHttp(FightingConfig.webPort, new WebHandler());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void shutDown() {
 
		DruidDataSourceFactory.getInstance().getDataSource().close();
		
		socketServer.stop();
	}
	
	private static final FightingServer instance=new FightingServer();
	
	public static FightingServer getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		FightingServer.getInstance().startUp();
	}
}
