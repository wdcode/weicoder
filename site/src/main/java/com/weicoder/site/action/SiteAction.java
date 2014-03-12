package com.weicoder.site.action;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import com.weicoder.base.action.SuperAction;
import com.weicoder.base.entity.EntityUser;
import com.weicoder.base.token.AuthToken;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.site.constants.SiteConstants;
import com.weicoder.site.engine.LoginEngine;
import com.weicoder.site.params.SiteParams;
import com.weicoder.site.token.LoginToken;
import com.weicoder.web.email.EmailEngine;
import com.weicoder.web.util.VerifyCodeUtil;

/**
 * 登录Action
 * @author WD
 * @since JDK7
 * @version 1.0 2009-07-14
 */
public class SiteAction<U extends EntityUser> extends SuperAction {
	// 用户实体
	protected U		user;

	// 验证码
	private String	verifyCode;
	// 保存属性时间
	private boolean	autoLogin;
	// 原密码
	private String	oldPwd;
	// 新密码
	private String	newPwd;
	// 重复密码
	private String	echoPwd;
	// 验证码
	private String	activeCoding;

	@PostConstruct
	protected void init() {
		// 父类初始化
		super.init();
		// 获得登录凭证
		if (EmptyUtil.isEmpty(token)) {
			token = auth();
		}
	}

	/**
	 * 设置登录凭证
	 * @param token
	 */
	public void setToken(String token) {
		// 解析登录凭证
		LoginToken login = LoginEngine.decrypt(token);
		// 登录凭证不为空
		if (!EmptyUtil.isEmpty(login)) {
			this.token = login;
		}
	}

