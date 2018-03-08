package com.game.delay;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.game.polling.PollingUpdate;

/**
 *延迟 执行任务管理器池
 * @author weiping.li
 *
 */
public class DelayTaskQueue  implements PollingUpdate {
	
   /**延迟执行任务队列**/
	private ConcurrentLinkedQueue<DelayTask> delayTasks=new ConcurrentLinkedQueue<DelayTask>();
	
	/**
	 * 添加延迟任务
	 * @param run 要执行的任务
	 * @param delayTime 延迟时间
	 */
	public void addDelayTask(Runnable run,int delayTime){
		delayTasks.offer(new DelayTask(run, delayTime));
	}
	
	/**
	 * 添加延迟任务
	 * @param run 要执行的任务
	 * @param delayTime 延迟时间
	 */
	public void addDelayTask(Runnable run,int delayTime,int intervalTime){
		delayTasks.offer(new DelayTask(run, delayTime,intervalTime));
	}
	
	/**
	 * 添加延迟任务
	 * @param run 要执行的任务
	 * @param delayTime 延迟时间
	 */
	public void addDelayTask(Runnable run,int delayTime,long currentTime){
		delayTasks.offer(new DelayTask(run, delayTime,currentTime));
	}
	
	/**
	 * 添加延迟任务
	 * @param run 要执行的任务
	 * @param delayTime 延迟时间
	 */
	public void addDelayTask(Runnable run,int delayTime,int intervalTime,long currentTime){
		delayTasks.offer(new DelayTask(run, delayTime,intervalTime,currentTime));
	}
	
	public void addDelayTask(DelayTask delayTask){
		delayTasks.add(delayTask);
	}
	@Override
	public void update(long now) {
		if(!delayTasks.isEmpty()){
			for(Iterator<DelayTask> iter=delayTasks.iterator();iter.hasNext();){
				DelayTask task=iter.next();
				if(task.isCancel()){
					iter.remove();
					continue;
				}
				if(task.isIntervalOk(now)){
					task.excute();
					if(task.isArrived(now)){
						iter.remove();
					}
				}
			}
		}
		
	}
	public void cancelTask(DelayTask delayTask){
		delayTask.cancel();
		delayTasks.remove(delayTask);
	}
	public void destroy(){
		delayTasks.clear();
	}
   
}
