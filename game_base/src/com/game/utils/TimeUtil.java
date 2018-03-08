package com.game.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeUtil {
	private static final Logger LOGGER = LogManager.getLogger(TimeUtil.class.getName());

	private static final Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static final Map<String, Date> dateCache = new HashMap<String, Date>();
	/** 一天的毫秒数 */
	public static final long MILLISECONDS_ONE_DAY = 86400000;

	/** 一小时的毫秒数 */
	public static final long MILLISECONDS_ONE_HOUR = 3600000;

	/** 一分钟的毫秒数 */
	public static final long MILLISECONDS_ONE_MINUTE = 60000;

	/** 一秒钟的毫秒数 */
	public static final long MILLISECONDS_ONE_SEC = 1000;

	public static final SimpleDateFormat getSimpleDateFormat(final String pattern) {
		ThreadLocal<SimpleDateFormat> sdf = sdfMap.get(pattern);
		if (sdf == null) {
			sdf = new ThreadLocal<SimpleDateFormat>() {
				@Override
				protected SimpleDateFormat initialValue() {
					return new SimpleDateFormat(pattern);
				}
			};
			sdfMap.put(pattern, sdf);
		}
		return sdf.get();
	}

	/**
	 * 获取系统距1970年1月1日总毫秒
	 * 
	 * @return
	 */
	public static long getSysCurTimeMillis() {
		return System.currentTimeMillis();
	}

	public static long getSysnanoTime() {
		return System.nanoTime();
	}

	/**
	 * 获取系统距1970年1月1日总秒
	 * 
	 * @return
	 */
	public static long getSysCurSeconds() {
		return getCalendar().getTimeInMillis() / MILLISECONDS_ONE_SEC;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static Timestamp getSysteCurTime() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return ts;
	}

	/**
	 * 获取指定日期距1970年1月1日总秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateToSeconds(Date date) {
		return System.currentTimeMillis() / MILLISECONDS_ONE_SEC;
	}

	/**
	 * 指定的毫秒long值转成Timestamp类型
	 * 
	 * @param value
	 * @return
	 */
	public static java.sql.Timestamp getMillisToDate(long value) {
		return new java.sql.Timestamp(value);
	}

	/**
	 * 当前系统时间增加值
	 * 
	 * @param type
	 * @param value
	 *            限定值
	 *            ：Calendar.DATE，Calendar.HOUR，Calendar.MINUTE，Calendar.SECOND
	 *            ，Calendar.MILLISECOND
	 * @return
	 */
	public static java.util.Date addSystemCurTime(int type, int value) {
		Calendar cal = getCalendar();
		switch (type) {
		case Calendar.DATE:// 增加天数
			cal.add(Calendar.DATE, value);
			break;
		case Calendar.HOUR:// 增加小时
			cal.add(Calendar.HOUR, value);
			break;
		case Calendar.MINUTE:// 增加分钟
			cal.add(Calendar.MINUTE, value);
			break;
		case Calendar.SECOND:// 增加秒
			cal.add(Calendar.SECOND, value);
			break;
		case Calendar.MILLISECOND:// 增加毫秒
			cal.add(Calendar.MILLISECOND, value);
			break;
		default:
			System.err.println("当前类型不存在！");
			break;
		}
		return new java.util.Date(cal.getTimeInMillis());
	}

	/**
	 * 特定时间增加值
	 * 
	 * @param type
	 * @param value
	 *            限定值
	 *            ：Calendar.DATE，Calendar.HOUR，Calendar.MINUTE，Calendar.SECOND
	 *            ，Calendar.MILLISECOND
	 * @return
	 */
	public static java.util.Date addSpecialCurTime(Date date, int type, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (type) {
		case Calendar.DATE:// 增加天数
			cal.add(Calendar.DATE, value);
			break;
		case Calendar.HOUR:// 增加小时
			cal.add(Calendar.HOUR, value);
			break;
		case Calendar.MINUTE:// 增加分钟
			cal.add(Calendar.MINUTE, value);
			break;
		case Calendar.SECOND:// 增加秒
			cal.add(Calendar.SECOND, value);
			break;
		case Calendar.MILLISECOND:// 增加毫秒
			cal.add(Calendar.MILLISECOND, value);
			break;
		default:
			System.err.println("当前类型不存在！");
			break;
		}
		return new java.util.Date(cal.getTimeInMillis());
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFormat(java.util.Date date) {
		return getDateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateFormat(Date date, String format) {
		SimpleDateFormat dateFm = getSimpleDateFormat(format);
		if (date == null)
			date = getDefaultDate();
		return dateFm.format(date);
	}

	public static Date getDate(String dateStr, String format) {
		if (StringUtil.isEmpty(dateStr) || StringUtil.isEmpty(format)) {
			return null;
		}
		Date date = dateCache.get(dateStr);
		if (date == null) {
			SimpleDateFormat dateFormat = getSimpleDateFormat(format);
			try {
				date = dateFormat.parse(dateStr);
				dateCache.put(dateStr, date);
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
		return date;
	}

	/**
	 * 获取默认日期2000-01-01
	 * 
	 * @return 返回默认起始时间
	 */
	public static java.sql.Timestamp getDefaultDate() {

		Date defaultDate = getDate("2000-01-01 00:00:00", "yyyy-MM-dd hh:mm:ss");
		return new java.sql.Timestamp(defaultDate.getTime());
	}

	/**
	 * 获取指定日期格式"yyyy-MM-dd"(没有时分秒)
	 * 
	 * @return 返回日期格式变量
	 */
	public static java.sql.Timestamp getDefaultDate(String time) {
		Date tempDate = getDate(time, "yyyy-MM-dd");
		return new java.sql.Timestamp(tempDate.getTime());
	}

	/**
	 * 获取指定的时分秒
	 * 
	 * @param time
	 * @return
	 */
	public static java.sql.Timestamp getTimeByString(String time) {
		Date tempDate = getDate(time, "yyyy-MM-dd hh:mm:ss");

		return new java.sql.Timestamp(tempDate.getTime());
	}

	/**
	 * 返回日期类型的字符串
	 * 
	 * @return
	 */
	public static String getSimpleDate() {
		return getSimpleDate(new Date());
	}

	public static String getSimpleDate(Date date) {
		return getSimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static void main(String[] args) {
		Date tempDate = getDate("2099-01-01 00:00:00", "yyyy-MM-dd hh:mm:ss");
		String dateString = getDateFormat(tempDate, "yyMMddhhmmssSSS");
		System.out.println(dateString);
		System.out.println(tempDate.getTime());
		System.out.println(System.currentTimeMillis());
		System.out.println(Long.MAX_VALUE);
	}

	/**
	 * 获取默认目上限日期2999-01-01
	 * 
	 * @return 返回默认上限时间
	 */
	public static java.sql.Timestamp getDefaultMaxDate() {
		Date tempDate = getDate("2999-01-01 00:00:00", "yyyy-MM-dd hh:mm:ss");

		return new java.sql.Timestamp(tempDate.getTime());
	}

	/**
	 * 比较指定日期与当前日期是否同一天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isSameDay(Date date) {
		if (date == null) {
			return false;
		}
		java.util.Calendar now = getCalendar();
		java.util.Calendar other = getCalendar(date);
		return dateCompare(now, other) == 0 ? true : false;
	}

	/**
	 * 判断两个日期是否同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		SimpleDateFormat format = getSimpleDateFormat("yyyy-MM-dd");
		return format.format(date1).equals(format.format(date2));
	}

	/**
	 * 返回两个日期相差天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static long dateCompare(java.util.Calendar startDate, java.util.Calendar endDate) {
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		endDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.MILLISECOND, 0);

		long day = ((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / MILLISECONDS_ONE_DAY);
		return day;
	}

	public static int calcDistanceMillis(Date startTime, Date endTime) {
		if (startTime == null) {
			return Integer.MAX_VALUE;
		}

		long startSecond = getDateToSeconds(startTime);
		long endSecond = getDateToSeconds(endTime);
		return (int) (endSecond - startSecond) * 1000;
	}

	/**
	 * 间隔时间以小时为单位
	 * 
	 * @param startDate
	 * @param interval
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean isInterval(Date startDate, int interval) {
		// 开始日期
		java.util.Calendar startCalendar = java.util.Calendar.getInstance();
		startCalendar.setTime(startDate);
		// 结束日期
		java.util.Calendar endCalendar = getCalendar();
		if (dateCompare(startCalendar, endCalendar) == 0) {
			if (endCalendar.get(Calendar.HOUR_OF_DAY) / interval == startCalendar.get(Calendar.HOUR_OF_DAY)
					/ interval) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回两个日期相隔的（小时，分钟，秒）
	 * 
	 * @param startTime
	 * @param endTime
	 * @param type
	 *            类型：hour、day、min、sec
	 * @return
	 */
	public static long timeSpan(Timestamp startTime, Timestamp endTime, String type) {

		long span = endTime.getTime() - startTime.getTime();

		if (type.equalsIgnoreCase("day"))
			return span / MILLISECONDS_ONE_DAY;
		else if (type.equalsIgnoreCase("hour"))
			return span / MILLISECONDS_ONE_HOUR;
		else if (type.equalsIgnoreCase("min"))
			return span / MILLISECONDS_ONE_MINUTE;
		else if (type.equalsIgnoreCase("sec"))
			return span / MILLISECONDS_ONE_SEC;
		else
			return span;
	}

	// 计算两个日期的日子差
	public static int dayInterval(Date start, Date end) {
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		calendarStart.setTime(start);
		calendarEnd.setTime(end);

		return (int) dateCompare(calendarStart, calendarEnd);
	}

	/**
	 * 返回两个日期相隔的（小时，分钟，秒）
	 * 
	 * @param startTime
	 * @param endTime
	 * @param type
	 *            [day,hour,min,sec]
	 * @return
	 */
	public static long timeSpan(Date startTime, Date endTime, String type) {

		long span = endTime.getTime() - startTime.getTime();

		if (type.equalsIgnoreCase("day"))
			return span / MILLISECONDS_ONE_DAY;
		else if (type.equalsIgnoreCase("hour"))
			return span / MILLISECONDS_ONE_HOUR;
		else if (type.equalsIgnoreCase("min"))
			return span / MILLISECONDS_ONE_MINUTE;
		else if (type.equalsIgnoreCase("sec"))
			return span / MILLISECONDS_ONE_SEC;
		else
			return span;
	}

	public static int timeToFrame(int secondTime) {
		return (secondTime * 25) / 1000;
	}

	

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static java.util.Calendar getCalendar() {
		java.util.Calendar nowCalendar = java.util.Calendar.getInstance();
		nowCalendar.setTime(new java.util.Date());
		return nowCalendar;
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		java.util.Calendar nowCalendar = java.util.Calendar.getInstance();
		nowCalendar.setTime(date);
		return nowCalendar;
	}

	/**
	 * 比较两个时间（不包括日期）的相隔
	 * 
	 * @param startTime
	 * @param endTime
	 * @param type
	 *            (hour,min,sec)
	 * @return
	 */
	public static long compareTimeSpan(Date startTime, Date endTime, String type) {

		Calendar start = new GregorianCalendar();
		start.setTime(startTime);

		Calendar end = new GregorianCalendar();
		end.setTime(endTime);

		if (type.equalsIgnoreCase("hour"))
			return (end.getTimeInMillis() - start.getTimeInMillis()) / MILLISECONDS_ONE_HOUR;

		if (type.equalsIgnoreCase("min")) {
			return (end.getTimeInMillis() - start.getTimeInMillis()) / MILLISECONDS_ONE_MINUTE;
		}

		if (type.equalsIgnoreCase("sec")) {
			return (end.getTimeInMillis() - start.getTimeInMillis()) / MILLISECONDS_ONE_SEC;
		}

		return (end.getTimeInMillis() - start.getTimeInMillis());
	}

	/**
	 * 获取一周的第几天,值为 0 ~ 6
	 */
	public static int getDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static int getValueOfDay(int value) {
		return Calendar.getInstance().get(value);
	}

	/**
	 * 获取指定日期为一周的第几天,值为 0 ~ 6
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 时间间隔转x天x小时x分钟x秒，不足填入0
	 * 
	 * @param during
	 *            时间间隔单位毫秒
	 * @return
	 */
	public static Map<String, Long> formatDuring(long during) {
		long days = during / MILLISECONDS_ONE_DAY;
		long hours = (during % MILLISECONDS_ONE_DAY) / MILLISECONDS_ONE_HOUR;
		long minutes = (during % MILLISECONDS_ONE_HOUR) / MILLISECONDS_ONE_MINUTE;
		long seconds = (during % MILLISECONDS_ONE_MINUTE) / MILLISECONDS_ONE_SEC;
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("days", new Long(days));
		map.put("hours", new Long(hours));
		map.put("minutes", new Long(minutes));
		map.put("seconds", new Long(seconds));
		return map;
	}

	public static long timeSpanMin(Date startTime, Date endTime) {
		return timeSpan(startTime, endTime, "min");
	}

	public static Date addSpecialCurTimeMin(Date date, int value) {
		return addSpecialCurTime(date, Calendar.MINUTE, value);
	}

	/**
	 * 比较firstDate和secondDate的时间部分（不比较日期）
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return 两时间的差值（毫秒）
	 */
	public static long compareTimeOfDay(Date firstDate, Date secondDate) {
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.setTime(firstDate);
		firstCalendar.set(Calendar.YEAR, 1);
		firstCalendar.set(Calendar.MONTH, 1);
		firstCalendar.set(Calendar.DATE, 1);
		Calendar secondCalendar = Calendar.getInstance();
		secondCalendar.setTime(secondDate);
		secondCalendar.set(Calendar.YEAR, 1);
		secondCalendar.set(Calendar.MONTH, 1);
		secondCalendar.set(Calendar.DATE, 1);
		return firstCalendar.getTime().getTime() - secondCalendar.getTime().getTime();
	}

	/**
	 * 比较firstDate和secondDate的日期部分（时间部分不参与比较）
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return 两时间的差值（毫秒）
	 */
	public static long compareDateOfDate(Date firstDate, Date seconDate) {
		long fstartDate = getStartTime(firstDate);
		long sstartDate = getStartTime(seconDate);
		return (fstartDate - sstartDate);
	}

	/**
	 * 获取第一个日期与第二个日期的时间差（毫秒）
	 * 
	 * @param first
	 *            第一个日期
	 * @param second
	 *            第二个日期
	 * @return
	 */
	public static long getTimeDiffMilliSecond(Date first, Date second) {
		return first.getTime() - second.getTime();
	}

	/**
	 * 获取指定日期(月份值一月为1 )
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0, 0, 0);
	}

	/**
	 * 获取指定日期(月份值一月为1 )
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar.getTime();
	}

	/**
	 * 获取当天起始时间
	 * 
	 * @return
	 */
	public static Long getTodayStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}

	/**
	 * 获取当天起始时间
	 * 
	 * @return
	 */
	public static Date getTodayStart() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 取日期部分
	 * 
	 * @return
	 */
	public static Date getDate(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 获取指定日期的起始时间
	 * 
	 * @return
	 */
	public static Long getStartTime(Date date) {
		Calendar start = Calendar.getInstance();
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		return start.getTime().getTime();
	}

	/**
	 * 获取当天指定时间
	 * 
	 * @param hourOfDay
	 *            时（24小时制）
	 * @param munute
	 *            分
	 * @param second
	 *            秒
	 * @param millsecond
	 *            毫秒
	 * @return
	 */
	public static long getTodayFixTime(int hourOfDay, int munute, int second, int millsecond) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
		todayStart.set(Calendar.MINUTE, munute);
		todayStart.set(Calendar.SECOND, second);
		todayStart.set(Calendar.MILLISECOND, millsecond);
		return todayStart.getTime().getTime();
	}

	/**
	 * 获取当天指定时间
	 * 
	 * @param hourOfDay
	 *            时（24小时制）
	 * @return
	 */
	public static long getTodayFixTime(int hourOfDay) {
		return getTodayFixTime(hourOfDay, 0, 0, 0);
	}

	/**
	 * 获取当天的结束时间
	 * 
	 * @return
	 */
	public static Long getTodayEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime().getTime();
	}

	/**
	 * 获取指定日期的结束时间
	 * 
	 * @return
	 */
	public static Long getEndTime(Date date) {
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);
		return end.getTime().getTime();
	}

	public static Date addDayOfYear(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	public static Date addHourOfDay(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	public static Date addMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	public static Date addMilliSecond(Date date, int milliSecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, milliSecond);
		return calendar.getTime();
	}

	/**
	 * 获取指定时间的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取指定时间的月份，一月值为1
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定时间为当月第几日
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定时间的小时（24小时制）
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定时间的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取指定时间的秒
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * mickey
	 * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11"
	 * ,"yyyy-MM-dd","yyyy年MM月dd日").
	 * 
	 * @param date
	 *            String 想要格式化的日期
	 * @param oldPattern
	 *            String 想要格式化的日期的现有格式
	 * @param newPattern
	 *            String 想要格式化成什么格式
	 * @return String
	 */
	public static final String StringPattern(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null)
			return "";
		SimpleDateFormat sdf2 = getSimpleDateFormat(newPattern); // 实例化模板对象
		Date d = getDate(date, oldPattern);
		return sdf2.format(d);
	}

	/**
	 * 获取时间通过字符串
	 * 
	 */
	public static Date getDateByString(String date) {
		Date myDate = getDate(date, "yyyy-MM-dd HH:mm:ss");
		if (myDate == null) {
			myDate = getDefaultDate();
		}

		return myDate;
	}

	public static Date getDateByDefaultString(String date) {
		Date myDate = getDate(date, "yyyy-MM-dd HH:mm:ss");
		if (myDate == null) {
			myDate = getDefaultDate();
		}

		return myDate;
	}

	/**
	 * 获取当前时间的类型
	 */
	public static int ValueOfCalenderType(Date date, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(type);
	}

	/**
	 * 获取一个时间
	 */
	public static Date getDateByIntValues(int year, int month, int day, int hour, int min, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, min, seconds);
		return calendar.getTime();
	}

	public static Date formatDate(Date date) {
		String s = getSimpleDate(date);

		return getDate(s, "yyyy-MM-dd");
	}

	/**
	 * 获取两个日期的间隔的天数
	 */
	public static int getDaysDiff(Date date1, Date date2) {
		Date first = formatDate(date1);
		Date second = formatDate(date2);
		long ms = getTimeDiffMilliSecond(first, second);
		return (int) (ms / MILLISECONDS_ONE_DAY);
	}

	/** 是否是两个相同的日期 */
	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}

		return DateUtils.isSameInstant(date1, date2);
	}

	/**
	 * 判断两个日期是否在同一周(以周一为一周的第一天)
	 * 
	 * @param dat1
	 * @param dat2
	 *            一定比dat1大
	 * @return
	 */
	public static boolean IsInTheSameWeek(Date dat1, Date dat2) {
		int wk1 = TimeUtil.getDayOfWeek(dat1);
		int wk2 = TimeUtil.getDayOfWeek(dat2);
		if (wk1 == 0)
			wk1 = 7;
		if (wk2 == 0)
			wk2 = 7;
		int days = TimeUtil.getDaysDiff(dat2, dat1);
		if (days == wk2 - wk1) {
			// 同一周
			return true;
		}
		// 不是同一周
		return false;
	}
}
