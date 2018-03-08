package com.road.fire.fighting.skill;

import com.game.consts.GameConsts;
import com.game.polling.PollingUpdate;
import com.game.property.PropertyConstants;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.entity.cfg.SkillCfg;
import com.road.fire.fighting.consts.SkillStatus;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.skill.cd.CdTime;
import com.road.fire.fighting.skill.container.SkillContainer;
import com.road.fire.fighting.target.impl.FindCloseTarget;
import com.road.fire.fighting.util.DistanceUtils;

/**
 * 主动技能
 * 
 * @author lip.li
 *
 */
public class ActiveSkill<T extends Living<?>> extends BaseSkill<T> implements PollingUpdate {
	/** 武器技能 */
	private boolean weaponSkill;
	/** 技能吟唱结束时间 */
	private long readEndTime = 0l;
	/** 僵直结束时间 */
	private long rigidEndTime = 0l;
	/** 施展结束时间 */
	private long spreadEndTime = 0l;

	private CdTime cdTime = new CdTime();

	private SkillStatus status = SkillStatus.wait;

	protected final SkillContainer<T> skillContainer;

	private int index;

	/**
	 * 技能使用上下文
	 */
	protected SkillContext context;

	public ActiveSkill(SkillCfg skillCfg, T owner, SkillContainer<T> skillContainer) {
		super(skillCfg, owner);
		this.skillContainer = skillContainer;
	}

	@Override
	public void update(long now) {
		if (status == SkillStatus.read) {
			switchSpread(now);
		} else if (status == SkillStatus.spread) {
			switchWait(now);
		}

	}

	/** 切换到等待状态 */
	private void switchWait(long now) {
		if (now >= spreadEndTime) {// 切换到技能持续状态
			onSpread(now);
			status = SkillStatus.wait;
		}
	}

	/** 切换到施展状态 */
	private void switchSpread(long now) {
		if (now >= readEndTime) {// 切换到技能施展状态
			status = SkillStatus.spread;
			switchWait(now);
		}
	}

	public SkillStatus getStatus() {
		return status;
	}

	public void setStatus(SkillStatus status) {
		this.status = status;
	}

	/** 技能施展 */
	public void onSpread(long now) {

	}

	/** 技能开始释放 */
	public void onStart() {
		long now = owner.getFrameTime();
		readEndTime = now + skillCfg.getReadTime();
		rigidEndTime = now + skillCfg.getRigidTime();
		spreadEndTime = now + skillCfg.getSpreadTime();

		status = SkillStatus.read;
		skillContainer.setCurrSkill(this);
		
		owner.setX(context.getStartX());
		owner.setY(context.getStartY());

		// 广播技能释放消息
		owner.broadcastMsg(MsgCode.SkillUseResp, FtMsgUtil.builderSkillUseResp(this));
		update(now);
	}

	/** 技能释放完成 */
	public void onFinish() {
		// 弹夹处理
		int reloadTime = clipProcessing();

		CdTime groupCd = skillContainer.getGroupCd();
		if (reloadTime > skillCfg.getCoolTime()) {
			// 改变
			cdTime.setCdEndTime(owner.getFrameTime() + reloadTime);

		} else {
			cdTime.setCdEndTime(owner.getFrameTime() + skillCfg.getCoolTime());
		}

		if (reloadTime > 0) {// 换弹夹时候，不能攻击
			groupCd.setCdEndTime(owner.getFrameTime() + reloadTime);
		} else {
			groupCd.setCdEndTime(owner.getFrameTime() + skillCfg.getGroupCoolTime());
		}

		owner.sendMsg(MsgCode.SkillCdResp, FtMsgUtil.builderSkillCdResp(this));

		setContext(null);
		skillContainer.setCurrSkill(null);
	}

	/**
	 * 弹夹处理
	 * 
	 * @return 换弹夹延迟时间
	 */
	public int clipProcessing() {
		if (!isWeaponSkill()) {
			return 0;
		}
		// 弹夹数量减一
		owner.reduceClipSize();

		if (owner.getCurrClipSize() <= 0) {

			return reloadClip();

		}

		return 0;

	}

