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
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.web.action.Action;
import com.weicoder.web.params.ServletParams;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.web.util.ResponseUtil;

/**
 * 基础Servlet 3
 * @author WD
 * @since JDK7
 * @version 1.0 2015-10-21
 */
public class BasicServlet extends HttpServlet {
	private static final long					serialVersionUID	= 3117468121294921856L;
	// Action列表
	private final static Map<String, Action>	ACTIONS				= Maps.getMap();
	// 回调方法处理
	private final static Map<Action, Method>	METHODS				= Maps.getMap();

	@Override
	public void init() throws ServletException {
		// 判断是否开启Servlet
		if (ServletParams.POWER) {
			// 按包处理
			for (String p : ServletParams.PACKAGES) {
				// Servlet
				for (Class<?> c : ClassUtil.getAnnotationClass(p, Action.class)) {
					try {
						// 实例化Action
						Action action = (Action) BeanUtil.newInstance(c);
						ACTIONS.put(c.getName(), action);
						if (action != null) {
							// 循环判断方法
							for (Method m : c.getMethods()) {
								METHODS.put(action, m);
							}
						}
					} catch (Exception ex) {
						Logs.warn(ex);
					}
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获得path
		String path = request.getPathInfo();
		// 分解提交action
		String[] actions = new String[] { StringConstants.EMPTY };
		if (!EmptyUtil.isEmpty(path)) {
			// 去处开头的/ 并且按_分解出数组
			actions = StringUtil.split(StringUtil.subString(path, 1, path.length()), StringConstants.UNDERLINE);
			// 获得模板名
			Action action = ACTIONS.get(actions[0]);
			if (action != null) {
				// 方法名
				Method method = METHODS.get(actions.length > 1 ? actions[1] : actions[0]);
				if (method != null) {
					// 设置参数
					Class<?>[] cs = method.getParameterTypes();
					Map<String, String> ps = RequestUtil.getParameters(request);
					Object[] params = null;
					if (!EmptyUtil.isEmpty(cs)) {
						// 参数不为空 设置参数
						params = new Object[cs.length];
						for (int i = 0; i < cs.length; i++) {
							// 判断类型并设置
							Class<?> c = cs[i];
							if (HttpServletRequest.class.equals(c)) {
								params[i] = request;
							} else if (HttpServletResponse.class.equals(c)) {
								params[i] = response;
							} else {
								params[i] = BeanUtil.copy(ps, c);
							}
						}
					}
					// 调用方法
					ResponseUtil.json(response, RequestUtil.getParameter(request, "callback"), BeanUtil.invoke(action, method, params));
					// // 获得方法名
					// String mode = action.length > 2 ? action[2] : action.length == 2 ? action[1]
					// :
					// action[0];
					// // 如果mode为空
					// if (EmptyUtil.isEmpty(mode)) {
					// mode = "call";
					// }
					// 获得
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (ServletParams.GET) {
			doPost(req, resp);
		}
	}
}