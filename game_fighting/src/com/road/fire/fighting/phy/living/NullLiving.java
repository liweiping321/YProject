package com.road.fire.fighting.phy.living;

import com.game.fighting.TimeLog;
import com.game.protobuf.fighting.FightingProto.PBLivingInfoBean;
import com.road.fire.fighting.BaseGame;

public class NullLiving  extends Living<NullLiving>{
	
	public NullLiving(int x, int y, BaseGame game, int campType) {
		super(x, y, game, campType);
	}

	@Override
	public void onInit() {
		 
		
	}

	@Override
	public PBLivingInfoBean getLivingBean() {
		 
		return null;
	}

	@Override
	public int getPhType() {
		 
		return 0;
	}

	@Override
	public String getName() {
	 
		return null;
	}

	@Override
	public int getTemplateId() {
		 
		return 0;
	}
 
}
