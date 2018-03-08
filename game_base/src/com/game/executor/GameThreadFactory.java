package com.game.executor;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 * 
 * @author lip.li
 * 
 */
public class GameThreadFactory implements ThreadFactory {
	public String threadName;

	private int threadIndex = 0;

	public GameThreadFactory(String threadName) {
		this.threadName = threadName;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setName("pool-" + threadName + "-" + threadIndex++);
		return thread;
	}

}
