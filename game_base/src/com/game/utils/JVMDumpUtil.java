package com.game.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;



/**
 * 
 * @author weiping.li
 * 
 */
public class JVMDumpUtil {
	public static void dumpThreads() {
		long now = System.currentTimeMillis();
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] in = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), Integer.MAX_VALUE);
		String st = System.getProperty("line.separator");
		for (ThreadInfo info : in) {
			StringBuilder infos = new StringBuilder();
			StackTraceElement[] el = info.getStackTrace();
			infos.append(info.getThreadName() + "\n");
			for (StackTraceElement e : el) {
				infos.append(
						"FileName：" + e.getFileName() + "|ClassName：" + e.getClassName() + "|MethodName："
								+ e.getMethodName() + "|LineNumber：" + e.getLineNumber()).append(st);
			}
			LogUtils.info(infos.toString());
		}
		LogUtils.info("dump线程累计消耗时间:" + (System.currentTimeMillis() - now));
	}
}
