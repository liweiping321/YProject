package com.road.fire.fighting.ai.move;

import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.util.DistanceUtils;

public class MoveController<T extends Living<?>> implements IMoveController {
	
	private MoveInfo moveInfo;
	
	protected T owner;
	
	public MoveController( T owner){
		this.owner=owner;
	}
	
	
	@Override
	public boolean checkArrived() {
		if(!isMoving()){
			return true;
		}
		return walkByTime();
		
		 
	}

	private boolean walkByTime() {
		//移动时间
	    long  mt=owner.getFrameTime()-moveInfo.getStartTime();
		
	    mt=Math.min(mt, 100);
		//移动距离
	    int md=(int) (mt*getMoveSpeed()*0.001);
		
	    int endX=moveInfo.getStartX()+DistanceUtils.computeAddX(md, moveInfo.getMoveDir());
	    int endY=moveInfo.getStartY()+DistanceUtils.computeAddY(md, moveInfo.getMoveDir());
	    //遇到障碍点，移动结束
	    if(!owner.getScene().canWalking(endX, endY, owner)){
	    	return true;
	    }
	    
	    owner.setX(endX);
	    owner.setY(endY);
	    return mt>=moveInfo.getMoveTime();
	}


	@Override
	public void stopMove() {
		moveInfo=null;
	}

	@Override
	public boolean isMoving() {
		 
		return moveInfo!=null;
	}

	@Override
	public void setMoveInfo(MoveInfo moveInfo) {
		this.moveInfo=moveInfo;
		
		owner.setX(moveInfo.getStartX());
		owner.setY(moveInfo.getStartY());
		owner.setFaceDir(moveInfo.getFaceDir());
		owner.setMoveDir(moveInfo.getMoveDir());
	}


	@Override
	public int getMoveSpeed() {
	 
		return owner.getMoveSpeed();
	}
	
	

}
