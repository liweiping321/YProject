package com.road.fire.match;

import java.util.*;

/**
 * @author jianpeng.zhang
 * @since 2017/5/8.
 */
public class MatchQueue {
	private TreeMap<Integer, List<PlayerMatchInfo>> matchQueue = new TreeMap<>();

	public int getTotalNum() {
		int sum = 0;
		for (List<PlayerMatchInfo> list : matchQueue.values()) {
			sum += list.size();
		}
		return sum;
	}

	public void addAll(List<PlayerMatchInfo> list) {
		for (PlayerMatchInfo playerInfo : list) {
			if (matchQueue.get(playerInfo.getElo()) == null) {
				matchQueue.put(playerInfo.getElo(),
						new ArrayList<PlayerMatchInfo>());
			}
			matchQueue.get(playerInfo.getElo()).add(playerInfo);
		}
	}

	/**
	 * 得到前几个元素
	 */
	public LinkedList<PlayerMatchInfo> getTopN(int num) {
		LinkedList<PlayerMatchInfo> matchList = new LinkedList<>();

		if (num == 0) {
			return matchList;
		}

		int currentNum = 0;
		Iterator<Map.Entry<Integer, List<PlayerMatchInfo>>> iterator = matchQueue
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, List<PlayerMatchInfo>> entry = iterator.next();

			Iterator<PlayerMatchInfo> listIterator = entry.getValue().iterator();
			while (listIterator.hasNext()) {
				PlayerMatchInfo matchInfo = listIterator.next();
				if (!matchInfo.getPlayerInfo().isOnline()) {
					continue;
				}
				matchList.add(matchInfo);
				listIterator.remove();
				currentNum++;
				if (currentNum == num) {
					break;
				}
			}
			if (!listIterator.hasNext()) {
				iterator.remove();
			}
			if (currentNum == num) {
				return matchList;
			}
		}
		return matchList;
	}

}
