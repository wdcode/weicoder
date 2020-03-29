package com.weicoder.common.queue;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 唯一队列值 基础并发队列ConcurrentLinkedQueue 确保元素唯一
 * @author WD
 */
public class OnlyQueue<E> extends ConcurrentLinkedQueue<E> {
	private static final long serialVersionUID = -790216735906542874L;

	@Override
	public boolean add(E e) {
		if (contains(e))
			return remove(e);
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (containsAll(c))
			return removeAll(c);
		return super.addAll(c);
	}
}
