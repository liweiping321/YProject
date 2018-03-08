package com.road.fire.fighting.phy.living;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.game.ai.fsm.FsmState;
import com.game.ai.fsm.IStateMachine;
import com.game.cfg.entity.ConfigsCfg;
import com.game.cfg.entity.PointCfg;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBLivingInfoBean;
import com.google.protobuf.GeneratedMessage;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.BuffCfgProvider;
import com.road.fire.cfg.provider.DropBoxCfgProvider;
import com.road.fire.cfg.provider.DropTemplateCfgProvider;
import com.road.fire.entity.cfg.BuffCfg;
import com.road.fire.entity.cfg.DropBoxCfg;
import com.road.fire.entity.cfg.DropItem;
import com.road.fire.entity.cfg.DropTemplateCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.ai.move.IMoveController;
import com.road.fire.fighting.buff.BuffContainer;
import com.road.fire.fighting.consts.BuffConsts;
import com.road.fire.fighting.consts.FightingConsts;
import com.road.fire.fighting.phy.LivingManager;
import com.road.fire.fighting.phy.Physics;
import com.road.fire.fighting.phy.living.drop.KillDropGoods;
import com.road.fire.fighting.skill.container.SkillContainer;

/**
 * 
 * @author lip.li
 *
 */
public abstract class Living<T> extends Physics<T> {

	protected BuffContainer buffContainer;
	/** 生物状态机 */
	protected IStateMachine<T> stateMachine;
	/** 技能容器 */
	protected SkillContainer<?> skillContainer;
	/** 移动控制器 */
	protected IMoveController moveController;

	/** 是否已经死亡的,当血量小于0时并且执行过onDead方法则为true,或者为false */
	protected boolean dead;
	/** 标记的最后攻击目标 */
	private Living<?> attackTarget;
	/** 锁定攻击结束事件 */
	private long lockEndTime;
	
	protected List<PointCfg> dropPoints;

	public Living(int x, int y, BaseGame game, int campType) {
		super(x, y, game, campType);
		// game.getScene().initPosition(tileX, tileY, this);
	}

	public void init() {
		buffContainer = new BuffContainer(this, game);
		onInit();

		game.addLiving(this);
	}

	@Override
	public void update(long now) {

		if (null != stateMachine) {
			long starTime=System.currentTimeMillis();
			stateMachine.update(now);
			long endTime=System.currentTimeMillis();
			long costTime=endTime-starTime;
			if(costTime>0){
				LOG.error(" living{} update cost Time {} stateMachine",getName(),costTime);
			}
		}

		if (null != buffContainer) {
			long starTime=System.currentTimeMillis();
			buffContainer.update(now);
			long endTime=System.currentTimeMillis();
			long costTime=endTime-starTime;
			if(costTime>0){
				LOG.error(" living{} update cost Time {} buffContainer",getName(),costTime);
			}
		}

		if (null != skillContainer) {
			long starTime=System.currentTimeMillis();
			skillContainer.update(now);
			long endTime=System.currentTimeMillis();
			long costTime=endTime-starTime;
			if(costTime>0){
				LOG.error(" living{} update cost Time {} skillContainer",getName(),costTime);
			}
		}
		long starTime=System.currentTimeMillis();
		taskQueue.update(now);
		long endTime=System.currentTimeMillis();
		long costTime=endTime-starTime;
		if(costTime>0){
			LOG.error(" living{} update cost Time {} taskQueue",getName(),costTime);
		}

		if (getCurrHp() <= 0 && !isDead()) {
			onDie();
		}
	}
	/**切换进入死亡状态*/
	public void onDie(){
		// 发送生物死亡消息
		broadcastMsg(MsgCode.LivingDieResp, getDieMsg());
		// 进入死亡状态
		switchState(FsmState.die);
		attackTarget = null;
		dead=true;
	}
	
	
	
	/** 血量小于0触发死亡 */
	public void onDead() {
		
	}
	
	public void onDeadDrop(){
		
	}
	protected void addDropGoods(DropItem dropItem,int x,int y) {
		DropTemplateCfg templateCfg = DropTemplateCfgProvider.getInstance().getConfigVoByKey(dropItem.getGoodsId());

		KillDropGoods killDropGoods = new KillDropGoods(x, y, getX(),
				getY(), game, FightingConsts.CampTypeMiddle, dropItem, templateCfg);

		killDropGoods.init();

		game.bornLiving(killDropGoods);
	}
	
