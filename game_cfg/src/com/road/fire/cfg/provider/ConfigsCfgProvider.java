package com.road.fire.cfg.provider;

import java.util.HashMap;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
import com.game.cfg.entity.ConfigsCfg;
import com.game.utils.PropertyConfigUtil;

/**
 *@author 工具生成
 *@date 2017-05-22 05:38:41 
 */
public class ConfigsCfgProvider extends BaseCfgEntiyProvider<String,ConfigsCfg> {

	private Map<String, String> map = new HashMap<>();
	 
	@Override
	public void afterReload(ConfigsCfg value) {
	}

	@Override
	public void loadEnd()
	{
		HashMap<String, String> hashMap = new HashMap<>();
		for (ConfigsCfg cfg : instance.getConfigDatas())
		{
			hashMap.put(cfg.getName(), cfg.getValue());
		};
		map = hashMap;

	   try {
		   PropertyConfigUtil.toBeanObj(ConfigsCfg.class, hashMap);
	   } catch (Exception e) {
		   logger.error(e.getMessage(),e);
	   }
	}

	public Class<?> getCfgClass(){
		
		return ConfigsCfg.class;
	}

	private static final ConfigsCfgProvider instance=new ConfigsCfgProvider();

	public static ConfigsCfgProvider getInstance() {
		return instance;
	}
 
 
}
