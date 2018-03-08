package com.road.fire.fighting.skill.impl;

import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.skill.computer.TargetPointMany;
import com.road.fire.fighting.skill.container.SkillContainer;
import com.road.fire.fighting.target.impl.FindCloseTarget;
import com.road.fire.fighting.util.DistanceUtils;

/**
 * 手雷
 * @author lip.li
 *
 */
 
public class GrenadeSkill extends PropSkill{

	public GrenadeSkill(SkillCfg skillCfg, Living<?> owner,
			SkillContainer<?> skillContainer) {
		super(skillCfg, owner, skillContainer);
		 
	}
	@Override
	public void onSpread(long now) {
		
		  int distance=DistanceUtils.getDistance(owner.getX(), owner.getY(), context.getLockTargetX(), context.getLockTargetY());
		 
		  int flyTime=(int) (distance/(skillCfg.getCallLivingSpeed()*0.001));
	 
		  owner.getTaskQueue().addDelayTask(new TargetPointMany(owner, this), flyTime);
	}
	
	@Override
	public Living<?> autoSelectTarget() {
		return FindCloseTarget.findNearestLiving(false, false, owner, skillCfg.getTargetType(), getAttackDistance(), 0, 0,  Hero.class, Monster.class);
	}
	
	 @Override
	public void setSkillTargetAngle() {
		 context.setCorrectSkillAngle(context.getSkillAngle() < 0 ? owner.getFaceDir() : context.getSkillAngle());
		 //超出攻击范围
		boolean isInRange = DistanceUtils.isInRange(owner.getX(), owner.getY(),
				context.getLockTargetX(), context.getLockTargetY(),
				getAttackDistance());

		if (!isInRange) {

			int targetX = owner.getX()
					+ DistanceUtils.computeAddX(getAttackDistance(),
							context.getCorrectSkillAngle());
			int targetY = owner.getY()
					+ DistanceUtils.computeAddY(getAttackDistance(),
							context.getCorrectSkillAngle());
			context.setLockTargetX(targetX);
			context.setLockTargetY(targetY);
		}
	}
	@Override
	public boolean onCheck() {
		 
		return true;
	}    	 
}
