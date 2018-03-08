package com.game.db.asyn;

import com.game.executor.GameTask;

/**
 * 数据库保存任务
 * @author lip.li
 *
 */
public class AsynTask implements Runnable {
	 
	private  GameTask  task;
	
	private AsynObj asynObj;
	
	public AsynTask(AsynObj asynObj,GameTask  task){
		this.asynObj=asynObj;
		this.task=task;
		
	}
	 
	
	public GameTask getTask() {
		return task;
	}


	public void setTask(GameTask task) {
		this.task = task;
	}


	 

	@Override
	public void run() {
		asynObj.getAysndbTaskCount().decrementAndGet();
		task.run();
		 
		asynObj=null;
		task=null;
	
	}
	
}
