/**
 * Date: Mar 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.game.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * 
 * @author jason.lin
 */
public class StringUtil {
	/**
	 * 验证email地址是否合法
	 * 
	 * @param address
	 * @return
	 */
	public static boolean isValidEmailAddress(String address) {
		if (address == null) {
			return false;
		}
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(address);
		return m.find();
	}

	/**
	 * 判断字符串是否为空。
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isNullOrEmpty(String src) {
		if (src == null || src.isEmpty() || src.trim().length() <= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符串是否为空。
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isNotNullAndNotEmpty(String src) {
		if (src != null && !src.isEmpty() && src.trim().length() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符串是否为空或者只包含空格。
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isEmptyOrWhitespaceOnly(String src) {
		if (isNullOrEmpty(src)) {
			return true;
		}

		if (src.trim().length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 獲取字符長度
	 * 
	 * @param str
	 * @return
	 */
	public static int getStrLength(String str) {
		int strLength = 0;
		String chinese = "[\u4e00-\u9fa5]";

		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				strLength += 2;
			} else {
				// 其他字符长度为1
				strLength += 1;
			}
		}
		return strLength;
	}

	/**
	 * 将字符串转换成数字
	 * 
	 * @param data
	 * @return
	 */
	public static int getInt(String data) {
		if (data == null || data.trim().length() == 0) {
			return 0;
		}
		return Integer.valueOf(data);
	}

	/**
	 * 判断字符串是否超过最大长度
	 * 
	 * @param rawStr
	 * @param maxLen
	 * @return
	 */
	public static String verifyMaxLen(String rawStr, int maxLen) {
		if (rawStr == null || rawStr.trim().length() == 0) {
			return rawStr;
		}
		if (rawStr.length() > maxLen) {
			return rawStr.substring(0, maxLen);
		}
		return rawStr;
	}

	public static void geneteCheckImg(int[] checkCodeSet, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Time New Roman", Font.PLAIN, 60));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 100; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x + x1, y + y1);
		}

		for (int i = 0; i < checkCodeSet.length; i++) {
			int j = checkCodeSet[i];
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(j + "", 30 * i + 10, 66);
		}

		// try {
		// ImageIO.write(image, "png", new
		// File("D:\\yishi\\slg\\server\\branches\\Workspace\\test.png"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength / 2;
	}

	/**
	 * <pre>
	 * 是否为数字类型(负数返回false)
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否包含特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean matchSpecial(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		return matcher.find() || hasEnterSpecial(str);
	}
	
	/**
	 * 换行符
	 * @param str
	 * @return
	 */
	public static boolean hasEnterSpecial(String str){
		if(str.contains("\n") || str.contains("\r")){
			return true;
		}
		
		return false;
	}

	/**
	 * <pre>
	 * 是否为数字类型(-999999999  到 999999999)
	 * </pre>
	 */
	public static boolean isNumber2(String str) {
		if (str.matches("^(-?[1-9]\\d{0,9}|0)$")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isBlank(String str) {
		// 标记字符长度
		int strlen;
		// 字符串不存在或者长度为0
		if (str == null || (strlen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strlen; i++) {
			// 判断空格，回车，行等等，如果有一个不是上述字符，就返回false
			if (Character.isWhitespace(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 昵称检测 中文汉字、大小写英文、数字、下划线
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkNickNameRegex(String str) {
		if (str.matches("^[\u4e00-\u9fa5A-Za-z0-9_]+$")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str) || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static Map<Integer, Integer> getStringToIntMap(String str, String partition1, String partition2) {

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (isBlank(str) || (isBlank(partition1) && isBlank(partition2))) {
			return map;
		}

		if (isNotBlank(partition1) && isNotBlank(partition2)) {
			String[] strPar1 = str.split(partition1);
			for (String s1 : strPar1) {
				String[] strPar2 = s1.split(partition2);
				map.put(Integer.parseInt(strPar2[0]), Integer.parseInt(strPar2[1]));
			}
		}

		return map;

	}

	public static List<Integer> getStrToIntList(String str, String partition) {

		List<Integer> list = new ArrayList<Integer>();
		if (isBlank(str)) {
			return list;
		}

		if (isNotBlank(partition)) {

			String[] strPar = str.split(partition);
			for (String s : strPar) {
				list.add(Integer.parseInt(s));
			}

		}
		return list;
	}

	public static String ListToString(List<Integer> list, String partition) {

		if (list == null || list.size() <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Integer i : list) {
			sb.append(i).append(partition);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static boolean arrayContains(String[] array, String match) {
		if (array != null && array.length > 0) {
			for (String str : array) {
				if (str.equals(match)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将字符串转换成Map
	 * 
	 * @param source
	 *            原字符串
	 * @param separateFlag1
	 *            一级分割符
	 * @param separateFlage2
	 *            二级分割符
	 * @return
	 */
	public static Map<Integer, Integer> changeToMap(String source, String separateFlag1, String separateFlage2) {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		if (source == null || source.trim().length() == 0) {
			return result;
		}

		// 如果没有二级分割符，直接退出
		if (!source.contains(separateFlage2)) {
			return result;
		}

		// 处理常用特殊符号
		if ("|".equals(separateFlag1)) {
			separateFlag1 = "\\|";
		}
		if ("|".equals(separateFlage2)) {
			separateFlage2 = "\\|";
		}
		for (String temp1 : source.split(separateFlag1)) {
			String[] temp2 = temp1.split(separateFlage2);
			result.put(Integer.valueOf(temp2[0]), Integer.valueOf(temp2[1]));
		}
		return result;
	}

	/**
	 * UTF-8编码格式判断
	 * 
	 * @param text
	 *            需要分析的数据
	 * @return 是否为UTF-8编码格式
	 */
	public static boolean isUTF8(String text) {
		byte[] rawtext = text.getBytes();
		int score = 0;
		int i, rawtextlen = 0;
		int goodbytes = 0, asciibytes = 0;
		// Maybe also use UTF8 Byte Order Mark: EF BB BF
		// Check to see if characters fit into acceptable ranges
		rawtextlen = rawtext.length;
		for (i = 0; i < rawtextlen; i++) {
			if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) {
				// 最高位是0的ASCII字符
				asciibytes++;
				// Ignore ASCII, can throw off count
			} else if (-64 <= rawtext[i] && rawtext[i] <= -33
			// -0x40~-0x21
					&& // Two bytes
					i + 1 < rawtextlen && -128 <= rawtext[i + 1] && rawtext[i + 1] <= -65) {
				goodbytes += 2;
				i++;
			} else if (-32 <= rawtext[i] && rawtext[i] <= -17 && // Three bytes
					i + 2 < rawtextlen && -128 <= rawtext[i + 1] && rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2] && rawtext[i + 2] <= -65) {
				goodbytes += 3;
				i += 2;
			}
		}
		if (asciibytes == rawtextlen) {
			return false;
		}
		score = 100 * goodbytes / (rawtextlen - asciibytes);
		// If not above 98, reduce to zero to prevent coincidental matches
		// Allows for some (few) bad formed sequences
		if (score > 98) {
			return true;
		} else if (score > 95 && goodbytes > 30) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 过滤非UTF-8
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	static public String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
		byte[] bytes = text.getBytes("utf-8");
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		int i = 0;
		while (i < bytes.length) {
			short b = bytes[i];
			if (b > 0) {
				buffer.put(bytes[i++]);
				continue;
			}
			b += 256; // 去掉符号位
			if (((b >> 5) ^ 0x6) == 0) {
				buffer.put(bytes, i, 2);
				i += 2;
			} else if (((b >> 4) ^ 0xE) == 0) {
				buffer.put(bytes, i, 3);
				i += 3;
			} else if (((b >> 3) ^ 0x1E) == 0) {
				i += 4;
			} else if (((b >> 2) ^ 0x3E) == 0) {
				i += 5;
			} else if (((b >> 1) ^ 0x7E) == 0) {
				i += 6;
			} else {
				buffer.put(bytes[i++]);
			}
		}
		buffer.flip();
		return new String(buffer.array(), 0, buffer.limit(), "utf-8");
	}
	
	/**
	 * 如果str为空，返回默认值def
	 * @param str
	 * @param def
	 * @return
	 */
	public static String getDefaultStr(String str, String def){
		if(str == null){
			return def;
		}
		return str;
	}

}
