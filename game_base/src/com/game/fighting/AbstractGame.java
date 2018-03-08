package com.game.fighting;

import com.game.polling.PollingUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractGame implements PollingUpdate {
	protected static final Logger LOG=LogManager.getLogger(AbstractGame.class);
	
	protected GameStatus status;
	/**当前帧时间点*/
	protected long frameTime=System.currentTimeMillis();
	/**状态初始进入开始时间*/
	protected Map<GameStatus,Long> statusStartTimes=new HashMap<>();
	
	@Override
	public void update(long now) {
		//状态驱动执行 (后期版本需要改造成状态机)
    	if(status==GameStatus.init){
    		onUpdateInit(now);
    	}else if(status==GameStatus.loading){
    		onUpdateLoading(now);
    	}else if(status==GameStatus.playerReady){
    		onUpdatePlayerReady(now);
    	}else if(status==GameStatus.fighting){
    		onUpdateFighting(now);
    	}else if(status==GameStatus.computing){
    		onUpdateComputing(now);
    	}else if(status==GameStatus.gameOver){
    		onUpdateGameOver(now);
    	}
		
	}


	public void setFrameTime(long frameTime) {
		this.frameTime = frameTime;
	}

	public long getFrameTime() {
		return frameTime;
	}
	
	public abstract void init();
	
	/**游戏结束*/
	public void onUpdateGameOver(long now) {
		 
		
	}
	/**游戏结算*/
	public void onUpdateComputing(long now) {
		 
		
	}
	/**进入游戏后玩家准备*/
	public void onUpdatePlayerReady(long now) {
	 
		
	}
	/**游戏加载*/
	public void onUpdateLoading(long now) {
		 
		
	}
	/**游戏初始化*/
	public void onUpdateInit(long now) {
		
	}
	/**战斗中*/
	public void onUpdateFighting(long now) {
		
	}
	

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
		statusStartTimes.put(status, frameTime);
	}

}
