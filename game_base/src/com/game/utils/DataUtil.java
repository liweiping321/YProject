package com.game.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数据类工具
 * 
 * @author jason.lin
 *
 */
public class DataUtil {

	/**
	 * 返回最大值
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static int max(int data1, int data2) {
		return data1 > data2 ? data1 : data2;
	}

	/**
	 * 返回最小值
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static int min(int data1, int data2) {
		return data1 < data2 ? data1 : data2;
	}

	/**
	 * 根据次数获取对应数据
	 * 
	 * @param datas
	 * @param count
	 * @return
	 */
	public static int getDataByCount(String datas, int count) {
		String[] dataArray = datas.split(",");
		if (count < 1) {
			count = 1;
		} else if (count >= dataArray.length) {
			count = dataArray.length;
		}

		return Integer.valueOf(dataArray[count - 1]);
	}

	/**
	 * 获取索引对应数据
	 * 
	 * @param datas
	 * @param index
	 * @return
	 */
	public static int getDataByIndex(String datas, int index) {
		String[] dataArray = datas.split(",");
		if(index >= dataArray.length){
			index = dataArray.length - 1;
		}
		return Integer.valueOf(dataArray[index]);
	}

	/**
	 * 获得随机数
	 * 
	 * @param data
	 * @param flag
	 * @return
	 */
	public static int getRandom(String data, String flag) {
		String[] datas = data.split(flag);
		int index = (int) (Math.random() * datas.length);
		return Integer.valueOf(datas[index]);
	}

	/**
	 * 获得权值随机数
	 * 
	 * @param data
	 * @param flag1
	 * @param flag2
	 * @return
	 */
	public static int getRandom(String data, String flag1, String flag2) {
		String[] datas = data.split(flag1);
		int[][] qualities = new int[datas.length][2];
		int totalPro = 0;// 总权值
		for (int index = 0; index < datas.length; index++) {
			String str = datas[index];
			String[] qualityAndPro = str.split(flag2);
			int quality = Integer.valueOf(qualityAndPro[0]);
			int pro = Integer.valueOf(qualityAndPro[1]);
			totalPro += pro;
			qualities[index][0] = quality;
			qualities[index][1] = pro;
		}

		int randPro = (int) (Math.random() * totalPro);
		int proTemp = 0;
		for (int[] qualityAndPro : qualities) {
			proTemp += qualityAndPro[1];
			if (randPro <= proTemp) {
				return qualityAndPro[0];
			}
		}
		return qualities[0][0];
	}

	/**
	 * 根据key获取数据
	 * 
	 * @param data
	 * @param key
	 * @param index
	 * @return
	 */
	public static float getDataByKey(String data, int key, int index) {
		String[] strs = data.split(",");
		for (String str : strs) {
			String[] datas = str.split(":");
			if (Integer.valueOf(datas[0]) == key) {
				return Float.valueOf(datas[index]);
			}
		}
		return 0;
	}

	/**
	 * 转换成Map
	 * 
	 * @param data
	 * @return
	 */
	public static Map<Integer, List<Integer>> toMap(String data) {
		Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
		String[] strs = data.split(",");
		for (String str : strs) {
			String[] datas = str.split(":");
			List<Integer> list = new ArrayList<Integer>();
			for (String temp : datas) {
				list.add(Integer.valueOf(temp));
			}
			result.put(Integer.valueOf(datas[0]), list);
		}
		return result;
	}

	/**
	 * 去掉末尾匹配符
	 * 
	 * @param source
	 * @param end
	 * @return
	 */
	public static String dropEndFix(String source, String end) {
		if (source == null) {
			return null;
		}
		if (source.endsWith(end)) {
			return source.substring(0, source.length() - end.length());
		}

		return source;
	}

	// public static void main(String[] args) {
	// for(int count = 1; count <= 1; count ++){
	// List<Integer> all = new ArrayList<Integer>(){{
	// for(int index = 0 ;index <= 10; index ++){
	// add(index);
	// }
	// }};
	// List<Integer> except = new ArrayList<Integer>(){{
	// for(int index = 1 ;index <= 5; index ++){
	// add(index);
	// }
	// }};
	//
	// int num = 10;
	// System.err.println(all);
	// System.err.println(except);
	// System.err.println(getRandom(all, num, except));
	// }
	// }

