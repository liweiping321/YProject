package com.road.fire.consts.config;

import com.game.type.Configuration;
import java.util.List;


/**
 * 
 * @author lip.li
 *
 */
@Configuration(config = "config/match.properties")
public class MatchConfig {
	
	public static int serverId;
	
	public static int socketPort;
 
	public  static int minaThreadCount;
	
	public  static  List<Integer> fightingTypeIds;
	
	public  static int webPort;
	
	public static int webMinThreads;

	public static int webMaxThreads;

	public static long webIdleTimeOut;
	

	public static int getSocketPort() {
		return socketPort;
	}

	public static void setSocketPort(int socketPort) {
		MatchConfig.socketPort = socketPort;
	}

	public static int getMinaThreadCount() {
		return minaThreadCount;
	}

	public static void setMinaThreadCount(int minaThreadCount) {
		MatchConfig.minaThreadCount = minaThreadCount;
	}

	public static List<Integer> getFightingTypeIds() {
		return fightingTypeIds;
	}

	public static void setFightingTypeIds(List<Integer> fightingTypeIds) {
		MatchConfig.fightingTypeIds = fightingTypeIds;
	}

	public static int getServerId() {
		return serverId;
	}

	public static void setServerId(int serverId) {
		MatchConfig.serverId = serverId;
	}

	public static int getWebPort() {
		return webPort;
	}

	public static void setWebPort(int webPort) {
		MatchConfig.webPort = webPort;
	}

	public static int getWebMinThreads() {
		return webMinThreads;
	}

	public static void setWebMinThreads(int webMinThreads) {
		MatchConfig.webMinThreads = webMinThreads;
	}

	public static int getWebMaxThreads() {
		return webMaxThreads;
	}

	public static void setWebMaxThreads(int webMaxThreads) {
		MatchConfig.webMaxThreads = webMaxThreads;
	}

	public static long getWebIdleTimeOut() {
		return webIdleTimeOut;
	}

	public static void setWebIdleTimeOut(long webIdleTimeOut) {
		MatchConfig.webIdleTimeOut = webIdleTimeOut;
	}
	
	
	
	
	 
}
