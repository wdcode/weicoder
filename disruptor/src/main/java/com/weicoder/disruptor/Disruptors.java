package com.weicoder.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.weicoder.common.U;
import com.weicoder.common.params.CommonParams;

/**
 * Disruptor工具
 * 
 * @author wdcode
 *
 */
public final class Disruptors {
	private Disruptors() {
	}

	/**
	 * 创建一个Disruptor ringBufferSize使用buffer.size ThreadFactory使用DaemonThreadFactory
	 * 
	 * @param <T>     泛型
	 * @param factory 生成泛型数据工厂
	 * @return Disruptor
	 */
	public static <T> Disruptor<T> create(EventFactory<T> factory) {
		return new Disruptor<T>(factory, CommonParams.BUFFER_SIZE, U.DTF.INSTANCE);
	}
}