	/**
	 * 获取随机数
	 * 
	 * @param all
	 *            所有种子
	 * @param num
	 *            随机总数
	 * @param except
	 *            排除的
	 * @return
	 */
	public static List<Integer> getRandom(List<Integer> all, int num, List<Integer> except) {
		List<Integer> allCopy = copyList(all);
		List<Integer> left = subList(allCopy, except);
		List<Integer> result = new ArrayList<Integer>();
		if (left.size() <= num) {
			return left;
		}

		while (result.size() < num) {
			result.add(left.remove((int) (Math.random() * left.size())));
		}
		return result;
	}

	private static List<Integer> copyList(List<Integer> all) {
		List<Integer> list = new ArrayList<Integer>(all);
		return list;
	}

	/**
	 * 求余
	 * 
	 * @param all
	 * @param except
	 * @return
	 */
	public static List<Integer> subList(List<Integer> all, List<Integer> except) {
		List<Integer> left = new ArrayList<Integer>();
		for (int data : all) {
			if (except.contains(data)) {
				continue;
			}
			left.add(data);
		}

		return left;
	}

	/**
	 * 获取随机数
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static int getRandom(String data, int key) {
		String[] strs = data.split(",");
		for (String str : strs) {
			String[] datas = str.split(":");
			if (Integer.valueOf(datas[0]) != key) {
				continue;
			}

			int min = Integer.valueOf(datas[1]);
			int max = Integer.valueOf(datas[2]);
			return min + (int) ((max - min + 1) * Math.random());
		}
		return 0;
	}

	/**
	 * 将String解析成List
	 * 
	 * @param data
	 * @param flag
	 * @return
	 */
	public static List<Integer> getList(String data, String flag) {
		List<Integer> list = new ArrayList<Integer>();
		if (StringUtil.isBlank(data)) {
			return list;
		}
		String[] strs = data.split(flag);
		for (String str : strs) {
			list.add(Integer.valueOf(str));
		}
		return list;
	}

	/**
	 * 获取剩余时间
	 * 
	 * @return
	 */
	public static int getLeftSecond(long lastTime, int keepSecond) {
		long leftTime = System.currentTimeMillis() - lastTime;
		leftTime = keepSecond * 1000 - leftTime;
		return leftTime > 0 ? (int) (leftTime / 1000) : 0;
	}

	/**
	 * 求两点距离
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static int getDistance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/**
	 * 获取斜率绝对值
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static float getGradientAbs(float x1, float y1, float x2, float y2) {
		float diffX = x1 - x2;
		float diffY = y1 - y2;
		if (diffX == 0) {
			return Integer.MAX_VALUE;
		}

		return Math.abs(diffY / diffX);
	}

	/**
	 * 修改奖励状态
	 * 
	 * @param state
	 * @param index
	 * @param flag
	 * @return
	 */
	public static String updateAwardState(String state, int index, char flag) {
		char[] states = state.toCharArray();
		states[index] = flag;
		return new String(states);
	}

	/**
	 * 转换列表中的对象信息
	 * 
	 * @param list
	 * @param m2n
	 * @return
	 */
	public static <M, N> List<N> changeList(Collection<M> list, M2N<M, N> m2n) {
		List<N> result = new ArrayList<N>();
		if (list == null || list.size() == 0) {
			return result;
		}
		for (M m : list) {
			N n = m2n.change(m);
			if (n != null) {
				result.add(n);
			}
		}
		return result;
	}

	/**
	 * 转换列表中的对象信息
	 * 
	 * @author jason.lin
	 *
	 * @param <M>
	 * @param <N>
	 */
	public static interface M2N<M, N> {
		N change(M m);
	}

	/**
	 * 判定是否空集合
	 * 
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection collection) {
		if (collection == null || collection.size() <= 0) {
			return true;
		}
		return false;
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		if (map == null || map.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 筛选，返回元素集合
	 * 
	 * @param collection
	 * @param filter
	 * @return
	 */
	public static <T> List<T> filterList(Collection<T> collection, Filter<T> filter) {
		// 检验集合是否为空
		if (isEmpty(collection)) {
			return null;
		}

		List<T> result = new ArrayList<T>();
		for (T t : collection) {
			if (filter.pass(t)) {
				result.add(t);
			}
		}
		return result;
	}

