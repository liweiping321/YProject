package com.road.fire.consts.config;

import java.util.List;

import com.game.type.Configuration;

/**
 * 
 * @author lip.li
 * 
 */
@Configuration(config = "config/fighting.properties")
public class FightingConfig {
	public static int serverId;

	public static int socketPort;

	public static int minaThreadCount;

	public static List<Integer> fightingTypeIds;

	public static int webPort;

	public static int webMinThreads;

	public static int webMaxThreads;

	public static long webIdleTimeOut;

	public static int getServerId() {
		return serverId;
	}

	public static void setServerId(int serverId) {
		FightingConfig.serverId = serverId;
	}

	public static int getSocketPort() {
		return socketPort;
	}

	public static void setSocketPort(int socketPort) {
		FightingConfig.socketPort = socketPort;
	}

	public static int getMinaThreadCount() {
		return minaThreadCount;
	}

	public static void setMinaThreadCount(int minaThreadCount) {
		FightingConfig.minaThreadCount = minaThreadCount;
	}

	public static List<Integer> getFightingTypeIds() {
		return fightingTypeIds;
	}

	public static void setFightingTypeIds(List<Integer> fightingTypeIds) {
		FightingConfig.fightingTypeIds = fightingTypeIds;
	}

	public static int getWebPort() {
		return webPort;
	}

	public static void setWebPort(int webPort) {
		FightingConfig.webPort = webPort;
	}

	public static int getWebMinThreads() {
		return webMinThreads;
	}

	public static void setWebMinThreads(int webMinThreads) {
		FightingConfig.webMinThreads = webMinThreads;
	}

	public static int getWebMaxThreads() {
		return webMaxThreads;
	}

	public static void setWebMaxThreads(int webMaxThreads) {
		FightingConfig.webMaxThreads = webMaxThreads;
	}

	public static long getWebIdleTimeOut() {
		return webIdleTimeOut;
	}

	public static void setWebIdleTimeOut(long webIdleTimeOut) {
		FightingConfig.webIdleTimeOut = webIdleTimeOut;
	}

}
