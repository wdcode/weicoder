package com.weicoder.common.constants;

/**
 * 日期常量
 * 
 * @author WD
 */
public sealed class DateConstants permits C.D {
	/** 日期格式 yyyyMMdd */
	public final static String	FORMAT_YYYYMMDD		= "yyyyMMdd";
	/** 日期格式 yyyyMMdd */
	public final static String	FORMAT_YYYYMM		= "yyyyMM";
	/** 日期格式 yyyy-MM-dd */
	public final static String	FORMAT_YYYY_MM_DD	= "yyyy-MM-dd";
	/** 日期格式 MM-dd */
	public final static String	FORMAT_MM_DD		= "MM-dd";
	/** 日期格式 yyyy-MM-dd HH:mm:ss */
	public final static String	FORMAT_Y_M_D_H_M_S	= "yyyy-MM-dd HH:mm:ss";
	/** 日期格式 yyyy-MM-dd HH:mm:ss */
	public final static String	FORMAT_Y_M_D_H_M	= "yyyy-MM-dd HH:mm";
	/** 日期格式 yyyyMMdd HH:mm:ss */
	public final static String	FORMAT_YMD_H_M_S	= "yyyyMMdd HH:mm:ss";
	/** 日期格式 HH:mm:ss */
	public final static String	FORMAT_HH_MM_SS		= "HH:mm:ss";
	/** 日期格式 HH */
	public final static String	FORMAT_HH			= "HH";
	/** 日期格式 dd */
	public final static String	FORMAT_DD			= "dd";
	/** 日期格式 MM */
	public final static String	FORMAT_MM			= "MM";
	/** 日期时间 一天开始 00:00:00 */
	public final static String	DATE_DAY_STATR		= "00:00:00";
	/** 日期时间 一天开始 23:59:59 */
	public final static String	DATE_DAY_END		= "23:59:59";
	/** 时间常量 秒 = X毫秒 */
	public final static long	TIME_SECOND			= 1000L;
	/** 时间常量 分 = X毫秒 */
	public final static long	TIME_MINUTE			= 60000L;
	/** 时间常量 时 = X毫秒 */
	public final static long	TIME_HOUR			= 3600000L;
	/** 时间常量 天 = X毫秒 */
	public final static long	TIME_DAY			= 86400000L;
	/** 时间常量 周 = X毫秒 */
	public final static long	TIME_WEEK			= 604800000L;
	/** 时间常量 秒 = X秒 */
	public final static int		SECOND				= 1;
	/** 时间常量 分 = X秒 */
	public final static int		MINUTE				= 60;
	/** 时间常量 时 = X秒 */
	public final static int		HOUR				= 3600;
	/** 时间常量 天 = X秒 */
	public final static int		DAY					= 86400;
	/** 时间常量 周 = X秒 */
	public final static int		WEEK				= 604800;
}
