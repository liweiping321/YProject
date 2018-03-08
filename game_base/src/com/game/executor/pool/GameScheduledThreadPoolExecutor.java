package com.game.executor.pool;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import com.game.executor.CancellableTask;

public class GameScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	public GameScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
		super(corePoolSize, threadFactory);
	}

	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {
		if (runnable instanceof CancellableTask) {
			((CancellableTask) runnable).sf = task;
		}
		return task;
	}

}
