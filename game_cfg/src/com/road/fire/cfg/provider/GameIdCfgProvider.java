package com.road.fire.cfg.provider;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.GameIdCfg;

/**
 *@author 工具生成
 *@date 2017-04-25 02:45:24 
 */
public class GameIdCfgProvider extends BaseCfgEntiyProvider<Integer,GameIdCfg> {
	
	private GameIdCfg idCfg;
	@Override
	public void afterReload(GameIdCfg value) {
		idCfg=value;
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return GameIdCfg.class;
	}

	private static final GameIdCfgProvider instance=new GameIdCfgProvider();

	public static GameIdCfgProvider getInstance() {
		return instance;
	}

	public GameIdCfg getCurrentGameId() {
	 
		return idCfg;
	}
	
	
}
