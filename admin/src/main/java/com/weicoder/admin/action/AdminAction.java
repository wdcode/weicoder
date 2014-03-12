package com.weicoder.admin.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import com.weicoder.admin.constants.AdminConstants;
import com.weicoder.admin.params.AdminParams;
import com.weicoder.admin.po.Admin;
import com.weicoder.admin.template.TemplateEngine;
import com.weicoder.admin.token.AdminToken;
import com.weicoder.base.entity.Entity;
import com.weicoder.base.token.AuthToken;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.params.Params;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.site.action.SiteAction;
import com.weicoder.site.engine.LoginEngine;
import com.weicoder.web.constants.HttpConstants;
import com.weicoder.web.util.HttpUtil;

/**
 * 后台Action
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-19
 */
@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AdminAction extends SiteAction<Admin> {
	// 模板
	protected Map<String, Object>	template;
	// 模版名
	private String					themeName;
	// 模版目录列表
	private List<String>			themes;

	@PostConstruct
	protected void init() {
		// 父类初始化
		super.init();
		// 获得认证凭证
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 认证不为空
		if (auth == null) {
			// 如果是后台主页
			if (request.getServletPath().equals(AdminParams.BACK_PATH + "index.htm")) {
				// 凭证置空
				token = LoginEngine.empty();
			}
		} else {
			// 获得登录管理员
			Object principal = auth.getPrincipal();
			if (principal instanceof AdminToken) {
				token = ((AdminToken) principal);
			} else {
				// 凭证置空
				token = LoginEngine.empty();
			}
		}
	}

	/**
	 * 主入口
	 */
	public String main() throws Exception {
		// 是否开启
		if (AdminParams.BACK_URL) {
			// 获得上次url
			url = Conversion.toString(get(StringConstants.URL));
			// 设置空url
			set(StringConstants.URL, null);
		}
		// 如果为空设置欢迎页
		url = EmptyUtil.isEmpty(url) ? AdminConstants.WELCOME : url;
		// 返回成功页
		return SUCCESS;
	}

	/**
	 * 静态化页面
	 * @return
	 * @throws Exception
	 */
	public String statics() throws Exception {
		// 网址
		String url = HttpConstants.HTTP + request.getLocalAddr() + request.getContextPath() + StringConstants.BACKSLASH;
		// 保存路径
		String path = getRealPath(StringConstants.BACKSLASH);
		String name = entity.getClass().getSimpleName().toLowerCase();
		// 静态化实体
		for (Entity e : service.list(entity, 0, 0)) {
			String f = name + StringConstants.BACKSLASH + e.getKey() + ".html";
			HttpUtil.saveToFile(url + f, path + f);
		}
		// 保存主页
		HttpUtil.saveToFile(url + "index.htm", path + "index.html");
		// 返回成功
		return SUCCESS;
	}

	/**
	 * 跳转到修改页
	 * @return 跳转
	 * @throws Exception
	 */
	public String toThemes() throws Exception {
		// 获得模版路径
		String path = getRealPath(File.separator) + "back";
		// 获得目录文件
		File theme = FileUtil.getFile(path);
		// 获得目录下所有文件
		File[] files = theme.listFiles();
		// 临时文件
		File temp = null;
		// 判断文件不为空
		if (!EmptyUtil.isEmpty(files)) {
			// 获得模版目录列表
			themes = Lists.getList(files.length);
			// 循环目录
			for (int i = 0; i < files.length; i++) {
				// 获得文件
				temp = files[i];
				// 判断是目录
				if (temp.isDirectory()) {
					// 添加到列表
					themes.add(temp.getName());
				}
			}
		}
		// 返回成功
		return SUCCESS;
	}

	/**
	 * 修改模版
	 * @return 跳转
	 * @throws Exception
	 */
	public String themes() throws Exception {
		// 替换内存的模版名
		getServletContext().setAttribute(AdminConstants.THEME_BACK, themeName);
		// 设置到参数中
		Params.setProperty(AdminConstants.BACK_THEME_KEY, themeName);
		// 写配置文件
		Params.write();
		// 返回成功
		return SUCCESS;
	}

	/**
	 * 加载模板
	 * @return
	 * @throws Exception
	 */
	public String templates() throws Exception {
		// 重新初始化模板
		TemplateEngine.init();
		// 返回成功
		return addMessage(SUCCESS);
	}

	/**
	 * 获得模板
	 * @return
	 */
	public Map<String, Object> getTemplate() {
		// 判断如果模板为空
		if (template == null) {
			// 获得模板
			Map<String, Map<String, Object>> mapModule = TemplateEngine.TEMPLATES.get(module);
			// 获得方面模板
			template = mapModule.get(mode);
			// 如果template为空 默认返回list
			template = EmptyUtil.isEmpty(template) ? mapModule.get(LIST) : template;
		}
		// 返回模板
		return template;
	}

	/**
	 * 获得模版目录列表
	 * @return 模版目录列表
	 */
	public List<String> getThemes() {
		return themes;
	}

	/**
	 * 设置模版目录列表
	 * @param themes 模版目录列表
	 */
	public void setThemes(List<String> themes) {
		this.themes = themes;
	}

	/**
	 * 获得模版名
	 * @return 模版名
	 */
	public String getThemeName() {
		return themeName;
	}

	/**
	 * 设置模版名
	 * @param themeName 模版名
	 */
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	@Override
	protected AuthToken auth() {
		return LoginEngine.getLoginBean(request, Admin.class.getSimpleName());
	}
}
