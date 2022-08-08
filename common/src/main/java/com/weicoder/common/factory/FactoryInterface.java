package com.weicoder.common.factory;

import com.weicoder.common.U;

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
			return (E) U.C.ioc(U.C.from(U.C.getGenericClass(this.getClass(), 0)));
		else
			return (E) U.C.ioc(U.C.bean(U.C.getGenericClass(this.getClass(), 0), key));
	}
}
