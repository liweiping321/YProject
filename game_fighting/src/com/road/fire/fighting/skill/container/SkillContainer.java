package com.road.fire.fighting.skill.container;

import com.game.polling.PollingUpdate;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.consts.SkillStatus;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.SkillContext;
import com.road.fire.fighting.skill.cd.CdTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * 技能容器,管理主动技能
 *
 * @author lip.li
 *
 */
public class SkillContainer<T extends Living<?>> implements PollingUpdate {

	private static final Logger LOG = LogManager.getLogger(SkillContainer.class);

	/** 容器的主人 */
	protected final T owner;
	/** 游戏 */

	protected final BaseGame game;

	/** 当前正在执行中的技能 */
	protected ActiveSkill<?> currSkill;
	/** 主动技能(0,1,2,3)四个技能槽 */
	protected final Map<Integer, ActiveSkill<?>> activeSkillMap = new TreeMap<>();
	/** 公共CD */
	protected final CdTime groupCd = new CdTime();

	public SkillContainer(T owner, BaseGame game) {
		this.owner = owner;
		this.game = game;
		init();
	}

	public void init() {
	}

	@Override
	public void update(long now) {
		if (null != currSkill) {

			currSkill.update(now);

			// 技能释放流程结束
			if (currSkill.getStatus() == SkillStatus.wait) {
				currSkill.onFinish();
			}
		}

	}

	public Map<Integer, ActiveSkill<?>> getActiveSkillMap() {
		return activeSkillMap;
	}

	/** 技能是否僵直中 */
	public boolean isRigid() {
		if (currSkill != null) {
			return currSkill.isRigid();
		}
		return false;
	}

	public boolean isReleaseIn() {
		return currSkill != null;
	}

	/** 使用技能 */
	public void useSkill(SkillContext context) {

		// 检测技能CD
		if (checkInCd(context.getIndex())) {
			return;
		}
		ActiveSkill<?> activeSkill = activeSkillMap.get(context.getIndex());

		activeSkill.setContext(context);

		if (activeSkill.onCheck()) {

			setCurrSkill(activeSkill);
			// 锁定目标
			activeSkill.lockTarget();
			// 技能开始释放
			activeSkill.onStart();
		}

	}

	/** 检查技能是否正在CD中 */
	public boolean checkInCd(int index) {
		long frameTime = owner.getFrameTime();
		// 普通技能不检查公共CD
		if (index != 0 && groupCd.checkIn(frameTime)) {

			return true;
		}

		ActiveSkill<?> activeSkill = activeSkillMap.get(index);
		if (null != activeSkill) {
			return activeSkill.checkInCd();
		}

		return false;
	}

	public boolean hasSkill(int index) {
		return activeSkillMap.get(index) != null;
	}

	/** 打断技能 */
	public void stopSkill() {
		if (null != currSkill) {
			// 技能在吟唱中可以被中断
			if (currSkill.getStatus() == SkillStatus.read) {

				currSkill.setStatus(SkillStatus.wait);

				owner.sendMsg(MsgCode.SkillStopResp, FtMsgUtil.builderSkillStopResp(currSkill));
			}
		}

	}

	public ActiveSkill<?> getCurrSkill() {
		return currSkill;
	}

	public void setCurrSkill(ActiveSkill<?> currSkill) {
		this.currSkill = currSkill;
	}

	public CdTime getGroupCd() {
		return groupCd;
	}

	/** 添加道具技能 */
	public void addPropSkill(int skillId) {
	}

	public boolean hasFreeIndex(){
		return true;
	}
	public void addActiveSkill(int index, ActiveSkill<?> activeSkill) {
		activeSkillMap.put(index, activeSkill);
		if (null != activeSkill) {
			activeSkill.setIndex(index);
		}

	}
	/**死亡触发技能(直接选择目标计算伤害,没有施展动作,动作融合到死亡动画里面)*/
	public void onDieTriggerSKill(int skillId) {
	 
		
	}

}
