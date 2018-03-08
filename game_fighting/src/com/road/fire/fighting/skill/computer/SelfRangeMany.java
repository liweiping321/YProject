package com.road.fire.fighting.skill.computer;

import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.CastResult;
import com.road.fire.fighting.target.impl.FindCloseTarget;

import java.util.ArrayList;
import java.util.List;

/***
 *
 *以自身为中心，攻击多个目标
 * 
 * @author lip.li
 *
 */
public class SelfRangeMany implements Runnable {

	private final Living<?> source;

	private final ActiveSkill<?> activeSkill;
 
	/** 检查地图障碍点 */
	private final boolean checkObstacle;
	/** 检查怪物zany */
	private final boolean checkMonsterPoint;

	public SelfRangeMany(Living<?> source, ActiveSkill<?> activeSkill) {
		super();
		this.source = source;
		this.activeSkill = activeSkill;
		this.checkObstacle=false;
		this.checkMonsterPoint=false;
	 
	}

	public SelfRangeMany(Living<?> source, ActiveSkill<?> activeSkill,
			boolean checkObstacle, boolean checkMonsterPoint) {
		super();
		this.source = source;
		this.activeSkill = activeSkill;
		this.checkObstacle = checkObstacle;
		this.checkMonsterPoint = checkMonsterPoint;
	}

	@Override
	public void run() {

		List<Living<?>> targets = FindCloseTarget.findNearestLivings(checkObstacle, checkMonsterPoint, source,
				activeSkill.getSkillCfg().getTargetType(), activeSkill.getAttackDistance(), Hero.class, Monster.class);

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
