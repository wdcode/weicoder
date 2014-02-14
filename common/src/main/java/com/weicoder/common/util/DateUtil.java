package com.weicoder.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.RegexConstants;

import com.weicoder.common.constants.StringConstants;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Validate;
import com.weicoder.common.params.CommonParams;

/**
 * 获得日期,日期类型和字符串类型之间的转化
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-01
 */
public final class DateUtil {
	// Calendar实例
	private final static Calendar	CALENDAR	= Calendar.getInstance();

	/**
	 * 根据给定的日期字符串返回它的日期Format
	 * @param date 日期
	 * @return Format
	 */
	public static String getFormat(String date) {
		// 开始判断格式
		if (Validate.is(RegexConstants.DATE_YYYYMMDD, date)) {
			return DateConstants.FORMAT_YYYYMMDD;
		} else if (Validate.is(RegexConstants.DATE_YYYY_MM_DD, date)) {
			return DateConstants.FORMAT_YYYY_MM_DD;
		} else if (Validate.is(RegexConstants.DATE_Y_M_D_H_M_S, date)) {
			return DateConstants.FORMAT_Y_M_D_H_M_S;
		} else if (Validate.is(RegexConstants.DATE_Y_M_D_H_M, date)) {
			return DateConstants.FORMAT_Y_M_D_H_M;
		} else if (Validate.is(RegexConstants.DATE_YMD_H_M_S, date)) {
			return DateConstants.FORMAT_YMD_H_M_S;
		} else if (Validate.is(RegexConstants.DATE_HH_MM_SS, date)) {
			return DateConstants.FORMAT_HH_MM_SS;
		} else if (Validate.is(RegexConstants.DATE_YYYYMM, date)) {
			return DateConstants.FORMAT_YYYYMM;
		}
		return null;
	}

	/**
	 * 获得Calendar
	 * @return Calendar
	 */
	public static Calendar getCalendar() {
		return CALENDAR;
	}

