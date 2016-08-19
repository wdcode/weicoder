package com.weicoder.nosql.params;

import com.weicoder.common.params.Params;

/**
 * kafka参数
 * @author WD
 */
public final class KafkaParams {
	/** kafka 服务器 */
	public final static String SERVERS = Params.getString("kafka.servers", "127.0.0.1:9092");

	private KafkaParams() {}
}
