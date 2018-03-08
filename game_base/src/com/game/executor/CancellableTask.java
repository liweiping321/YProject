package com.game.executor;

import java.util.concurrent.ScheduledFuture;

/**
 * 
 * @author lip.li 需要在任务内部取消任务的, 请集成本类
 */
public abstract class CancellableTask implements Runnable {
	public volatile ScheduledFuture<?> sf = null;

	public boolean cancel(boolean mayInterruptIfRunning) {
		if (sf != null) {
			boolean cancel = sf.cancel(mayInterruptIfRunning);
			return cancel;
		}
		return true;
	}
}
