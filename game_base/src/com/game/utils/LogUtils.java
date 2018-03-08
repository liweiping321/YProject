package com.game.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 日志工具
 * 
 * @author jason.lin
 *
 */
public class LogUtils {
	 
 	
 	private static final Logger logger = LogManager.getLogger(LogUtils.class);
	private static final String thisClassName = LogUtils.class.getName();
	private static final String msgSep = " ";// \r\n
	
	 

	public static void debug(String msg) {
		Object message = getStackMsg(msg);
		logger.debug(message);
	}

	public static void debug(String msg, Throwable t) {
	 
		logger.debug(msg, t);
	}

	public static void info(String msg) {
		Object message = getStackMsg(msg);
		logger.info(message);
	}

	public static void info(String msg, Throwable t) {
		 
		logger.info(msg, t);
	}

	public static void warn(String msg) {
		Object message = getStackMsg(msg);
		logger.warn(message);
	}

	public static void warn(String msg, Throwable t) {
		 
		logger.warn(msg, t);
	}

	public static void error(String msg) {
		Object message = getStackMsg(msg);
		logger.error(message);
	}

	public static void error(String msg, Throwable t) {
	  logger.error(msg, t);
		 
	}


	private static Object getStackMsg(Object msg) {
		if(msg == null || msg.toString().trim().length() == 0){
			return msg;
		}

		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		if (ste == null) {
			return "";
		}

		boolean srcFlag = false;
		for (int i = 0; i < ste.length; i++) {
			StackTraceElement s = ste[i];
			// 如果上一行堆栈代码是本类的堆栈，则该行代码则为源代码的最原始堆栈。
			if (srcFlag) {
				return s == null ? "" : toString(s) + msgSep + msg;
			}
			// 定位本类的堆栈
			if (thisClassName.equals(s.getClassName())) {
				srcFlag = true;
				i++;
			}
		}
		return "";
	}

	private static String toString(StackTraceElement element) {
		return element.getClassName() + "." + element.getMethodName()
				+ (element.isNativeMethod() ? "(Native Method)"
				: (element.getFileName() != null && element.getLineNumber() >= 0 ? "(" + element.getFileName() + ":" + element.getLineNumber() + ")"
				: (element.getFileName() != null ? "(" + element.getFileName() + ")" : "(Unknown Source)")));
	}

	 
}
