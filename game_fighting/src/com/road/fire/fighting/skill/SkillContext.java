package com.road.fire.fighting.skill;

import com.road.fire.fighting.phy.living.Living;

public class SkillContext {
	private final int index;
	private final int skillAngle;
	private final int startX;
	private final int startY;
	private final int targetX;
	private final int targetY;

	/** 修正角度 */
	private int correctSkillAngle;
	/** 锁定目标 */
	private Living<?> lockTarget;
	/**锁定目标点位置X*/
	private int lockTargetX;
	/**锁定目标点位置Y*/
	private int lockTargetY;

	public SkillContext(int index, int skillAngle, int startX, int startY, int targetX, int targetY) {
		super();
		this.index = index;
		this.skillAngle = skillAngle%360;
		this.startX = startX;
		this.startY = startY;
		this.targetX = targetX;
		this.targetY = targetY;
		this.lockTargetX=targetX;
		this.lockTargetY=targetY;
		
	}

	public int getIndex() {
		return index;
	}
 

	public int getSkillAngle() {
		return skillAngle;
	}
 

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}
 

	public int getTargetX() {
		return targetX;
	}

	public int getTargetY() {
		return targetY;
	}
 
	public int getCorrectSkillAngle() {
		return correctSkillAngle;
	}

	public void setCorrectSkillAngle(int correctSkillAngle) {
		this.correctSkillAngle = correctSkillAngle;
	}

	public Living<?> getLockTarget() {
		return lockTarget;
	}

	public void setLockTarget(Living<?> lockTarget) {
		this.lockTarget = lockTarget;
	}

	public int getLockTargetX() {
		return lockTargetX;
	}

	public void setLockTargetX(int lockTargetX) {
		this.lockTargetX = lockTargetX;
	}

	public int getLockTargetY() {
		return lockTargetY;
	}

	public void setLockTargetY(int lockTargetY) {
		this.lockTargetY = lockTargetY;
	}

	public int getTargetPhId() {
		if(lockTarget!=null){
			return lockTarget.getPhyId();
		}
		return 0;
	}
	

}
