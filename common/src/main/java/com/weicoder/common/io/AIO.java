package com.weicoder.common.io;

import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.interfaces.CallbackVoid;

/**
 * 异步IO实现
 * @author wdcode
 *
 */
public class AIO implements IO {

	@Override
	public byte[] read(InputStream in, boolean isClose) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long read(InputStream in, int buff, boolean isClose, CallbackVoid<Buffer> call) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long write(OutputStream out, InputStream in, boolean isClose) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long write(OutputStream out, InputStream in, int buff, boolean isClose, Callback<Buffer, Buffer> call) {
		// TODO Auto-generated method stub
		return 0;
	}

}
