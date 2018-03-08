package com.road.fire.fighting.skill;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.consts.HurtType;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;

public class CastResult {

	private Living<?> source;

	private Living<?> target;

	private boolean mis;

	private int type;

	private int value;

	public CastResult(Living<?> source, Living<?> target, int type, int value) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
		this.value = value;
	}

	public CastResult(Living<?> source, Living<?> target) {
		super();
		this.source = source;
		this.target = target;
	}

	/** 计算伤害 */
	public void castSkill(ActiveSkill<?> activeSkill) {
		// 植入BUFF

		if (HurtType.Hurt == activeSkill.getHurtType()) {
			if (target.isBreachType()) {
				castBreachHurt(activeSkill);
			} else {
				castHurt(activeSkill);
			}

		} else if (HurtType.Cure == activeSkill.getHurtType()) {
			// 回血
			castCure();
		}

		// 添加BUFF列表
		source.addBuff(target, activeSkill.getBuffs());
		target.onbeAttacked(source);

		// 目标发送伤害消息
		target.sendMsg(MsgCode.SkillResultResp,
				FtMsgUtil.builderSkillResultResp(activeSkill.getSkillId(), source.getPhyId(), this));

		if (source instanceof Hero) {

			source.getGame().getFightDataManager().addHurt((Hero) source, target, value);
		}
		if (target instanceof Hero) {
			source.getGame().getFightDataManager().addReceiveHurt((Hero) target, source, value);
		}
	}

	/** 计算破坏力伤害 */
	private void castBreachHurt(ActiveSkill<?> activeSkill) {
		value = (int) (source.getBreachPower() * activeSkill.getSkillCfg().getBreachPowerRatio() / 10000.0);

		target.reduceHp(value, true);
		mis = true;
		type = HurtType.ResultTypeBreach;

	}

	public void castCure() {

	}

	private void castHurt(ActiveSkill<?> activeSkill) {
		value = activeSkill.getSkillCfg().getHurtValue() + source.getAttack() - target.getDefense();
		target.reduceHp(value, true);

		mis = true;
		type = HurtType.ResultTypeCommon;

	}

	public Living<?> getSource() {
		return source;
	}

	public void setSource(Living<?> source) {
		this.source = source;
	}

	public Living<?> getTarget() {
		return target;
	}

	public void setTarget(Living<?> target) {
		this.target = target;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isMis() {
		return mis;
	}

	public void setMis(boolean mis) {
		this.mis = mis;
	}

}
