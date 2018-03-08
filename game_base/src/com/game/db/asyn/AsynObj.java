package com.game.db.asyn;

import java.util.concurrent.atomic.AtomicInteger;

public class AsynObj {
	
	private final AtomicInteger aysndbTaskCount = new AtomicInteger(0);
	
	public AtomicInteger getAysndbTaskCount() {
		return aysndbTaskCount;
	}
	/**异步队列保存，取模ID*/
	public long getModId(){
		return 0;
	}

}