	/**
	 * 主页
	 */
	public String index() throws Exception {
		if (token.isLogin()) {
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}

	/**
	 * 修改个人密码
	 * @return
	 * @throws Exception
	 */
	public String changePwd() throws Exception {
		if (newPwd.equals(echoPwd)) {
			// 获得原Bean
			EntityUser u = service.get(user.getClass(), key);
			// 判断是否原始密码
			if (Digest.absolute(oldPwd).equals(u.getPassword())) {
				// 设置新密码
				u.setPassword(newPwd);
				// 返回成功
				return callback(service.update(u));
			}
		}
		// 返回失败
		return callback(ERROR);
	}

	/**
	 * 注册用户
	 * @return
	 * @throws Exception
	 */
	public String register() throws Exception {
		// 注册ip
		if (EmptyUtil.isEmpty(user.getIp())) {
			String ip = getIp();
			user.setIp(ip);
			user.setLoginIp(ip);
		}
		// 创建时间
		int time = DateUtil.getTime();
		user.setTime(time);
		user.setLoginTime(time);
		// 是否Email验证
		if (SiteParams.USER_VERIFY_EMAIL) {
			// 设置状态无效
			user.setState(SiteConstants.STATE_INAVAIL);
		} else {
			// 设置状态有效
			user.setState(SiteConstants.STATE_AVAIL);
		}
		// 添加
		service.insert(user);
		// 获得用户ID
		int id = user.getId();
		// 注册成功
		if (id > 0) {
			// 是否Email验证
			if (SiteParams.USER_VERIFY_EMAIL) {
				// 获得激活码
				String activeCoding = Encrypts.encrypt(user.getId() + StringConstants.AMP + user.getEmail());
				// 邮件标题
				String subject = SiteParams.USER_VERIFY_EMAIL_SUBJECT.replaceAll(SiteParams.USER_VERIFY_EMAIL_NAME, user.getName());
				// 获得Url
				String url = SiteParams.USER_VERIFY_EMAIL_ACTION + activeCoding;
				// 邮件正文
				String content = SiteParams.USER_VERIFY_EMAIL_CONTENT.replaceAll(SiteParams.USER_VERIFY_EMAIL_URL, url);
				// 发生激活信
				EmailEngine.send(user.getEmail(), subject, content);
			} else {
				// 添加用户登录信息
				token = LoginEngine.addLogin(request, response, user, getLoginTime());
			}
		}
		// 返回
		return callback(user);
	}

	/**
	 * 激活用户
	 * @return
	 * @throws Exception
	 */
	public String active() throws Exception {
		// 获得验证码解析后的字符串数组
		String[] temp = Decrypts.decryptString(activeCoding).split(StringConstants.AMP);
		// 获得用户ID
		int userId = Conversion.toInt(temp[0]);
		// 获得Email
		String email = temp[1];
		// 根据id获得用户实体
		if (userId > 0) {
			// 获得用户实体
			// 设置属性
			user.setId(userId);
			user.setEmail(email);
		}
		// 判断激活码是否正确
		if (user != null && user.getId() > 0) {
			// 设置状态为有效
			user.setState(SiteConstants.STATE_AVAIL);
			// 修改用户实体
			return callback(EmptyUtil.isEmpty(service.update(user)) ? SUCCESS : ERROR);
		} else {
			// 验证码错误 返回到错误页
			return callback(ERROR);
		}
	}

	/**
	 * 执行退出
	 */
	public String logout() throws Exception {
		// 移除登录信息
		LoginEngine.removeLogin(request, response, getLoginKey());
		// 返回登录页
		return callback(SUCCESS);
	}

	/**
	 * 用户登录方法
	 */
	public String login() throws Exception {
		// 验证验证码 判断验证码都为空 跳过验证码检查
		if (!VerifyCodeUtil.check(request, response, verifyCode)) {
			// 添加错误信息
			addError("verifyCode,error");
			// 返回登陆页
			return callback(LOGIN);
		}
		// 查询获得用户实体
		U bean = service.get(user);
		// 登录标识
		boolean is = false;
		// 获得用户ID
		int uid = bean == null ? 0 : Conversion.toInt(bean.getKey());
		// 判断用户是否存在
		if (!EmptyUtil.isEmpty(bean) && uid > 0) {
			// 判断用户名和密码相等
			if (user.getPassword().equals(bean.getPassword())) {
				// 判断是否验证状态
				if (SiteParams.USER_VERIFY_STATE) {
					if (Conversion.toInt(bean.getState()) == SiteConstants.STATE_AVAIL) {
						// 设置登录成功
						is = true;
					}
				} else {
					// 设置登录成功
					is = true;
				}
			}
		}
		// 登录验证
		if (is) {
			// 添加用户登录信息
			token = LoginEngine.addLogin(request, response, bean, getLoginTime());
			// 添加登录信息
			bean.setLoginIp(getIp());
			bean.setLoginTime(DateUtil.getTime());
			service.update(bean);
			// 登录成功
			return callback(user = bean);
		} else {
			// 添加错误信息
			addError("login,fail");
			// 登录失败
			return callback(LOGIN);
		}
	}

	/**
	 * 获得验证码
	 * @return 验证码
	 */
	public String getVerifyCode() {
		return verifyCode;
	}

	/**
	 * 设置验证码
	 * @param verifyCode 验证码
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	/**
	 * 是否登录
	 * @return 是否自动登录
	 */
	public String isLogin() throws Exception {
		return callback(token.isLogin());
	}

	/**
	 * 获得登录凭证
	 * @return 获得登录凭证
	 */
	public String token() throws Exception {
		return callback(LoginEngine.encrypt(token));
	}

	/**
	 * 获得登录凭证
	 * @return 获得登录凭证
	 */
	public String verifyToken() throws Exception {
		return callback(token = LoginEngine.decrypt(Conversion.toString(key)));
	}

	/**
	 * 获得登录凭证
	 * @return 获得登录凭证
	 */
	public String tokenUser() throws Exception {
		return callback(user = (U) service.get(getUser().getClass(), token.getId()));
	}

	/**
	 * 获得登录凭证
	 * @return 获得登录凭证
	 */
	public String token(Object obj) throws Exception {
		return ajax(LoginEngine.encrypt(token));
	}

	/**
	 * 获得验证码方法
	 * @return
	 * @throws Exception
	 */
	public String verifyCode() throws Exception {
		// 获得验证码
		VerifyCodeUtil.make(request, response);
		// 返回到登录页
		return null;
	}

	/**
	 * 是否自动登录
	 * @return 是否自动登录
	 */
	public boolean isAutoLogin() {
		return autoLogin;
	}

	/**
	 * 设置是否自动登录
	 * @param autoLogin 是否自动登录
	 */
	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	/**
	 * 获得原密码
	 * @return 原密码
	 */
	public String getOldPwd() {
		return oldPwd;
	}

	/**
	 * 设置原密码
	 * @param oldPwd 原密码
	 */
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	/**
	 * 获得新密码
	 * @return 新密码
	 */
	public String getNewPwd() {
		return newPwd;
	}

	/**
	 * 设置新密码
	 * @param newPwd 新密码
	 */
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * 获得重复密码
	 * @return 重复密码
	 */
	public String getEchoPwd() {
		return echoPwd;
	}

	/**
	 * 设置重复密码
	 * @param echoPwd 重复密码
	 */
	public void setEchoPwd(String echoPwd) {
		this.echoPwd = echoPwd;
	}

	/**
	 * 获得验证码
	 * @return 验证码
	 */
	public String getActiveCoding() {
		return activeCoding;
	}

	/**
	 * 设置验证码
	 * @param activeCoding 验证码
	 */
	public void setActiveCoding(String activeCoding) {
		this.activeCoding = activeCoding;
	}

	/**
	 * 获得用户实体
	 * @return 用户实体
	 */
	public U getUser() {
		return user;
	}

	/**
	 * 设置登录实体
	 * @param user 登录实体
	 */
	@Autowired
	public void setUser(U user) {
		this.user = user;
	}

	/**
	 * 获得登录Key
	 * @return 登录Key
	 */
	public String getLoginKey() {
		return user.getClass().getSimpleName();
	}

	/**
	 * 获得登录保存时间
	 * @return 登录保存时间
	 */
	protected int getLoginTime() {
		return autoLogin ? SiteParams.LOGIN_MAX_AGE : SiteParams.LOGIN_MIN_AGE;
	}

	@Override
	protected AuthToken auth() {
		return LoginEngine.getLoginBean(request, getLoginKey());
	}
}
