package com.road.fire.cfg;

import com.game.cfg.CfgEntityProviders;
import com.road.fire.cfg.provider.*;


/**
 * 战斗服配置数据
 * 
 * @author lip.li
 * 
 */
public class FightingCfgProviders  extends CfgEntityProviders{
 
	public void  registers() {
		register(ConfigsCfgProvider.getInstance());
		register(FightingTypeCfgProvider.getInstance());
		register(BornPositionCfgProvider.getInstance());
		register(EffectCfgProvider.getInstance());
		register(BuffCfgProvider.getInstance());
		register(HeroCfgProvider.getInstance());
		register(HeroLevelCfgProvider.getInstance());
		register(MonsterCfgProvider.getInstance());
		register(SceneCfgProvider.getInstance());
		register(SceneMonsterCfgProvider.getInstance());
		register(SkillCfgProvider.getInstance());
		register(WeaponCfgProvider.getInstance());
		register(DropBoxCfgProvider.getInstance());
		register(NameCfgProvider.getInstance());
		register(SceneDropCfgProvider.getInstance());
		register(DropTemplateCfgProvider.getInstance());
		register(LevelExpCfgProvider.getInstance());
		register(TalentCfgProvider.getInstance());
		

	}
	
	private static final FightingCfgProviders instance=new FightingCfgProviders();

	public static FightingCfgProviders getInstance() {
		return instance;
	}
	
	
 
}
