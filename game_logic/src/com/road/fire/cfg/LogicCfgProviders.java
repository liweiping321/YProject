package com.road.fire.cfg;

import com.game.cfg.CfgEntityProviders;
import com.road.fire.cfg.provider.FightingTypeCfgProvider;
import com.road.fire.cfg.provider.GameIdCfgProvider;
import com.road.fire.cfg.provider.HeroCfgProvider;
import com.road.fire.cfg.provider.NameCfgProvider;
 

/**
 *逻辑服配置数据
 * 
 * @author lip.li
 * 
 */
public class LogicCfgProviders  extends CfgEntityProviders{
 
	public void  registers() {
		register(GameIdCfgProvider.getInstance());
		register(FightingTypeCfgProvider.getInstance());
		register(HeroCfgProvider.getInstance());
		 
		register(NameCfgProvider.getInstance());

	}
	
	private static final LogicCfgProviders instance=new LogicCfgProviders();

	public static LogicCfgProviders getInstance() {
		return instance;
	}
	
	
 
}
