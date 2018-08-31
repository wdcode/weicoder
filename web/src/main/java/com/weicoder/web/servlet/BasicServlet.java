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
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.IpUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.annotation.Cookies;
import com.weicoder.web.annotation.Forward;
import com.weicoder.web.annotation.Get;
import com.weicoder.web.annotation.Post;
import com.weicoder.web.annotation.Redirect;
import com.weicoder.web.annotation.State;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.params.ErrorCodeParams;
import com.weicoder.web.params.WebParams;
import com.weicoder.web.util.CookieUtil;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.web.util.ResponseUtil;
import com.weicoder.web.validator.Validators;

/**
 * 基础Servlet 3
 * @author WD
 */
@WebServlet("/*")
public class BasicServlet extends HttpServlet {
	private static final long	serialVersionUID	= 3117468121294921856L;
	// 日志
	private final static Log	LOG					= LogFactory.getLog(BasicServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long curr = System.currentTimeMillis();
		// 获得客户端IP
		String ip = RequestUtil.getIp(request);
		// 获得callback
		String callback = RequestUtil.getParameter(request, "callback");
		// 提交方法
		String m = request.getMethod();
		// 获得path
		String path = request.getPathInfo();
		String queryString = request.getQueryString();
		LOG.debug("request ip={} path={} Method={} scheme={} queryString={}", ip, path, m, request.getScheme(), queryString);
		if (EmptyUtil.isNotEmpty(path)) {
			// 分解提交action 去处开头的/ 并且按/或者_分解出数组
			String actionName = StringUtil.subString(path, 1, path.length());
			String[] actions = StringUtil.contains(actionName, StringConstants.BACKSLASH) ? StringUtil.split(actionName, StringConstants.BACKSLASH)
					: StringUtil.split(actionName, StringConstants.UNDERLINE);
			if (EmptyUtil.isEmpty(actions)) {
				LOG.debug("this path={}", path);
				ResponseUtil.json(response, callback, "action is null path");
				return;
			}
			// 获得Action
			String name = actions[actions.length - 1];
			Object action = null;
			for (int i = actions.length - 2; i >= 0; i--) {
				name = actions[i];
				action = WebCommons.ACTIONS.get(name);
				if (action != null)
					break;
			}
			// action为空
			if (action == null) {
				// 如果使用action[_/]method模式 直接返回
				if (actions.length == 2) {
					LOG.debug("request ip={},path={},no action", ip, path);
					ResponseUtil.json(response, callback, "no action");
					return;
				}
				// 查找方法对应action
				action = WebCommons.METHODS_ACTIONS.get(name);
			}
			// action为空
			if (action == null) {
				// 还是为空
				LOG.warn("request ip={},path={},name={},actionName={},ma={},no action and method", ip, path, name, actionName, WebCommons.METHODS_ACTIONS);
				ResponseUtil.json(response, callback, "no action and method");
				return;
			}
			// 过滤IP
			Action a = action.getClass().getAnnotation(Action.class);
			if (a.ips()) {
				// 如果在允许列表继续 否则退出
				if (!IpUtil.contains(ip)) {
					LOG.debug("this ip={}", ip);
					ResponseUtil.json(response, callback, "not exist ip");
					return;
				}
			}
			// 获得方法
			Map<String, Method> methods = WebCommons.ACTIONS_METHODS.get(name);
			if (EmptyUtil.isEmpty(methods))
				methods = WebCommons.METHODS;
			Method method = methods.get(actions[actions.length - 1]);
			if (method == null) {
				LOG.debug("request ip={},path={},no method", ip, path);
				ResponseUtil.json(response, callback, "no method");
				return;
			}
			// 判断提交方法
			// if (a.method()) {
			// 校验是否只使用post方法提交
			if (method.isAnnotationPresent(Post.class) && !StringUtil.equals("POST", m.toUpperCase())) {
				ResponseUtil.json(response, callback, "no method is " + m);
				return;
			}
			// 校验是否只使用get方法提交
			if (method.isAnnotationPresent(Get.class) && !StringUtil.equals("GET", m.toUpperCase())) {
				ResponseUtil.json(response, callback, "no method is " + m);
				return;
			}
			// }
			// 设置参数
			Parameter[] pars = WebCommons.METHODS_PARAMES.get(method);
			Object[] params = null;
			// 所有提交的参数
			Map<String, String> ps = RequestUtil.getAll(request);
			LOG.debug("action={} params={}", actionName, ps);
			// 验证
			int code = Validators.validator(method, action, ps, ip);
			if (EmptyUtil.isNotEmpty(pars)) {
				// 参数不为空 设置参数
				params = new Object[pars.length];
				if (EmptyUtil.isEmpty(ps.get("ip")))
					ps.put("ip", ip);
				LOG.trace("request all ip={} params={}", ip, params);
				// token验证通过在执行
				if (code == WebParams.STATE_SUCCESS) {
					// action全部参数下标
					int i = 0;
					for (; i < pars.length; i++) {
						// 判断类型并设置
						Parameter p = pars[i];
						// 参数的类型
						Class<?> cs = p.getType();
						if (HttpServletRequest.class.equals(cs))
							params[i] = request;
						else if (HttpServletResponse.class.equals(cs))
							params[i] = response;
						else if (TokenBean.class.equals(cs))
							// 设置Token
							params[i] = TokenEngine.decrypt(ps.get(p.getName()));
						else if (Map.class.equals(cs))
							params[i] = ps;
						else if (ClassUtil.isBaseType(cs)) {
							// 获得参数
							params[i] = Conversion.to(ps.get(p.getName()), cs);
							// 验证参数
							if ((code = Validators.validator(p, params[i])) != WebParams.STATE_SUCCESS)
								break;
						} else {
							// 设置属性
							params[i] = BeanUtil.copy(ps, cs);
							// 验证参数
							if ((code = Validators.validator(params[i])) != WebParams.STATE_SUCCESS)
								break;
						}
					}
				}
			}
			// 调用方法
			// try {
			Object res = null;
			if (code == WebParams.STATE_SUCCESS)
				res = BeanUtil.invoke(action, method, params);
			else
				res = code;
			// 判断是否需要写cookie
			boolean cookie = method.isAnnotationPresent(Cookies.class) || action.getClass().isAnnotationPresent(Cookies.class);
			String[] names = null;
			Cookies c = null;
			if (cookie) {
				// 获得Cookies注解
				c = method.getAnnotation(Cookies.class);
				if (c == null)
					c = action.getClass().getAnnotation(Cookies.class);
				names = c.names();
			}
			// 判断是否跳转url
			if (method.isAnnotationPresent(Redirect.class) || action.getClass().isAnnotationPresent(Redirect.class)) {
				String url = Conversion.toString(res);
				if (EmptyUtil.isEmpty(url))
					ResponseUtil.json(response, callback, "Redirect is null");
				else {
					LOG.debug("redirect url:{}", url);
					response.sendRedirect(url);
					return;
				}
			} else if (method.isAnnotationPresent(Forward.class) || action.getClass().isAnnotationPresent(Forward.class)) {
				String url = Conversion.toString(res);
				if (EmptyUtil.isEmpty(url))
					ResponseUtil.json(response, callback, "Forward is null");
				else {
					LOG.debug("forward url:{}", url);
					request.getRequestDispatcher(url).forward(request, response);
					return;
				}
			} else if (method.isAnnotationPresent(State.class) || action.getClass().isAnnotationPresent(State.class)) {
				// 状态码对象
				State state = method.getAnnotation(State.class);
				if (state == null)
					state = action.getClass().getAnnotation(State.class);
				// 字段名
				String status = state.state();
				String success = state.success();
				String error = state.error();
				// 如果res为状态码
				if (res == null)
					// 写空信息
					ResponseUtil.json(response, callback,
							Maps.newMap(new String[] { status, error }, new Object[] { WebParams.STATE_ERROR_NULL, ErrorCodeParams.getMessage(WebParams.STATE_ERROR_NULL) }));
				else if (res instanceof Integer) {
					// 写错误信息
					int errorcode = Conversion.toInt(res);
					// 写入到前端
					ResponseUtil.json(response, callback, Maps.newMap(new String[] { status, errorcode == WebParams.STATE_SUCCESS ? success : error },
							new Object[] { errorcode, errorcode == WebParams.STATE_SUCCESS ? WebParams.STATE_SUCCESS_MSG : ErrorCodeParams.getMessage(errorcode) }));
				} else {
					// 是否写cookie
					if (cookie)
						CookieUtil.adds(response, c.maxAge(), res, names);
					// 写入到前端
					res = Maps.newMap(new String[] { status, success }, new Object[] { WebParams.STATE_SUCCESS, res });
				}
			} else {
				// 如果结果为空
				if (res == null)
					// 结果设置为空map
					res = Maps.emptyMap();
				else if (cookie)
					// 写cookie
					CookieUtil.adds(response, c.maxAge(), res, names);
			}
			// 写到前端
			LOG.info("request ip={} name={}  params={} pars={} time={} res={} end", ip, actionName, params, pars, System.currentTimeMillis() - curr, ResponseUtil.json(response, callback, res));
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (WebParams.GET)
			doPost(request, response);
		else
			ResponseUtil.json(response, "not supported get");
	}
}