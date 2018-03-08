package com.road.fire.fighting.phy.living.drop;

import com.game.cfg.entity.ConfigsCfg;
import com.game.polling.PollingTimer;
import com.road.fire.entity.cfg.DropItem;
import com.road.fire.entity.cfg.DropTemplateCfg;
import com.road.fire.fighting.BaseGame;

import java.util.ArrayList;
import java.util.List;

/**
 * 击杀掉落物品
 * 
 * @author lip.li
 *
 */
public class KillDropGoods extends DropGoods {

	private final List<DropItem> dropItems = new ArrayList<>();

	private final DropTemplateCfg templateCfg;

	public KillDropGoods(int x, int y, int startX, int startY, BaseGame game, int campType, DropItem dropItem,
			DropTemplateCfg templateCfg) {
		super(x, y, startX, startY, game, campType);

		this.dropItems.add(dropItem);
		this.templateCfg = templateCfg;

	}

	@Override
	public void onDead() {
		// game.getScene().removePosition(this);
		game.removeLiving(this);
	}

	@Override
	public List<DropItem> getDropItems() {

		return dropItems;
	}

	@Override
	public void initTimer() {
		long endTime = game.getFrameTime() + ConfigsCfg.getDropLifeTime();

		timer = new PollingTimer(ConfigsCfg.pickCheckTime, game.getFrameTime(), endTime);
	}

	@Override
	public void onInit() {
		faceDir = templateCfg.getBornFaceDir();
		moveDir = templateCfg.getBornFaceDir();

	}

	@Override
	public String getName() {

		return templateCfg.getName();
	}

	@Override
	public int getTemplateId() {

		return templateCfg.getTemplateId();
	}

	@Override
	public int getDamageRadius() {

		return templateCfg.getPickRadius();
	}
 
}
