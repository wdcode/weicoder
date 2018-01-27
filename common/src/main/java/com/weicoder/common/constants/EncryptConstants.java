package com.weicoder.common.constants;

/**
 * 加密常量
 * @author WD
 */
public final class EncryptConstants {
	/** 加密算法 MD5 */
	public final static String	ALGO_MD5			= "MD5";
	/** 加密算法 SHA-1 */
	public final static String	ALGO_SHA_1			= "SHA-1";
	/** 加密算法 SHA-256 */
	public final static String	ALGO_SHA_256		= "SHA-256";
	/** 加密算法 SHA-384 */
	public final static String	ALGO_SHA_384		= "SHA-384";
	/** 加密算法 SHA-512 */
	public final static String	ALGO_SHA_512		= "SHA-512";
	/** 加密算法 HmacSHA1 */
	public final static String	ALGO_HMAC_SHA_1		= "HmacSHA1";
	/** 加密算法 HmacSHA1 */
	public final static String	ALGO_HMAC_SHA_256	= "HmacSHA256";
	/** 加密算法 HmacSHA1 */
	public final static String	ALGO_HMAC_SHA_384	= "HmacSHA384";
	/** 加密算法 HmacSHA1 */
	public final static String	ALGO_HMAC_SHA_512	= "HmacSHA512";
	/** 加密算法 DES */
	public final static String	ALGO_DES			= "DES";
	/** 加密算法 AES */
	public final static String	ALGO_AES			= "AES";
	/** 加密算法 RC4 */
	public final static String	ALGO_RC4			= "RC4";
	/** DES加密使用的密钥的长度(位) */
	public final static int		LENGTH_DES			= 8;
	/** AES加密使用的密钥的长度(位) */
	public final static int		LENGTH_AES			= 16;
	/** RC4加密使用的密钥的长度(位) */
	public final static int		LENGTH_RC4			= 16;

	private EncryptConstants() {}
}
