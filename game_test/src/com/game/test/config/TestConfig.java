package com.game.test.config;

import com.game.type.Configuration;

/**
 * 
 * @author lip.li
 * 
 */
@Configuration(config = "config/test.properties")
public class TestConfig {

	public static String ip;

	public static int socketPort;

	public static int interval;

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		TestConfig.ip = ip;
	}

	public static int getSocketPort() {
		return socketPort;
	}

	public static void setSocketPort(int socketPort) {
		TestConfig.socketPort = socketPort;
	}

	public static int getInterval() {
		return interval;
	}

	public static void setInterval(int interval) {
		TestConfig.interval = interval;
	}
}
