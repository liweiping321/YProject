package com.game.utils;

import java.security.MessageDigest;

public class Md5Tool {
	/**
	 * MD5加密字符串
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String text) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] md5 = digest.digest(text.getBytes("UTF-8"));
		StringBuffer md5StringBuffer = new StringBuffer();
		String part = null;
		for (int i = 0; i < md5.length; i++) {
			part = Integer.toHexString(md5[i] & 0xFF);
			if (part.length() == 1) {
				part = "0" + part;
			}
			md5StringBuffer.append(part);
		}
		return md5StringBuffer.toString().toUpperCase();
	}

	/**
	 * MD5加密字符串(小写)
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encryptToLower(String text) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] md5 = digest.digest(text.getBytes("UTF-8"));
		StringBuffer md5StringBuffer = new StringBuffer();
		String part = null;
		for (int i = 0; i < md5.length; i++) {
			part = Integer.toHexString(md5[i] & 0xFF);
			if (part.length() == 1) {
				part = "0" + part;
			}
			md5StringBuffer.append(part);
		}
		return md5StringBuffer.toString().toLowerCase();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Md5Tool.encrypt("root"));
		System.out.println(Md5Tool.encrypt("test"));
		System.out.println(Md5Tool.encrypt("admin"));
	}
}
