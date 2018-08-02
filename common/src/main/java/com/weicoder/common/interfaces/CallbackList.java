package com.weicoder.common.interfaces;

import java.util.List;

/**
 * 回调方法
 * @author WD
 */
public interface CallbackList<E> {
	/**
	 * 回调方法
	 * @param results 结果
	 */
	void callback(List<E> results);
}
