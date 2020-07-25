package com.weicoder.hadoop.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.hadoop.Hadoop;
import com.weicoder.hadoop.params.HadoopParams;

/**
 * Hadoop工厂类
 * 
 * @author wdcode
 *
 */
public final class HadoopFactory extends FactoryKey<String, Hadoop> {
	private final static HadoopFactory FACTORY = new HadoopFactory();

	/**
	 * 获得Hadoop
	 * 
	 * @return
	 */
	public static Hadoop getHadoop() {
		return FACTORY.getInstance(HadoopParams.URI);
	}

	/**
	 * 获得Hadoop
	 * 
	 * @param uri 地址
	 * @return
	 */
	public static Hadoop getHadoop(String uri) {
		return FACTORY.getInstance(uri);
	}

	@Override
	public Hadoop newInstance(String uri) {
		return new Hadoop(uri);
	}

	private HadoopFactory() {
	}
}
