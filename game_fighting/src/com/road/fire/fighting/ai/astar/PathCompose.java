package com.road.fire.fighting.ai.astar;

/**
 * 合并路径
 * @author Administrator
 *
 */
public class PathCompose {
	public static short[] composePath(short[] path1,short[] path2){
		if(path1[path1.length-2]==path2[0]&&path1[path1.length-1]==path2[1]){
			short[] t=new short[path1.length+path2.length-2];
			System.arraycopy(path1, 0, t, 0, path1.length);
			System.arraycopy(path2, 2, t, path1.length, path2.length-2);
			return t;
		}
		return null;
	}
	
}
