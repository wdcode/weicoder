package com.weicoder.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U;

/**
 * URL相关操作
 * @author WD
 * @version 1.0
 */
public final class UrlUtil {
	/**
	 * 获得URL
	 * @param url URL
	 * @return 输入流
	 */
	public static InputStream openStream(URL url) {
		try {
			return U.E.isEmpty(url) ? null : url.openStream();
		} catch (IOException e) {
			// 记录日志
			Logs.warn(e);
			// 返回null
			return null;
		}
	}

	private UrlUtil() {}
}
