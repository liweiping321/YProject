package com.game.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtil {

	/**
	 * 获取主机IP地址
	 * 
	 * @return
	 */
	public static String getHostAddress() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if (ip.getHostAddress().startsWith("127.")) {
							continue;
						}
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "127.0.0.1";
	}

//	public static String getIp(HttpServletRequest request) {
//		String ip = request.getHeader("X-Real-IP");
//		if ((ip == null) || ip.isEmpty() || ("unknown".equalsIgnoreCase(ip))) {
//			ip = request.getHeader("X-Forwarded-For");
//		}
//		if ((ip == null) || ip.isEmpty() || ("unknown".equalsIgnoreCase(ip))) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if ((ip == null) || ip.isEmpty() || ("unknown".equalsIgnoreCase(ip))) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if ((ip == null) || ip.isEmpty() || ("unknown".equalsIgnoreCase(ip))) {
//			ip = request.getHeader("HTTP_CLIENT_IP");
//		}
//		if ((ip == null) || ip.isEmpty() || ("unknown".equalsIgnoreCase(ip))) {
//			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//		}
//		if ((ip == null) || ip.isEmpty() || ("unknown".equalsIgnoreCase(ip))) {
//			ip = request.getRemoteAddr();
//		}
//		try {
//			if (ip.length() > 15) {
//				ip = ip.replaceAll(" ", "");
//				int index = ip.lastIndexOf(",");
//				if (index > 0) {
//					ip = ip.substring(index + 1);
//				}
//			}
//		} catch (Exception e) {
//		}
//		if (ip.length() > 15) {
//			int length = ip.length();
//			ip = ip.substring(length - 15);
//		}
//		return ip;
//	}
}
