package com.weicoder.influxdb.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.influxdb.Influx;
import com.weicoder.influxdb.params.InfluxParams;

/**
 * Influx工厂
 * 
 * @author wdcode
 *
 */
public final class InfluxFactory extends FactoryKey<String, Influx> {
	private final static InfluxFactory FACTORY = new InfluxFactory();

	/**
	 * 获得Influx
	 * 
	 * @return Influx
	 */
	public static Influx getInflux() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得Influx
	 * 
	 * @param name 名称
	 * @return Influx
	 */
	public static Influx getInflux(String name) {
		return FACTORY.getInstance(name);
	}

	@Override
	public Influx newInstance(String name) {
		return new Influx(InfluxParams.getUrl(name), InfluxParams.getUsername(name), InfluxParams.getPassword(name),
				InfluxParams.getDatabase(name), InfluxParams.getPolicy(name));
	}

	private InfluxFactory() {
	}
}
