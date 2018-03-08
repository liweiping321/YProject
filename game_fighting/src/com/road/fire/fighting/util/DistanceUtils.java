package com.road.fire.fighting.util;

import java.util.HashMap;
import java.util.Map;

import com.game.cfg.entity.ConfigsCfg;
import com.road.fire.fighting.phy.living.Living;

/**
 * 移动工具
 * @author jason.lin
 *
 */
public class DistanceUtils {
	/**最大角度**/
	public static int Max_Degree = 360;
	
	public static int Max_X=1000;

	private static Map<Integer, Double> sinMap = new HashMap<Integer, Double>();
	private static Map<Integer, Double> cosMap = new HashMap<Integer, Double>();
	
	private static Map<Integer,Map<Integer,Integer> > distanceMap=new HashMap<>();
	
	public static void initCache(){
	
		for(int degree =0; degree <=Max_Degree; degree ++){
			double angle = Math.toRadians(degree);
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			
			sinMap.put(degree, sin);
			cosMap.put(degree, cos);
		}
		
		for(int x1=0;x1<Max_X;x1++){
			Map<Integer,Integer> yMap=new HashMap<>();
			for(int y1=0;y1<Max_X;y1++){
				yMap.put(y1, (int) Math.round(Math.sqrt(x1 * x1 + y1 * y1)));
			}
			distanceMap.put(x1, yMap);
		}
	}
	
	public static double getSinFromCache(int degree){
		return sinMap.get(degree);
	}
	
	public static double getCosFromCache(int degree){
		return cosMap.get(degree);
	}
	
	/**
	 * 根据角度计算x轴增量
	 * @param distance
	 * @param angle
	 * @return
	 */
	public static int computeAddX(int distance, int angle) {
		return (int)(distance * getCosFromCache(angle));
	}

	/**
	 * 根据角度计算y轴增量
	 * @param distance
	 * @param angle
	 * @return
	 */
	public static int computeAddY(int distance, int angle) {
		return (int)(distance * getSinFromCache(angle));
	}
	
	//获得两点的距离，并进行四舍五入
	 public static int getDistance(int x,int y){
		    Map<Integer,Integer> yMap=	distanceMap.get(x);
		    if(null!=yMap){
		    	Integer distance=yMap.get(y);
		    	if(null!=distance){
		    		return distance;
		    	}
		    }
			return (int) Math.round(Math.sqrt(x * x + y * y));
	}
	 
	 
	 
	 public static boolean between(int value, int start, int end) {
		 if(start<end){
			 return value>=start && value<=end;
		 }else{
			 return value >=end &&value<=start;
		 }
		
	}
	 /**获取两个生物之间角度*/
	 public static int getAngle(Living<?> source,Living<?> target){
		 return getAngle(source.getX(),source.getY(),target.getX(),target.getY());
	 }
	 /**获取两个点之间角度*/
	 public static int getAngle(int x, int y, int x2, int y2) {
	 
		int angle=  (int) (Math.atan2(y2-y,x2-x )*(180/Math.PI));
		if(angle<0){
			angle=360+angle;
		}
		return angle;
	}

	public static int getDistance(Living<?> source,Living<?> target){
		 return getDistance(Math.abs(source.getX()-target.getX()), Math.abs(source.getY()-target.getY()));
	 }
	 
	 public static int getDistance(int x1,int y1,int x2,int y2){
		 return getDistance(Math.abs(x1-x2), Math.abs(y1-y2));
	 }
	 /**两个生物在指定的距离范围内*/
	 public static boolean isInRange(Living<?> source,Living<?> target,int distance){
		 int x=Math.abs(target.getX()-source.getX());
		 if(x>distance){
			 return false;
		 }
		 int y=Math.abs(target.getY()-source.getY());
		 if(y>distance){
			 return false;
		 }
		 int cellSize=ConfigsCfg.cellSzie;
		 //同时缩小
		 int x1=x/cellSize;
		 int y1=y/cellSize;
		 int distance1=distance/cellSize;
		 target.setTeampDistance(getDistance(x1, y1));

		 return target.getTempDistance() <= distance1;

	 }
	 
	 public static boolean isInRange(int x1,int y1,int x2,int y2,int distance){
		 int x=Math.abs(x1-x2);
		 if(x>distance){
			 return false;
		 }
		 int y=Math.abs(y1-y2);
		 if(y>distance){
			 return false;
		 }
		 int cellSize=ConfigsCfg.cellSzie;
		 //同时缩小
		 int tx1=x/cellSize;
		 int ty1=y/cellSize;
		 int distance1=distance/cellSize;
		 
		 return getDistance(tx1, ty1) <= distance1;
	 }
	 /**是否在夹角内*/
	 public static boolean isInAngle(Living<?> source,Living<?> target,int downAngle,int upAngle){
		 int angle= getAngle(source, target);
		 if(downAngle<0){
			 downAngle=360+downAngle;
			
		 }
		 if(upAngle>360){
			 upAngle=upAngle-360;
		 }
		 if(downAngle>upAngle){
			 return between(angle, 0, upAngle)||between(angle, downAngle, 360);
		 }else{
			 return between(angle, downAngle, upAngle);
		 }
	 }
	public static void main(String[] args) {
		long startTime=System.nanoTime();
		 for(int i=0;i<100;i++){
			 for(int j=0;j<10000;j++){
				 getAngle(0, 0, i, j);
			 }
		 }
		 long endTime=System.nanoTime();
		 
		 System.out.println((endTime-startTime)/1000000);
	}

	
	
 
}
