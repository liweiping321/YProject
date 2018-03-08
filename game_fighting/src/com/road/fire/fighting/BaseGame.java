package com.road.fire.fighting;

import com.game.delay.DelayTaskQueue;
import com.game.fighting.AbstractGame;
import com.game.fighting.GameStatus;
import com.game.protobuf.MsgCode;
import com.google.protobuf.GeneratedMessage;
import com.road.fire.FtMsgUtil;
import com.road.fire.consts.PlayerStatus;
import com.road.fire.entity.cfg.FightingTypeCfg;
import com.road.fire.fighting.buff.BuffContainer;
import com.road.fire.fighting.data.FightStatisticManager;
import com.road.fire.fighting.phy.LivingManager;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.drop.KillDropGoods;
import com.road.fire.fighting.phy.scene.GameScene;
import com.road.fire.fighting.util.PhyIncrId;

import java.util.Collection;

/**
 * 游戏基础类
 * 
 * @author lip.li
 *
 */
public class BaseGame extends AbstractGame {

	protected final FightingTypeCfg fightingType;
	/** 游戏ID */
	protected int gameId;
	/** 战斗生物管理器 */
	protected LivingManager livingManager;
	/** 异步任务执行队列 */
	protected final DelayTaskQueue taskQueue = new DelayTaskQueue();
	/** BUFF容器 */
	protected BuffContainer buffContainer;

	protected GameScene scene;

	protected FightStatisticManager fightDataManager;

	protected ReliveMgr reliveMgr;

	public BaseGame(FightingTypeCfg fightingType) {
		this.fightingType = fightingType;
		fightDataManager = new FightStatisticManager();
		reliveMgr = new ReliveMgr();
	}

	public LivingManager getLivingManager() {
		return livingManager;
	}

	@Override
	public void update(long now) {
		executeHandlers();

		taskQueue.update(now);

		super.update(now);

		reliveMgr.update(now);
		
		
	}

	private void executeHandlers() {
		Collection<Hero> heros = livingManager.getLivings(Hero.class);
		for (Hero hero : heros) {
			hero.executeHandlers();
		}
	}

	public BuffContainer getBuffContainer() {
		return buffContainer;
	}

	public void setBuffContainer(BuffContainer buffContainer) {
		this.buffContainer = buffContainer;
	}

	public int getGameId() {
		return gameId;
	}

	public DelayTaskQueue getTaskQueue() {
		return taskQueue;
	}

	public void sendMsg(int code, Living<?> except) {
		Collection<Hero> heros = livingManager.getLivings(Hero.class);
		for (Hero hero : heros) {
			if (except != hero) {
				hero.sendMsg(code);
			}
		}

	}

	public void sendMsg(int code, GeneratedMessage msg, Living<?> except) {

		Collection<Hero> heros = livingManager.getLivings(Hero.class);

		for (Hero hero : heros) {
			if (except != hero) {
				hero.sendMsg(code, msg);
			}
		}
	}

	public void sendMsg(int code) {
		Collection<Hero> heros = livingManager.getLivings(Hero.class);
		for (Hero hero : heros) {
			hero.sendMsg(code);
		}

	}

	public void sendMsg(int code, GeneratedMessage msg) {
		sendMsg(code, msg, null);

	}

	public void init() {
		gameId = PhyIncrId.incrAndGetPhyId();
		buffContainer = new BuffContainer(null, this);
		livingManager = new LivingManager(fightingType.getCmpNum());
		scene = new GameScene(fightingType.getSceneId());
		doInit();
		setStatus(GameStatus.init);

	}

	protected void doInit() {

	}

	public GameScene getScene() {
		return scene;
	}

	public void addLiving(Living<?> living) {
		livingManager.addLiving(living);
	}

	public void removeLiving(Living<?> living) {
		livingManager.removeLiving(living);

		sendMsg(MsgCode.LivingRemoveResp, FtMsgUtil.builderLivingRemoveResp(living));
	}

	public int getSceneId() {
		return scene.getSceneId();
	}

	public void setPlayerStatus(PlayerStatus status) {

	}

	public FightingTypeCfg getFightingType() {
		return fightingType;
	}

	public void bornLiving(KillDropGoods killDropGoods) {
		addLiving(killDropGoods);
		sendMsg(MsgCode.LivingBornResp, FtMsgUtil.builderLivingBornResp(killDropGoods));

	}

	public FightStatisticManager getFightDataManager() {
		return fightDataManager;
	}

	public ReliveMgr getReliveMgr() {
		return reliveMgr;
	}
}
