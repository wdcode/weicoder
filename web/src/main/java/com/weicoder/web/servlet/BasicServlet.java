package com.weicoder.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.web.action.Action;
import com.weicoder.web.params.ServletParams;

/**
 * 基础Servlet 3
 * @author WD
 * @since JDK7
 * @version 1.0 2015-10-21
 */
public class BasicServlet extends HttpServlet {
	private static final long					serialVersionUID	= 3117468121294921856L;
	// 回调方法处理
	private final static Map<String, Method>	METHODS				= Maps.getMap();

	@Override
	public void init() throws ServletException {
		// 判断是否开启Servlet
		if (ServletParams.POWER) {
			// 按包处理
			for (String p : ServletParams.PACKAGES) {
				// Servlet
				for (Class<?> c : ClassUtil.getAnnotationClass(p, Action.class)) {
					try {
						// 循环判断方法
						for (Method m : c.getMethods()) {
							METHODS.put(m.getName(), m);
						}
					} catch (Exception ex) {
						Logs.warn(ex);
					}
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获得pathInfo
		String pathInfo = req.getPathInfo();
		// 分解提交action
		String[] action = new String[] { StringConstants.EMPTY };
		if (!EmptyUtil.isEmpty(pathInfo)) {
			// 去处开头的/ 并且按_分解出数组
			action = StringUtil.split(StringUtil.subString(pathInfo, 1, pathInfo.length()), StringConstants.UNDERLINE);
		}
		// 获得模板名
		String module = action[0];
		// 方法名
		String method = action.length > 1 ? action[1] : action[0];
		// 获得方法名
		String mode = action.length > 2 ? action[2] : action.length == 2 ? action[1] : action[0];
		// 如果mode为空
		if (EmptyUtil.isEmpty(mode)) {
			mode = "call";
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (ServletParams.GET) {
			doPost(req, resp);
		}
	}
}