/**
 *All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.game.fighting;

import com.game.executor.GameThreadFactory;
import com.game.executor.pool.GameScheduledThreadPoolExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lip.li
 * @date 2016-10-23
 * @version
 * 
 */
public class AbstractGameMgr {

	private static final Logger logger = LogManager.getLogger(AbstractGameMgr.class);

	protected final List<GameAction> gameActions = new CopyOnWriteArrayList<GameAction>();

	protected AtomicInteger gameId = new AtomicInteger(0);

	// 所有房间逻辑使用的线程池
	protected ScheduledThreadPoolExecutor gameThreadPool;

	protected int threadNum;

	/**
	 *
	 */
	private void initGameAction(int threadNum) {
		this.threadNum = threadNum;

		gameThreadPool = new GameScheduledThreadPoolExecutor(threadNum, new GameThreadFactory("GameMgr"));

		for (int i = 0; i < threadNum; i++) {
			GameAction gameAction = new GameAction(i);
			gameThreadPool.execute(gameAction);
			gameActions.add(gameAction);
		}

	}

	public AbstractGame getGame(int gameId) {
		for (GameAction gameAction : gameActions) {
			AbstractGame baseGame = gameAction.getGame(gameId);
			if (baseGame != null) {
				return baseGame;
			}
		}
		return null;
	}

	public void addBaseGame(AbstractGame game) {
		GameAction action = getGameAction();
		action.addGame(game);
	}

	public void removeBaseGame(AbstractGame game) {
		for (GameAction action : gameActions) {
			if (action.removeGame(game)) {
				return;
			}
		}
	}

	/**
	 * @return 返回当前战斗数最少的GameAction容器
	 */
	private GameAction getGameAction() {

		List<GameAction> actions = new ArrayList<GameAction>();
		for (GameAction action : gameActions) {
			if (action.checkActive()) {
				actions.add(action);
			} else {
				logger.error("GameAction delay actionId {},delayTime {}", action.getActionId(),
						(System.currentTimeMillis() - action.getLastTime()));

				if ((gameThreadPool.getTaskCount() - gameThreadPool.getCompletedTaskCount()) < threadNum) {
					action.restart();
					gameThreadPool.execute(action);
				}
			}
		}

		Collections.sort(actions);
		return actions.get(0);
	}

	public List<GameAction> getGameActions() {
		return gameActions;
	}

	protected int getNextGameId() {
		return gameId.incrementAndGet();
	}

	public void start(int threadNum) {
		initGameAction(threadNum);
	}

	public void stop() {
		gameThreadPool.shutdown();
	}
}
