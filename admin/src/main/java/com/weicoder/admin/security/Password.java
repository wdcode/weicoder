package com.weicoder.admin.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.weicoder.common.crypto.Digest;

/**
 * 密码验证
 * @author WD
 * @since JDK6
 * @version 1.0 2012-08-24
 */
@Component
public final class Password implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return Digest.password(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}
}
