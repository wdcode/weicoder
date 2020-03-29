package com.weicoder.common.interfaces;

/**
 * 回调方法
 * 
 * @author WD
 */
public interface Callback<E, R> {
	/**
	 * 回调方法
	 * 
	 * @param result 结果
	 */
	R callback(E result);
}
