/**
 *All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.game.fighting;

import com.game.consts.GameConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author lip.li
 *
 */
public class GameAction implements Runnable, Comparable<GameAction> {
	
	private static final Logger logger = LogManager.getLogger(GameAction.class.getName());
	public static final long THREAD_INTERVAL = 41;

	public static int DELAY_TIME = 0;

	public static final int CHECKACTIVE_TIME = 10 * 1000;
	private int actionId;

	private long lastTime = 0l;



	private final List<AbstractGame> baseGames = new CopyOnWriteArrayList<AbstractGame>();

	public GameAction(int actionId) {
		this.actionId = actionId;
		this.lastTime = System.currentTimeMillis();
	}

	public void restart() {
		logger.error("GameAction restart actionId {},delayTime {}", actionId, (System.currentTimeMillis() - lastTime));
		this.lastTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		while (true) {

			try {
				long start =System.currentTimeMillis();
				GameConsts.GlobalCurrTime=start;
				lastTime = start;
				int balance = 0;
				for (AbstractGame baseGame : baseGames) {
					try {
						long gameStart = System.currentTimeMillis();
						baseGame.setFrameTime(gameStart);
						 
						baseGame.update(gameStart);
				 
						long gameCost =System.currentTimeMillis() - gameStart;
						if (gameCost > 0) {
							logger.warn("game {} is delay ,cost time  {} ms !", baseGame.toString(), gameCost);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}

				// 时间补偿
				long end = System.currentTimeMillis();

				balance += THREAD_INTERVAL - (end - start);

				if (balance > 0) {
					try {
						Thread.sleep(balance);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
					balance = 0;
				} else {
					if (balance < -1000) {
						logger.warn("game action is delay " + balance + " ms!");
						balance += 1000;
					}
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}

		}

	}

	public List<AbstractGame> getBaseGames() {
		return baseGames;
	}

	public void addGame(AbstractGame baseGame) {
		baseGames.add(baseGame);
	}

	public boolean removeGame(AbstractGame baseGame) {
		return baseGames.remove(baseGame);
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	@Override
	public int compareTo(GameAction o) {

		return baseGames.size() - o.getBaseGames().size();
	}

	public void disposeGame(AbstractGame game) {
		 
		removeGame(game);
	}

	public AbstractGame getGame(int gameId) {
	 

		return null;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public boolean checkActive() {
		if (System.currentTimeMillis() - lastTime > CHECKACTIVE_TIME) {
			return false;
		}
		return true;
	}

}
