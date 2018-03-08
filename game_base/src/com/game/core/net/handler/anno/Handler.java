 
package com.game.core.net.handler.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 /**
  * 
  * @author  weiping.li
  *
  */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
	/**true 处理器需要授权验证检查，false不需要进行授权验证*/
	boolean needAuth() default true;
	 /** true 处理器使用游戏循环线程进行处理(顺序执行)
	  *  false 处理器使用线程池中的线程进行处理
	  *  建议读取数据和非读取数据但操作比较耗时的设置为false
	 */
	boolean singleThread() default true;
	/**消息发送的合法间隔时间**/
	int intervalTime() default 200;
	
	/**
	 * 
	 * 指令号
	 */
	int code() ;
	 
	
}
