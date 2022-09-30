package com.weicoder.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.weicoder.common.params.P;
import com.weicoder.common.thread.concurrent.factory.DTF;

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
		return new Disruptor<T>(factory, P.C.BUFFER_SIZE, DTF.INSTANCE);
	}
}
