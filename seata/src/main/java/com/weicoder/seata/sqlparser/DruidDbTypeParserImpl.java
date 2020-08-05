package com.weicoder.seata.sqlparser;

import io.seata.sqlparser.util.DbTypeParser;

/**
 * DbTypeParser实现
 * 
 * @author wdcode
 *
 */
public class DruidDbTypeParserImpl implements DbTypeParser {

	@Override
	public String parseFromJdbcUrl(String jdbcUrl) {
		return "mysql";
	}
}
