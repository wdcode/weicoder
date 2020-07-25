package com.weicoder.spark;

import java.util.Arrays;
import java.util.List; 

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD; 
import org.apache.spark.api.java.JavaSparkContext; 
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class CoGroup {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("spark WordCount!").setMaster("local");
		JavaSparkContext sContext = new JavaSparkContext(conf);
		List<Tuple2<Integer, String>> namesList = Arrays.asList(new Tuple2<Integer, String>(1, "Spark"),
				new Tuple2<Integer, String>(3, "Tachyon"), new Tuple2<Integer, String>(4, "Sqoop"),
				new Tuple2<Integer, String>(2, "Hadoop"), new Tuple2<Integer, String>(2, "Hadoop2"));

		List<Tuple2<Integer, Integer>> scoresList = Arrays.asList(new Tuple2<Integer, Integer>(1, 100),
				new Tuple2<Integer, Integer>(3, 70), new Tuple2<Integer, Integer>(3, 77),
				new Tuple2<Integer, Integer>(2, 90), new Tuple2<Integer, Integer>(2, 80));
		JavaPairRDD<Integer, String> names = sContext.parallelizePairs(namesList);
		JavaPairRDD<Integer, Integer> scores = sContext.parallelizePairs(scoresList);
		/**
		 * <Integer> JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Integer>>>
		 * org.apache.spark.api.java.JavaPairRDD.cogroup(JavaPairRDD<Integer, Integer> other)
		 */
		JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> nameScores = names.cogroup(scores);

		nameScores.foreach(new VoidFunction<Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>>>() {
			private static final long	serialVersionUID	= 1L;
			int							i					= 1;

			@Override
			public void call(Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> t) throws Exception {
				String string = "ID:" + t._1 + " , " + "Name:" + t._2._1 + " , " + "Score:" + t._2._2;
				string += "     count:" + i;
				System.out.println(string);
				i++;
			}
		});

		sContext.close();
	}
}
