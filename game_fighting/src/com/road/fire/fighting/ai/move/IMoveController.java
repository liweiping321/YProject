package com.road.fire.fighting.ai.move;

public interface IMoveController {
	/**设置路径信息*/
	public  void setMoveInfo(MoveInfo moveInfo);
	/**执行时间更新,并返回是否走到头了**/
	public  boolean checkArrived();
	/**停止移动并清空路线**/
	public  void stopMove();

	/**是否正在移动*/
	public  boolean isMoving();
	/**获取移动速度*/
	public  int getMoveSpeed();
	
	
}
