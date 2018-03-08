package com.road.fire.fighting.ai.move;
/**
 * 移动信息
 * @author lip.li
 *
 */
public class MoveInfo {

	/**开始时间*/
	private long startTime;
	/**开始X轴*/
	private int startX;
	/**开始Y轴*/
	private int startY;
	/**移动方向*/
	private int moveDir;
	
	/**移动时朝向*/
	private int faceDir;
	/**移动时长 */
	private int moveTime;

	public MoveInfo(long startTime, int startX, int startY,int moveDir,	int faceDir) {
		super();
		this.startTime = startTime;
		this.startX = startX;
		this.startY = startY;
		this.moveDir=moveDir;
		this.faceDir = faceDir;
	}
 

	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getFaceDir() {
		return faceDir;
	}

	public void setFaceDir(int faceDir) {
		this.faceDir = faceDir;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public int getMoveDir() {
		return moveDir;
	}

	public void setMoveDir(int moveDir) {
		this.moveDir = moveDir;
	}

	public int getMoveTime() {
		return moveTime;
	}


	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}
	 
	
	
}
