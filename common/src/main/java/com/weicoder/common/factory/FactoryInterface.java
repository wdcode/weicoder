package com.weicoder.common.factory;

import com.weicoder.common.U;
import com.weicoder.common.U.C;

/**
 * 拥有Key功能的工厂基础实现 根据Key生成单例
 * 
 * @author WD
 */
public class FactoryInterface<E> extends FactoryKey<String, E> {
	@SuppressWarnings("unchecked")
	@Override
	public E newInstance(String key) {
		if (U.E.isEmpty(key))
			return (E) C.ioc(C.from(C.getGenericClass(this.getClass(), 0)));
		else
			return (E) C.ioc(C.bean(C.getGenericClass(this.getClass(), 0), key));
	}
}
