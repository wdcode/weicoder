package com.weicoder.common.io;

/**
 * IO操作集合类短类名
 * 
 * @author wdcode
 *
 */
public final class I extends IOUtil {
	private I() {
	}

	/**
	 * @see AsynChannelUtil 异步流操作类引用
	 * @author wudi
	 */
	public static final class A extends AsynChannelUtil {
	}

	/**
	 * @see ChannelUtil nio通道工具类引用
	 * @author wudi
	 */
	public static final class C extends ChannelUtil {
	}

	/**
	 * @see FileUtil 文件操作类引用
	 * @author wudi
	 */
	public static final class F extends FileUtil {
	}
}
