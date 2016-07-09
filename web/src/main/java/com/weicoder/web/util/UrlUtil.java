package com.weicoder.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;

/**
 * URL相关操作
 * @author WD 
 * @version 1.0 
 */
public final class UrlUtil {
	/**
	 * 获得URL
	 * @param url
	 * @return
	 */
	public static InputStream openStream(URL url) {
		try {
			return EmptyUtil.isEmpty(url) ? null : url.openStream();
		} catch (IOException e) {
			// 记录日志
			Logs.warn(e);
			// 返回null
			return null;
		}
	}

	private UrlUtil() {}
}
