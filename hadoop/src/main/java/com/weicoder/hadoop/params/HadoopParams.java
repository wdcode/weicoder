package com.weicoder.hadoop.params;

import com.weicoder.common.params.P;

/**
 * Hapoop参数
 * 
 * @author wdcode
 *
 */
public final class HadoopParams {
	/** Hadoop 服务器地址 */
	public final static String URI = P.getString("hadoop.uri");

	private HadoopParams() {
	}
}
