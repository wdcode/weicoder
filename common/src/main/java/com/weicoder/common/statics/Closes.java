package com.weicoder.common.statics;

import java.io.OutputStream;

import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U;

/**
 * 关闭各种资源方法
 * 
 * @author WD
 */
public sealed class Closes permits S.C {
	/**
	 * 关闭Close流数据源接口
	 * 
	 * @param cs 流数据源
	 */
	public static void close(AutoCloseable... cs) {
		// 循环关闭资源
		for (AutoCloseable c : cs) {
			try {
				// 判断不为空
				if (!U.E.isEmpty(c)) {
					// 是输出流
					if (c instanceof OutputStream)
						((OutputStream) c).flush();
					// 关闭
					c.close();
				}
			} catch (Exception e) {
				Logs.warn(e);
			}
		}
	}
}
