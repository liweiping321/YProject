package com.road.fire.match;

import com.game.executor.GameTask;
import com.game.protobuf.MsgCode;
import com.game.protobuf.match.MatchProto;
import com.google.common.collect.ImmutableList;
import com.road.fire.entity.cfg.FightingTypeCfg;
import com.road.fire.fighting.GameMgr;
import com.road.fire.fighting.PVPGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author jianpeng.zhang
 * @since 2017/5/3.
 */
public class MatchPool extends GameTask {

	private final FightingTypeCfg typeCfg;

	private final int grade;

	private List<PlayerMatchInfo> waitQueue = new ArrayList<>();

	// private TreeSet<PlayerMatchInfo> matchQueue = new TreeSet<>();

	/**
	 * 不用TreeSet的原因是，Tree列表不能有两个分数相同的值
	 * 
	 */
	private MatchQueue matchQueue = new MatchQueue();

	public MatchPool(FightingTypeCfg typeCfg, int grade) {
		this.typeCfg = typeCfg;
		this.grade = grade;
	}

	@Override
	public void doRun() {
		match();
	}

	private void startPVP(Room room) {

		PVPGame pvpGame = new PVPGame(room);

		pvpGame.init();

		GameMgr.getInstance().addBaseGame(pvpGame);

		for (Team team : room.getTeams().values()) {
			for (PlayerMatchInfo playerMatchInfo : team.getMatchInfos()) {
				MatchProto.PBStartMatchResp.Builder builder = MatchProto.PBStartMatchResp
						.newBuilder();
				builder.setMatchTime(999);
				playerMatchInfo.getPlayerInfo().getPlayer()
						.sendMsg(MsgCode.StartMatchResp, builder.build());
			}
		}

	}

	public void addMatcher(PlayerMatchInfo matchInfo) {
		waitQueue.add(matchInfo);

	}

	public void match() {
		int queueNum = matchQueue.getTotalNum();
		int waitNum = waitQueue.size() + queueNum;

		if (waitNum < typeCfg.getTotalNum()) {
			if (waitNum > 0) {
				LOGGER.info(
						"match pool type:{} grade:{}  waitQueueSize:{},matchQueueSize:{}",
						typeCfg.getId(), grade, waitQueue.size(), queueNum);
			}
			return;
		}

		if (waitQueue.size() > 0) {
			List<PlayerMatchInfo> tempWait = waitQueue;
			waitQueue = new ArrayList<>();
			matchQueue.addAll(tempWait);
		}

		do {

			LinkedList<PlayerMatchInfo> matchList = getGamePlayerList(typeCfg
					.getTotalNum());
			if (matchList.size() < typeCfg.getTotalNum()) {
				return;
			}
			ImmutableList<PlayerMatchInfo> copy = ImmutableList
					.copyOf(matchList);
			if (matchList.size() < typeCfg.getTotalNum()) {
				waitQueue.addAll(matchList);
				return;
			}

			Room room = new Room(typeCfg);

			outer: for (int teami = 0; teami < typeCfg.getTeamNum(); teami++) {

				for (int cmpi = 1; cmpi <= typeCfg.getCmpNum(); cmpi++) {
					if (matchList.isEmpty()) {
						break outer;
					}
					if (teami % 2 == 0) {// 从头部取
						room.addMatchPlayer(cmpi, matchList.removeFirst());

					} else {// 从尾部取
						room.addMatchPlayer(cmpi, matchList.removeLast());
					}
				}

			}

			TreeSet<Integer> avgElos = room.getAvgElos();

			if (Math.abs(avgElos.last() - avgElos.first()) < typeCfg.getMd()) {
				startPVP(room);
			} else {
				waitQueue.addAll(copy);
			}
		} while (matchQueue.getTotalNum() >= typeCfg.getTotalNum());

	}

	/**
	 * 具体分配战斗团队的算法。
	 */
	private LinkedList<PlayerMatchInfo> getGamePlayerList(int teamTotalNum) {
		return matchQueue.getTopN(teamTotalNum);
	}

}
