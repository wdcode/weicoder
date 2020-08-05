package com.weicoder.flink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.weicoder.common.factory.FactoryKey;

/**
 * flink StreamExecutionEnvironment 工厂
 * 
 * @author wdcode
 *
 */
final class StreamExecutionEnvironmentFactory extends FactoryKey<Tuple2<String, Integer>, StreamExecutionEnvironment> {

	@Override
	public StreamExecutionEnvironment newInstance(Tuple2<String, Integer> key) {
		return key == null ? StreamExecutionEnvironment.getExecutionEnvironment() : StreamExecutionEnvironment.createRemoteEnvironment(key.f0, key.f1);
	}
}