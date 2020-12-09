package com.weicoder.common.factory;

import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.U.C;

/**
 * 拥有Key功能的工厂基础实现 根据Key生成单例
 * 
 * @author WD
 */
public abstract class FactoryInterface<E> extends FactoryKey<String, E> {
	@SuppressWarnings("unchecked")
	@Override
	public E newInstance(String key) {
		if (U.E.isEmpty(key))
			return (E) C.ioc(W.C.value(C.from(C.getGenericClass(this.getClass(), 0)), def()));
		else
			return (E) C.ioc(W.C.value(C.bean(C.getGenericClass(this.getClass(), 0), key), def()));
	}

	protected abstract Class<? extends E> def();
}
