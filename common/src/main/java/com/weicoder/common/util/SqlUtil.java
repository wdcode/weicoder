package com.weicoder.common.util;

import com.weicoder.common.constants.StringConstants;

/**
 * 对SQL语句进行处理
 * @author WD
 * @since JDK7
 * @version 1.0 2009-12-27
 */
public final class SqlUtil {
	// 查询总行数语句头
	private final static String	SQLCOUNT	= "SELECT COUNT(1) FROM ";
	// from
	private final static String	FROM		= "from";
	// truncate表语句
	private final static String	TRUNCATE	= "TRUNCATE TABLE ";

	/**
	 * 根据传入的sql获得查询总行数的SQL语句
	 * @param sql SQL语句 必须有from的SQL查询语句
	 * @return 转换后查询总行数的SQL语句
	 */
	public static String getCountSQL(String sql) {
		return SQLCOUNT + StringUtil.subString(sql.toLowerCase(), FROM);
	}

	/**
	 * 根据传入的tableName获得清空表的SQL语句
	 * @param tableName 表名
	 * @return 清空表的SQL语句
	 */
	public static String getTruncateSQL(String tableName) {
		return TRUNCATE + tableName;
	}

	/**
	 * 过滤SQL语句中的单引号
	 * @param sql SQL语句
	 * @return 过滤后的语句
	 */
	public static String sqlFilterHigh(String sql) {
		return EmptyUtil.isEmpty(sql) ? StringConstants.EMPTY : sql.replaceAll(StringConstants.SINGLE_QUOT, StringConstants.TWO_SINGLE_QUOT);
	}

	/**
	 * 私有构造
	 */
	private SqlUtil() {}
}
