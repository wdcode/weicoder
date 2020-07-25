package com.weicoder.hadoop;

import com.weicoder.hadoop.factory.HadoopFactory;

/**
 * Hadoop静态操作类
 * 
 * @author wdcode
 *
 */
public class Hadoops {
	/** 获得默认的Hadoop */
	public final static Hadoop HADOOP = HadoopFactory.getHadoop();
}
