package com.road.fire.fighting.skill.computer;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.CastResult;
import com.road.fire.fighting.util.DistanceUtils;

/***
 * 假子弹延迟计算伤害
 * 
 * @author lip.li
 *
 */
public class DelayBulletOne implements Runnable {

	private Living<?> source;

	private Living<?> target;

	private ActiveSkill<?> activeSkill;

	private int targetX;

	private int targetY;

	public DelayBulletOne(Living<?> source, Living<?> target, ActiveSkill<?> activeSkill) {
		super();
		this.source = source;
		this.target = target;
		this.activeSkill = activeSkill;
		this.targetX = activeSkill.getContext().getLockTargetX();
		this.targetY = activeSkill.getContext().getLockTargetY();
	}

	@Override
	public void run() {
		int moveDistance = DistanceUtils.getDistance(targetX, targetY, target.getX(), target.getY());

		if (moveDistance >= (activeSkill.getDamageRadius() + target.getDamageRadius()) ) {
			return;
		}

		CastResult castResult = new CastResult(source, target);
		// 施法
		castResult.castSkill(activeSkill);

		// 给自己发送
		source.sendMsg(MsgCode.SkillResultResp,
				FtMsgUtil.builderSkillResultResp(activeSkill.getSkillId(), source.getPhyId(), castResult));

	}

}
