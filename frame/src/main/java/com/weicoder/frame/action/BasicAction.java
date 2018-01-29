package com.weicoder.frame.action;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.frame.util.ResponseUtil; 

/**
 * Struts2 Action 的抽象实现 其它Struts2 Action可继承此类
 * @author WD
 * @since JDK7
 * @version 1.0 2009-08-26
 */
public abstract class BasicAction {
	// 回调方法处理
	protected final static Map<String, Method>	METHODS	= Maps.newMap();

	/**
	 * 以ajax模式输出数据到客户端方法
	 * @param response
	 * @param json 对象
	 */
	public String ajax(HttpServletResponse response, Object data) {
		// 写字符串
		ResponseUtil.json(response, data);
		// 返回空
		return null;
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * @param response
	 * @param obj 处理对象
	 * @return 返回标识
	 */
	public String callback(HttpServletResponse response, Object obj) {
		return callback(response, obj, StringConstants.EMPTY);
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * @param response
	 * @param obj 处理对象
	 * @return 返回标识
	 */
	public String callback(HttpServletResponse response, Object obj, String mode) {
		// 声明方法
		Method method = null;
		// 获得Key相对的方法是否存在
		if (METHODS.containsKey(mode)) {
			method = METHODS.get(mode);
		} else {
			// 不存在获得
			synchronized (METHODS) {
				METHODS.put(mode, method = BeanUtil.getMethod(this, mode, Object.class));
			}
		}
		// 方法不为空
		if (method != null) {
			obj = BeanUtil.invoke(this, method, response, obj);
		}
		// 返回对象字符串
		return Conversion.toString(obj, null);
	}

	/**
	 * 获得程序路径
	 * @param request
	 * @param name 文件名
	 * @return 程序路径
	 */
	public String getRealPath(HttpServletRequest request, String name) {
		return request.getServletContext().getRealPath(StringConstants.BACKSLASH) + name;
	}
}
