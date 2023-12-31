package com.weicoder.flink;
 
import org.apache.flink.api.java.tuple.Tuple2; 
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment; 

import com.weicoder.common.interfaces.Calls;
import com.weicoder.common.log.Logs;

/**
 * flink 工具
 * 
 * @author wdcode
 *
 */
public final class Flinks {
	private final static StreamExecutionEnvironmentFactory SEEF = new StreamExecutionEnvironmentFactory();

	/**
	 * 添加数据源到远程服务器 并执行
	 * 
	 * @param <E>  泛型
	 * @param host 远程地址
	 * @param port 远程端口
	 * @param from 开始
	 * @param to 结束
	 */
	public static <E> void source(String host, int port, long from, long to) {
		try {
			StreamExecutionEnvironment env = SEEF.getInstance(Tuple2.of(host, port));
			env.fromSequence(from, to);
			env.execute();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 获得远程服务器数据 并执行
	 * 
	 * @param host
	 * @param port
	 * @param call
	 */
	public static void data(String host, int port, Calls.EoV<DataStreamSource<String>> call) {
		try {
			StreamExecutionEnvironment env = SEEF.getInstance();
			call.call(env.socketTextStream(host, port));
			env.execute();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private Flinks() {
	}
}
