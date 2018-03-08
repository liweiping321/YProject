package com.road.fire.cfg;

import com.game.cfg.CfgEntityProviders;
import com.road.fire.cfg.provider.FightingTypeCfgProvider;
import com.road.fire.cfg.provider.NameCfgProvider;
 

/**
 * 匹配服配置数据
 * 
 * @author lip.li
 * 
 */
public class MatchCfgProviders  extends CfgEntityProviders{
 
	public void  registers() {
		 
		register(FightingTypeCfgProvider.getInstance());
		 
		register(NameCfgProvider.getInstance());

	}
	
	private static final MatchCfgProviders instance=new MatchCfgProviders();

	public static MatchCfgProviders getInstance() {
		return instance;
	}
	
	
 
}
