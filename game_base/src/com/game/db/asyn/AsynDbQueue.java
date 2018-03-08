package com.game.db.asyn;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 


/**
 * 数据库异步队列
 * @author lip.li
 *
 */
public class AsynDbQueue implements Runnable {
 
	private static final Logger LOG = LogManager.getLogger(AsynDbQueue.class);
	
	private ConcurrentLinkedQueue<Runnable> dbtasks = new ConcurrentLinkedQueue<Runnable>();
	
	private final String name;
	
	public AsynDbQueue(String name){
		this.name=name;
	}
	
	public void startUp(ScheduledThreadPoolExecutor executorService,long period ){
		executorService.scheduleWithFixedDelay(this, 1000, period, TimeUnit.MILLISECONDS);
	}
    
	public void addDbTask(Runnable run){
		dbtasks.add(run);
	}

	@Override
	public void run() {
		int size=dbtasks.size();
		if(size==0){
			return;
		}
		long begin=System.currentTimeMillis();
		for(Runnable run=dbtasks.poll();run!=null;run=dbtasks.poll()){
			try{
				run.run();
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
			
		}
		long end=System.currentTimeMillis();
		LOG.info("异步队列执行 size="+size+"条记录 耗时：("+(end-begin)+")毫秒秒|| endtime="+end);
	}

	public void shutDownExecute(){
		Runnable run=null;
		while((run=dbtasks.poll())!=null){
			try{
				run.run();
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		LOG.info("异步队列任务执行完毕。。。");
	}

	public String getName() {
		return name;
	}
	
}
