package com.road.fire.fighting.data;

import com.road.fire.fighting.phy.living.Living;

import java.util.Map;

/**
 * @author jianpeng.zhang
 * @since 2017/5/31. 战斗数据统计
 */
public class FightStatistic {
	/**
	 * 本类所属玩家
	 */
	 Living<?> self;

	/**
	 * 击杀人数
	 */
	int killNum = 0;

	/**
	 * 死亡次数
	 */
	int deadCount = 0;

	/**
	 * 助攻次数
	 */
	int assistsCount = 0;

	/**
	 * 记录单前生命所受到的敌人最后一次伤害的时间点
	 */
	Map<Living<?>, Long> hurtMap;

	/**
	 * 对英雄伤害
	 */
	int heroHurt = 0;

	/**
	 * 对怪物伤害
	 */
	int monsterHurt = 0;

	/**
	 * 承受总伤害
	 */
	int receiveHurt = 0;

	/**
	 * 效率值 输出总量/死亡次数
	 */
	float efficacy = 0;

	/**
	 * 连续杀人数，被杀后清零
	 */
	int sequentKillNum = 0;

	/**
	 * 在一段时间间隔内连续杀人数
	 */
	int timeSequentKillNum = 0;

	/**
	 * 最后杀人时间
	 */
	long lastKillTime = 0;

	public Living<?> getSelf() {
		return self;
	}

	public int getKillNum() {
		return killNum;
	}

	public int getDeadCount() {
		return deadCount;
	}

	public int getAssistsCount() {
		return assistsCount;
	}

	public Map<Living<?>, Long> getHurtMap() {
		return hurtMap;
	}

	public int getHeroHurt() {
		return heroHurt;
	}

	public int getMonsterHurt() {
		return monsterHurt;
	}

	public int getReceiveHurt() {
		return receiveHurt;
	}

	public float getEfficacy() {
		return efficacy;
	}

	public int getSequentKillNum()
	{
		return sequentKillNum;
	}

	public int getTimeSequentKillNum()
	{
		return timeSequentKillNum;
	}

	public long getLastKillTime()
	{
		return lastKillTime;
	}
}

