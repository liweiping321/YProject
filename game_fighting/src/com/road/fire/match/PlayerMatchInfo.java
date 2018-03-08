package com.road.fire.match;

import com.road.fire.consts.PlayerStatus;
import com.road.fire.entity.cfg.FightingTypeCfg;
import com.road.fire.entity.cfg.HeroCfg;
import com.road.fire.entity.cfg.WeaponCfg;
import com.road.fire.player.PlayerInfo;

/**
 * @author jianpeng.zhang
 * @since 2017/5/3.
 */
public class PlayerMatchInfo implements Comparable<PlayerMatchInfo> {
	
	private PlayerInfo playerInfo;

	private FightingTypeCfg fightingTypeCfg;

	private int elo;
	
	private HeroCfg heroCfg;

	private WeaponCfg weaponCfg;
	
	

	public PlayerMatchInfo(PlayerInfo playerInfo,
			FightingTypeCfg fightingTypeCfg, int elo, HeroCfg heroCfg, WeaponCfg weaponCfg) {
		super();
		this.playerInfo = playerInfo;
		this.fightingTypeCfg = fightingTypeCfg;
		this.elo = elo;
		this.heroCfg = heroCfg;
		this.weaponCfg = weaponCfg;
		init();
	}

	private void init() {
		 playerInfo.setMatchInfo(this);
		 playerInfo.setStatus(PlayerStatus.matching);
		
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public FightingTypeCfg getFightingTypeCfg() {
		return fightingTypeCfg;
	}

	public void setFightingTypeCfg(FightingTypeCfg fightingTypeCfg) {
		this.fightingTypeCfg = fightingTypeCfg;
	}

	public int getElo() {
		return elo;
	}

	public void setElo(int elo) {
		this.elo = elo;
	}
	public int getFightingType(){
		return fightingTypeCfg.getId();
	}
	

	public HeroCfg getHeroCfg() {
		return heroCfg;
	}

	public void setHeroCfg(HeroCfg heroCfg) {
		this.heroCfg = heroCfg;
	}

	public WeaponCfg getWeaponCfg()
	{
		return weaponCfg;
	}

	public void setWeaponCfg(WeaponCfg weaponCfg)
	{
		this.weaponCfg = weaponCfg;
	}

	@Override
	public int compareTo(PlayerMatchInfo o) {
	 
		return this.elo-o.elo;
	}
}
