package com.weicoder.gpu;

import com.aparapi.Kernel;
import com.weicoder.common.interfaces.CallbackVoid;

/**
 * 调用gpu计算
 * 
 * @author wdcode
 *
 */
public final class Gpus {
	/**
	 * 调用gpu进行计算
	 * 
	 * @param range 范围
	 * @param call  回调
	 */
	public static void execute(int range, CallbackVoid<Kernel> call) {
		// 声明内核处理数据
		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				call.callback(this);
			}
		};
		// 启动
		kernel.execute(range);
	}
}
