package com.weicoder.common.config;

import java.util.List;

import com.weicoder.common.C;
import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.U.E;

/**
 * 配置读取的基本实现
 * 
 * @author wdcode
 *
 */
public abstract class BaseConfig implements Config { 

	@Override
	public List<String> getList(String key, List<String> defaultValue) {
		return W.L.newList(getStringArray(key, U.E.isEmpty(defaultValue) ? C.A.STRING_EMPTY : W.L.toArray(defaultValue)));
	}

	@Override
	public String[] getStringArray(String key) {
		return getStringArray(key, C.A.STRING_EMPTY);
	}

	@Override
	public String[] getStringArray(String key, String[] defaultValue) {
		// 获得字符串
		String s = getString(key);
		// 如果为空返回默认值 不为空以,拆分
		if (U.E.isEmpty(s))
			return defaultValue;
		else
			return s.split(C.S.COMMA);
	}

//	@Override
//	public String getString(String key) {
//		return getString(key, C.S.EMPTY);
//	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return W.C.toBoolean(getString(key), defaultValue);
	}

	@Override
	public int getInt(String key) {
		return getInt(key, 0);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return W.C.toInt(getString(key), defaultValue);
	}

	@Override
	public byte getByte(String key) {
		return getByte(key, Byte.parseByte("0"));
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		return W.C.toByte(getString(key), defaultValue);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return W.C.toLong(getString(key), defaultValue);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		return W.C.toShort(getString(key), defaultValue);
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		return W.C.value(getString(key), defaultValue);
	}

	@Override
	public boolean exists(String key) {
		return E.isNotEmpty(getString(key));
	}
}
