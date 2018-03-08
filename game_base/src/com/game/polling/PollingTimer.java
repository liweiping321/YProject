package com.game.polling;
 /**
  * 
  * @author  weiping.li
  *
  */
public class PollingTimer {
   private long startTime;
   
   private long endTime=-1;
   
   private long interval;
   
   private boolean isfirst=true;
   
   public PollingTimer(){
   }
   public PollingTimer(long dur,long startTime){
	   start(dur,startTime);
   }
   public PollingTimer(long dur,long startTime,long endTime){
	   start(dur,startTime,endTime);
   }
   public void changeInerval(long dur){
	   this.interval=dur;
   }
   
   public void start(long dur,long startTime){
	   this.interval=dur;
	   this.startTime=startTime;
	   isfirst=true;
   }
   

   public void start(long dur,long startTime,long endTime){
	   this.interval=dur;
	   this.startTime=startTime;
	   this.endTime=endTime;
	   isfirst=true;
   }
   
   public void reStart(long startTime){
	  this.startTime=startTime;
	   isfirst=true;
   }
   
   public void reStart(long startTime,long endTime){
	   this.startTime=startTime;
	   this.endTime=endTime;
	   isfirst=true;
   }
   
   private void reStart(long dur,long elapse,long now){
	   this.interval=dur;
	   startTime=now-elapse;
   }
   
   public boolean isOk(){
	   if(endTime<0){
		   return false;
	   }
	   long now=System.currentTimeMillis();
	   if(now>=endTime){
		   return true;
	   }
	   return false;
   }
   
   public boolean isOk(long now){
	   if(endTime<0){
		   return false;
	   }
	   if(now>endTime){
		   return true;
	   }
	   return false;
   }
   
   public boolean isFirstOk(long now){
	   if(isfirst&&now-startTime>=interval){
		   isfirst=false;
		   return true;
	   }
	   return false;
   }
   /**距下次执行的时间**/
   public long getDistanceNextTime(long now){
	   return interval-(now-startTime);
   }
   
   public boolean isIntervalOk(long now){
	   if(interval<0){
		   return false;
	   }
	   if(now-startTime>=interval){
		   reStart(interval, now-startTime-interval, now);
		   return true;
	   }
	   return false;
   }

	public long getInterval() {
		return interval;
	}
	public long getStartTime() {
		return startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
}
