package com.road.fire.fighting.skill.cd;
/**
 * 
 * @author lip.li
 *
 */
public class CdTime {
	
	private long cdEndTime=0;

	public long getCdEndTime() {
		return cdEndTime;
	}

	public void setCdEndTime(long cdEndTime) {
		this.cdEndTime = cdEndTime;
	}

	public boolean checkIn(long frameTime) {
	 
		return cdEndTime>frameTime;
	}

	 
	
	
}
