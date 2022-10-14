package com.weicoder.common.interfaces;

import java.util.List;

/**
 * 调用类接口 类下为回调接口 格式为 X(参数Z无参，E一个,T两个,L列表,A数组,V可变参数)o(or)Y(返回值V无返回,R单对象,L返回列表,A返回数组)
 * 
 * @author wdcode
 *
 */
public final class Calls {
	private Calls() {
	}

	public static interface ZoV {
		void call();
	}

	public static interface EoV<E> {
		void call(E e);
	}

	public static interface ToV<E, T> {
		void call(E e, T t);
	}

	public static interface ToR<E, T, R> {
		R call(E e, T t);
	}

	public static interface LoV<E> {
		void call(List<E> list);
	}

	public static interface EoR<E, R> {
		R call(E e);
	}
}
