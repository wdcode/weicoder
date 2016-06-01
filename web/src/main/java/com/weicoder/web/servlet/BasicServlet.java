package com.weicoder.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.context.Contexts;
import com.weicoder.web.params.ServletParams;
import com.weicoder.web.params.WebParams;
import com.weicoder.web.util.IpUtil;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.web.util.ResponseUtil;

/**
 * 基础Servlet 3
 * 
 * @author WD
 * 
 */
public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 3117468121294921856L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获得客户端IP
		String ip = IpUtil.getIp(request);
		// 获得callback
		String callback = RequestUtil.getParameter(request, "callback");
		// 过滤IP
		if (!EmptyUtil.isEmpty(WebParams.SERVLET_IPS)) {
			// 如果在允许列表继续 否则退出
			if (!WebParams.SERVLET_IPS.contains(ip)) {
				Logs.info("this ip=" + ip);
				ResponseUtil.json(response, callback, "not exist ip");
				return;
			}
		}
		// 获得path
		String path = request.getPathInfo();
		if (!EmptyUtil.isEmpty(path)) {
			// 分解提交action 去处开头的/ 并且按_分解出数组
			String[] actions = StringUtil.split(StringUtil.subString(path, 1, path.length()), StringConstants.BACKSLASH);
			// 获得Action
			String name = actions[0];
			Object action = Contexts.ACTIONS.get(name);
			// action为空
			if (action == null) {
				// 如果使用action_method模式 直接返回
				if (actions.length == 2) {
					ResponseUtil.json(response, callback, "no.action");
					return;
				}
				// 查找方法对应action
				action = Contexts.METHODS_ACTIONS.get(name);
			}
			// 获得方法
			Map<String, Method> methods = Contexts.ACTIONS_METHODS.get(name);
			if (EmptyUtil.isEmpty(methods)) {
				methods = Contexts.METHODS;
			}
			Method method = methods.get(actions.length > 1 ? actions[1] : actions[0]);
			if (method == null) {
				ResponseUtil.json(response, callback, "no.method");
				return;
			}
			// 设置参数
			Parameter[] pars = method.getParameters();
			Object[] params = null;
			if (!EmptyUtil.isEmpty(pars)) {
				// 参数不为空 设置参数
				params = new Object[pars.length];
				// 所有提交的参数
				Map<String, String> ps = RequestUtil.getParameters(request);
				// action全部参数下标
				int i = 0;
				for (; i < pars.length; i++) {
					// 判断类型并设置
					Parameter p = pars[i];
					// 参数的类型
					Class<?> cs = p.getType();
					if (HttpServletRequest.class.equals(cs)) {
						params[i] = request;
					} else if (HttpServletResponse.class.equals(cs)) {
						params[i] = response;
					} else if (ClassUtil.isBaseType(cs)) {
						params[i] = Conversion.to(ps.get(p.getName()), cs);
					} else {
						params[i] = BeanUtil.copy(ps, cs);
					}
				}
			}
			// 调用方法
			ResponseUtil.json(response, callback, BeanUtil.invoke(action, method, params));
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (ServletParams.GET) {
			doPost(request, response);
		} else {
			ResponseUtil.json(response, "not supported get");
		}
	}
}