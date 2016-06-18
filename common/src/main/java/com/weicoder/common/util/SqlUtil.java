package com.weicoder.common.util;

import com.weicoder.common.constants.StringConstants;

/**
 * 对SQL语句进行处理
 * @author WD
 */
public final class SqlUtil {
	/**
	 * 根据传入的sql获得查询总行数的SQL语句
	 * @param sql SQL语句 必须有from的SQL查询语句
	 * @return 转换后查询总行数的SQL语句
	 */
	public static String getCountSQL(String sql) {
		return "SELECT COUNT(1) FROM " + StringUtil.subString(sql.toLowerCase(), "from");
	}

	/**
	 * 根据传入的tableName获得清空表的SQL语句
	 * @param tableName 表名
	 * @return 清空表的SQL语句
	 */
	public static String getTruncateSQL(String tableName) {
		return "TRUNCATE TABLE " + tableName;
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
	 * 根据SQL获得表名
	 * @param sql 执行的SQL
	 * @return 截取出表名
	 */
	public static String getTable(String sql) {
		// 查找表名
		String name = StringConstants.EMPTY;
		if (sql.startsWith("insert")) {
			name = StringUtil.subString(sql, "into ", StringConstants.BLANK);
		} else if (sql.startsWith("update")) {
			name = StringUtil.subString(sql, "update ", StringConstants.BLANK);
		} else if (sql.startsWith("select")) {
			name = StringUtil.subString(sql, "from ", StringConstants.BLANK);
		}
		return name;
	}

	private SqlUtil() {
	}
}
