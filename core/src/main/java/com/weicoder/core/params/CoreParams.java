package com.weicoder.core.params;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * WdCore包所用参数读取类
 * @author WD 
 * @version 1.0 
 */
public final class CoreParams {
	/**  Xml的根节点名称 */
	public final static String	XML_ROOT					= Params.getString("xml.root", "root");
	/** Xml的编码格式 */
	public final static String	XML_ENCODING				= Params.getString("xml.encoding", CommonParams.ENCODING);
	/** 线程池大小  */
	public final static int		THREAD_SIZE					= Params.getInt("thread.size", SystemConstants.CPU_NUM + 1);
	/** XML解析所用的包 */
	public final static String	XML_PARSE					= Params.getString("xml.parse", "jdom2");
	/** 压缩算法 */
	public final static String	ZIP							= Params.getString("zip", "zlib");
	/** BerkeleyDB资源文件 */
	public final static String	NOSQL_BERKELEYDB_RESOURCE	= Params.getString("nosql.berkeleydb.resource", StringConstants.EMPTY);
	/** MongoDB主机 */
	public final static String	NOSQL_HBASE_HOST			= Params.getString("nosql.hbase.host", "127.0.0.1");
	/** MongoDB端口 */
	public final static int		NOSQL_HBASE_PORT			= Params.getInt("nosql.hbase.port", 2181);
	/** 默认解析Excel所需要的包 */
	public final static String	EXCEL_PARSE					= Params.getString("excel.parse", "poi");
	/** Lucene索引保存位置 */
	public final static String	LUCENE_INDEX_PATH			= Params.getString("lucene.index.path");
	/** 系统发送邮件所用地址 */
	public final static String	EMAIL_FROM					= Params.getString("email.from", StringConstants.EMPTY);
	/** 邮件密码 */
	public final static String	EMAIL_PASSWORD				= Params.getString("email.password", StringConstants.EMPTY);
	/** 邮件服务器Host */
	public final static String	EMAIL_HOST					= Params.getString("email.host", StringConstants.EMPTY);
	/** 发送Email使用的默认包 */
	public final static String	EMAIL_PARSE					= Params.getString("email.parse", "Apache");
	/** 发送Email是否验证 */
	public final static boolean	EMAIL_AUTH					= Params.getBoolean("email.auth", true);
	/** 发送Email使用的编码格式 */
	public final static String	EMAIL_ENCODING				= Params.getString("email.encoding", CommonParams.ENCODING);

	private CoreParams() {}
}
