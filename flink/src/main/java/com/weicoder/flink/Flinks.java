package com.weicoder.flink;
 
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;

import com.weicoder.common.interfaces.CallbackVoid;
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
	 * @param call 执行回调
	 */
	public static <E> void source(String host, int port, CallbackVoid<SourceContext<E>> call) {
		try {
			StreamExecutionEnvironment env = SEEF.getInstance(Tuple2.of(host, port));
			env.addSource(new RichSourceFunction<E>() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run(SourceContext<E> ctx) throws Exception {
					call.callback(ctx);
				}

				@Override
				public void cancel() {
				}
			});
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
	public static void data(String host, int port, CallbackVoid<DataStreamSource<String>> call) {
		try {
			StreamExecutionEnvironment env = SEEF.getInstance();
			call.callback(env.socketTextStream(host, port));
			env.execute();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private Flinks() {
	}
}
