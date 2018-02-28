package com.weicoder.nosql.params;

import java.util.List;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.params.Params;

/**
 * kafka参数
 * @author WD
 */
public final class KafkaParams {
	/** kafka 服务器 */
	public final static String			SERVERS	= Params.getString("kafka.servers");
	/** 要监听的topic */
	public final static List<String>	TOPICS		= Params.getList("kafka.topics", Lists.emptyList());

	private KafkaParams() {}
}
