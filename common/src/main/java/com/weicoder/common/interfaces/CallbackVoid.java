package com.weicoder.common.interfaces;

/**
 * 回调方法
 * 
 * @author WD
 */
public interface CallbackVoid<E> {
	/**
	 * 回调方法
	 * 
	 * @param result 结果
	 */
	void callback(E result);
}
