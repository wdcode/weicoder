package com.weicoder.okhttp;

import java.util.Map;

/**
 * okhttp 添加头接口
 * 
 * @author wdcode
 *
 */
public interface Headers {
	/**
	 * 所有要添加的请求头
	 * 
	 * @return
	 */
	Map<String, String> headers();
}