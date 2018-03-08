package com.game.executor;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

/**
 * GameTask 执行统计耗时多少
 * 
 * @author lip.li
 * 
 */
public abstract class GameTask extends CancellableTask {
	private static final Logger LOG = LogManager.getLogger("TASK_TIME");

	protected static final Logger LOGGER = LogManager.getLogger(GameTask.class);

	private static final Map<String, AtomicInteger> taskCreateCount = new TreeMap<String, AtomicInteger>();

	private static final Map<String, AtomicInteger> taskCancelCount = new TreeMap<String, AtomicInteger>();

	private static final Map<String, AtomicInteger> taskExecutCount = new TreeMap<String, AtomicInteger>();

	private static long lastTime = 0;

	private static final int StatOutputTime = 5 * 60 * 1000;

	public GameTask() {
		stat(taskCreateCount);
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();

		try {
			doRun();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			long endTime = System.currentTimeMillis();
			long costTime = endTime - startTime;
			LOG.info("DdtTask [{}], cost [{}] millis", getTaskName(), costTime);

			if (costTime > 2000) {
				LOGGER.error("DdtTask [{}], cost [{}] millis", getTaskName(), costTime);
			}

			stat(taskExecutCount);
		}
	}

	public abstract void doRun();

	public String getTaskName() {
		return this.getClass().getName();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		stat(taskCancelCount);

		return super.cancel(mayInterruptIfRunning);
	}

	 
	@Override
	public String toString() {

		return this.getClass().getName();
	}

	public void stat(Map<String, AtomicInteger> countMap) {
		synchronized (countMap) {
			String key = this.getClass().getSimpleName();
			AtomicInteger count = countMap.get(key);
			if (count == null) {
				count = new AtomicInteger(0);
				countMap.put(key, count);
			}

			count.incrementAndGet();
		}

		long costTime = System.currentTimeMillis() - lastTime;
		if (costTime >= StatOutputTime) {
			LOG.info("Create:" + taskCreateCount);
			LOG.info("Cancle:" + taskCancelCount);
			LOG.info("Execut" + taskExecutCount);
			lastTime = System.currentTimeMillis();
		}
	}

}
