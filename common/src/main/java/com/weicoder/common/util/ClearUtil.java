package com.weicoder.common.util;

import java.util.Collection;
import java.util.Map;

import com.weicoder.common.interfaces.Clear;

/**
 * 关闭各种资源方法
 * @author WD 
 * @version 1.0 
 */
public final class ClearUtil {
	/**
	 * 清除Clear接口数据
	 * @param cs Clear接口
	 */
	public static void clear(Clear... cs) {
		// 循环清除资源
		for (Clear c : cs) {
			if (!EmptyUtil.isEmpty(c)) {
				c.clear();
			}
		}
	}

	/**
	 * 清除Collection接口数据
	 * @param c Collection接口
	 */
	public static void clear(Collection<?>... cs) {
		// 循环清除资源
		for (Collection<?> c : cs) {
			if (!EmptyUtil.isEmpty(c)) {
				c.clear();
			}
		}
	}

	/**
	 * 清除Map接口数据
	 * @param m Map接口
	 */
	public static void clear(Map<?, ?>... ms) {
		// 循环清除资源
		for (Map<?, ?> m : ms) {
			if (!EmptyUtil.isEmpty(m)) {
				m.clear();
			}
		}
	}

	private ClearUtil() {}
}
