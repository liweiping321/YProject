package com.game.delay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.polling.PollingTimer;

/**
 * 延迟执行的任务
 * @author weiping.li
 *
 */
public class DelayTask {
	
	private static final Logger LOG =LogManager.getLogger(DelayTask.class);
	private boolean cancel;
	 
	private Runnable run;
	
	private PollingTimer pollingTimer;
	
	private long endTime=0L;
	
	public DelayTask(Runnable run,int delayTime){
		this.run=run;
		long startTime=System.currentTimeMillis();
		this.endTime=startTime+delayTime;
		this.pollingTimer=new PollingTimer(delayTime, startTime,endTime);
	
	}
	public DelayTask(Runnable run,int delayTime,int intervalTime){
		this.run=run;
		long startTime=System.currentTimeMillis();
		this.endTime=startTime+delayTime;
		this.pollingTimer=new PollingTimer(intervalTime,startTime,endTime);
		
	}
	
	public DelayTask(Runnable run,int delayTime,long currentTime){
		this.run=run;
		this.endTime=currentTime+delayTime;
		this.pollingTimer=new PollingTimer(delayTime,currentTime,endTime);
		
	}
	public DelayTask(Runnable run,int delayTime,int intervalTime,long currentTime){
		this.run=run;
		this.endTime=currentTime+delayTime;
		this.pollingTimer=new PollingTimer(intervalTime,currentTime,endTime);
		
	}
	public boolean isCancel(){
		return cancel;
	}
	/**取消任务的执行*/
	public final void cancel(){
		 cancel=true;
		 onCancel();
	}
	/**当任务取消的时候做的操作*/
	protected void onCancel(){
		
	}
	/**是否到执行时间点**/
	public boolean isArrived(long now){
		return endTime<=now;
	}
	
	public boolean  isIntervalOk(long now){
		return pollingTimer.isIntervalOk(now);
	}
	 
	/**执行任务**/
	public void excute(){
		try{
			run.run();
		}catch(Exception e){
			LOG.error("delay task excute fail!", e);
		}
		
	}
}
