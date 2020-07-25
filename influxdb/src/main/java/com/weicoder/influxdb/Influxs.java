package com.weicoder.influxdb;

import com.weicoder.influxdb.factory.InfluxFactory;

/**
 * Influx操作类
 * 
 * @author wdcode
 *
 */
public class Influxs {
	/** 默认Influx */
	public final static Influx INFLUX = InfluxFactory.getInflux();

	private Influxs() {
	}
}
