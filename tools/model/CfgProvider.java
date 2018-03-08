package com.road.fire.cfg.provider;

import	com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.${className};

/**
 *@author 工具生成
 *@date ${date}
 */
public class ${className}Provider extends BaseCfgEntiyProvider<${keyType},${className}> {
	
 
	@Override
	public void afterReload(${className} value) {
		logger.info(value);
	}
	
	public Class<?> getCfgClass(){
		
		return ${className}.class;
	}

	private static final ${className}Provider instance=new ${className}Provider();

	public static ${className}Provider getInstance() {
		return instance;
	}
	
	
}
