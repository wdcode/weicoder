package com.weicoder.common.bean;

/**
 * 排序使用
 * 
 * @author wdcode
 *
 * @param <E>
 */
public record Sort<E>(long s, E e) implements Comparable<Sort<E>> {

	@Override
	public int compareTo(Sort<E> o) {
		return Long.compare(s, o.s);
	}
}