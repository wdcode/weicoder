package com.weicoder.base.params;

import java.util.List;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.params.Params;

/**
 * 上传参数
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-18
 */
public final class UploadParams {
	/**
	 * 上传文件路径
	 */
	public final static String			UPLOAD_PATH					= Params.getString("upload.path", "upload/");
	/**
	 * 是否上传的资源目录
	 */
	public final static boolean			UPLOAD_RESOURCE				= Params.getBoolean("upload.resource", false);
	/**
	 * 是否使用服务器地址保存文件路径
	 */
	public final static boolean			UPLOAD_SERVER				= Params.getBoolean("upload.server", true);
	/**
	 * 是否需要上传后缀名
	 */
	public final static boolean			UPLOAD_SUFFIX				= Params.getBoolean("upload.suffix", true);
	/**
	 * 是否需要上传后缀名
	 */
	public final static List<String>	UPLOAD_POSTFIX				= Params.getList("upload.postfix", Lists.getList(StringConstants.EMPTY));
	/**
	 * 是否需要上传后压缩图片
	 */
	public final static boolean			UPLOAD_IMAGE_COMPRESS_POWER	= Params.getBoolean("upload.image.compress.power", false);
	/**
	 * 是否需要上传后压缩图片
	 */
	public final static List<String>	UPLOAD_IMAGE_COMPRESS_NAMES	= Params.getList("upload.image.compress.names", Lists.getList(StringConstants.EMPTY));

	/**
	 * 获得压缩图片宽度<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: upload.image.compress.xxx.width = ? <br/>
	 * XML:
	 * {@literal <upload><image><compress><xxx><width>?</width></xxx></compress></image></upload>}</h2>
	 * @return 压缩图片宽度
	 */
	public static int getWidth(String name) {
		return Params.getInt(Params.getKey("upload.image.compress", name, "width"), 0);
	}

	/**
	 * 获得压缩图片高度<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: upload.image.compress.xxx.height = ? <br/>
	 * XML:
	 * {@literal <upload><image><compress><xxx><height>?</height></xxx></compress></image></upload>}
	 * </h2>
	 * @return 压缩图片宽度
	 */
	public static int getHeight(String name) {
		return Params.getInt(Params.getKey("upload.image.compress", name, "height"), 0);
	}

	private UploadParams() {}
}
