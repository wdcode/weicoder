package com.weicoder.hibernate;

import org.hibernate.Session;

/**
 * Hibernate回调方法
 * 
 * @author WD
 */
public interface Callback<T> {
	/**
	 * 调用Hibernate执行操作
	 * 
	 * @param session hibernate Session
	 * @return 指定的泛型
	 */
	T callback(Session session);
}
