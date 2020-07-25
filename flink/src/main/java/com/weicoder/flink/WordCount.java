package com.weicoder.flink;

import java.util.Arrays;

import org.apache.commons.lang3.RandomUtils;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCount {
	public static void main(String[] args) throws Exception {
		// 1. 构建Flink流式初始化环境
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// 2. 自定义source - 每秒发送一行文本
		DataStreamSource<String> wordLineDS = env.addSource(new RichSourceFunction<String>() {
			private static final long serialVersionUID = 1L;
			private boolean		isCanal	= false;
			private String[]	words	= { "important oracle jdk license update",
					"the oracle jdk license has changed for releases starting april 16 2019",
					"the new oracle technology network license agreement for oracle java se is substantially different from prior oracle jdk licenses the new license permits certain uses such as ",
					"personal use and development use at no cost but other uses authorized under prior oracle jdk licenses may no longer be available please review the terms carefully before ",
					"downloading and using this product an faq is available here ",
					"commercial license and support is available with a low cost java se subscription",
					"oracle also provides the latest openjdk release under the open source gpl license at jdk java net" };

			@Override
			public void run(SourceContext<String> ctx) throws Exception {
				// 每秒发送一行文本
				while (!isCanal) {
					int randomIndex = RandomUtils.nextInt(0, words.length);
					ctx.collect(words[randomIndex]);
					Thread.sleep(1000);
				}
			}

			@Override
			public void cancel() {
				isCanal = true;
			}
		}); 
		// 3. 单词统计
		// 3.1 将文本行切分成一个个的单词
		SingleOutputStreamOperator<String> wordsDS = wordLineDS.flatMap((String line, Collector<String> ctx) -> {
			// 切分单词
			Arrays.stream(line.split(" ")).forEach(word -> {
				ctx.collect(word);
			});
		}).returns(Types.STRING);

		// 3.2 将单词转换为一个个的元组
		SingleOutputStreamOperator<Tuple2<String, Integer>> tupleDS = wordsDS.map(word -> Tuple2.of(word, 1))
				.returns(Types.TUPLE(Types.STRING, Types.INT));

		// 3.3 按照单词进行分组
		KeyedStream<Tuple2<String, Integer>, String> keyedDS = tupleDS.keyBy(tuple -> tuple.f0);

		// 3.4 对每组单词数量进行累加
		SingleOutputStreamOperator<Tuple2<String, Integer>> resultDS = keyedDS.timeWindow(Time.seconds(3))
				.reduce((t1, t2) -> Tuple2.of(t1.f0, t1.f1 + t2.f1));

		resultDS.print();

		env.execute("app");
	}
}
