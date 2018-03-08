package com.road.fire.fighting.ai.astar;
 

/**
 * 工具类
 * @author spring
 *
 */
public class ArithmeticUtils {
	/**
	 * Logger for this class
	 */
	

	public static int shortToInt(short x, short y){
		int result = x;
		result = result << 16;
		result += y;
		return result;
	}
	
	public static short[] intToShort(int bl) {
		short y = (short)(bl & 0xffff);
		short x = (short)(bl >> 16);
		short[] result = new short[]{x,y};
		return result;
	}
 
 
}
