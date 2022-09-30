package com.weicoder.dao.util;

import com.weicoder.common.constants.C;
import com.weicoder.common.util.U; 

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
		return "SELECT COUNT(1) FROM " + U.S.subString(sql.toLowerCase(), "from");
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
		return U.E.isEmpty(sql) ? C.S.EMPTY : sql.replaceAll("'", "''");
	}

	/**
	 * 根据SQL获得表名
	 * @param sql 执行的SQL
	 * @return 截取出表名
	 */
	public static String getTable(String sql) {
		// 查找表名
		String name = C.S.EMPTY;
		if (sql.startsWith("insert"))
			name = U.S.subString(sql, "into ", C.S.BLANK);
		else if (sql.startsWith("update"))
			name = U.S.subString(sql, "update ", C.S.BLANK);
		else if (sql.startsWith("select"))
			name = U.S.subString(sql, "from ", C.S.BLANK);
		return name;
	}

	private SqlUtil() {}
}
