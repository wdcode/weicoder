package com.weicoder.core.nosql.berkeley.factory;

import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.nosql.berkeley.Berkeley;
import com.weicoder.core.nosql.berkeley.impl.BerkeleyImpl;

/**
 * Berkeley工厂
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-27
 */
public final class BerkeleyFactory extends FactoryKey<String, Berkeley> {
	// 工厂
	private final static BerkeleyFactory	FACTORY;
	static {
		FACTORY = new BerkeleyFactory();
	}

	/**
	 * 获得Berkeley
	 * @return Berkeley
	 */
	public static Berkeley getBerkeley() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得Berkeley
	 * @return Berkeley
	 */
	public static Berkeley getBerkeley(String key) {
		return FACTORY.getInstance(key);
	}

	@Override
	public Berkeley newInstance(String key) {
		return new BerkeleyImpl(key);
	}
}
