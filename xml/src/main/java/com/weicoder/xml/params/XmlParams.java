package com.weicoder.xml.params;

import com.weicoder.common.params.P;

/**
 * XML包所用参数读取类
 * 
 * @author WD
 */
public final class XmlParams {
	private XmlParams() {
	}

	/** Xml的根节点名称 */
	public final static String	ROOT		= P.getString("xml.root", "root");
	/** Xml的编码格式 */
	public final static String	ENCODING	= P.getString("xml.encoding", P.C.ENCODING);
	/** Xml的解析包 */
	public final static String	PARSE		= P.getString("xml.parse", "dom4j");
}