	protected void onDeadDrop(Set<Integer> dropIds) {
		int index = 0;
		int dropPointSize = dropPoints.size();
		if (CollectionUtils.isNotEmpty(dropIds)) {
			for (Integer dropId : dropIds) {
				DropBoxCfg dropBoxCfg = DropBoxCfgProvider.getInstance().getConfigVoByKey(dropId);
				List<DropItem> dropItems = dropBoxCfg.drop();

				if (CollectionUtils.isNotEmpty(dropItems)) {
					for (DropItem dropItem : dropItems) {
						PointCfg pointCfg = dropPoints.get(index % dropPointSize);
						addDropGoods(dropItem, pointCfg.getX(),pointCfg.getY());
						index++;
					}

				}

			}
		}
	}
	public abstract void onInit();

	public abstract PBLivingInfoBean getLivingBean();

	public GeneratedMessage getBornMsg() {
		return FtMsgUtil.builderLivingBornResp(this);
	}

	public LivingManager getLivingManager() {
		return game.getLivingManager();
	}

	public GeneratedMessage getDieMsg() {
		return FtMsgUtil.builderLivingDieResp(this);
	}

	public BuffContainer getBuffContainer() {
		return buffContainer;
	}

	public void setBuffContainer(BuffContainer buffContainer) {
		this.buffContainer = buffContainer;
	}

	public IStateMachine<T> getStateMachine() {
		return stateMachine;
	}

	public void setStateMachine(IStateMachine<T> stateMachine) {
		this.stateMachine = stateMachine;
	}

	public SkillContainer<?> getSkillContainer() {
		return skillContainer;
	}

	public void setSkillContainer(SkillContainer<?> skillContainer) {
		this.skillContainer = skillContainer;
	}

	/** 切换状态机 */
	public void switchState(FsmState state) {
		if (null != stateMachine) {
			stateMachine.switchState(state);
		}
	}

	public FsmState getFsmState() {
		return stateMachine.getCurrMainState().getState();
	}

	public IMoveController getMoveController() {
		return moveController;
	}

	public void setMoveController(IMoveController moveController) {
		this.moveController = moveController;
	}

	/** 技能释放僵直中 */
	public boolean isSkillRigid() {
		return skillContainer.isRigid();
	}

	/** 技能释放中 */
	public boolean isSkillReleaseIn() {
		return skillContainer.isReleaseIn();
	}

	public boolean hasEffect(int effectId) {
		return buffContainer.hasEffect(effectId);
	}

	public boolean hasEffectType(int effectType) {
		return buffContainer.hasEffectType(effectType);
	}

	/** 停止移动 */
	public void stopMove() {
		if (getFsmState() == FsmState.move) {
			switchState(FsmState.idle);
		}
		broadcastMsg(MsgCode.MoveStopResp, FtMsgUtil.builderMoveStopResp(this));

	}

	public Living<?> getAttackTarget() {
		if (attackTarget != null) {
			if (attackTarget.isDie()) {
				return null;
			}
			if (lockEndTime < game.getFrameTime()) {
				return null;
			}
		}
		return attackTarget;
	}

	public void setAttackTarget(Living<?> attackTarget) {
		this.attackTarget = attackTarget;
		this.lockEndTime = game.getFrameTime() + ConfigsCfg.lockAttackTime;
	}

	/** 添加BUFF列表 */
	public void addBuff(Living<?> target, List<Integer> buffs) {
		if (!CollectionUtils.isEmpty(buffs)) {
			for (Integer buffId : buffs) {
				addBuff(target, buffId);
			}

		}
	}

	public void addBuff(Living<?> target, Integer buffId) {
		BuffCfg buffCfg = BuffCfgProvider.getInstance().getConfigVoByKey(buffId);
		if (buffCfg.getObjType() == BuffConsts.ObjTypeTarget) {
			target.getBuffContainer().addBuff(buffId, this);
		} else if (buffCfg.getObjType() == BuffConsts.ObjTypeSource) {
			getBuffContainer().addBuff(buffId, this);
		}

	}

	public boolean isDie() {
		return getCurrHp() <= 0 || isDead();
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/** 回城复活 */
	public void onCityRelive() {
		setCurrHp(getMaxHp());
		setX(getBornX());
		setY(getBornY());
		setDead(false);
		broadcastMsg(MsgCode.LivingBornResp, FtMsgUtil.builderLivingBornResp(this));
	}

	/** 是否是破坏类型生物 */
	public boolean isBreachType() {
		return false;
	}

	/**
	 * 
	 */
	public boolean onRelive() {
		return false;
	}

	/** 生物被攻击 */
	public void onbeAttacked(Living<?> source) {
		source.setAttackTarget(this);

	}
	/**添加经验*/
	public void addExp(int exp){
		
	}
}
