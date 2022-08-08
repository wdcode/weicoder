package com.weicoder.common;

import com.weicoder.common.concurrent.DaemonThreadFactory;
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.concurrent.ScheduledUtil;
import com.weicoder.common.io.AsynChannelUtil;
import com.weicoder.common.io.ChannelUtil;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.util.ArrayUtil;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.EnumUtil;
import com.weicoder.common.util.IpUtil;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.RegexUtil;
import com.weicoder.common.util.ResourceUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.util.ThreadUtil;

/**
 * Util类的短名称引用类
 * 
 * @author wudi
 */
public final class U {
	private U() {
	}

	/**
	 * @see ArrayUtil 数组帮助类引用
	 * @author wudi
	 */
	public static class A extends ArrayUtil {
	}

	/**
	 * @see AsynChannelUtil 异步流操作类引用
	 * @author wudi
	 */
	public static class AC extends AsynChannelUtil {
	}

	/**
	 * @see BeanUtil Bean工具类引用
	 * @author wudi
	 */
	public static class B extends BeanUtil {
	}

	/**
	 * @see ClassUtil Class工具类引用
	 * @author wudi
	 */
	public static class C extends ClassUtil {
	}

	/**
	 * @see ChannelUtil nio通道工具类引用
	 * @author wudi
	 */
	public static class Ch extends ChannelUtil {
	}

	/**
	 * @see DateUtil 日期工具类引用
	 * @author wudi
	 */
	public static class D extends DateUtil {
	}

	/**
	 * @see DaemonThreadFactory 守护线程工厂
	 * @author wudi
	 */
	public static class DTF extends DaemonThreadFactory {
	}

	/**
	 * @see EmptyUtil 判断空类引用
	 * @author wudi
	 */
	public static class E extends EmptyUtil {
	}

	/**
	 * @see ExecutorUtil 并发线程池类引用
	 * @author wudi
	 */
	public static class ES extends ExecutorUtil {
	}

	/**
	 * @see EnumUtil 枚举工具类引用
	 * @author wudi
	 */
	public static class Enum extends EnumUtil {
	}

	/**
	 * @see FileUtil 文件操作类引用
	 * @author wudi
	 */
	public static class F extends FileUtil {
	}

	/**
	 * @see MathUtil 数学相关操作类引用
	 * @author wudi
	 */
	public static class M extends MathUtil {
	}

	/**
	 * @see IOUtil IO操作类引用
	 * @author wudi
	 */
	public static class I extends IOUtil {
	}

	/**
	 * @see IpUtil IP工具类引用
	 * @author wudi
	 */
	public static class IP extends IpUtil {
	}

//	/**
//	 * @see    ImageUtil 图片工具类引用
//	 * @author wudi
//	 */
//	public static class Image extends ImageUtil {
//	}

	/**
	 * @see ResourceUtil 资源工具类引用
	 * @author wudi
	 */
	public static class R extends ResourceUtil {
	}

	/**
	 * @see RegexUtil 正则工具类引用
	 * @author wudi
	 */
	public static class Regex extends RegexUtil {
	}

	/**
	 * @see StringUtil 字符串操作类引用
	 * @author wudi
	 */
	public static class S extends StringUtil {
	}

	/**
	 * @see ScheduledUtil 并发线程定时类引用
	 * @author wudi
	 */
	public static class SES extends ScheduledUtil {
	}

	/**
	 * @see ThreadUtil 线程操作类引用
	 * @author wudi
	 */
	public static class T extends ThreadUtil {
	}
}
