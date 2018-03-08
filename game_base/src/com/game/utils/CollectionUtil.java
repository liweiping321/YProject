package com.game.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.util.TypeUtils;
/**
 * 
 * @author lip.li
 *
 */
public class CollectionUtil {
	
	 public static <T>   List<T> convertList(List<String> values,Class<T> castType){
		 List<T> list=new ArrayList<>();
		 if(CollectionUtils.isNotEmpty(values)){
			 for(String value:values){
				 list.add(TypeUtils.castToJavaBean(value, castType));
			 }
		 }
		 return list;
	 }
	 
	 public static <T>   Set<T> convertSet(List<String> values,Class<T> castType){
		 Set<T> set=new LinkedHashSet<>();
		 if(CollectionUtils.isNotEmpty(values)){
			 for(String value:values){
				 set.add(TypeUtils.castToJavaBean(value, castType));
			 }
		 }
		 return set;
	 }

	public static <K,V> Map<K,V> convertMap(Map<String, String> maps, Class<K> keyType,
			Class<V> valueType) {
		Map<K,V> returnMap=new HashMap<>();
		if(maps!=null&&maps.size()>0){
			for(Map.Entry<String, String> entry:maps.entrySet()){
				K key=TypeUtils.castToJavaBean(entry.getKey(), keyType);
				V value=TypeUtils.castToJavaBean(entry.getValue(), valueType);
				returnMap.put(key, value);
			}
		}
		return returnMap;
	}

	public static <T> T randomGetFromCollection(Collection<T> collections) {
		 
		if (collections.size() == 0) {
			return null;
		}

		int endIndex = ProbabilityUtil.randomGetInt(1, collections.size());
		int i = 0;
		for (T t : collections) {
			i++;
			if (i == endIndex) {
				return t;
			}
		}
		return null;
	}
}
