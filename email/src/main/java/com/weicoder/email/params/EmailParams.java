package com.weicoder.email.params;

import com.weicoder.common.constants.C;
import com.weicoder.common.params.P;

/**
 * email包所用参数读取类
 * 
 * @author WD
 */
public final class EmailParams {
	private EmailParams() {
	}

	/** 系统发送邮件所用地址 */
	public final static String	FROM		= P.getString("email.from", C.S.EMPTY);
	/** 邮件密码 */
	public final static String	PASSWORD	= P.getString("email.password", C.S.EMPTY);
	/** 邮件服务器Host */
	public final static String	HOST		= P.getString("email.host", C.S.EMPTY);
	/** 发送Email使用的默认包 */
	public final static String	PARSE		= P.getString("email.parse", "Apache");
	/** 发送Email是否验证 */
	public final static boolean	AUTH		= P.getBoolean("email.auth", true);
	/** 发送Email是否启用SSL */
	public final static boolean	SSL			= P.getBoolean("email.ssl", true);
	/** 发送Email使用的编码格式 */
	public final static String	ENCODING	= P.getString("email.encoding", P.C.ENCODING);
}
