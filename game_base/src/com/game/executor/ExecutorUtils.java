package com.game.executor;

import com.game.executor.pool.GameScheduledThreadPoolExecutor;

public class ExecutorUtils {
	 private static  GameScheduledThreadPoolExecutor CommonPool;
	 
	 public void init(int commonSize){
		 CommonPool=new GameScheduledThreadPoolExecutor(commonSize, new GameThreadFactory("common"));
		 
	 }
	 
	 
	 public void execute(Runnable run ){
		 CommonPool.execute(run);
	 }
	 
	private static final ExecutorUtils ins=new ExecutorUtils();

	public static ExecutorUtils getIns() {
		return ins;
	}
	 
	 
}
