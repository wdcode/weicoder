package com.weicoder.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.annotation.Forward;
import com.weicoder.web.annotation.Redirect;
import com.weicoder.web.annotation.State;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.params.ErrorCodeParams;
import com.weicoder.web.params.WebParams;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.web.util.ResponseUtil;

/**
 * 基础Servlet 3
 * @author WD
 */
@WebServlet("/*")
public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 3117468121294921856L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long curr = System.currentTimeMillis();
		// 获得客户端IP
		String ip = RequestUtil.getIp(request);
		// 获得callback
		String callback = RequestUtil.getParameter(request, "callback");
		Logs.debug("check ip request ip={},ips={}", ip, WebParams.IPS);
		// 获得path
		String path = request.getPathInfo();
		Logs.debug("request ip={},path={},{}", ip, path, request.getQueryString());
		if (!EmptyUtil.isEmpty(path)) {
			// 分解提交action 去处开头的/ 并且按/或者_分解出数组
			String actionName = StringUtil.subString(path, 1, path.length());
			String[] actions = StringUtil.contains(actionName, StringConstants.BACKSLASH)
					? StringUtil.split(actionName, StringConstants.BACKSLASH)
					: StringUtil.split(actionName, StringConstants.UNDERLINE);
			if (EmptyUtil.isEmpty(actions)) {
				Logs.debug("this path={}", path);
				ResponseUtil.json(response, callback, "action is null path");
				return;
			}
			// 获得Action
			// String name = actions.length > 2 ? actions[actions.length - 2] : actions[actions.length - 1];
			// Object action = WebCommons.ACTIONS.get(name);
			String name = null;
			Object action = null;
			for (String n : actions) {
				name = n;
				action = WebCommons.ACTIONS.get(name);
				if (action != null) {
					break;
				}
			}
			// action为空
			if (action == null) {
				// 如果使用action[_/]method模式 直接返回
				if (actions.length == 2) {
					Logs.debug("request ip={},path={},no action", ip, path);
					ResponseUtil.json(response, callback, "no action");
					return;
				}
				// 查找方法对应action
				action = WebCommons.METHODS_ACTIONS.get(name);
			}
			// action为空
			if (action == null) {
				// 还是为空
				Logs.warn("request ip={},path={},no action and method", ip, path);
				ResponseUtil.json(response, callback, "no action and method");
				return;
			}
			// 过滤IP
			Action a = action.getClass().getAnnotation(Action.class);
			if (a.ips() && !EmptyUtil.isEmpty(WebParams.IPS)) {
				// 如果在允许列表继续 否则退出
				if (!WebParams.IPS.contains(ip)) {
					Logs.debug("this ip={}", ip);
					ResponseUtil.json(response, callback, "not exist ip");
					return;
				}
			}
			// 验证token
			if (a.token()) {
				// 获得token
				String token = RequestUtil.getParameter(request, "token");
				if (!TokenEngine.decrypt(token).isLogin()) {
					Logs.debug("this token={}", token);
					ResponseUtil.json(response, callback, "token is no login");
					return;
				}
			}
			// 获得方法
			Map<String, Method> methods = WebCommons.ACTIONS_METHODS.get(name);
			if (EmptyUtil.isEmpty(methods)) {
				methods = WebCommons.METHODS;
			}
			Method method = methods.get(actions[actions.length - 1]);
			if (method == null) {
				Logs.debug("request ip={},path={},no method", ip, path);
				ResponseUtil.json(response, callback, "no method");
				return;
			}
			Logs.debug("request ip={},name={}", ip, actionName);
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
					} else if (Map.class.equals(cs)) {
						params[i] = ps;
					} else if (ClassUtil.isBaseType(cs)) {
						params[i] = Conversion.to(ps.get(p.getName()), cs);
						// 判断参数为空并且参数名为ip
						if (EmptyUtil.isEmpty(params[i]) && "ip".equals(p.getName())) {
							// 赋值为调用客户端IP
							params[i] = ip;
						}
						Logs.debug("request ip={},name={},params index={},name={},type={},value={}", ip, actionName, i,
								p.getName(), cs, params[i]);
					} else {
						params[i] = BeanUtil.copy(ps, cs);
						Logs.debug("request ip={},name={},params={}", ip, actionName, params[i]);
					}
				}
			}
			// 调用方法
			// try {
			Object res = BeanUtil.invoke(action, method, params);
			Logs.debug("invoke method={},params={},res={} end", method.getName(), params, res);
			// 判断是否跳转url
			if (method.isAnnotationPresent(Redirect.class) || action.getClass().isAnnotationPresent(Redirect.class)) {
				String url = Conversion.toString(res);
				if (EmptyUtil.isEmpty(url)) {
					ResponseUtil.json(response, callback, "Redirect is null");
				} else {
					response.sendRedirect(url);
				}
				Logs.debug("redirect url:{}", url);
			} else if (method.isAnnotationPresent(Forward.class)
					|| action.getClass().isAnnotationPresent(Forward.class)) {
				String url = Conversion.toString(res);
				if (EmptyUtil.isEmpty(url)) {
					ResponseUtil.json(response, callback, "Forward is null");
				} else {
					request.getRequestDispatcher(url).forward(request, response);
				}
				Logs.debug("forward url:{}", url);
			} else if (method.isAnnotationPresent(State.class) || action.getClass().isAnnotationPresent(State.class)) {
				// 状态码对象
				State state = method.getAnnotation(State.class);
				if (state == null) {
					state = action.getClass().getAnnotation(State.class);
				}
				// 字段名
				String status = state.state();
				String success = state.success();
				String error = state.error();
				// 如果res为状态码
				if (res == null) {
					// 写空信息
					ResponseUtil.json(response, callback, Maps.newMap(new String[] { status, error }, new Object[] {
							WebParams.STATE_ERROR_NULL, ErrorCodeParams.getMessage(WebParams.STATE_ERROR_NULL) }));
				} else if (res instanceof Void) {
					// 空返回
					ResponseUtil.json(response, callback, Maps.newMap(new String[] { status, success },
							new Object[] { WebParams.STATE_SUCCESS, WebParams.STATE_SUCCESS_MSG }));
				} else if (res instanceof Integer) {
					// 写错误信息
					int errorcode = Conversion.toInt(res);
					ResponseUtil.json(response, callback, Maps.newMap(new String[] { status, error },
							new Object[] { errorcode, ErrorCodeParams.getMessage(errorcode) }));
				} else {
					ResponseUtil.json(response, callback,
							Maps.newMap(new String[] { status, success }, new Object[] { 0, res }));
				}
				Logs.debug("servlet state={} method={},params={},res={} end", state, method.getName(), params, res);
			} else {
				// 如果结果为空
				if (res == null || res instanceof Void) {
					// 结果设置为空map
					res = Maps.emptyMap();
				}
				// 写到前端
				ResponseUtil.json(response, callback, res);
				Logs.debug("servlet  method={},params={},res={} end", method.getName(), params, res);
			}
			Logs.info("request ip={},name={},time={},res={},params={} end", ip, actionName,
					System.currentTimeMillis() - curr, res, params);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (WebParams.GET) {
			doPost(request, response);
		} else {
			ResponseUtil.json(response, "not supported get");
		}
	}
}