
package com.road.fire.fighting.skill.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.computer.DelayBulletOne;
import com.road.fire.fighting.skill.container.SkillContainer;
import com.road.fire.fighting.util.DistanceUtils;
/**
 * 快速射击(客户端产生假子，服务端延迟计算伤害)
 * @author lip.li
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QuickShotSkill extends ActiveSkill{
	private static final Logger LOG =LogManager.getLogger(QuickShotSkill.class);
	
	
	public QuickShotSkill(SkillCfg skillCfg, Living<?> owner,
			SkillContainer<?> skillContainer) {
		super(skillCfg, owner, skillContainer);
	}
	
	
	@Override
	public void onSpread(long now) {
		LOG.info("技能施展----------------------------------------------");
		  Living<?>  target=context.getLockTarget(); 
		  if(target==null){
			 return;
		  }
 
		  int distance=DistanceUtils.getDistance(owner, target);
		  int flyDistance=Math.max(0, distance-(skillCfg.getAttackDistance()-skillCfg.getFlyDistance()));
		  
		  int flyTime=(int) (flyDistance/(skillCfg.getCallLivingSpeed()*0.001));
		  
		  owner.getTaskQueue().addDelayTask(new DelayBulletOne(owner, target, this), flyTime);
		  
	}
	
	
	
	
	
	
}
