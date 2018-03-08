package com.game.consts;


/**
 * 游戏常量
 * 
 * @author jason.lin
 */
public class GameConsts
{
	/**全局当前时间*/
	public static  long GlobalCurrTime=System.currentTimeMillis();
	/**失去心跳时间*/
	public static final int LoseHeartTime=30000;
	/**一年时长(单位毫秒)*/
	public static final long YearTime=365*24*3600*1000l;

	/**** 匹配一轮的时长 单位毫秒 ****/
	public static final int MATCH_TIME = 30000;

	/**** 匹配玩家分组人数 ****/
	public static final int MATCH_PLAYER_NUM = 2;

	/***英雄时装基数****/
	public static final int HERO_AVARTAR_BASE = 1000;
	
	/**十万分之一*/
	public static final double OneInOneHundredThousand=0.00001;
	
	/**十万*/
	public static final int HundredThousand=100000;
	/**名字姓*/
	public static final int NameTypeFamily =1;
	/**女名*/
	public static final int NameTypeMale=2;
	/**男名*/
	public static final int NameTypeFemale=3;
	/**性别男*/
	public static final int SexMale=0;
	/**性别女*/
	public static final int SexFemale=1;


	/**固定概率掉落(每项概率不相互影响，可能掉多个，可能一个都不掉落)*/
	public static final int DropTypeFix=0;
	/**整体概率多选一(概率相互影响，只能掉落一个) */
	public static final int DropTypeOne=1;
	
	/**英雄等级经验*/
	public static final int LevelExpHero = 0;
	
 
	
	
	
	
 
}
