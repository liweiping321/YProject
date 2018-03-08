package com.road.fire.consts.config;

import com.game.type.Configuration;


/**
 * 应用配置
 * 
 * @author jason.lin
 *
 */
@Configuration(config = "config/logic.properties")
public class LogicConfig {
    public static int serverId;

	public static int socketPort;

	public  static int webPort;

	public  static int maxOnlineCount;

	public  static String centerUrl;

	public  static String centerPublicKey;

	public  static int maxLogCache;
	
	public  static int gamePoolThreadCount;
	
	public  static int minaThreadCount;
	
	public  static String neteaseAppKey = "de860aa1bf46f9238484284201d744d8";
	
	public  static String neteaseAppSecret = "dc1f88e05cc6";
	
	public static int  dataSavePeriod=2000;
	
	public static int dbQueueSize=3;
 
	public static int webMinThreads;

	public static int webMaxThreads;

	public static long webIdleTimeOut;
	
	
 
	public static int getSocketPort() {
		return socketPort;
	}


	public static void setSocketPort(int socketPort) {
		LogicConfig.socketPort = socketPort;
	}


	public static int getWebPort() {
		return webPort;
	}


	public static void setWebPort(int webPort) {
		LogicConfig.webPort = webPort;
	}


	public static int getMaxOnlineCount() {
		return maxOnlineCount;
	}


	public static void setMaxOnlineCount(int maxOnlineCount) {
		LogicConfig.maxOnlineCount = maxOnlineCount;
	}


	public static String getCenterUrl() {
		return centerUrl;
	}


	public static void setCenterUrl(String centerUrl) {
		LogicConfig.centerUrl = centerUrl;
	}


	public static String getCenterPublicKey() {
		return centerPublicKey;
	}


	public static void setCenterPublicKey(String centerPublicKey) {
		LogicConfig.centerPublicKey = centerPublicKey;
	}


	public static int getMaxLogCache() {
		return maxLogCache;
	}


	public static void setMaxLogCache(int maxLogCache) {
		LogicConfig.maxLogCache = maxLogCache;
	}


	public static int getGamePoolThreadCount() {
		return gamePoolThreadCount;
	}


	public static void setGamePoolThreadCount(int gamePoolThreadCount) {
		LogicConfig.gamePoolThreadCount = gamePoolThreadCount;
	}


	public static int getMinaThreadCount() {
		return minaThreadCount;
	}


	public static void setMinaThreadCount(int minaThreadCount) {
		LogicConfig.minaThreadCount = minaThreadCount;
	}


	public static String getNeteaseAppKey() {
		return neteaseAppKey;
	}


	public static void setNeteaseAppKey(String neteaseAppKey) {
		LogicConfig.neteaseAppKey = neteaseAppKey;
	}


	public static String getNeteaseAppSecret() {
		return neteaseAppSecret;
	}


	public static void setNeteaseAppSecret(String neteaseAppSecret) {
		LogicConfig.neteaseAppSecret = neteaseAppSecret;
	}


	public static int getServerId() {
		return serverId;
	}


	public static void setServerId(int serverId) {
		LogicConfig.serverId = serverId;
	}

    
 
 
	 
	 
}
