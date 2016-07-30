package com.weicoder.socket;

import com.weicoder.common.init.Init;

/**
 * Socket初始化
 * @author WD
 */
public class SocketInit implements Init {

	@Override
	public void init() {
		Sockets.init();
	}
}
