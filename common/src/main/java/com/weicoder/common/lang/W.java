package com.weicoder.common.lang;

import com.weicoder.common.codec.URLCode;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.http.HttpEngine;
import com.weicoder.common.token.TokenEngine;

/**
 * 常用类短名称静态引用
 * 
 * @author wudi
 */
public final class W {
	private W() {
	}

	/**
	 * @see Bytes 字节数组操作类引用
	 * @author wudi
	 */
	public static final class B extends Bytes {
	}

	/**
	 * @see Conversion 类型转换类引用
	 * @author wudi
	 */
	public static final class C extends Conversion {
	}

	/**
	 * @see Digest 信息摘要类引用
	 * @author wudi
	 */
	public static final class D extends Digest {
	}

	/**
	 * @see Decrypts 对称解密类引用
	 * @author wudi
	 */
	public static final class De extends Decrypts {
	}

	/**
	 * @see Encrypts 对称加密类引用
	 * @author wudi
	 */
	public static final class En extends Encrypts {
	}

	/**
	 * @see HttpEngine http操作类引用
	 * @author wudi
	 */
	public static final class H extends HttpEngine {
	}

	/**
	 * @see Lists list集合操作类引用
	 * @author wudi
	 */
	public static final class L extends Lists {
	}

	/**
	 * @see Maps map集合操作类引用
	 * @author wudi
	 */
	public static final class M extends Maps {
	}

	/**
	 * @see Sets set操作类引用
	 * @author wudi
	 */
	public static final class S extends Sets {
	}

	/**
	 * @see TokenEngine Token操作类引用
	 * @author wudi
	 */
	public static final class T extends TokenEngine {
	}

	/**
	 * @see URLCode URL编码操作类引用
	 * @author wudi
	 */
	public static final class U extends URLCode {
	}

	/**
	 * @see Queues 队列操作类引用
	 * @author wudi
	 */
	public static final class Q extends Queues {
	}
}
