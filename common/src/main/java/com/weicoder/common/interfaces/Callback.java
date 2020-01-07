package com.weicoder.common.interfaces;

/**
 * 回调方法
 * 
 * @author WD
 */
public interface Callback<E> {
	/**
	 * 回调方法
	 * 
	 * @param result 结果
	 */
	Object callback(E result);
}