	/**
	 * 筛选，返回元素集合
	 * 
	 * @param collection
	 * @param map
	 *            元素总集
	 * @return
	 */
	public static <T> List<T> filterList(Collection<Integer> collection, Map<Integer, T> map) {
		// 检验集合是否为空
		if (isEmpty(collection)) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (Integer id : collection) {
			if (map.containsKey(id)) {
				result.add(map.get(id));
			}
		}
		return result;
	}

	/**
	 * 筛选，返回单个元素
	 * 
	 * @param collection
	 * @param filter
	 * @return
	 */
	public static <T> T filterSingle(Collection<T> collection, Filter<T> filter) {
		// 检验集合是否为空
		if (isEmpty(collection)) {
			return null;
		}

		for (T t : collection) {
			if (filter.pass(t)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * 过滤器
	 * 
	 * @author jason.lin
	 *
	 * @param <T>
	 */
	public static interface Filter<T> {
		boolean pass(T t);
	}

	public static boolean getBit(int flag, int pos) {
		return ((flag >> pos) & 1) == 0 ? false : true;
	}

	public static int updateBit(int flag, int pos) {
		return flag | (1 << pos);
	}

	public static int updateRewardBit(int flag, int pos) {
		return updateBit(flag, pos + 8);
	}

	public static boolean getRewardBit(int flag, int pos) {
		return getBit(flag, pos + 8);
	}

	/**
	 * 获取总值
	 * 
	 * @param array
	 * @return
	 */
	public static int getTotal(int[] array) {
		int total = 0;
		for (int value : array) {
			total += value;
		}
		return total;
	}
	
	/**
	 * 随机一个数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		int data = max - min;
		return min + (int)(Math.random() * (data + 1));
	}

	/**
	 * 是否在附近
	 * @param pos1
	 * @param pos2
	 * @param rangeX
	 * @param rangeY
	 * @return
	 */
	public static boolean near(Point pos1, Point pos2, int rangeX, int rangeY) {
		if(Math.abs(pos1.x - pos2.x) > rangeX){
			return false;
		}
		
		if(Math.abs(pos1.y - pos2.y) > rangeY){
			return false;
		}
		
		return true;
	}

	/**
	 * 获取反序List
	 * @param list
	 * @return
	 */
	public static <T> List<T> getInvertedList(List<T> list) {
		if(isEmpty(list)){
			return null;
		}
		
		List<T> result = new ArrayList<T>();
		for(int index = list.size() - 1; index >= 0; index --){
			result.add(list.get(index));
		}
		return result;
	}

	/**
	 * 是否匹配上
	 * @param list
	 * @param n
	 * @param match
	 * @return
	 */
	public static <M, N> M match(Collection<M> list, N n, IMatch<M, N> match) {
		if(isEmpty(list)){
			return null;
		}
		
		for(M m : list){
			if(match.match(m, n)){
				return m;
			}
		}
		
		return null;
	}
	
	public static interface IMatch<M, N>{
		public boolean match(M m, N n);
	}

	/**
	 * 计算相差时间（单位秒）
	 * @param now
	 * @param from
	 * @return
	 */
	public static int computeDisSecond(Date now, Date from) {
		if(from == null || now == null){
			return 0;
		}
		
		return (int)Math.abs(now.getTime() - from.getTime()) / 1000;
	}

	/**
	 * 在集合中随机N个不重复的元素
	 * @param list
	 * @param count
	 * @return
	 */
	public static <T> void getRandomList(List<T> list, int count, List<T> result) {
		if(list == null || list.size() <= count){
			result = list;
			return;
		}
		
		if(result.size() >= count){
			return;
		}
		
		int index = (int)(Math.random() * list.size());
		T t = list.remove(index);
		result.add(t);
		
		getRandomList(list, count, result);
	}
	
	/**
	 * 是否纯数字
	 * @param data
	 * @return
	 */
	public static boolean isData(String data){
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher(data);
		return matcher.matches();
	}
	
	/**
	 * 拼接字符串
	 * @param openHeros
	 * @param heroId
	 * @return
	 */
	public static String append(String openHeros, int heroId) {
		if(openHeros == null){
			return String.valueOf(heroId);
		}
		
		StringBuilder result = new StringBuilder(openHeros);
		if(openHeros.endsWith(",")){
			return result.append(heroId).toString();
		}
		
		return result.append(",").append(heroId).toString();
	}
	
	/**
	 * 获取List列表中对应下标的元素
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T> T getIndexCell(List<T> list, int index){
		if(isEmpty(list)){
			return null;
		}
		
		if(index >= list.size() || index <= -1){
			return null;
		}
		return list.get(index);
	}

	/**
	 * 计算元素个数
	 * @param list
	 * @return
	 */
	public static <T> int count(Collection<T> list) {
		if(isEmpty(list)){
			return 0;
		}
		
		return list.size();
	}

	/**
	 * 去除重复
	 * @param list
	 * @return
	 */
	public static <T>List<T> filteDouble(List<T> list) {
		if(list == null || list.size() <= 1){
			return list;
		}
		Set<T> set = new HashSet<T>(list);
		return new ArrayList<T>(set);
	}
	
	/**
	 * 时间转换成long
	 * @param date
	 * @return
	 */
	public static long date2long(Date date){
		return date == null ? 0 : date.getTime();
	}
	
	/**
	 * list转成Map
	 * @param list
	 * @param m2n
	 * @return
	 */
	public static <M, N> Map<M, N> list2Map(List<N> list, M2N<N, M> m2n){
		Map<M, N> map = new HashMap<M, N>();
		if(isEmpty(list)){
			return map;
		}
		for(N n : list){
			M m = m2n.change(n);
			map.put(m, n);
		}
		return map;
		
	}

	/**
	 * 截取n位
	 * @param data
	 * @param num
	 * @return
	 */
	public static String cutData(int data, int num) {
		int left = data % (int)Math.pow(10, num);
		String result = String.valueOf(left);
		if(result.length() >= num){
			return result;
		}
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < num - result.length(); index ++){
			sb.append("0");
		}
		sb.append(result);
		return sb.toString();
	}
	
	/**
	 * 判定是否有交集
	 * @param mList
	 * @param nList
	 * @return
	 */
	public static <T> boolean hasSame(List<T> mList, List<T> nList){
		if(isEmpty(mList) || isEmpty(nList)){
			return false;
		}
		
		for(T t : nList){
			if(mList.contains(t)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 字符串数组是否包含某个数字
	 * @param all
	 * @param data
	 * @return
	 */
	public static boolean contains(String[] all, int data) {
		String idStr = String.valueOf(data);
		for(String str : all){
			if(idStr.equals(str)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 重复随机
	 * @param min
	 * @param max
	 * @param num
	 * @return
	 */
	public static List<Integer> getRandom(int min, int max, int num) {
		List<Integer> result = new ArrayList<Integer>(num);
		
		for(int index = 0; index < num; index ++){
			result.add(random(min, max));
		}
		
		return result;
	}
	
	/**
	 * 计算总和
	 * @param list
	 * @return
	 */
	public static int sum(List<Integer> list){
		if(isEmpty(list)){
			return 0;
		}
		
		int total = 0;
		for(int data : list){
			total += data;
		}
		return total;
	}
	
	/**
	 * int数组转字符串
	 * @param bytes
	 * @return
	 */
	public static String intArray2String(byte[] bytes){
		if(bytes == null || bytes.length == 0){
			return null;
		}
		
		StringBuilder result = new StringBuilder();
		for(int data : bytes){
//			result.append(Integer.toHexString(data)).append(" ");
			result.append(data & 0xff ).append(" ");
		}
		result.setLength(result.length() - 1);
		return result.toString();
	}
	
	/**
	 * int数组转字符串
	 * @param bytes
	 * @return
	 */
	public static String intArray2String(int[] bytes){
		if(bytes == null || bytes.length == 0){
			return null;
		}
		
		StringBuilder result = new StringBuilder();
		for(int data : bytes){
//			result.append(Integer.toHexString(data)).append(" ");
			result.append(data & 0xff).append(" ");
		}
		result.setLength(result.length() - 1);
		return result.toString();
	}
	
	public static void main(String[] args) {
		for(int index = 0; index < 10; index ++){
			System.err.println(getRandom(1,  6, 3));
		}
	}
}















