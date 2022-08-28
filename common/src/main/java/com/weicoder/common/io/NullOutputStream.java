package com.weicoder.common.io;

import java.io.IOException;
import java.io.OutputStream; 

/**
 * 空输出流 临时为JDK8兼容
 * @author wdcode
 *
 */
public class NullOutputStream extends OutputStream {
	private volatile boolean closed;

    private void ensureOpen() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        }
    }

    @Override
    public void write(int b) throws IOException {
        ensureOpen();
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
//        Objects.checkFromIndexSize(off, len, b.length);
        ensureOpen();
    }

    @Override
    public void close() {
        closed = true;
    } 
}
