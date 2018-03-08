package com.road.fire.fighting.phy.living;

import com.game.cfg.entity.ConfigsCfg;
import com.game.cfg.entity.PointCfg;
import com.game.consts.GameConsts;
import com.game.property.PropertyConstants;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBLivingInfoBean;
import com.google.protobuf.GeneratedMessage;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.HeroLevelCfgProvider;
import com.road.fire.cfg.provider.LevelExpCfgProvider;
import com.road.fire.entity.cfg.HeroCfg;
import com.road.fire.entity.cfg.HeroLevelCfg;
import com.road.fire.entity.cfg.LevelExpCfg;
import com.road.fire.entity.cfg.WeaponCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.ai.fsm.hero.HeroStateMachine;
import com.road.fire.fighting.ai.move.HeroMoveController;
import com.road.fire.fighting.consts.PhysicsType;
import com.road.fire.fighting.property.HeroPropertyManger;
import com.road.fire.fighting.skill.container.HeroSkillContainer;
import com.road.fire.fighting.talent.TalentManager;
import com.road.fire.player.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Living<Hero> {

	private HeroCfg heroCfg;

	private final PlayerInfo playerInfo;

	private WeaponCfg weaponCfg;

	private HeroLevelCfg levelCfg;

	private LevelExpCfg expCfg;

	private int loadingProcess;

	private boolean roboot;
 
	private TalentManager talentManager;
	
	public Hero(int x, int y,BaseGame game,int campType,HeroCfg heroCfg,PlayerInfo playerInfo) {
		super(x, y,game,campType);
		this.heroCfg=heroCfg;
		this.playerInfo=playerInfo;
		
	}
 
	
	public void onInit(){
		this.levelCfg=HeroLevelCfgProvider.getInstance().getHeroLeveLCfg(heroCfg.getHeroId(),heroCfg.getLevel());
		this.expCfg=LevelExpCfgProvider.getInstance().getLevelExpCfg(GameConsts.LevelExpHero,heroCfg.getLevel());
		
		skillContainer=new HeroSkillContainer(this, game);
		propertyManager=new HeroPropertyManger(this);
		stateMachine=new HeroStateMachine(this);
		moveController=new HeroMoveController(this);
		talentManager=new TalentManager(this);
 
		playerInfo.setHero(this);
		playerInfo.setBaseGame(game);

		propertyManager.initializePropertyValue();
	}

	@Override
	public int getPhType() {

		return PhysicsType.HERO.getValue();
	}

	@Override
	public int getDamageRadius() {
		return heroCfg.getDamageRadius();
	}

	@Override
	public void sendMsg(int code) {
		if (playerInfo != null) {
			playerInfo.sendMsg(code);
		}

	}

	@Override
	public void sendMsg(int code, GeneratedMessage msg) {

		if (playerInfo != null) {
			playerInfo.sendMsg(code, msg);
		}

	}

	@Override
	public void onDead() {
		onDeadDrop();

		Living<?> killer = game.getFightDataManager().addHeroDead(this);
		game.getFightDataManager().sendKillAchieve(killer);
		List<Living<?>> livings = game.getFightDataManager().addAssistsTime(killer, this);
		if (killer != null)
			livings.add(killer);
		livings.add(this);
		broadcastMsg(MsgCode.RankBoardResp,
				FtMsgUtil.buildPBRankBoardResp(livings, game.getFightDataManager().getRankBoard()));
	}

	@Override
	public void onDeadDrop() {
		dropPoints = new ArrayList<>();

		dropPoints.add(new PointCfg(getX(), getY()));

		onDeadDrop(ConfigsCfg.getHeroDropIds());
	}

	public int getLoadingProcess() {
		return loadingProcess;
	}

	public void setLoadingProcess(int loadingProcess) {
		this.loadingProcess = loadingProcess;
	}

	public void executeHandlers() {
		if (playerInfo != null) {
			playerInfo.executeRequest();
		}

	}

	public HeroCfg getHeroCfg() {
		return heroCfg;
	}

	public void setHeroCfg(HeroCfg heroCfg) {
		this.heroCfg = heroCfg;
	}

	public WeaponCfg getWeaponCfg() {
		return weaponCfg;
	}

	public void setWeaponCfg(WeaponCfg weaponCfg) {
		this.weaponCfg = weaponCfg;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public long getPlayerId() {
		return playerInfo.getPlayerData().getUserID();
	}

	public PBLivingInfoBean getLivingBean() {
		return FtMsgUtil.builderHeroLivingBean(this);
	}

	public boolean isRoboot() {
		return roboot;
	}

	public void setRoboot(boolean roboot) {
		this.roboot = roboot;
	}

	@Override
	public String getName() {

		return heroCfg.getName();
	}

	public String getPlayerName() {
		return playerInfo.getPlayerName();
	}

	@Override
	public int getTemplateId() {

		return heroCfg.getTemplateId();
	}

	@Override
	public void update(long now) {
		talentManager.update(now);
		
		super.update(now);
	 
	}
	@Override
	public void addExp(int exp) {
		if (exp <= 0) {
			return;
		}

		if (levelCfg.getLevel() >= getHeroMaxLevel()) {
			propertyManager.addIntValue(PropertyConstants.CurrExp, exp);
			propertyManager.sendPropertyValue(true, PropertyConstants.CurrExp);
		} else {

			int currExp = getCurrExp();
			int nextLevelExp = expCfg.getNextLeveExp();
			if (exp + currExp < nextLevelExp) {
				propertyManager.addIntValue(PropertyConstants.CurrExp, exp);
				propertyManager.sendPropertyValue(true, PropertyConstants.CurrExp);
			} else {
				// 升级更新属性
				upLevel();

				int addExp = exp + currExp - nextLevelExp;
				addExp(addExp);
			}

			propertyManager.sendUpdatePropertyValue(true);
		}
	}

	private void upLevel() {
		HeroLevelCfg oldLevel = levelCfg;

		this.levelCfg = HeroLevelCfgProvider.getInstance().getHeroLeveLCfg(heroCfg.getHeroId(), levelCfg.getLevel() + 1);
		this.expCfg = LevelExpCfgProvider.getInstance().getLevelExpCfg(GameConsts.LevelExpHero, levelCfg.getLevel());
		((HeroPropertyManger) propertyManager).upLevel(oldLevel, levelCfg);

		if(expCfg.getTalentPoint()>0){
			talentManager.addTalentPoint(expCfg.getTalentPoint());
		}
		
	}

	public HeroLevelCfg getLevelCfg() {
		return levelCfg;
	}

	public void setLevelCfg(HeroLevelCfg levelCfg) {
		this.levelCfg = levelCfg;
	}

	public LevelExpCfg getExpCfg() {
		return expCfg;
	}

	public void setExpCfg(LevelExpCfg expCfg) {
		this.expCfg = expCfg;
	}

	public int getHeroMaxLevel() {
		return game.getFightingType().getHeroMaxLevel();
	}

	public TalentManager getTalentManager() {
		return talentManager;
	}

	public void setTalentManager(TalentManager talentManager) {
		this.talentManager = talentManager;
	}

}
