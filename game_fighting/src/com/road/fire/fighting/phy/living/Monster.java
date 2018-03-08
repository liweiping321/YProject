package com.road.fire.fighting.phy.living;

import com.game.fighting.TimeLog;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBLivingInfoBean;
import com.road.fire.FtMsgUtil;
import com.road.fire.entity.cfg.MonsterCfg;
import com.road.fire.entity.cfg.SceneMonsterCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.ai.fsm.monster.MonsterStateMachine;
import com.road.fire.fighting.ai.move.MoveController;
import com.road.fire.fighting.consts.MonsterConsts;
import com.road.fire.fighting.consts.PhysicsType;
import com.road.fire.fighting.consts.TileType;
import com.road.fire.fighting.phy.scene.MapPoint;
import com.road.fire.fighting.property.MonsterPropertyManger;
import com.road.fire.fighting.skill.container.MonsterSkillContainer;
import com.road.script.living.monster.CompositeActions;
import com.road.script.living.monster.composite.MonsterCompositeAction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 怪物
 * 
 * @author lip.li
 *
 */
public class Monster extends Living<Monster> {

	private final SceneMonsterCfg sceneMonsterCfg;

	private final MonsterCfg monsterCfg;

	private final Set<MapPoint> monsterPoints = new HashSet<>();

	public Monster(int x, int y, BaseGame game, int campType, SceneMonsterCfg sceneMonsterCfg, MonsterCfg monsterCfg) {
		super(x, y, game, campType);
		this.sceneMonsterCfg = sceneMonsterCfg;
		this.monsterCfg = monsterCfg;

	}

	@Override
	public int getPhType() {

		return PhysicsType.MONSTER.getValue();
	}

	public PBLivingInfoBean getLivingBean() {
		return FtMsgUtil.builderMonsterLivingBean(this);
	}

	@Override
	public int getDamageRadius() {
		return monsterCfg.getDamageRadius();
	}

	@Override
	public void onInit() {
		skillContainer = new MonsterSkillContainer(this, game);
		propertyManager = new MonsterPropertyManger(this);
		stateMachine = new MonsterStateMachine(this);
		moveController = new MoveController<>(this);

		propertyManager.initializePropertyValue();

		this.dropPoints = sceneMonsterCfg.getDropPointCfgs();

		setFaceDir(sceneMonsterCfg.getBornFaceDir());

		MonsterCompositeAction compositeAction = CompositeActions.getIns().getAction(monsterCfg.getType());
		if (compositeAction != null) {
			compositeAction.onInit(this);
		}

		addMonsterPoints();
	}

	public void addMonsterPoints() {
		for (MapPoint point : monsterPoints) {
			getScene().setTitleType(point.getX(), point.getY(), TileType.BOX);
		}
	}

	public void removeMonsterPoints() {
		for (MapPoint point : monsterPoints) {
			getScene().setTitleType(point.getX(), point.getY(), TileType.WALK);
		}

	}

	@Override
	public void onDead() {
		//死亡触发技能
		onDieTriggerSkill();
		
		removeMonsterPoints();

		onDeadDrop();

		MonsterCompositeAction compositeAction = CompositeActions.getIns().getAction(monsterCfg.getType());
		if (compositeAction != null) {
			compositeAction.onDead(this);
		}

		game.removeLiving(this);
		if (sceneMonsterCfg.getRelive()) {
			setReliveTime(game.getFrameTime() + sceneMonsterCfg.getReliveDelayTime());
			game.getReliveMgr().add2ReliveList(this);
		}
	}

	private void onDieTriggerSkill() {
		if(monsterCfg.getDieTriggerSkill()>0){
			skillContainer.onDieTriggerSKill(monsterCfg.getDieTriggerSkill());
		}
		
	}

	@Override
	public boolean onRelive() {

		setX(getBornX());
		setY(getBornY());
		setCurrHp(getMaxHp());
		setDead(false);
		addMonsterPoints();
		broadcastMsg(MsgCode.LivingBornResp, FtMsgUtil.builderLivingBornResp(this));
		game.addLiving(this);
		// getScene().initPosition(getTileX(), getTileY(), this);
		return true;
	}

	/** 死亡时掉落 */
	public void onDeadDrop() {
		Set<Integer> dropIds = monsterCfg.getDropIds();
	
		Collections.shuffle(dropPoints);
		
		onDeadDrop(dropIds);
	}
 
	public SceneMonsterCfg getSceneMonsterCfg() {
		return sceneMonsterCfg;
	}

	public MonsterCfg getMonsterCfg() {
		return monsterCfg;
	}

	@Override
	public boolean isBreachType() {
		return monsterCfg.getHurtFormulaType() == MonsterConsts.HurtFormulaBreach;
	}

	@Override
	public String getName() {

		return monsterCfg.getName();
	}

	@Override
	public int getTemplateId() {

		return monsterCfg.getTemplateId();
	}

	public Set<MapPoint> getMonsterPoints() {
		return monsterPoints;
	}

}
