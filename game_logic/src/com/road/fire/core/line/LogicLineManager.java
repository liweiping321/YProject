package com.road.fire.core.line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.Response;
import com.game.executor.GameTask;
import com.game.executor.GameThreadFactory;
import com.game.executor.pool.GameScheduledThreadPoolExecutor;
import com.road.fire.player.BaseOnlinePlayer;

public class LogicLineManager<Player extends BaseOnlinePlayer> {
	
	private static final Logger LOG =LogManager.getLogger(LogicLineManager.class);
	
	private ScheduledThreadPoolExecutor executorService;
	
	private Map<Integer, LogicLine<Player>> logicLineMap = new HashMap<>();

 
	public void addLogicLine(LogicLine<Player> line) {
		logicLineMap.put(line.getLineId(), line);
		 
	}

	public LogicLine<Player> getLogicLine(int lineId) {
		LogicLine<Player> logicLine = logicLineMap.get(lineId);
		 
		return logicLine;
	}

 
	public Collection<LogicLine<Player>> getAllLogicLines() {

		return logicLineMap.values();
	}

 
	public void removePlayerById(long playerId) {
		LogicLine<Player> logicLine=getLineByPlayerId(playerId);
		logicLine.removePlayerById(playerId);
		 
//		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
//		for (LogicLine<Player> line : logicLines) {
//			if (null != line.removePlayerById(playerId)) {
//				return;
//			}
//		}

	}
	public void addPlayer(Player  player){
		LogicLine<Player> logicLine=getLineByPlayerId(player.getPlayerId());
		logicLine.addPlayer(player);
	}

	private LogicLine<Player> getLineByPlayerId(long playerId) {
		int lineId=(int)(playerId%logicLineMap.size());
		return logicLineMap.get(lineId);
	}

	public Player getPlayerById(long playerId) {
		LogicLine<Player> logicLine=getLineByPlayerId(playerId);
 
		return logicLine.getPlayerById(playerId);
	}

	 
	public Player getPlayerByName(String roleName) {
		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
		for (LogicLine<Player> line : logicLines) {
			Player onlineRole = line.getPlayerByName(roleName);
			if (null != onlineRole) {
				return onlineRole;
			}
		}
		return null;
	}

	 
	public int getOnlineCount() {
		int onlineCount = 0;
		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
		for (LogicLine<Player> logicLine : logicLines) {
			onlineCount += logicLine.getOnlineCount();
		}
		return onlineCount;
	}

	 

	 
	public void broadcast(Response response) {
		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
		for (LogicLine<Player> logicLine : logicLines) {
			logicLine.broadcast(response);
		}

	}

 
	public Collection<Player> getAllOnlinePlayers() {
		Collection<Player> onlinePlayers = new ArrayList<>();
		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
		for (LogicLine<Player> logicLine : logicLines) {
			onlinePlayers.addAll(logicLine.getAllPlayers());
		}
		return onlinePlayers;
	}

 
	public void shutDown() {
		killAllPlayers();
		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
		for (LogicLine<Player> logicLine : logicLines) {
			logicLine.shutDown();
		}
	}

	 
	public boolean kickPlayerById(long playerId) {
		Collection<LogicLine<Player>> logicLines = getAllLogicLines();
		for (LogicLine<Player> logicLine : logicLines) {
			boolean kick = logicLine.kickPlayerById(playerId);
			if (kick) {
				return kick;
			}
		}
		return false;
	}

	 
	public void killAllPlayers() {
		
	}



	 
	public void startUp(int lineCount) {
	
		executorService=new GameScheduledThreadPoolExecutor(lineCount+1, new GameThreadFactory("logic-thread"));
		
		
		for(int lineId=0;lineId<lineCount;lineId++){
			LogicLine<Player> logicLine=new LogicLine<Player>(lineId);
			addLogicLine(logicLine);
		}
		
		
		for (LogicLine<Player> line : logicLineMap.values()) {
			line.startUp(executorService);
		}
		
		
		executorService.scheduleWithFixedDelay(new GameTask() {
			public void doRun() {
			  try{
				  long heartbeatTime=System.currentTimeMillis();
				  Collection<LogicLine<Player>> logicLines=logicLineMap.values();
				  for(LogicLine<Player> logicLine:logicLines){
					  logicLine.getLogicLineUpdateService().heartbeat(heartbeatTime);
				  }
			  }catch(Exception e){
				  LOG.error(e.getMessage(),e);
			  }
			}
		}, 100, 200, TimeUnit.MILLISECONDS);
		
	}

	 
 
	
}
