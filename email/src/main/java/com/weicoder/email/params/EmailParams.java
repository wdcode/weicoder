package com.weicoder.email.params;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * email包所用参数读取类
 * 
 * @author WD
 */
public final class EmailParams {
	/** 系统发送邮件所用地址 */
	public final static String  FROM     = Params.getString("email.from", StringConstants.EMPTY);
	/** 邮件密码 */
	public final static String  PASSWORD = Params.getString("email.password", StringConstants.EMPTY);
	/** 邮件服务器Host */
	public final static String  HOST     = Params.getString("email.host", StringConstants.EMPTY);
	/** 发送Email使用的默认包 */
	public final static String  PARSE    = Params.getString("email.parse", "Apache");
	/** 发送Email是否验证 */
	public final static boolean AUTH     = Params.getBoolean("email.auth", true);
	/** 发送Email是否启用SSL */
	public final static boolean SSL      = Params.getBoolean("email.ssl", true);
	/** 发送Email使用的编码格式 */
	public final static String  ENCODING = Params.getString("email.encoding", CommonParams.ENCODING);

	private EmailParams() {
	}
}
