package com.weicoder.base.token;

import com.weicoder.base.token.AuthToken;
import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * Token令牌处理器
 * @author WD
 * @since JDK7
 * @version 1.0 2014-2-12
 */
public final class TokenEngine {
	// 验证长度
	private final static int	LENGHT	= 5;

	/**
	 * 加密信息
	 * @param token 登录凭证
	 * @return 加密信息
	 */
	public static String encrypt(AuthToken token) {
		// 加密登录凭证字符串
		String info = Hex.encode(Encrypts.rc4(token.array()));
		// 返回加密字符串
		return Digest.absolute(info, LENGHT) + StringConstants.MIDLINE + info;
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
			if (!EmptyUtil.isEmpty(info) && info.length() > LENGHT && info.indexOf(StringConstants.MIDLINE) == LENGHT) {
				// 分解信息
				String[] temp = info.split(StringConstants.MIDLINE);
				// 分解的信息不为空并且只有2组
				if (!EmptyUtil.isEmpty(temp) && temp.length == 2) {
					// 判断校验串是否合法
					if (temp[0].equals(Digest.absolute(temp[1], LENGHT))) {
						token.array(Decrypts.rc4(Hex.decode(temp[1])));
					}
				}
			}
		} catch (Exception ex) {}
		// 返回token
		return token;
	}

	/**
	 * 私有构造
	 */
	private TokenEngine() {}
}