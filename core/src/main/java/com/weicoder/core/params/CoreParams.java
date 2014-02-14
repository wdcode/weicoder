package com.weicoder.core.params;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * WdCore包所用参数读取类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-05
 */
public final class CoreParams {
	/**
	 * Xml的根节点名称
	 */
	public final static String	XML_ROOT					= Params.getString("xml.root", "root");
	/**
	 * Xml的编码格式
	 */
	public final static String	XML_ENCODING				= Params.getString("xml.encoding", CommonParams.ENCODING);
	/**
	 * 线程池大小
	 */
	public final static int		THREAD_SIZE					= Params.getInt("thread.size", SystemConstants.CPU_NUM + 1);
	/**
	 * XML解析所用的包
	 */
	public final static String	XML_PARSE					= Params.getString("xml.parse", "jdom2");
	/**
	 * 压缩算法
	 */
	public final static String	ZIP							= Params.getString("zip", "zlib");
	/**
	 * BerkeleyDB资源文件
	 */
	public final static String	NOSQL_BERKELEYDB_RESOURCE	= Params.getString("nosql.berkeleydb.resource", StringConstants.EMPTY);
	/**
	 * MongoDB主机
	 */
	public final static String	NOSQL_HBASE_HOST			= Params.getString("nosql.hbase.host", "127.0.0.1");
	/**
	 * MongoDB端口
	 */
	public final static int		NOSQL_HBASE_PORT			= Params.getInt("nosql.hbase.port", 2181);
	/**
	 * 默认解析Excel所需要的包
	 */
	public final static String	EXCEL_PARSE					= Params.getString("excel.parse", "poi");
	/**
	 * Lucene索引保存位置
	 */
	public final static String	LUCENE_INDEX_PATH			= Params.getString("lucene.index.path");
	/**
	 * 同步或则异步打印日志
	 */
	public final static boolean	LOGS_SYNC					= Params.getBoolean("logs.sync", false);

	/**
	 * 私有构造
	 */
	private CoreParams() {}
}
