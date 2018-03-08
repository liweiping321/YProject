package com.road.fire.fighting.data;

import com.game.cfg.entity.ConfigsCfg;
import com.game.fighting.GameStatus;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;

import java.util.*;

/**
 * @author jianpeng.zhang
 * @since 2017/5/31. 战斗数据统计管理器
 */
public class FightStatisticManager {
	private Map<Living<?>, FightStatistic> dataMap = new HashMap<>();

	/**
	 * 忘记进入游戏时将玩家添加到管理器来
	 */
	public void addPlayer(Hero hero) {
		dataMap.put(hero, new FightStatistic());
		dataMap.get(hero).hurtMap = new HashMap<>();
		dataMap.get(hero).self = hero;
	}

	/**
	 * 记录对生物伤害
	 */
	public void addHurt(Hero hero, Living target, int num) {
		if (target instanceof Hero) {
			dataMap.get(hero).heroHurt += num;
		} else if (target instanceof Monster) {
			dataMap.get(hero).monsterHurt += num;
		}
	}

	/**
	 * 记录承受到的伤害，以及单前生命各敌人所造成的伤害
	 */
	public void addReceiveHurt(Hero hero, Living source, int num) {
		dataMap.get(hero).receiveHurt += num;
		dataMap.get(hero).hurtMap.put(source, hero.getGame().getFrameTime());
	}

	/**
	 * 记录杀人和被杀的英雄的次数
	 * 
	 * @param killHero
	 *            被击杀的英雄
	 * 
	 * @return 杀手
	 */
	public Living<?> addHeroDead(Hero killHero) {
		dataMap.get(killHero).deadCount++;
		dataMap.get(killHero).sequentKillNum = 0;
		dataMap.get(killHero).timeSequentKillNum = 0;

		// 通过最后击杀时间来查找杀手
		Living<?> living = null;
		long temp = 0;
		for (Map.Entry<Living<?>, Long> entry : dataMap.get(killHero).hurtMap.entrySet()) {
			if (entry.getValue() > temp) {
				living = entry.getKey();
			}
		}
		if (living instanceof Hero) {
			dataMap.get(living).killNum++;
			if (getCampScore(living.getCampType()) >= killHero.getGame().getFightingType().getVictoryNum()) {
				killHero.getGame().setStatus(GameStatus.computing);
			}
		}
		return living;
	}

	public void sendKillAchieve(Living<?> killer) {
		if (killer == null || !(killer instanceof Hero)) {
			return;
		}

		dataMap.get(killer).sequentKillNum++;
		if (killer.getFrameTime() - dataMap.get(killer).lastKillTime > ConfigsCfg.timeSequentKill) {
			dataMap.get(killer).timeSequentKillNum = 0;
		}
		dataMap.get(killer).timeSequentKillNum++;
		dataMap.get(killer).lastKillTime = killer.getFrameTime();
		killer.sendMsg(MsgCode.KillAchieveResp, FtMsgUtil.builderKillAchieveResp(dataMap.get(killer)));
	}

	/**
	 * 统计助攻人物并返回
	 * 
	 * @param living
	 *            击杀英雄的生物
	 * @param killHero
	 *            被击杀的英雄
	 * @return 助攻英雄
	 */
	public List<Living<?>> addAssistsTime(Living living, Hero killHero) {
		ArrayList<Living<?>> livings = new ArrayList<>();
		for (Map.Entry<Living<?>, Long> entry : dataMap.get(killHero).hurtMap.entrySet()) {
			if (entry.getKey() instanceof Hero && entry.getKey() != living) {
				if (killHero.getGame().getFrameTime() - entry.getValue() <= ConfigsCfg.assistsTime) {
					dataMap.get(entry.getKey()).assistsCount++;
					livings.add(entry.getKey());
				}
			}
		}
		dataMap.get(killHero).hurtMap.clear();
		return livings;
	}

	public List<Living<?>> getAllHero() {
		return new ArrayList<>(dataMap.keySet());
	}

	/**
	 * 计算所有人的击杀效率
	 */
	private void calculateEfficacy() {
		for (Living<?> hero : dataMap.keySet()) {
			if (dataMap.get(hero).deadCount == 0) {
				dataMap.get(hero).efficacy = dataMap.get(hero).heroHurt;
			} else {
				dataMap.get(hero).efficacy = dataMap.get(hero).heroHurt / (float) dataMap.get(hero).deadCount;
			}
		}
	}

	public int getCampScore(int campType) {
		int score = 0;
		for (Living<?> hero : dataMap.keySet()) {
			if (hero.getCampType() == campType) {
				score += dataMap.get(hero).killNum;
			}
		}
		return score;
	}

	/**
	 * @return 返回玩家排行
	 */
	public List<FightStatistic> getRankBoard() {
		calculateEfficacy();
		ArrayList<FightStatistic> result = new ArrayList<>(dataMap.values());
		Collections.sort(result, rankComp);
		return result;
	}

	public FightStatistic getMvp() {
		calculateEfficacy();
		ArrayList<FightStatistic> result = new ArrayList<>(dataMap.values());
		Collections.sort(result, mvpComp);
		return result.get(0);
	}

	/**
	 * 排名的比较器
	 */
	private final static Comparator<FightStatistic> rankComp = new Comparator<FightStatistic>() {
		@Override
		public int compare(FightStatistic o1, FightStatistic o2) {
			if (o1.killNum != o2.killNum) {
				return o2.killNum - o1.killNum;
			} else if (o1.assistsCount != o2.assistsCount) {
				return o2.assistsCount - o1.assistsCount;
			} else {
				return o2.efficacy - o1.efficacy >= 0 ? (int) Math.ceil(o2.efficacy - o1.efficacy)
						: (int) Math.floor(o2.efficacy - o1.efficacy);
			}
		}
	};

	/**
	 * mvp的比较器
	 */
	private final static Comparator<FightStatistic> mvpComp = new Comparator<FightStatistic>() {
		@Override
		public int compare(FightStatistic o1, FightStatistic o2) {
			return o2.efficacy - o1.efficacy >= 0 ? (int) Math.ceil(o2.efficacy - o1.efficacy)
					: (int) Math.floor(o2.efficacy - o1.efficacy);
		}
	};

}
