package com.road.fire;

import com.game.cfg.entity.ConfigsCfg;
import com.game.property.PropertyValue;
import com.game.protobuf.fighting.FightingProto.*;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.ai.move.MoveInfo;
import com.road.fire.fighting.buff.BaseBuff;
import com.road.fire.fighting.data.FightStatistic;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.living.Monster;
import com.road.fire.fighting.phy.living.drop.DropGoods;
import com.road.fire.fighting.skill.ActiveSkill;
import com.road.fire.fighting.skill.CastResult;
import com.road.fire.fighting.skill.SkillContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class FtMsgUtil {
	/** 游戏加载进度 */
	public static PBLoadingProcessResp builderLoadingProcessResp(Hero hero) {

		PBLoadingProcessResp.Builder processResp = PBLoadingProcessResp.newBuilder();
		processResp.setPlayerId(hero.getPlayerId());
		processResp.setProcess(hero.getLoadingProcess());

		return processResp.build();
	}

	public static PBGameStartResp builderGameStartResp(int playerReadyTime) {
		PBGameStartResp.Builder startResp = PBGameStartResp.newBuilder();
		startResp.setPlayerReadyTime(playerReadyTime);
		return startResp.build();
	}

	/** 构建英雄生物信息 */
	public static PBLivingInfoBean builderHeroLivingBean(Hero hero) {
		PBLivingInfoBean.Builder livingInfo = PBLivingInfoBean.newBuilder();
		livingInfo.setPhId(hero.getPhyId());
		livingInfo.setPhType(hero.getPhType());

		Collection<PropertyValue> propValues = hero.getPropertyManager().getViewPropertyValues();
		for (PropertyValue propValue : propValues) {
			PBPropValueBean propBean = builderPropValueBean(propValue);
			livingInfo.addPropValues(propBean);
		}

		livingInfo.setX(hero.getX());
		livingInfo.setY(hero.getY());
		livingInfo.setCampType(hero.getCampType());
		livingInfo.setTemplateId(hero.getTemplateId());
		livingInfo.setMoveDir(hero.getMoveDir());
		livingInfo.setFaceDir(hero.getFaceDir());
		livingInfo.setPlayerName(hero.getPlayerName());

		Set<Entry<Integer, ActiveSkill<?>>> entrySet = hero.getSkillContainer().getActiveSkillMap().entrySet();
		for (Entry<Integer, ActiveSkill<?>> entry : entrySet) {
			PBSKillBean.Builder skillBean = PBSKillBean.newBuilder();
			skillBean.setIndex(entry.getKey());
			ActiveSkill<?> activeSkill = entry.getValue();
			skillBean.setSkillId(activeSkill == null ? 0 : activeSkill.getSkillId());
			livingInfo.addSkills(skillBean.build());
		}

		livingInfo.setName(hero.getPlayerInfo().getPlayerData().getNickname());
		livingInfo.setPlayerId(hero.getPlayerId());

		return livingInfo.build();

	}

	public static PBPropValueBean builderPropValueBean(PropertyValue propValue) {
		PBPropValueBean.Builder valueBean = PBPropValueBean.newBuilder();
		valueBean.setType(propValue.getKey());

		if (propValue.getValueType() == PropertyValue.VALUE_TYPE_INT) {
			valueBean.setValue(propValue.getIntValue());
		} else {
			valueBean.setValue(propValue.getLongValue());
		}
		propValue.setUpdate(false);
		return valueBean.build();
	}

	/** 构建游戏加载消息 */
	public static PBGameLoadingResp builderGameLoadingResp(Hero hero) {
		PBGameLoadingResp.Builder loadingResp = PBGameLoadingResp.newBuilder();
		BaseGame game = hero.getGame();
		loadingResp.setGameId(game.getGameId());
		loadingResp.setFirstInGame(true);
		loadingResp.setSceneId(game.getSceneId());
		loadingResp.setPlayerId(hero.getPlayerId());
		loadingResp.setLoadingTime(0);
		Collection<Living<?>> livings = game.getLivingManager().getLivings();
		for (Living<?> living : livings) {
			loadingResp.addLivingInfo(living.getLivingBean());
		}

		return loadingResp.build();
	}

	/** 构建怪物生物信息 */
	public static PBLivingInfoBean builderMonsterLivingBean(Monster monster) {

		PBLivingInfoBean.Builder livingInfo = PBLivingInfoBean.newBuilder();

		livingInfo.setPhId(monster.getPhyId());
		livingInfo.setPhType(monster.getPhType());

		Collection<PropertyValue> propValues = monster.getPropertyManager().getViewPropertyValues();
		for (PropertyValue propValue : propValues) {
			PBPropValueBean propBean = builderPropValueBean(propValue);
			livingInfo.addPropValues(propBean);
		}

		livingInfo.setX(monster.getX());
		livingInfo.setY(monster.getY());
		livingInfo.setCampType(monster.getCampType());
		livingInfo.setTemplateId(monster.getTemplateId());
		livingInfo.setMoveDir(monster.getMoveDir());
		livingInfo.setFaceDir(monster.getFaceDir());

		Set<Entry<Integer, ActiveSkill<?>>> entrySet = monster.getSkillContainer().getActiveSkillMap().entrySet();
		for (Entry<Integer, ActiveSkill<?>> entry : entrySet) {
			PBSKillBean.Builder skillBean = PBSKillBean.newBuilder();
			skillBean.setIndex(entry.getKey());
			ActiveSkill<?> activeSkill = entry.getValue();
			skillBean.setSkillId(activeSkill == null ? 0 : activeSkill.getSkillId());
			livingInfo.addSkills(skillBean.build());
		}

		livingInfo.setName(monster.getName());
		livingInfo.setDetailType(monster.getMonsterCfg().getType());

		return livingInfo.build();

	}

	/** 属性同步 */
	public static PBPropValueUpdateResp builderPropValueUpdateResp(int phId, Collection<PropertyValue> propVaules) {
		PBPropValueUpdateResp.Builder propUpdateResp = PBPropValueUpdateResp.newBuilder();

		propUpdateResp.setPhId(phId);
		Collection<PropertyValue> propValues = propVaules;
		for (PropertyValue propValue : propValues) {
			PBPropValueBean propBean = builderPropValueBean(propValue);
			propUpdateResp.addPropValues(propBean);
		}

		return propUpdateResp.build();
	}

	/** 移动停止 */
	public static PBMoveStopResp builderMoveStopResp(Living<?> living) {
		PBMoveStopResp.Builder moveStopResp = PBMoveStopResp.newBuilder();
		moveStopResp.setPhId(living.getPhyId());
		moveStopResp.setToX(living.getX());
		moveStopResp.setToY(living.getY());
		return moveStopResp.build();

	}

	/** 移动 */
	public static PBMoveResp builderMoveResp(MoveInfo moveInfo, Living<?> living) {
		PBMoveResp.Builder moveResp = PBMoveResp.newBuilder();
		moveResp.setFaceDir(moveInfo.getFaceDir());
		moveResp.setMoveDir(moveInfo.getMoveDir());
		moveResp.setPhId(living.getPhyId());
		moveResp.setStartX(moveInfo.getStartX());
		moveResp.setStartY(moveInfo.getStartY());
		moveResp.setStartTime(moveInfo.getStartTime());
		return moveResp.build();
	}

	/** 构建生物死亡信息 */
	public static PBLivingDieResp builderLivingDieResp(Living<?> living) {
		PBLivingDieResp.Builder dieResp = PBLivingDieResp.newBuilder();
		dieResp.setPhID(living.getPhyId());
		dieResp.setDisappearTime(ConfigsCfg.bodyDisappeareTime);
		return dieResp.build();
	}

	/** 构建生物死亡信息 */
	public static PBLivingRemoveResp builderLivingRemoveResp(Living<?> living) {
		PBLivingRemoveResp.Builder removeResp = PBLivingRemoveResp.newBuilder();
		removeResp.setPhID(living.getPhyId());

		return removeResp.build();
	}

	/** BUFF移除 */
	public static PBBuffRemoveResp builderBuffRemoveResp(int buffPhyId) {
		PBBuffRemoveResp.Builder buffRemoveResp = PBBuffRemoveResp.newBuilder();
		buffRemoveResp.setPhID(buffPhyId);

		return buffRemoveResp.build();
	}

	/** BUFF更新 */
	public static PBBuffUpdateResp builderBuffUpdateResp(BaseBuff buff) {
		PBBuffUpdateResp.Builder buffUpdateResp = PBBuffUpdateResp.newBuilder();
		buffUpdateResp.setBuffId(buff.getBuffId());
		buffUpdateResp.setPhID(buff.getBuffPhyId());
		buffUpdateResp.setLastTime(buff.getLastTime());
		buffUpdateResp.setTargetId(buff.getTargetId());
		buffUpdateResp.setSourceId(buff.getSourceId());

		return buffUpdateResp.build();
	}

	/** 朝向 */
	public static PBFaceDirResp builderFaceDirResp(Hero hero) {
		PBFaceDirResp.Builder resp = PBFaceDirResp.newBuilder();
		resp.setFaceDir(hero.getFaceDir());
		resp.setPhID(hero.getPhyId());
		return resp.build();
	}

	/** 移动打点 */
	public static PBMoveCurPointResp builderMoveCurPointResp(Living<?> living) {
		PBMoveCurPointResp.Builder curPointResp = PBMoveCurPointResp.newBuilder();
		curPointResp.setPhID(living.getPhyId());
		curPointResp.setX(living.getX());
		curPointResp.setY(living.getY());
		return curPointResp.build();
	}

	/** 打断技能释放 */
	public static PBSkillStopResp builderSkillStopResp(ActiveSkill<?> activeSkill) {
		PBSkillStopResp.Builder skillStopResp = PBSkillStopResp.newBuilder();
		skillStopResp.setSkillId(activeSkill.getSkillId());
		skillStopResp.setSourucePhId(activeSkill.getOwner().getPhyId());
		return skillStopResp.build();

	}

	/** 技能使用 */
	public static PBSkillUseResp builderSkillUseResp(ActiveSkill<?> activeSkill) {
		PBSkillUseResp.Builder skillUseResp = PBSkillUseResp.newBuilder();
		SkillContext context = activeSkill.getContext();
		skillUseResp.setPhId(activeSkill.getOwner().getPhyId());
		skillUseResp.setSkillAngle(context.getCorrectSkillAngle());
		skillUseResp.setSkillId(activeSkill.getSkillId());
		skillUseResp.setStartX(context.getStartX());
		skillUseResp.setStartY(context.getStartY());
		skillUseResp.setTargetX(context.getLockTargetX());
		skillUseResp.setTargetY(context.getLockTargetY());
		skillUseResp.setTargetPhId(context.getTargetPhId());

		return skillUseResp.build();
	}

	/** 技能cd */
	public static PBSkillCdResp builderSkillCdResp(ActiveSkill<?> activeSkill) {
		PBSkillCdResp.Builder skillCdResp = PBSkillCdResp.newBuilder();

		skillCdResp.setIndex(activeSkill.getContext().getIndex());
		skillCdResp.setGroupCd(activeSkill.getGroupCd());
		skillCdResp.setSkillCd(activeSkill.getCd());

		return skillCdResp.build();
	}

	public static PBTargetHurtBean builderTargetHurtBean(CastResult castResult) {
		PBTargetHurtBean.Builder targetHurt = PBTargetHurtBean.newBuilder();
		targetHurt.setCurHp(castResult.getTarget().getCurrHp());
		targetHurt.setMiss(castResult.isMis());
		targetHurt.setType(castResult.getType());
		targetHurt.setValue(castResult.getValue());
		targetHurt.setTargetPhId(castResult.getTarget().getPhyId());
		return targetHurt.build();
	}

	/** 技能释放结果 */
	public static PBSkillResultResp builderSkillResultResp(int skillId, int sourcePhId, CastResult castResult) {
		PBSkillResultResp.Builder skillResultResp = PBSkillResultResp.newBuilder();
		skillResultResp.setSkillId(skillId);
		skillResultResp.setSourcePhId(sourcePhId);
		skillResultResp.addTargetHurts(builderTargetHurtBean(castResult));
		return skillResultResp.build();
	}

	/** 技能释放结果 */
	public static PBSkillResultResp builderSkillResultResp(int sourcePhId, CastResult castResult) {
		PBSkillResultResp.Builder skillResultResp = PBSkillResultResp.newBuilder();
		skillResultResp.setSourcePhId(sourcePhId);
		skillResultResp.addTargetHurts(builderTargetHurtBean(castResult));
		return skillResultResp.build();
	}

	/** 技能释放结果 */
	public static PBSkillResultResp builderSkillResultResp(int skillId, int sourcePhId, List<CastResult> castResults) {
		PBSkillResultResp.Builder skillResultResp = PBSkillResultResp.newBuilder();
		skillResultResp.setSkillId(skillId);
		skillResultResp.setSourcePhId(sourcePhId);
		for (CastResult castResult : castResults) {
			skillResultResp.addTargetHurts(builderTargetHurtBean(castResult));
		}
		return skillResultResp.build();
	}

	/** 英雄复活倒计时 */
	public static PBHeroReLiveTimeResp builderHeroReLiveTimeResp(Living<?> living) {
		PBHeroReLiveTimeResp.Builder heroReliveTime = PBHeroReLiveTimeResp.newBuilder();
		heroReliveTime.setPhId(living.getPhyId());
		heroReliveTime.setReliveTime(ConfigsCfg.getCityReliveTime());
		return heroReliveTime.build();
	}

	/** 生物出生信息 */
	public static final PBLivingBornResp builderLivingBornResp(Living<?> living) {
		PBLivingBornResp.Builder livingBornResp = PBLivingBornResp.newBuilder();

		livingBornResp.setBornLiving(living.getLivingBean());

		return livingBornResp.build();
	}

	/** 换弹夹reload时间 */
	public static final PBWeaponReloadResp builderWeaponReloadResp(int phId, int reloadTime) {
		PBWeaponReloadResp.Builder weaponReloadResp = PBWeaponReloadResp.newBuilder();
		weaponReloadResp.setReloadTime(reloadTime);
		weaponReloadResp.setPhId(phId);
		return weaponReloadResp.build();
	}

	/** 构建场景掉落生物消息 */
	public static PBLivingInfoBean builderDropLivingBean(DropGoods dropGoods) {
		PBLivingInfoBean.Builder livingInfo = PBLivingInfoBean.newBuilder();
		livingInfo.setPhId(dropGoods.getPhyId());
		livingInfo.setPhType(dropGoods.getPhType());
		livingInfo.setX(dropGoods.getX());
		livingInfo.setY(dropGoods.getY());
		livingInfo.setCampType(dropGoods.getCampType());
		livingInfo.setTemplateId(dropGoods.getTemplateId());
		livingInfo.setMoveDir(dropGoods.getMoveDir());
		livingInfo.setFaceDir(dropGoods.getFaceDir());
		livingInfo.setName(dropGoods.getName());
		livingInfo.setStartX(dropGoods.getStartX());
		livingInfo.setStartY(dropGoods.getStartY());
		return livingInfo.build();

	}

	/** 拾取成功消息 */
	public static PBPickDropResp builderPickDropResp(int phId, int temaplteId) {
		PBPickDropResp.Builder pickDropResp = PBPickDropResp.newBuilder();
		pickDropResp.setPhId(phId);
		pickDropResp.setTemplateId(temaplteId);

		return pickDropResp.build();
	}

	/** 技能槽更新 */
	public static PBSkillUpdateResp builderSkillUpdateResp(Living<?> living) {
		PBSkillUpdateResp.Builder skillUpdateResp = PBSkillUpdateResp.newBuilder();

		Set<Entry<Integer, ActiveSkill<?>>> entrySet = living.getSkillContainer().getActiveSkillMap().entrySet();
		for (Entry<Integer, ActiveSkill<?>> entry : entrySet) {
			PBSKillBean.Builder skillBean = PBSKillBean.newBuilder();
			skillBean.setIndex(entry.getKey());
			ActiveSkill<?> activeSkill = entry.getValue();
			skillBean.setSkillId(activeSkill == null ? 0 : activeSkill.getSkillId());
			skillUpdateResp.addSkill(skillBean);
		}
		return skillUpdateResp.build();
	}

	/**
	 * 排行榜数据，只发送有变化的玩家的数据
	 */
	public static PBRankBoardResp buildPBRankBoardResp(List<Living<?>> livings, List<FightStatistic> fightStatistics) {

		PBRankBoardResp.Builder builder = PBRankBoardResp.newBuilder();
		if (livings == null) {
			return builder.build();
		}
		for (FightStatistic fightStatistic : fightStatistics) {
			if (!livings.contains(fightStatistic.getSelf())) {
				continue;
			}
			RankBoardBean.Builder bean = RankBoardBean.newBuilder();
			bean.setPhId(fightStatistic.getSelf().getPhyId());
			bean.setKillNum(fightStatistic.getKillNum());
			bean.setDeadTime(fightStatistic.getDeadCount());
			bean.setAssistsTime(fightStatistic.getAssistsCount());
			bean.setHeroHurt(fightStatistic.getHeroHurt());
			bean.setMonsterHurt(fightStatistic.getMonsterHurt());
			bean.setReceiveHurt(fightStatistic.getReceiveHurt());
			builder.addRankBoard(bean);
		}
		return builder.build();
	}

	/** 更新部分战斗数据 */
	public static PBStatisticUpdateResp buildPBStatisticUpdateResp(List<FightStatistic> fightStatistics) {
		PBStatisticUpdateResp.Builder builder = PBStatisticUpdateResp.newBuilder();
		for (FightStatistic fightStatistic : fightStatistics) {
			StatisticBean.Builder bean = StatisticBean.newBuilder();
			bean.setPhId(fightStatistic.getSelf().getPhyId());
			bean.setHeroHurt(fightStatistic.getHeroHurt());
			bean.setMonsterHurt(fightStatistic.getMonsterHurt());
			bean.setReceiveHurt(fightStatistic.getReceiveHurt());
			builder.addStatisticBeans(bean);
		}
		return builder.build();
	}

	public static PBGameOverResp buildPBGameOverResp(BaseGame game) {
		PBGameOverResp.Builder builder = PBGameOverResp.newBuilder();
		Set<Integer> campSet = new HashSet<>();
		for (Hero hero : game.getLivingManager().getLivings(Hero.class)) {
			if (!campSet.contains(hero.getCampType())) {
				campSet.add(hero.getCampType());
				CampBean.Builder campBean = CampBean.newBuilder();
				campBean.setCampType(hero.getCampType());
				campBean.setScore(game.getFightDataManager().getCampScore(hero.getCampType()));
				builder.addCampBeans(campBean);
			}

			PlayerSettleBean.Builder settleBean = PlayerSettleBean.newBuilder();
			settleBean.setPhId(hero.getPhyId());
			builder.addSettleBeans(settleBean);
		}
		return builder.build();
	}

	public static PBTalentUpLevelListResp builderTalentUpLevelListResp(Collection<Integer> talentIds) {
		PBTalentUpLevelListResp.Builder builder = PBTalentUpLevelListResp.newBuilder();
		for (Integer talentId : talentIds) {
			builder.addTalentId(talentId);
		}
		return builder.build();
	}

	public static PBKillAchieveResp builderKillAchieveResp(FightStatistic killStatistic) {
		PBKillAchieveResp.Builder builder = PBKillAchieveResp.newBuilder();
		builder.setSequentKillNum(killStatistic.getSequentKillNum());
		builder.setTimeSequentKillNum(killStatistic.getTimeSequentKillNum());
		return builder.build();
	}

}