	/**
	 * 取得格式为默认格式的系统日期 返回的日期是字符串格式
	 * @return String 当前日期
	 */
	public static String getDate() {
		return getDate(CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得格式为yyyy-MM-dd HH:mm:ss 的系统日期 返回的日期是字符串格式
	 * @return String 当前日期
	 */
	public static String getLongDate() {
		return getDate(DateConstants.FORMAT_Y_M_D_H_M_S);
	}

	/**
	 * 取得格式为yyyy-MM-dd 的系统日期 返回的日期是字符串格式
	 * @return String 当前日期
	 */
	public static String getShortDate() {
		return getDate(DateConstants.FORMAT_YYYY_MM_DD);
	}

	/**
	 * 取得格式为yyyyMMdd 的系统日期 返回的日期是字符串格式
	 * @return String 当前日期
	 */
	public static String getTinyDate() {
		return getDate(DateConstants.FORMAT_YYYYMMDD);
	}

	/**
	 * 取得格式为HH:mm:ss 的系统日期 返回的日期是字符串格式
	 * @return String 当前日期
	 */
	public static String getTheDate() {
		return getDate(DateConstants.FORMAT_HH_MM_SS);
	}

	/**
	 * 取得指定格式的系统日期 返回的日期是字符串格式
	 * @param format 日期格式，如 "yyyy-MM-dd HH:mm:sss"
	 * @return String 当前日期
	 */
	public static String getDate(String format) {
		return toString(getCurrentDate(), format);
	}

	/**
	 * 取得当前时间 返回的是Date类型
	 * @return Date 当前日期
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 取得当前时间的秒数 返回的是int类型
	 * @return int
	 */
	public static int getTime() {
		return Conversion.toInt(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获得现在时间毫秒数
	 * @param date 要取的时间
	 * @return int
	 */
	public static int getTime(Date date) {
		return EmptyUtil.isEmpty(date) ? 0 : Conversion.toInt(date.getTime() / 1000);
	}

	/**
	 * 获得现在时间毫秒数
	 * @param date 要取的时间
	 * @return lointng
	 */
	public static int getTime(String dateString) {
		return getTime(toDate(dateString));
	}

	/**
	 * 获得现在时间毫秒数
	 * @param date 要取的时间
	 * @param format 时间字符串样式
	 * @return int
	 */
	public static int getTime(String dateString, String format) {
		return getTime(toDate(dateString, format));
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number) 默认格式
	 * @param time 时间
	 * @param number N天
	 * @return N天的日期
	 */
	public static int getTime(int time, int number) {
		return getTime(time, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param time 时间
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static int getTime(int time, int number, String format) {
		return getTime(getDate(time, number, format));
	}

	/**
	 * 根据时间变量返回时间字符串
	 * @param date 时间变量
	 * @param format 时间字符串样式
	 * @return 返回时间字符串
	 */
	public static String toString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 根据给定的时间返回相对的字符串 默认格式
	 * @param date 日期
	 * @return String 转换后的日期
	 */
	public static String toString(Date date) {
		return toString(date, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得time的日期
	 * @param time 毫秒
	 * @return String time的日期
	 */
	public static String toString(long time) {
		return toString(time, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得time的日期
	 * @param time 毫秒
	 * @param format 日期显示格式
	 * @return String time的日期
	 */
	public static String toString(long time, String format) {
		return toString(new Date(time), format);
	}

	/**
	 * 取得time的日期
	 * @param time 毫秒
	 * @return String time的日期
	 */
	public static String toString(int time) {
		return toString(time, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得time的日期
	 * @param time 毫秒
	 * @param format 日期显示格式
	 * @return String time的日期
	 */
	public static String toString(int time, String format) {
		return toString(Conversion.toLong(time) * 1000, format);
	}

	/**
	 * 字符串转换为日期 默认格式
	 * @param dateString 字符串
	 * @return Date 转换后的日期
	 */
	public static Date toDate(String dateString) {
		return toDate(dateString, getFormat(dateString));
	}

	/**
	 * 字符串转换为日期 dateString为空或异常返回当前时间
	 * @param dateString 字符串
	 * @param format 日期格式
	 * @return Date 转换后的日期
	 */
	public static Date toDate(String dateString, String format) {
		try {
			// 格式化日期
			DateFormat df = EmptyUtil.isEmpty(format) ? new SimpleDateFormat() : new SimpleDateFormat(format);
			// 返回转换后的日期
			return df.parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算两个日期相差的天数 传入的日期格式是 默认格式
	 * @param oneDate 开始日期
	 * @param twoDate 结束日期
	 * @return 返回两个日期相差的天数
	 */
	public static int marginDay(String oneDate, String twoDate) {
		return marginDay(oneDate, twoDate, getFormat(oneDate));
	}

	/**
	 * 计算两个日期相差的天数
	 * @param oneDate 开始日期
	 * @param twoDate 结束日期
	 * @param format 日期格式
	 * @return 返回两个日期相差的天数
	 */
	public static int marginDay(String oneDate, String twoDate, String format) {
		return marginDay(toDate(oneDate, format), toDate(twoDate, format));
	}

	/**
	 * 计算两个日期相差的天数
	 * @param oneDate 日期
	 * @param twoDate 日期
	 * @return 返回两个日期相差的天数
	 */
	public static int marginDay(Date oneDate, Date twoDate) {
		return Conversion.toInt((twoDate.getTime() - oneDate.getTime()) / DateConstants.TIME_DAY);
	}

	/**
	 * 两个日期相隔几个月 默认日期格式
	 * @param oneDate 日期
	 * @param twoDate 日期
	 * @return 返回两个日期相隔几个月
	 */
	public static int marginMonth(String oneDate, String twoDate) {
		return marginMonth(oneDate, twoDate, getFormat(oneDate));
	}

	/**
	 * 两个日期相隔几个月
	 * @param oneDate 日期
	 * @param twoDate 日期
	 * @param format 日期格式
	 * @return 返回两个日期相隔几个月
	 */
	public static int marginMonth(String oneDate, String twoDate, String format) {
		return marginMonth(toDate(oneDate, format), toDate(twoDate, format));
	}

	/**
	 * 两个日期相隔几个月
	 * @param oneDate 日期
	 * @param twoDate 日期
	 * @return 返回两个日期相隔几个月
	 */
	public static int marginMonth(Date oneDate, Date twoDate) {
		// 返回第一个日期的年份
		int oneYear = getYear(oneDate);
		// 返回第一个日期的月份
		int oneMonth = getMonth(oneDate);
		// 返回第二个日期的年份
		int twoYear = getYear(twoDate);
		// 返回第二个日期的月份
		int twoMonth = getMonth(twoDate);
		// 月为0设置为12月,并把年减一
		if (oneMonth == 0) {
			oneMonth = 12;
			oneYear -= 1;
		}
		// 月为0设置为12月,并把年减一
		if (twoMonth == 0) {
			twoMonth = 12;
			twoYear -= 1;
		}
		// 返回相差月份
		return (twoYear - oneYear) * 12 + (twoMonth - oneMonth);
	}

	/**
	 * 根据日期取得星期几 周日返回的是0
	 * @return 返回星期几
	 */
	public static int getDayOfWeek() {
		return getDayOfWeek(getCurrentDate());
	}

	/**
	 * 根据日期取得星期几 默认格式 周日返回的是0
	 * @param date 日期字符串
	 * @return 返回星期几
	 */
	public static int getDayOfWeek(String date) {
		return getDayOfWeek(date, getFormat(date));
	}

	/**
	 * 根据日期取得星期几 周日返回的是0
	 * @param date 日期字符串
	 * @param format 日期格式
	 * @return 返回星期几
	 */
	public static int getDayOfWeek(String date, String format) {
		return getDayOfWeek(toDate(date, format));
	}

	/**
	 * 根据日期取得星期几 周日返回的是0
	 * @param date 日期
	 * @return 返回星期几
	 */
	public static int getDayOfWeek(Date date) {
		return get(date, Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 根据本周的的日期
	 * @return 本周的日期
	 */
	public static String[] getDayOfWeeks() {
		return getDayOfWeeks(getDate());
	}

	/**
	 * 根据日期取得当前星期7天的日期 默认格式
	 * @param date 日期字符串
	 * @return 本周的日期
	 */
	public static String[] getDayOfWeeks(String date) {
		return getDayOfWeeks(date, getFormat(date));
	}

	/**
	 * 根据日期取得当前星期7天的日期 默认格式
	 * @param date 日期字符串
	 * @param format 日期格式
	 * @return 本周的日期
	 */
	public static String[] getDayOfWeeks(String date, String format) {
		return getDayOfWeeks(toDate(date, format), format);
	}

	/**
	 * 根据日期取得当前星期7天日期
	 * @param date 日期
	 * @return 本周的日期
	 */
	public static String[] getDayOfWeeks(Date date) {
		return getDayOfWeeks(date, CommonParams.DATE_FORMAT);
	}

	/**
	 * 根据日期取得当前星期7天日期
	 * @param date 日期
	 * @param format 返回的日期格式
	 * @return 本周的日期
	 */
	public static String[] getDayOfWeeks(Date date, String format) {
		// 声明一个数组 保存本周日期
		String[] weekInfo = new String[7];
		// 获得今天是星期几
		int week = getDayOfWeek(date);
		// 循环7天
		for (int i = 1; i < 8; i++) {
			// 设置相隔天数 并保存在数组中
			weekInfo[i - 1] = getDate(date, -(week - i), format);
		}
		// 返回本周信息
		return weekInfo;
	}

	/**
	 * 根据日期取得一年的第N周
	 * @return 返回第N周
	 */
	public static int getWeek() {
		return getWeek(getCurrentDate());
	}

	/**
	 * 根据日期取得一年的第N周 默认格式
	 * @param date 日期字符串
	 * @return 返回第N周
	 */
	public static int getWeek(String date) {
		return getWeek(date, getFormat(date));
	}

	/**
	 * 根据日期取得一年的第N周
	 * @param date 日期字符串
	 * @param format 日期格式
	 * @return 返回第N周
	 */
	public static int getWeek(String date, String format) {
		return getWeek(toDate(date, format));
	}

	/**
	 * 根据日期取得一年的第N周
	 * @param date 日期
	 * @return 返回第N周
	 */
	public static int getWeek(Date date) {
		return get(date, Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 根据日期取得一年的第N天
	 * @return 返回第N天
	 */
	public static int getDayOfYear() {
		return getDayOfYear(getCurrentDate());
	}

	/**
	 * 根据日期取得一年的第N天
	 * @param date 日期字符串
	 * @return 返回第N天
	 */
	public static int getDayOfYear(String date) {
		return getDayOfYear(date, getFormat(date));
	}

	/**
	 * 根据日期取得一年的第N天
	 * @param date 日期字符串
	 * @param format 日期格式
	 * @return 返回第N天
	 */
	public static int getDayOfYear(String date, String format) {
		return getDayOfYear(toDate(date, format));
	}

	/**
	 * 根据日期取得一年的第N天
	 * @param date 日期
	 * @return 返回第N天
	 */
	public static int getDayOfYear(Date date) {
		return get(date, Calendar.DAY_OF_YEAR);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number) 默认格式
	 * @param number N天
	 * @return N天的日期
	 */
	public static String getDate(int number) {
		return getDate(number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static String getDate(int number, String format) {
		return getDate(getCurrentDate(), number, format);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number) 默认格式
	 * @param time 时间
	 * @param number N天
	 * @return N天的日期
	 */
	public static String getDate(int time, int number) {
		return getDate(time, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param time 时间
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static String getDate(int time, int number, String format) {
		return getDate(toString(time), number, format);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param date 日期字符串
	 * @param number N天
	 * @return N天的日期
	 */
	public static String getDate(String date, int number) {
		return getDate(date, number, getFormat(date));
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param date 日期字符串
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static String getDate(String date, int number, String format) {
		return getDate(toDate(date, format), number, format);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param date 日期
	 * @param number N天
	 * @return N天的日期
	 */
	public static String getDate(Date date, int number) {
		return getDate(date, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N天后的日期(如果想获得前几天的日期用-number)
	 * @param date 日期
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static String getDate(Date date, int number, String format) {
		return discrepancy(date, number, format, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得当前日期的N月后的日期(如果想获得前几月的日期用-number) 默认格式
	 * @param number N月
	 * @return N月的日期
	 */
	public static String getMonth(int number) {
		return getMonth(number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N月后的日期(如果想获得前几月的日期用-number)
	 * @param number N月
	 * @param format 日期格式
	 * @return N月的日期
	 */
	public static String getMonth(int number, String format) {
		return getMonth(getCurrentDate(), number, format);
	}

	/**
	 * 取得当前日期的N月后的日期(如果想获得前几月的日期用-number)
	 * @param date 日期字符串
	 * @param number N月
	 * @return N月的日期
	 */
	public static String getMonth(String date, int number) {
		return getMonth(date, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N月后的日期(如果想获得前几月的日期用-number)
	 * @param date 日期字符串
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static String getMonth(String date, int number, String format) {
		return getMonth(toDate(date, format), number, format);
	}

	/**
	 * 取得当前日期的N月后的日期(如果想获得前几月的日期用-number)
	 * @param date 日期
	 * @param number N月
	 * @return N月的日期
	 */
	public static String getMonth(Date date, int number) {
		return getMonth(date, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N月后的日期(如果想获得前几月的日期用-number)
	 * @param date 日期
	 * @param number N月
	 * @param format 日期格式
	 * @return N月的日期
	 */
	public static String getMonth(Date date, int number, String format) {
		return discrepancy(date, number, format, Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前日期的月份所有日期
	 * @return 返回本月的所有日期
	 */
	public static String[] getMonths() {
		return getMonths(getDate());
	}

	/**
	 * 根据指定日期的月份所有日期
	 * @param date 日期字符串
	 * @return 返回本月的所有日期
	 */
	public static String[] getMonths(String date) {
		return getMonths(toDate(date, CommonParams.DATE_FORMAT), CommonParams.DATE_FORMAT);
	}

	/**
	 * 根据指定日期的月份所有日期
	 * @param date 日期字符串
	 * @param format 日期格式
	 * @return 返回本月的所有日期
	 */
	public static String[] getMonths(String date, String format) {
		return getMonths(toDate(date, format), format);
	}

	/**
	 * 根据指定日期的月份所有日期
	 * @param date 日期
	 * @return 回本月的所有日期 默认格式
	 */
	public static String[] getMonths(Date date) {
		return getMonths(date, CommonParams.DATE_FORMAT);
	}

	/**
	 * 根据指定日期的月份所有日期
	 * @param date 日期
	 * @param format 日期格式
	 * @return 回本月的所有日期
	 */
	public static String[] getMonths(Date date, String format) {
		// 获得指定日期的日子
		int day = getDay(date);
		// 获得本月最大日子
		int maxDay = getMonthHaveDay(date);
		// 声明数组 保存本月日期格式
		String[] monthInfo = new String[maxDay];
		// 循环最大日子
		for (int i = 1; i <= maxDay; i++) {
			// 设置相隔天数 并保存在数组中
			monthInfo[i - 1] = getDate(date, -(day - i), format);
		}
		// 返回月份信息
		return monthInfo;
	}

	/**
	 * 取得当前日期的N年后的日期(如果想获得前几年的日期用-number) 默认格式
	 * @param number N年
	 * @return N年的日期
	 */
	public static String getYear(int number) {
		return getYear(number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N年后的日期(如果想获得前几年的日期用-number)
	 * @param number N年
	 * @param format 日期格式
	 * @return N年的日期
	 */
	public static String getYear(int number, String format) {
		return getYear(getCurrentDate(), number, format);
	}

	/**
	 * 取得当前日期的N年后的日期(如果想获得前几年的日期用-number)
	 * @param date 日期字符串
	 * @param number N年
	 * @return N年的日期
	 */
	public static String getYear(String date, int number) {
		return getYear(date, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N年后的日期(如果想获得前几年的日期用-number)
	 * @param date 日期字符串
	 * @param number N天
	 * @param format 日期格式
	 * @return N天的日期
	 */
	public static String getYear(String date, int number, String format) {
		return getYear(toDate(date, format), number, format);
	}

	/**
	 * 取得当前日期的N年后的日期(如果想获得前几年的日期用-number)
	 * @param date 日期
	 * @param number N年
	 * @return N年的日期
	 */
	public static String getYear(Date date, int number) {
		return getYear(date, number, CommonParams.DATE_FORMAT);
	}

	/**
	 * 取得当前日期的N年后的日期(如果想获得前几年的日期用-number)
	 * @param date 日期
	 * @param number N年
	 * @param format 日期格式
	 * @return N年的日期
	 */
	public static String getYear(Date date, int number, String format) {
		return discrepancy(date, number, format, Calendar.YEAR);
	}

	/**
	 * 日期字符串格式转换
	 * @param src 日期字符串
	 * @param srcfmt 源日期格式
	 * @param desfmt 目标日期格式
	 * @return 转换后的日期
	 */
	public static String format(String src, String srcfmt, String desfmt) {
		return toString(toDate(src, srcfmt), desfmt);
	}

	/**
	 * 取指定日期的年份
	 * @param date 日期
	 * @return 年
	 */
	public static int getYear(Date date) {
		return get(date, Calendar.YEAR);
	}

	/**
	 * 取指定日期的月份
	 * @param date 日期
	 * @return 月
	 */
	public static int getMonth(Date date) {
		return get(date, Calendar.MONTH) + 1;
	}

	/**
	 * 取指定日期月份的日
	 * @param date 日期
	 * @return 日
	 */
	public static int getDay(Date date) {
		return get(date, Calendar.DATE);
	}

	/**
	 * 获取当前 时
	 * @param date 日期
	 * @return 时
	 */
	public static int getHour(Date date) {
		return get(date, Calendar.HOUR);
	}

	/**
	 * 获取当前 分
	 * @param date 日期
	 * @return 分
	 */
	public static int getMinute(Date date) {
		return get(date, Calendar.MINUTE);
	}

	/**
	 * 获取当前 秒
	 * @param date 日期
	 * @return 秒
	 */
	public static int getSecond(Date date) {
		return get(date, Calendar.SECOND);
	}

	/**
	 * 获取当前 年
	 * @return 年
	 */
	public static int getYear() {
		return getYear(getCurrentDate());
	}

	/**
	 * 获取当前 月
	 * @return 月
	 */
	public static int getMonth() {
		return getMonth(getCurrentDate());
	}

	/**
	 * 获取当前月份的 日
	 * @return 日
	 */
	public static int getDay() {
		return getDay(getCurrentDate());
	}

	/**
	 * 获取当前 时
	 * @return 时
	 */
	public static int getHour() {
		return getHour(getCurrentDate());
	}

	/**
	 * 获取当前 分
	 * @return 分
	 */
	public static int getMinute() {
		return getMinute(getCurrentDate());
	}

	/**
	 * 获取当前 秒
	 * @return 秒
	 */
	public static int getSecond() {
		return getSecond(getCurrentDate());
	}

	/**
	 * 获得当前月份有几天
	 * @return 几天
	 */
	public static int getMonthHaveDay() {
		return getMonthHaveDay(getCurrentDate());
	}

	/**
	 * 获得指定月份有几天
	 * @param date 日期
	 * @return 几天
	 */
	public static int getMonthHaveDay(Date date) {
		return getActualMaximum(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前年份有几天
	 * @return 几天
	 */
	public static int getYearHaveDay() {
		return getYearHaveDay(getCurrentDate());
	}

	/**
	 * 获得指定年份有几天
	 * @param date 日期
	 * @return 几天
	 */
	public static int getYearHaveDay(Date date) {
		return getActualMaximum(date, Calendar.DAY_OF_YEAR);
	}

	/**
	 * 转换时间到字符串格式 例如 输入300 转成00:05:00
	 * @param time 要转换的时间 单位秒
	 * @return 转换完格式的字符串
	 */
	public static String secondToTime(int time) {
		// 定义变量
		// timeString 要生成的时间格式字符串
		StringBuilder timeString = new StringBuilder();
		// hour 表示小时
		int hour = 0;
		// minute 表示分
		int minute = 0;
		// second 表示秒
		int second = 0;
		// 是否为负数 初始为假 正数
		boolean isNegative = false;
		// 判断是否正数
		if (time < 0) {
			time *= -1;
			isNegative = true;
		}
		// 把输入的时间转成 时分秒
		if (time >= 60) {
			minute = time / 60;
			second = time % 60;
			if (minute >= 60) {
				hour = minute / 60;
				minute = minute % 60;
			}
		} else {
			second = time;
		}
		// 连成时间字符串 不足10的补0
		if (hour < 10) {
			timeString.append(StringConstants.ZERO);
		}
		timeString.append(hour);
		timeString.append(":");
		if (minute < 10) {
			timeString.append(StringConstants.ZERO);
		}
		timeString.append(minute);
		timeString.append(":");
		if (second < 10) {
			timeString.append(StringConstants.ZERO);
		}
		if (isNegative) {
			time *= -1;
			timeString.append("-");
			timeString.append(timeString);
		}
		return timeString.toString();
	}

	/**
	 * 根据字段返回结果
	 * @param date
	 * @param field
	 * @return
	 */
	private static int get(Date date, int field) {
		// 设置时间
		getCalendar().setTime(date);
		// 根据字段返回结果
		return getCalendar().get(field);
	}

	/**
	 * 返回相差数
	 * @param date
	 * @param number
	 * @param format
	 * @param field
	 * @return
	 */
	private static String discrepancy(Date date, int number, String format, int field) {
		// 设置时间
		getCalendar().setTime(date);
		// 添加时间差额的条件
		getCalendar().add(field, number);
		// 返回日期
		return new SimpleDateFormat(format).format(getCalendar().getTime());
	}

	/**
	 * 根据字断获得最大日子
	 * @param date
	 * @param field
	 * @return
	 */
	private static int getActualMaximum(Date date, int field) {
		// 设置时间
		getCalendar().setTime(date);
		// 获得本月最大日子
		return getCalendar().getActualMaximum(field);
	}

	/**
	 * 私有构造
	 */
	private DateUtil() {}
}
