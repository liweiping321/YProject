package com.road.fire.fighting.skill.computer;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.phy.living.NullLiving;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.CastResult;
import com.road.fire.fighting.target.impl.FindCloseTarget;

import java.util.ArrayList;
import java.util.List;

/***
 * 以目标点为中心，范围攻击多个目标
 * 
 * @author lip.li
 *
 */
public class TargetPointMany implements Runnable {

	private final Living<?> source;

	private final ActiveSkill<?> activeSkill;

	private final int targetX;

	private final int targetY;
	/** 检查地图障碍点 */
	private final boolean checkObstacle;
	/** 检查怪物zany */
	private final boolean checkMonsterPoint;

	public TargetPointMany(Living<?> source, ActiveSkill<?> activeSkill) {
		super();
		this.source = source;
		this.activeSkill = activeSkill;
		this.targetX = activeSkill.getContext().getLockTargetX();
		this.targetY = activeSkill.getContext().getLockTargetY();
		this.checkObstacle = false;
		this.checkMonsterPoint = false;
	}

	public TargetPointMany(Living<?> source, ActiveSkill<?> activeSkill, int targetX, int targetY,
			boolean checkObstacle, boolean checkMonsterPoint) {
		super();
		this.source = source;
		this.activeSkill = activeSkill;
		this.targetX = targetX;
		this.targetY = targetY;
		this.checkObstacle = checkObstacle;
		this.checkMonsterPoint = checkMonsterPoint;
	}

	@Override
	public void run() {

		NullLiving targetPoint = new NullLiving(targetX, targetY, source.getGame(), source.getCampType());

		List<Living<?>> targets = FindCloseTarget.findNearestLivings(checkObstacle, checkMonsterPoint, targetPoint,
				activeSkill.getSkillCfg().getTargetType(), activeSkill.getDamageRadius(), Hero.class, Monster.class);

		List<CastResult> castResults = new ArrayList<>(targets.size());

		for (Living<?> target : targets) {
			CastResult castResult = new CastResult(source, target);
			// 施法
			castResult.castSkill(activeSkill);

			castResults.add(castResult);
		}

		// 给自己发送
		source.sendMsg(MsgCode.SkillResultResp,
				FtMsgUtil.builderSkillResultResp(activeSkill.getSkillId(), source.getPhyId(), castResults));

	}

}
