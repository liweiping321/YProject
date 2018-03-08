package com.road.fire.consts.config;

import com.game.type.Configuration;


/**
 * 应用配置
 * 
 * @author jason.lin
 *
 */
@Configuration(config = "config/config.properties")
public class AppConfig {

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
		AppConfig.socketPort = socketPort;
	}


	public static int getWebPort() {
		return webPort;
	}


	public static void setWebPort(int webPort) {
		AppConfig.webPort = webPort;
	}


	public static int getMaxOnlineCount() {
		return maxOnlineCount;
	}


	public static void setMaxOnlineCount(int maxOnlineCount) {
		AppConfig.maxOnlineCount = maxOnlineCount;
	}


	public static String getCenterUrl() {
		return centerUrl;
	}


	public static void setCenterUrl(String centerUrl) {
		AppConfig.centerUrl = centerUrl;
	}


	public static String getCenterPublicKey() {
		return centerPublicKey;
	}


	public static void setCenterPublicKey(String centerPublicKey) {
		AppConfig.centerPublicKey = centerPublicKey;
	}


	public static int getMaxLogCache() {
		return maxLogCache;
	}


	public static void setMaxLogCache(int maxLogCache) {
		AppConfig.maxLogCache = maxLogCache;
	}


	public static int getGamePoolThreadCount() {
		return gamePoolThreadCount;
	}


	public static void setGamePoolThreadCount(int gamePoolThreadCount) {
		AppConfig.gamePoolThreadCount = gamePoolThreadCount;
	}


	public static int getMinaThreadCount() {
		return minaThreadCount;
	}


	public static void setMinaThreadCount(int minaThreadCount) {
		AppConfig.minaThreadCount = minaThreadCount;
	}


	public static String getNeteaseAppKey() {
		return neteaseAppKey;
	}


	public static void setNeteaseAppKey(String neteaseAppKey) {
		AppConfig.neteaseAppKey = neteaseAppKey;
	}


	public static String getNeteaseAppSecret() {
		return neteaseAppSecret;
	}


	public static void setNeteaseAppSecret(String neteaseAppSecret) {
		AppConfig.neteaseAppSecret = neteaseAppSecret;
	}

 
	public static  String getString() {
		return "AppConfig [socketPort=" + socketPort + ", webPort=" + webPort
				+ ", maxOnlineCount=" + maxOnlineCount + ", centerUrl="
				+ centerUrl + ", centerPublicKey=" + centerPublicKey
				+ ", maxLogCache=" + maxLogCache + ", gamePoolThreadCount="
				+ gamePoolThreadCount + ", minaThreadCount=" + minaThreadCount
				+ ", neteaseAppKey=" + neteaseAppKey + ", neteaseAppSecret="
				+ neteaseAppSecret + "]";
	}
 
	 
	 
}
