
package com.game.db.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.game.db.base.handle.DefaultHandler;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyMap {
	/**
	 * 属性对应的数据库表列名
	 */
	public String column() default "";

	/**
	 * 属性处理器
	 */
	public Class<?> handler() default DefaultHandler.class;

	/** List,Set,Map元素分割符 */
	public String splitRex() default ";";

	/** Map key,Value分割符 */
	public String splitRex1() default ",";

	/**
	 * 是否是数据库自动生成key
	 */
	boolean generatedKey() default false;

	/** 是否是主键(不支持符合主键) */
	boolean primarykey() default false;
}
