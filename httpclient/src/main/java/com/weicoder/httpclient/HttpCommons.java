package com.weicoder.httpclient;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.AbstractHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import com.weicoder.common.io.I;

/**
 * 公共服务
 * 
 * @author wdcode
 *
 */
public class HttpCommons {
	//
	public final static HttpClientResponseHandler<String>	HCRHS	= new BasicHttpClientResponseHandler();
	public final static HttpClientResponseHandler<byte[]>	HCRHB	= new AbstractHttpClientResponseHandler<byte[]>() {
																		@Override
																		public byte[] handleEntity(HttpEntity entity)
																				throws IOException {
																			return I.read(entity.getContent());
																		}
																	};
}
