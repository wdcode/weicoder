package com.weicoder.frame.token;

import com.weicoder.frame.token.AuthToken;
import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * Token令牌处理器
 * @author WD 
 * @version 1.0 
 */
public final class TokenEngine {
	// 验证长度
	private final static int LENGHT = 8;

	/**
	 * 加密信息
	 * @param token 登录凭证
	 * @return 加密信息
	 */
	public static String encrypt(AuthToken token) {
		// 加密登录凭证字符串
		String info = Hex.encode(Encrypts.rc4(token.array()));
		// 返回加密字符串
		return StringUtil.combine(Digest.absolute(info, LENGHT), info).toUpperCase();
	}

	/**
	 * 验证登录凭证
	 * @return 登录实体
	 */
	public static <E extends AuthToken> E decrypt(String info, E token) {
		try {
			// 验证去掉"""
			info = StringUtil.replace(info, StringConstants.DOUBLE_QUOT, StringConstants.EMPTY);
			// 判断验证串是否符合标准
			if (!EmptyUtil.isEmpty(info) && info.length() > LENGHT) {
				// 变为小写
				info = info.toLowerCase();
				// 拆分字符串
				String[] temp = StringUtil.separate(info, info.length() / LENGHT);
				if (!EmptyUtil.isEmpty(temp) && temp.length == 2) {
					// 验证串
					String ver = temp[0];// StringUtil.subString(info, 0, LENGHT);
					// 信息串
					String user = temp[1];// StringUtil.subString(info, LENGHT);
					// 判断校验串是否合法
					if (ver.equals(Digest.absolute(user, LENGHT))) {
						token.array(Decrypts.rc4(Hex.decode(user)));
					}
				}
			}
		} catch (Exception e) {
			Logs.debug(e);
		}
		// 返回token
		return token;
	}

	private TokenEngine() {}
}