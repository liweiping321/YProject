package com.road.fire.fighting.phy.living.drop;

import com.game.polling.PollingTimer;
import com.game.property.PropertyConstants;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBLivingInfoBean;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.DropTemplateCfgProvider;
import com.road.fire.entity.cfg.DropItem;
import com.road.fire.entity.cfg.DropTemplateCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.consts.PhysicsType;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.target.impl.FindCloseTarget;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 场景掉落
 * 
 * @author lip.li
 * 
 */
public abstract class DropGoods extends Living<DropGoods> {
	/** 出生起始位置X坐标 */
	private int startX;
	/** 出生起始位置Y坐标 */
	private int startY;

	protected PollingTimer timer;

	public DropGoods(int x, int y, int startX, int startY, BaseGame game, int campType) {
		super(x, y, game, campType);
		this.startX = startX;
		this.startY = startY;

	}

	public DropGoods(int x, int y, BaseGame game, int campType) {
		super(x, y, game, campType);

	}

	@Override
	public void init() {
		onInit();
		initTimer();
		game.addLiving(this);
	}

	@Override
	public void update(long now) {
 
		if (timer.isIntervalOk(now)) {
			dropToLiving(Hero.class);
		}
 

		if (timer.isOk(now)) {
			onDead();
		}
 
	}

	public void dropToLiving(Class<? extends Living<?>> clazz) {

		Living<?> living = FindCloseTarget.findNearestFriend(false, false, this, Hero.class);
	 
		if (living == null) {
			return;
		}
		List<DropItem> dropItems = getDropItems();
		if (CollectionUtils.isNotEmpty(dropItems)) {

			if (canAddDropItems(living, dropItems)) {

				for (DropItem dropItem : dropItems) {
					addDropItem(living, dropItem);
				}

				living.sendMsg(MsgCode.PickDropResp, FtMsgUtil.builderPickDropResp(living.getPhyId(), getTemplateId()));
				onDead();
			}

		}
	}

	public abstract List<DropItem> getDropItems();

	/** 给生物添加掉落物品 */
	private void addDropItem(Living<?> living, DropItem dropItem) {

		DropTemplateCfg templateCfg = DropTemplateCfgProvider.getInstance().getConfigVoByKey(dropItem.getGoodsId());

		if (templateCfg.getType() == PropertyConstants.Buff) {
			living.addBuff(living, dropItem.getGoodsId());
		} else if (templateCfg.getType() == PropertyConstants.Skill) {
			living.getSkillContainer().addPropSkill(dropItem.getGoodsId());
		} else if (templateCfg.getType() == PropertyConstants.CurrExp) {// 添加经验
			living.addExp(dropItem.getNum());
		}
	}

	private boolean canAddDropItems(Living<?> living, List<DropItem> dropItems) {

		for (DropItem dropItem : dropItems) {
			if (!canAddDropItem(living, dropItem)) {
				return false;
			}
		}
		return true;
	}

	private boolean canAddDropItem(Living<?> living, DropItem dropItem) {
		DropTemplateCfg templateCfg = DropTemplateCfgProvider.getInstance().getConfigVoByKey(dropItem.getGoodsId());

		if (templateCfg.getType() == PropertyConstants.Skill) {
			boolean freeIndex = living.getSkillContainer().hasFreeIndex();
			if (!freeIndex) {
				return freeIndex;
			}
		}
		return true;
	}

	public abstract void initTimer();

	public int getPhType() {

		return PhysicsType.DROPGOODS.getValue();
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	@Override
	public PBLivingInfoBean getLivingBean() {

		return FtMsgUtil.builderDropLivingBean(this);
	}

}
