package com.schooner.MemCached;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import com.schooner.MemCached.SchoonerSockIOPool.TCPSockIO;
import com.schooner.MemCached.SchoonerSockIOPool.UDPSockIO; 

/**
 * {@link SchoonerSockIOFactory} is used to create and destroy socket for connection pool.
 * 
 * @author Meng Li
 * @since  2.6.0
 * @see    SchoonerSockIOFactory
 */
public class SchoonerSockIOFactory extends BasePooledObjectFactory<SchoonerSockIO> {

	protected GenericObjectPool<SchoonerSockIO> sockets;

	protected String host;

	protected int bufferSize;

	protected int socketTO;

	protected int socketConnectTO;

	protected boolean isTcp;

	protected boolean nagle;

	public SchoonerSockIOFactory(String host, boolean isTcp, int bufferSize, int socketTO, int socketConnectTO, boolean nagle) {
		super();
		this.host = host;
		this.isTcp = isTcp;
		this.bufferSize = bufferSize;
		this.socketTO = socketTO;
		this.socketConnectTO = socketConnectTO;
		this.nagle = nagle; 
	}

	@Override
	public PooledObject<SchoonerSockIO> makeObject() throws Exception {
		return wrap(create());
	}

	@Override
	public SchoonerSockIO create() throws Exception {
		return createSocket(host);
	}

	@Override
	public PooledObject<SchoonerSockIO> wrap(SchoonerSockIO arg0) {
		return new DefaultPooledObject<SchoonerSockIO>(arg0);
	}

	/**
	 * Creates a new SockIO obj for the given server. If server fails to connect, then return null and do not try<br/>
	 * again until a duration has passed. This duration will grow<br/>
	 * by doubling after each failed attempt to connect.
	 * 
	 * @param  host host:port to connect to
	 * @return      SockIO obj or null if failed to create
	 */
	protected final SchoonerSockIO createSocket(String host) throws Exception {
		SchoonerSockIO socket = null;

		if (isTcp) {
			socket = new TCPSockIO(sockets, host, bufferSize, socketTO, socketConnectTO, nagle);
		} else {
			socket = new UDPSockIO(sockets, host, bufferSize, socketTO);
		}

		return socket;
	}

	public void setSockets(GenericObjectPool<SchoonerSockIO> sockets) {
		this.sockets = sockets;
	}
}
