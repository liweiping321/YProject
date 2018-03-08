package com.road.fire.fighting.util;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 物体ID生成器
 * @author lip.li
 *
 */
public class PhyIncrId  {
	private static final AtomicInteger PhyIncrId = new AtomicInteger(0);
	
	public static int incrAndGetPhyId() {
		 
		int  id= PhyIncrId.incrementAndGet();
		
		if(id<0||id==Integer.MAX_VALUE){
			PhyIncrId.set(0);
		}
		return id;
	}
}
