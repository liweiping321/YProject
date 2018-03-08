package com.game.cfg;

import java.util.LinkedHashMap;
import java.util.Map;

import com.game.cfg.BaseCfgEntiyProvider;
 

/**
 * 配置数据实体提供者集合
 * 
 * @author lip.li
 * 
 */
public class CfgEntityProviders {

	private Map<String, BaseCfgEntiyProvider<?, ?>> cfgProviderMap = new LinkedHashMap<>();

	protected void register(BaseCfgEntiyProvider<?, ?> provider) {
		cfgProviderMap.put(provider.getName(), provider);
	}

	public void init() {
		
		registers();
		
		loadData();
	}
	
	public void  registers(){};
	
	public void loadData() {
		for(BaseCfgEntiyProvider<?,?> provider:cfgProviderMap.values()){
			provider.reLoad();
		}
		
		for(BaseCfgEntiyProvider<?,?> provider:cfgProviderMap.values()){
			provider.loadEnd();
			
			provider.printLoadOver();
		}
	}
	
	public void loadData(String names[]) {
		for (String table : names) {

			BaseCfgEntiyProvider<?, ?> provider = cfgProviderMap.get(table);
			if (null != provider) {
				provider.reLoad();
			}
		}

		for (String table : names) {

			BaseCfgEntiyProvider<?, ?> provider = cfgProviderMap.get(table);
			if (null != provider) {
				provider.loadEnd();
				
				provider.printLoadOver();
			}
		}
	}
 
}
