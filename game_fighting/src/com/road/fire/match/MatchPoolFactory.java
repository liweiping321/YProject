package com.road.fire.match;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.game.executor.GameTask;
import com.game.executor.GameThreadFactory;
import com.game.executor.pool.GameScheduledThreadPoolExecutor;
import com.google.common.collect.Lists;
import com.road.fire.cfg.provider.FightingTypeCfgProvider;
import com.road.fire.entity.cfg.FightingTypeCfg;

/**
 * @author jianpeng.zhang
 * @since 2017/5/3.
 */
public class MatchPoolFactory extends GameTask {

	private final GameScheduledThreadPoolExecutor matchThread;

	private Map<Integer, TreeMap<Integer, MatchPool>> matchPools = new HashMap<>();

	private MatchPoolFactory() {
		matchThread = new GameScheduledThreadPoolExecutor(1,
				new GameThreadFactory("matchThread"));
	}

	/**
	 * 加载匹配池分层信息
	 */
	public void init() {
		Collection<FightingTypeCfg> fightingTypeCfgs = FightingTypeCfgProvider
				.getInstance().getConfigDatas();

		List<Integer> grades = Lists.newArrayList(500, 1000, 1500);
		for (FightingTypeCfg typeCfg : fightingTypeCfgs) {

			TreeMap<Integer, MatchPool> treeMap = new TreeMap<>();
			for (int grade : grades) {
				treeMap.put(grade, new MatchPool(typeCfg, grade));
			}

			matchPools.put(typeCfg.getId(), treeMap);
		}
		matchThread.scheduleWithFixedDelay(this, 1000, 1000,
				TimeUnit.MILLISECONDS);
	}

	public void addMatcher(PlayerMatchInfo matchInfoBean) {

		MatchPool matchPool = matchPools.get(matchInfoBean.getFightingType())
				.floorEntry(matchInfoBean.getElo()).getValue();

		matchPool.addMatcher(matchInfoBean);
	}
	
	@Override
	public void doRun() {
		Collection<TreeMap<Integer, MatchPool>> treeMaps = matchPools.values();
		for (TreeMap<Integer, MatchPool> treeMap : treeMaps) {

			for (MatchPool matchPool : treeMap.values()) {
				matchPool.run();
			}
		}

	}
	
	public static MatchPoolFactory getInstance() {
		return instance;
	}

	private static MatchPoolFactory instance = new MatchPoolFactory();


}
