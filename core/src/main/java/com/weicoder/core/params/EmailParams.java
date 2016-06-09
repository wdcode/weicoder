package com.weicoder.core.params;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * Email配置
 * @author WD
 */
public final class EmailParams {
	/** 系统发送邮件所用地址 */
	public final static String	EMAIL_FROM		= Params.getString("email.from", StringConstants.EMPTY);
	/** 邮件密码 */
	public final static String	EMAIL_PASSWORD	= Params.getString("email.password", StringConstants.EMPTY);
	/** 邮件服务器Host */
	public final static String	EMAIL_HOST		= Params.getString("email.host", StringConstants.EMPTY);
	/** 发送Email使用的默认包 */
	public final static String	EMAIL_PARSE		= Params.getString("email.parse", "Apache");
	/** 发送Email是否验证 */
	public final static boolean	EMAIL_AUTH		= Params.getBoolean("email.auth", true);
	/** 发送Email使用的编码格式 */
	public final static String	EMAIL_ENCODING	= Params.getString("email.encoding", CommonParams.ENCODING);

	private EmailParams() {}
}
