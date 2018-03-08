package com.game.cfg.entity;

import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 工具生成
 * @date 2017-05-22 05:38:41
 */
@EntityMap(table = "t_s_configs")
public class ConfigsCfg extends AbstractEntityBean<String, ConfigsCfg> {

	/** 配置的主键 */
	@PropertyMap(column = "Name", primarykey = true)
	private String name;
	/** 配置的值（任意类型,之后在程序里转） */
	@PropertyMap(column = "Value")
	private String value;
	/** 配置说明 */
	@PropertyMap(column = "Desc")
	private String desc;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	/** 掉落物物品存活时间 */
	public static int dropLifeTime = 10000;
	/** 格子大小 */
	public static int cellSzie = 500;
	/** 回城复活时间 */
	public static int cityReliveTime = 3000;
	/** 尸体消失时间 */
	public static int bodyDisappeareTime = -1;
	/** 移动时长 */
	public static int moveTime = 300;
	/** 是否输出生物当前位置 */
	public static boolean showCurPoint = true;
	/** 辅助射击锁定攻击时间 */
	public static int lockAttackTime = 1000;
	/** 拾取物品检查时间间隔(单位毫秒) */
	public static int pickCheckTime = 1000;
	/** 是否输出消息内容 */
	public static boolean showMsg = true;
	/** 助攻判定时间 */
	public static int assistsTime = 30000;
	/** 连续击杀成就的限定时间间隔 */
	public static int timeSequentKill = 30000;
	/** 英雄被击杀掉落ID */
	public static Set<Integer> heroDropIds = new HashSet<>();
	/** 英雄最大等级 */
	public static int heroMaxLevel = 5;

	public static int getDropLifeTime() {
		return dropLifeTime;
	}

	public static void setDropLifeTime(int dropLifeTime) {
		ConfigsCfg.dropLifeTime = dropLifeTime;
	}

	public static int getCellSzie() {
		return cellSzie;
	}

	public static void setCellSzie(int cellSzie) {
		ConfigsCfg.cellSzie = cellSzie;
	}

	public static int getCityReliveTime() {
		return cityReliveTime;
	}

	public static void setCityReliveTime(int cityReliveTime) {
		ConfigsCfg.cityReliveTime = cityReliveTime;
	}

	public static int getBodyDisappeareTime() {
		return bodyDisappeareTime;
	}

	public static void setBodyDisappeareTime(int bodyDisappeareTime) {
		ConfigsCfg.bodyDisappeareTime = bodyDisappeareTime;
	}

	public static int getMoveTime() {
		return moveTime;
	}

	public static void setMoveTime(int moveTime) {
		ConfigsCfg.moveTime = moveTime;
	}

	public static boolean isShowCurPoint() {
		return showCurPoint;
	}

	public static void setShowCurPoint(boolean showCurPoint) {
		ConfigsCfg.showCurPoint = showCurPoint;
	}

	public static boolean isShowMsg() {
		return showMsg;
	}

	public static void setShowMsg(boolean showMsg) {
		ConfigsCfg.showMsg = showMsg;
	}

	public static int getLockAttackTime() {
		return lockAttackTime;
	}

	public static void setLockAttackTime(int lockAttackTime) {
		ConfigsCfg.lockAttackTime = lockAttackTime;
	}

	public static int getPickCheckTime() {
		return pickCheckTime;
	}

	public static void setPickCheckTime(int pickCheckTime) {
		ConfigsCfg.pickCheckTime = pickCheckTime;
	}

	public static int getAssistsTime() {
		return assistsTime;
	}

	public static void setAssistsTime(int assistsTime) {
		ConfigsCfg.assistsTime = assistsTime;
	}

	public static Set<Integer> getHeroDropIds() {
		return heroDropIds;
	}

	public static void setHeroDropIds(Set<Integer> heroDropIds) {
		ConfigsCfg.heroDropIds = heroDropIds;
	}

	public static int getTimeSequentKill() {
		return timeSequentKill;
	}

	public static void setTimeSequentKill(int timeSequentKill) {
		ConfigsCfg.timeSequentKill = timeSequentKill;
	}
}