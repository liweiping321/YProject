package com.road.fire.fighting.target;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jianpeng.zhang
 * @since 2017/5/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FindTargetStrategy {
	/**
	 * 指明哪种策略
	 */
	FindTargetCommand command();

	/**
	 * 策略描述
	 */
	String desc();
}
