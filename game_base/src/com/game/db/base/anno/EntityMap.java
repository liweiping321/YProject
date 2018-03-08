
package com.game.db.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityMap {
	/**
	 * 实体对象映射的表明
	 */
	String table();
	/**
	 * 数据源
	 * @return
	 */
	String ds() default "";
	
	

}
