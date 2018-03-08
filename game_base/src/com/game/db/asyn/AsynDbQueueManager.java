package com.game.db.asyn;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.game.executor.GameTask;
import com.game.executor.GameThreadFactory;
import com.game.executor.pool.GameScheduledThreadPoolExecutor;
/**
 * 
 * @author lip.li
 *
 */
public class AsynDbQueueManager {
   
	private AsynDbQueue[] queues;
	
	private ScheduledThreadPoolExecutor executorService;
	
	public void init(int dataSavePeriod,int dbQueueSize){
		
		executorService=new GameScheduledThreadPoolExecutor(dbQueueSize, new GameThreadFactory("AsynDbPool"));
		
		queues=new AsynDbQueue[dbQueueSize];
		for(int i=0;i<dbQueueSize;i++){
			queues[i]=new AsynDbQueue("db-queue-"+i);
			
			queues[i].startUp(executorService, dataSavePeriod);
		}
	}
	
	public void addTask(AsynObj asynObj,GameTask task){
		int index=(int)(asynObj.getModId()%queues.length);
		queues[index].addDbTask(task);
	}
	
	public void addTask(String key,GameTask task){
		int index=Objects.hash(key)%queues.length;
		queues[index].addDbTask(task);
	}
			
	public void shutDownExecute(){
		for(AsynDbQueue asynDbQueue:queues){
			asynDbQueue.shutDownExecute();
		}
	}
	private static final AsynDbQueueManager instance=new AsynDbQueueManager();

	public static AsynDbQueueManager getInstance() {
		return instance;
	}
	
	
}
