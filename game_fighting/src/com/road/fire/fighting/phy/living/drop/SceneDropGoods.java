package com.road.fire.fighting.phy.living.drop;

import com.game.cfg.entity.ConfigsCfg;
import com.game.consts.GameConsts;
import com.game.polling.PollingTimer;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.entity.cfg.DropBoxCfg;
import com.road.fire.entity.cfg.DropItem;
import com.road.fire.entity.cfg.SceneDropCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.consts.PhysicsType;

import java.util.List;

/**
 * 场景掉落
 * 
 * @author lip.li
 * 
 */
public class SceneDropGoods extends DropGoods {

	private final SceneDropCfg sceneDropCfg;

	private final DropBoxCfg dropBoxCfg;

	public SceneDropGoods(int x, int y, BaseGame game, int campType, SceneDropCfg sceneDropCfg, DropBoxCfg dropBoxCfg) {
		super(x, y, game, campType);
		this.sceneDropCfg = sceneDropCfg;
		this.dropBoxCfg = dropBoxCfg;
	}

	@Override
	public void onInit() {
		faceDir = sceneDropCfg.getBornFaceDir();
		moveDir = sceneDropCfg.getBornFaceDir();
	}

	public void initTimer() {
		long endTime = game.getFrameTime() + GameConsts.YearTime;
		if (sceneDropCfg.getLifeTime() > 0) {
			endTime = game.getFrameTime() + sceneDropCfg.getLifeTime();
		}
		timer = new PollingTimer(ConfigsCfg.pickCheckTime, game.getFrameTime(), endTime);
	}

	@Override
	public int getDamageRadius() {

		return sceneDropCfg.getPickRadius();
	}

	@Override
	public void onDead() {
		// game.getScene().removePosition(this);
		game.removeLiving(this);
		if (sceneDropCfg.getRelive()) {
			setReliveTime(game.getFrameTime() + sceneDropCfg.getReliveDelayTime());
			game.getReliveMgr().add2ReliveList(this);
		}
	}

	@Override
	public boolean onRelive() {
		game.addLiving(this);
		// getScene().initPosition(getTileX(), getTileY(), this);
		broadcastMsg(MsgCode.LivingBornResp, FtMsgUtil.builderLivingBornResp(this));
		return true;
	}

	public int getPhType() {

		return PhysicsType.DROPGOODS.getValue();
	}

	public SceneDropCfg getSceneDropCfg() {
		return sceneDropCfg;
	}

	public DropBoxCfg getDropBoxCfg() {
		return dropBoxCfg;
	}

	@Override
	public String getName() {

		return sceneDropCfg.getName();
	}

	@Override
	public int getTemplateId() {

		return sceneDropCfg.getTemplateId();
	}

	@Override
	public List<DropItem> getDropItems() {

		return dropBoxCfg.drop();
	}
 
}
