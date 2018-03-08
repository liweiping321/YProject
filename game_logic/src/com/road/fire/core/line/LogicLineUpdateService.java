package com.road.fire.core.line;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.Request;
import com.game.core.net.handler.RequestHandler;
import com.game.core.net.handler.RequestHandlerCenter;
import com.game.core.net.session.NetSession;
import com.game.executor.CancellableTask;
import com.game.utils.JVMDumpUtil;
import com.road.fire.player.BaseOnlinePlayer;

/**
 * 
 * 逻辑分线刷帧处理
 */
public class LogicLineUpdateService<Player extends BaseOnlinePlayer> extends CancellableTask {
	 private static final Logger LOG =LogManager.getLogger(LogicLineUpdateService.class);

	private static final long DealmsgInterval = 30;
	
	private LogicLine<Player> logicLine;
	
	private ScheduledThreadPoolExecutor executorService;

	private ConcurrentLinkedQueue<Runnable> taskList = new ConcurrentLinkedQueue<Runnable>();
	
	private long now;

 

	public LogicLineUpdateService(LogicLine<Player> logicLine,ScheduledThreadPoolExecutor executorService) {
		this.logicLine = logicLine;
		this.executorService=executorService;
	 
	}

	@Override
	public void run() {
		// 战斗刷新间隔
		long sleepTime = 0;
		try {
			now = System.currentTimeMillis();
			 
			// 处理玩家消息
			runHandlers();
			// 执行战斗逻辑
	 

			long usetime = System.currentTimeMillis() - now;
			sleepTime = sleepTime + DealmsgInterval - usetime;
		} catch (Throwable e) {
			LOG.error("线程刷帧 line"+this.logicLine.getLineId()+" "+e.getMessage(), e);
		}
	}

	private void runHandlers() {
		Collection<Player> onlinePlayers = logicLine.getAllPlayers();
		for (Player onlineRole : onlinePlayers) {
			NetSession gamePlayer = onlineRole.getGamePlayer();
			dealHandler(gamePlayer);
		}
		 

	}

	private void dealHandler(NetSession gamePlayer) {
		if (gamePlayer != null) {
			 gamePlayer.executeRequest();
		}

	}

	/**
	 * 为了避免同步的产生的问题，有些事情必须放到桢循环 比如玩家下线，如果允许其他线程将role的场景置空，则游戏循环可能有异常抛出
	 * 包括下线、上线、切换场景、角色已在服务器上继续使用、进入副本
	 * 
	 * @param run
	 */
	public void addTask(Runnable run) {
		if (run == null) {
			return;
		}
		taskList.offer(run);
	}

	public void start() {
		executorService.scheduleWithFixedDelay(this, DealmsgInterval,
				DealmsgInterval, TimeUnit.MILLISECONDS);
	}
	
	public void reStart() {
		JVMDumpUtil.dumpThreads();
		this.cancel(true);
		executorService.scheduleWithFixedDelay(this, DealmsgInterval,
				DealmsgInterval, TimeUnit.MILLISECONDS);
		 
	}

	public void heartbeat(long heartbeatTime){
		 long delayTime=heartbeatTime-now;
		 if(delayTime>800L){
			 JVMDumpUtil.dumpThreads();
			LOG.info("分线："+logicLine.getLineId()+" 抓取堆栈  delayTime:"+delayTime);
		 }
		 if(delayTime>10000L){
			 reStart();
			 LOG.info("分线："+logicLine.getLineId()+" 重启  delayTime:"+delayTime);
		 }
		 
	}
	public void stop() {
		for (Runnable run = taskList.poll(); run != null; run = taskList.poll()) {
			try {
				run.run();
			} catch (Exception ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}
		this.cancel(true);
	}

	public LogicLine<Player> getLogicLine() {
		return logicLine;
	}

	public void setLogicLine(LogicLine<Player> logicLine) {
		this.logicLine = logicLine;
	}

	public long getCurrentTime() {

		return now;
	}
}
