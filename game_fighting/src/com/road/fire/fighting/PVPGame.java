package com.road.fire.fighting;

import com.game.fighting.GameStatus;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.*;
import com.road.fire.consts.PlayerStatus;
import com.road.fire.entity.cfg.*;
import com.road.fire.fighting.consts.DropConsts;
import com.road.fire.fighting.consts.FightingConsts;
import com.road.fire.fighting.consts.MonsterConsts;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.phy.living.drop.SceneDropGoods;
import com.road.fire.match.PlayerMatchInfo;
import com.road.fire.match.Room;
import com.road.fire.match.Team;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;

import static com.road.fire.consts.PlayerStatus.game;

/**
 * PVP战斗
 * 
 * @author lip.li
 *
 */
public class PVPGame extends BaseGame {

	private final Room room;

	public PVPGame(Room room) {
		super(room.getFightignTypeCfg());
		this.room = room;

	}

	@Override
	protected void doInit() {
		initHero();
		initMonster();
		initSceneDrop();
	}

	protected void initSceneDrop() {
		Collection<SceneDropCfg> dropCfgs = SceneDropCfgProvider.getInstance().getSceneDropCfgs(getSceneId());
		if (CollectionUtils.isNotEmpty(dropCfgs)) {
			for (SceneDropCfg dropCfg : dropCfgs) {
				if (dropCfg.getBornType() == DropConsts.BornTypeInit) {
					DropBoxCfg boxCfg = DropBoxCfgProvider.getInstance().getConfigVoByKey(dropCfg.getDropBoxId());

					SceneDropGoods sceneDrop = new SceneDropGoods(dropCfg.getRealX(), dropCfg.getRealY(), this,
							FightingConsts.CampTypeMiddle, dropCfg, boxCfg);
					sceneDrop.init();
				} else {// 其它类型的掉落

				}
			}
		}

	}

	public Room getRoom() {
		return room;
	}

	private void initHero() {
		Collection<Team> teams = room.getTeamMap().values();
		for (Team team : teams) {
			List<PlayerMatchInfo> matchInfos = team.getMatchInfos();

			for (int index = 0; index < matchInfos.size(); index++) {

				PlayerMatchInfo matchInfo = matchInfos.get(index);

				BornPositionCfg bornPoint = BornPositionCfgProvider.getInstance().getBornPositions(getSceneId(),
						team.getCmpType(), index);

				Hero hero = new Hero(bornPoint.getRealX(), bornPoint.getRealY(), this, team.getCmpType(),
						matchInfo.getHeroCfg(), matchInfo.getPlayerInfo());
				hero.setWeaponCfg(matchInfo.getWeaponCfg());

				hero.init();
				fightDataManager.addPlayer(hero);
			}
		}
	}

	private void initMonster() {

		Collection<SceneMonsterCfg> sceneMonsters = SceneMonsterCfgProvider.getInstance()
				.getSceneMonsterCfgs(getSceneId());
		if (!CollectionUtils.isEmpty(sceneMonsters)) {

			for (SceneMonsterCfg sceneMonsterCfg : sceneMonsters) {
				if (sceneMonsterCfg.getBornType() == MonsterConsts.BornTypeInit) {

					MonsterCfg monsterCfg = MonsterCfgProvider.getInstance()
							.getConfigVoByKey(sceneMonsterCfg.getMonsterId());

					Monster monster = new Monster(sceneMonsterCfg.getRealX(), sceneMonsterCfg.getRealY(), this,
							FightingConsts.CampTypeMiddle, sceneMonsterCfg, monsterCfg);

					monster.init();
				} else {// 其它类型的怪物放到刷怪控制器里面处理

				}
			}
		}
		System.out.println(getLivingManager().getLivingCount(Monster.class));
	}

	@Override
	public void onUpdateInit(long now) {

		Collection<Hero> heros = livingManager.getLivings(Hero.class);
		for (Hero hero : heros) {
			if (!hero.isRoboot()) {
				hero.sendMsg(MsgCode.GameLoadingResp, FtMsgUtil.builderGameLoadingResp(hero));
			}

		}

		setStatus(GameStatus.loading);
		room.setPlayerStatus(PlayerStatus.loading);
	}

	@Override
	public void onUpdateLoading(long now) {
		if (checkLoadingOver()) {

			// 发送消息游戏正式开始
			sendMsg(MsgCode.GameStartResp, FtMsgUtil.builderGameStartResp(fightingType.getPlayerReadyTime()));
			setStatus(GameStatus.playerReady);
			room.setPlayerStatus(game);
		}

	}

	private boolean init = false;

	@Override
	public void onUpdatePlayerReady(long now) {

		if (!init) {
			init = true;
			sendMsg(MsgCode.RankBoardResp, FtMsgUtil.buildPBRankBoardResp(getFightDataManager().getAllHero(),
					getFightDataManager().getRankBoard()));
		}

		// 准备时间到，可以开始干了
		if (now - statusStartTimes.get(GameStatus.playerReady) > fightingType.getPlayerReadyTime()) {
			setStatus(GameStatus.fighting);
		}

	}

	@Override
	public void onUpdateFighting(long now) {

	 
		buffContainer.update(now);
 

		Collection<Living<?>> livings = livingManager.getLivings();
		for (Living<?> living : livings) {
			try {
		       
				//long starTime=System.currentTimeMillis();
				living.update(now);
				//long endTime=System.currentTimeMillis();
				//long costTime=endTime-starTime;
				//if(costTime>0){
				//	LOG.error(" living{} update cost Time {}",living.getName(),costTime);
				//}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		// 战斗时间到了，战斗结算
		if (now - statusStartTimes.get(GameStatus.fighting) > fightingType.getFightingTime()) {
			setStatus(GameStatus.computing);
		}

	}

	@Override
	public void onUpdateComputing(long now) {
		// TODO 结算完切换到游戏结束逻辑处理
		setStatus(GameStatus.gameOver);
		sendMsg(MsgCode.GameOverResp, FtMsgUtil.buildPBGameOverResp(this));
		sendMsg(MsgCode.RankBoardResp, FtMsgUtil.buildPBRankBoardResp(getFightDataManager().getAllHero(),
				getFightDataManager().getRankBoard()));
	}

	/**
	 * 移出战斗轮询列表
	 */
	@Override
	public void onUpdateGameOver(long now) {
		GameMgr.getInstance().removeBaseGame(this);
	}

	@Override
	public void setPlayerStatus(PlayerStatus playerStatus) {
		room.setPlayerStatus(playerStatus);
	}

	/** 检查资源是否加载完成 */
	public boolean checkLoadingOver() {
		Collection<Hero> heros = livingManager.getLivings(Hero.class);
		for (Hero hero : heros) {
			if (hero.getLoadingProcess() < 100) {
				return false;
			}
		}
		return true;
	}

}
