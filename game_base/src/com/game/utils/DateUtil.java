package com.game.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateUtil {
	/** 一天的毫秒表示 */
	public static final long DAY_MILLIONS = 86400000;
	/** 一小时的毫秒表示 */
	public static final long HOUR_MILLIONS = 3600000;
	/** 一分钟的毫秒表示 */
	public static final long MINUTE_MILLIONS = 60000;

	/** 一分钟的秒表示,一小时分钟数 */
	public static final int MINUTE = 60;
	/** 一秒的毫秒表示 */
	public static final int MILLIONS = 1000;

	public static final SimpleDateFormat YMDHMS_FORMAT = TimeUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat YMDHMSS_FORMAT = TimeUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	public static final SimpleDateFormat YMD_FORMAT = TimeUtil.getSimpleDateFormat("yyyy-MM-dd");

	private static final int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };

	public static boolean isSameWeek(int year, int week, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return ((year == cal.get(Calendar.YEAR)) && (week == cal.get(Calendar.WEEK_OF_YEAR)));
	}

	public static boolean isSameWeek(Date time, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return isSameWeek(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), firstDayOfWeek);
	}

	/**
	 * 判断时间是否是本周内
	 * 
	 * @param time
	 * @return
	 */
	public static final boolean isThisWeek(Date time) {
		if (time == null) {
			return false;
		}
		Calendar cal_mon = Calendar.getInstance();
		cal_mon.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		Calendar cal_sun = Calendar.getInstance();
		cal_sun.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal_sun.add(Calendar.WEEK_OF_YEAR, 1);

		return betweenStartAndEnd(cal_mon.getTime(), cal_sun.getTime(), time);
	}

	/**
	 * 判断时间是否是上周内
	 * 
	 * @param time
	 * @return
	 */
	public static final boolean isLastWeek(Date time) {
		if (time == null) {
			return false;
		}
		Calendar cal_mon = Calendar.getInstance();
		cal_mon.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal_mon.add(Calendar.WEEK_OF_YEAR, -1);

		Calendar cal_sun = Calendar.getInstance();
		cal_sun.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return betweenStartAndEnd(cal_mon.getTime(), cal_sun.getTime(), time);
	}

	public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		cal.setFirstDayOfWeek(firstDayOfWeek);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		if (day == firstDayOfWeek) {
			day = 0;
		} else if (day < firstDayOfWeek) {
			day += 7 - firstDayOfWeek;
		} else if (day > firstDayOfWeek) {
			day -= firstDayOfWeek;
		}

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.DAY_OF_MONTH, -day);
		return cal.getTime();
	}

	/**
	 * 以自然时间即0点区别
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		if (date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date end = cal.getTime();
		cal.add(Calendar.MILLISECOND, -1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date start = cal.getTime();
		return ((date.after(start)) && (date.before(end)));
	}

	public static Date string2Date(String dateString, String datePattern) {
		if ((dateString == null) || (dateString.trim().isEmpty())) {
			return null;
		}

		return TimeUtil.getDate(dateString, datePattern);
	}

	public static String date2String(Date theDate, String datePattern) {
		if (theDate == null) {
			return "";
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.format(theDate);
		} catch (Exception ignored) {
		}
		return "";
	}

	public static long toMillisSecond(long... seconds) {
		long millis = 0L;
		if ((seconds != null) && (seconds.length > 0)) {
			for (long time : seconds) {
				millis += time * 1000L;
			}
		}
		return millis;
	}

	public static int toSecond(long millis) {
		long second = millis / 1000L;
		return (int) second;
	}

	public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(Calendar.DAY_OF_MONTH, addDays);

		if ((hour >= 0) && (hour <= 24)) {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		if ((minute >= 0) && (minute <= 60)) {
			cal.set(Calendar.MINUTE, minute);
		}
		if ((second >= 0) && (second <= 60)) {
			cal.set(Calendar.SECOND, second);
		}
		return cal.getTime();
	}

	public static Date add(Date theDate, int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(Calendar.HOUR_OF_DAY, addHours);
		cal.add(Calendar.MINUTE, addMinutes);
		cal.add(Calendar.SECOND, addSecond);

		return cal.getTime();
	}

	public static int dayOfWeek(Date theDate) {
		if (theDate == null) {
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据日期返回周几 如周三返回 3 周日返回 7
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		if (isToday(date)) {
			return weekDays[dayOfWeek(date) - 1];
		} else {
			return weekDays[dayOfWeek(date) - 2];
		}
	}

	/**
	 * 本日期的零点时刻
	 * 
	 * @param theDate
	 * @return
	 */
	public static Date getDate0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
				.getTime();
	}

	/**
	 * 本日期的明天零点时刻
	 * 
	 * @param theDate
	 * @return
	 */
	public static Date getNextDay0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
				.getTime();
	}

	public static Date getThisDay2359PM(Date theDate) {
		if (theDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date date = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH)).getTime();
		return new Date(date.getTime() - 1000L);
	}

	/**
	 * 获得结束时间是从开始时间的第几天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calc2DateTDOADays(Date startDate, Date endDate) {
		if ((startDate == null) || (endDate == null)) {
			return 0;
		}
		Date startDate0AM = getDate0AM(startDate);
		Date endDate0AM = getDate0AM(endDate);
		long v1 = startDate0AM.getTime() - endDate0AM.getTime();
		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(DAY_MILLIONS);
		return Math.abs((int) bd1.divide(bd2, 0, 0).doubleValue()) + 1;
	}

	public static Date getNextMonday(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate0AM(date));
		cal.set(Calendar.DAY_OF_WEEK, 2);

		Calendar nextMondayCal = Calendar.getInstance();
		nextMondayCal.setTimeInMillis(cal.getTimeInMillis() + 604800000L);
		return nextMondayCal.getTime();
	}

	public static Date add(int addDay, boolean to0AM) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, addDay);
		Date time = calendar.getTime();
		return ((to0AM) ? getDate0AM(time) : time);
	}

	/**
	 * 返回当前时间的秒数，单位：秒
	 * 
	 * @return
	 */
	public static int getCurrentSecond() {
		return toSecond(System.currentTimeMillis());
	}

	/**
	 * 返回当前毫秒时间，单位：毫秒
	 * 
	 * @return
	 */
	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 比较当前的date是否在[start, end]之间
	 * 
	 * @param start
	 * @param end
	 * @param date
	 * @return
	 */
	public static boolean betweenStartAndEnd(Date start, Date end, Date date) {
		long startMin = start.getTime();
		long endMin = end.getTime();
		long currMin = date.getTime();
		return startMin <= currMin && endMin >= currMin;
	}

	/**
	 * 获取当前时间的月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据日期返回 yyyy-MM-dd 格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static final String getCurrentDateString() {
		return YMD_FORMAT.format(Calendar.getInstance().getTime());
	}

	/**
	 * 根据当前日期返回 yyyy-MM-dd HH:mm:ss 格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static final String getCurrentDateTimeString() {
		return YMDHMS_FORMAT.format(Calendar.getInstance().getTime());
	}

	/**
	 * 根据日期返回 yyyy-MM-dd HH:mm:ss 格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static final String getCurrentDateTimeString(long currDate) {
		return YMDHMS_FORMAT.format(currDate);
	}

	/**
	 * 根据日期返回 yyyy-MM-dd HH:mm:ss SSS 格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static final String getCurrentDateTimeStringMi() {
		return YMDHMSS_FORMAT.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获得指定时间、指定天数后的 时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 获得当天任意时间
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static final Date getDate(int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得当天任意时间
	 * 
	 * @param hh
	 *            12
	 * @return
	 */
	public static final Date getDateByHours(int hh) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hh);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得当前小时数 (24小时制)
	 * 
	 * @return
	 */
	public static final int getHours() {
		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 是否是指定整点时间(小时相等分为0)
	 * 
	 * @param hours
	 * @return
	 */
	public static final boolean isHours(int hours) {
		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(Calendar.HOUR_OF_DAY) == hours && rightNow.get(Calendar.MINUTE) == 0;
	}

	/**
	 * 是否是整点时间 分钟为0
	 * 
	 * @return
	 */
	public static final boolean isPoint() {
		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(Calendar.MINUTE) == 0;
	}

	/**
	 * 取上一个指定的时间
	 * 
	 * @param hour
	 * @return
	 */
	public static Date getLastTime(int hour) {
		// 当前时间大于指定时间，则上一个时间为今天的时间，否则为昨天的时间
		if (getHours() >= hour) {
			return getDateByHours(hour);
		} else {
			return DateUtil.changeDateTime(DateUtil.getCurrentDate(), -1, hour, 0, 0);
		}
	}

	/**
	 * 获取当天的星期几
	 * 
	 * @return
	 */
	public static int getTodayWeek(Calendar now) {
		int week = now.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			return 7;
		}
		return week - 1;
	}

	/**
	 * 获取当天的星期几
	 * 
	 * @return
	 */
	public static int getTodayWeek() {
		return getTodayWeek(Calendar.getInstance());
	}

}
