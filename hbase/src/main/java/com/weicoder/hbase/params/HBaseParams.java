package com.weicoder.hbase.params;

import com.weicoder.common.U.IP;
import com.weicoder.common.params.Params;

/**
 * hbase 参数
 * 
 * @author wudi
 */
public final class HBaseParams {
	/** hbase主机 */
	public final static String HOST = Params.getString("hbase.host", IP.LOCAL_IP);
	/** hbase端口 */
	public final static int    PORT = Params.getInt("hbase.port", 2181);
}