	/** 换弹夹 */
	private int reloadClip() {
		int reloadTime = owner.getReloadTime();

		owner.getTaskQueue().addDelayTask(new Runnable() {
			public void run() {
				owner.getPropertyManager().setIntValue(PropertyConstants.currClipSize, owner.getClipSize());
				owner.getPropertyManager().sendPropertyValue(true, PropertyConstants.currClipSize);
			}
		}, reloadTime);
		// 发送换弹夹消息
		owner.sendMsg(MsgCode.WeaponReloadResp,
				FtMsgUtil.builderWeaponReloadResp(owner.getPhyId(), owner.getReloadTime()));

		return reloadTime;
	}

	/** 技能是否僵直中 */
	public boolean isRigid() {
		return rigidEndTime > owner.getFrameTime();
	}

	/** 检查是否在CD中 */
	public boolean checkInCd() {
		return cdTime.checkIn(owner.getFrameTime());
	}

	public SkillContext getContext() {
		return context;
	}

	public void setContext(SkillContext context) {
		this.context = context;
	}

	/** 根据技能释放角度，锁定一个目标，来确定技能最终的释放方向 */
	public void lockTarget() {

		// 如果上次攻击的敌人还在我的攻击范围内，我要不要继续对他进行攻击呢；
		Living<?> lockTarget = null;

		// 上报的角度(<0、>=360)自动锁定

		if (context.getSkillAngle() < 0 || context.getSkillAngle() >= 360) {
			// 自动锁定
			lockTarget = autoSelectTarget();
		} else {

			if (skillCfg.getAssistLock()) {
				// 技能配置的锁定角度(<=0,>=360)自动锁定
				if (skillCfg.getLockDegree() <= 0 || skillCfg.getLockDegree() >= 360) {
					lockTarget = autoSelectTarget();
				} else {
					// 辅助角度锁定
					lockTarget = assistSelectTarget();
				}

			}

		}

		if (lockTarget != null) {
			setLockTarget(lockTarget);
		} else {
			setSkillTargetAngle();
		}

		owner.setFaceDir(context.getCorrectSkillAngle());

	}

	/** 设置技能释放目标方向 */
	public void setSkillTargetAngle() {
		context.setCorrectSkillAngle(context.getSkillAngle() < 0 ? owner.getFaceDir() : context.getSkillAngle());
	}

	public void setLockTarget(Living<?> lockTarget) {

		context.setLockTarget(lockTarget);
		context.setLockTargetX(lockTarget.getX());
		context.setLockTargetY(lockTarget.getY());

		context.setCorrectSkillAngle(DistanceUtils.getAngle(owner, lockTarget));

	}

	/** 360度自动查找目标 */
	public Living<?> autoSelectTarget() {
		return FindCloseTarget.findNearestLiving(true, true, owner, skillCfg.getTargetType(), getAttackDistance(), 0, 0,  Hero.class, Monster.class);
 
	}

	/** 辅助角度锁定目标 */
	public Living<?> assistSelectTarget() {
		// 根据锁定角度查找最近的敌人
		int downAngle = context.getSkillAngle() - getLockAngle() / 2;
		int upAngle = downAngle + getLockAngle();

		return FindCloseTarget.findNearestLiving(true, true, owner, skillCfg.getTargetType(), getAttackDistance(),
				downAngle, upAngle, Hero.class, Monster.class);
	}

	public int getCd() {
		return (int) (cdTime.getCdEndTime() - owner.getFrameTime());
	}

	public int getGroupCd() {
		return (int) (skillContainer.getGroupCd().getCdEndTime() - owner.getFrameTime());
	}

	public int getDamageRadius() {

		return skillCfg.getDamageRadius();
	}

	public int getAttackDistance() {
		return skillCfg.getAttackDistance();
	}

	public int getLockAngle() {
		return skillCfg.getLockDegree();
	}

	public boolean isWeaponSkill() {
		return weaponSkill;
	}

	public void setWeaponSkill(boolean weaponSkill) {
		this.weaponSkill = weaponSkill;
	}

	public boolean onCheck() {

		return true;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
