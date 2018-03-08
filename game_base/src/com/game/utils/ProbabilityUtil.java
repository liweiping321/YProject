package com.game.utils;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
/**
 * 根据几率 计算 是否命中
 * @author Administrator
 *
 */
public class ProbabilityUtil{
	public static final int gailv=10000;//总概率  
	public  static final Random random=new Random();	
	/**
     * 根据几率  计算是否生成 
     * @param probability
     * @return
     */
    public  static boolean isGenerate(int probability,int gailv)
    {
    	if(gailv==0){gailv=1000;}
    	int random_seed=random.nextInt(gailv+1);
    	return probability>=random_seed;
    }
    
    /**
     * 
     * gailv/probability 比率形式
     * @param probability
     * @param gailv
     * @return
     */
    public  static boolean isGenerate2(int probability,int gailv)
    {
    	if (probability == gailv) return true;
    	if(gailv==0){gailv=1;}
    	int random_seed=random.nextInt(probability);
    	return random_seed + 1 <= gailv;
    }
    /**
     * 根据几率  计算是否生成 
     * @param probability
     * @return
     */
    public  static boolean defaultIsGenerate(int probability)
    {
    	 if(probability<0){
    		 new Exception("非法的概率数据："+probability+" must between 0 to 100000").printStackTrace();
    		 return false;
    	 }
    	 int random_seed=random.nextInt(gailv);
    	 return probability>=random_seed;
    }
    /**
     * 随机一个值 在min到100000之间
     * @param min
     * @return 包含min 100000
     */
    public static int  randomValue(int min)
    {
    	int temp = gailv - min;
		temp = ProbabilityUtil.random.nextInt(temp + 1);
		temp = temp + min;
		return temp;
    }
    /**
     * 从 min 和 max 中间随机一个值
     * @param max
     * @param min
     * @return 包含min max
     */
    public static int  randomValue(int max,int min)
    {
    	int temp = max - min;
		temp = ProbabilityUtil.random.nextInt(temp + 1);
		temp = temp + min;
		return temp;
    }
    
    
     /**
      * 返回在0-maxcout之间产生的随机数时候小于num
      * @param num
      * @return
      */
    public static boolean isGenerateToBoolean(int num,int maxcout){
    	double count=Math.random()*maxcout;
    	if(num>count){
    		return true;	
    	}
    	return false;
    }
    /**
     * 返回在0-maxcout之间产生的随机数时候小于num
     * @param num
     * @return
     */
   public static boolean isGenerateToBoolean(double num,int maxcout){
   	double count=Math.random()*maxcout;
   	if(num>count){
   		return true;	
   	}
   	return false;
   }
   /**
    * 随机产生min到max之间的整数值 包括min max
    * @param min
    * @param max
    * @return
    */
   public static int randomIntValue(int min,int max){
	   return (int)(Math.random() * (double)(max - min + 1)) + min;  
   }
   
   
   public static float randomFloatValue(float min,float max){
	   return (float)(Math.random() * (double)(max-min)) + min;  
   }
   
   /**
	 * 从min到max随机，包括min跟max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */

	public static int randomGetInt(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("maxValue must > minValue");
		} else if (min == max) {
			return min;
		} else {
			int interval = max - min + 1;
			int value = min + RandomUtils.nextInt(interval);
			return value;
		}
	}

	public static boolean randomGetBoolean() {
		return RandomUtils.nextBoolean();
	}
  
}
