package com.weicoder.xml.params;

import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * WdCore包所用参数读取类
 * @author WD
 * @version 1.0
 */
public final class XmlParams {
	/** Xml的根节点名称 */
	public final static String	ROOT		= Params.getString("xml.root", "root");
	/** Xml的编码格式 */
	public final static String	ENCODING	= Params.getString("xml.encoding", CommonParams.ENCODING);
	/** Xml的解析包 */
	public final static String	PARSE		= Params.getString("xml.parse", "dom4j");

	private XmlParams() {
	}
}
