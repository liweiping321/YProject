/**
 * Date: Mar 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.game.utils;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.util.TypeUtils;
import com.game.consts.Splitable;
import com.game.type.Configuration;
import com.google.common.base.Splitter;

/**
 * property配置工具类
 * 
 * @author jason.lin
 */
public class PropertyConfigUtil {

	private static final Logger logger =LogManager.getLogger(PropertyConfigUtil.class);
	
	/**
	 * 初始化所有配置
	 * @param pack
	 */
	public static void init(Package pack)throws Exception {
		 init(pack.getName());
	}
	
	/**
	 * 初始化所有配置
	 * @param pack
	 */
	public static void init(String pack)throws Exception {
		List<Class<?>> classSet = ClassUtil.getClasses(pack);
		if (DataUtil.isEmpty(classSet)) {
			LogUtils.error("Attention !!! no config class: " + pack.toString());
			return;
		}

		// 遍历并初始化配置
		for (Class<?> clazz : classSet) {
				// 过滤非配置类
				Configuration config = clazz.getAnnotation(Configuration.class);
				if (config == null) {
					continue;
				}

				// 初始化配置
				String path = config.config();

				// 初始化配置
				config(path, clazz);
			 

		}
	}

	/**
	 * 初始化单个配置
	 * 
	 * @param path
	 * @param obj
	 */
	private static void config(String path, Class<?> clazz)throws Exception {
	
		try(FileInputStream fis =  new FileInputStream(path);) {
			Properties properties = new Properties();
			properties.load(new FileInputStream(path));
			
			toBeanObj(clazz, properties); 
		} 
	}

	public static void toBeanObj( Class<?> clazz,
			Map<?,?> properties) throws InstantiationException,
			IllegalAccessException, Exception {
		// 实例化对象
		Object obj = clazz.newInstance();
		
		Field[] fields=clazz.getDeclaredFields();
		if(!ArrayUtils.isEmpty(fields)){
			for(Field field:fields){
				field.setAccessible(true);
			}
		}
		for(Field field:fields){
			String key=field.getName();
			if(!properties.containsKey(key)){
				logger.error("{} file   not config property {}",key);
			}
		
			String value=StringUtils.trim((String)properties.get(key));
			
			Object valueObj=getJavaBeanValue(field, value);
			
			field.set(obj, valueObj);
		}
	}

	public static Object getJavaBeanValue(Field field, String value)throws Exception {
		Class<?> fileldType = field.getType();
		Object valueObj = null;
		if (fileldType == List.class || fileldType == Map.class|| fileldType == Set.class) {
			Type type = field.getGenericType();
			Type[] actualTypes = null;
			if (type instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType) type;
				actualTypes = pType.getActualTypeArguments();
			}
			if (fileldType == List.class) {
				List<String> strings = Splitter.on(Splitable.DOUHAO).omitEmptyStrings()
						.splitToList(value);
				valueObj = CollectionUtil.convertList(strings,(Class<?>) actualTypes[0]);
			} else if (fileldType == Set.class) {
				List<String> strings = Splitter.on(Splitable.DOUHAO).omitEmptyStrings().splitToList(value);
				valueObj = CollectionUtil.convertSet(strings,(Class<?>) actualTypes[0]);
			} else if (fileldType == Map.class) {
				Map<String, String> maps = Splitter.on(Splitable.DOUHAO).withKeyValueSeparator(Splitable.MAOHAO).split(value);
				valueObj = CollectionUtil.convertMap(maps,
						(Class<?>) actualTypes[0], (Class<?>) actualTypes[1]);
			}
		} else {
			valueObj = TypeUtils.castToJavaBean(value, fileldType);
		
		}

		return valueObj;
	}
 
}
