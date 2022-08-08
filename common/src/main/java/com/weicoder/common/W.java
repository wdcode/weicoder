package com.weicoder.common;

import com.weicoder.common.codec.URLCode;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.http.HttpEngine;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.lang.Queues;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.params.Params;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.zip.ZipEngine;

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
	public static class B extends Bytes {
	}

	/**
	 * @see Conversion 类型转换类引用
	 * @author wudi
	 */
	public static class C extends Conversion {
	}

	/**
	 * @see Digest 信息摘要类引用
	 * @author wudi
	 */
	public static class D extends Digest {
	}

	/**
	 * @see Decrypts 对称解密类引用
	 * @author wudi
	 */
	public static class De extends Decrypts {
	}

	/**
	 * @see Encrypts 对称加密类引用
	 * @author wudi
	 */
	public static class En extends Encrypts {
	}

	/**
	 * @see HttpEngine http操作类引用
	 * @author wudi
	 */
	public static class H extends HttpEngine {
	}

	/**
	 * @see Lists list集合操作类引用
	 * @author wudi
	 */
	public static class L extends Lists {
	}

	/**
	 * @see Maps map集合操作类引用
	 * @author wudi
	 */
	public static class M extends Maps {
	}

	/**
	 * @see Params 参数读取类引用
	 * @author wudi
	 */
	public static class P extends Params {
	}

	/**
	 * @see Sets set操作类引用
	 * @author wudi
	 */
	public static class S extends Sets {
	}

	/**
	 * @see TokenEngine Token操作类引用
	 * @author wudi
	 */
	public static class T extends TokenEngine {
	}

	/**
	 * @see URLCode URL编码操作类引用
	 * @author wudi
	 */
	public static class U extends URLCode {
	}

	/**
	 * @see Queues 队列操作类引用
	 * @author wudi
	 */
	public static class Q extends Queues {
	}

	/**
	 * @see ZipEngine 压缩引擎类引用
	 * @author wudi
	 */
	public static class Z extends ZipEngine {
	}
}
