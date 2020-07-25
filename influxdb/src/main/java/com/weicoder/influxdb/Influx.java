package com.weicoder.influxdb;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point; 
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult; 

/**
 * InfluxDB实现
 * 
 * @author wdcode
 *
 */
public class Influx {
	private InfluxDB	db;
	// 表名
	private String		database;
	// 策略
	private String		retentionPolicy;

	/**
	 * 构造
	 * 
	 * @param url             连接
	 * @param username        用户名
	 * @param password        密码
	 * @param database        表面
	 * @param retentionPolicy 策略
	 */
	public Influx(String url, String username, String password, String database, String retentionPolicy) {
		db = InfluxDBFactory.connect(url, username, password);
		this.database = database;
		this.retentionPolicy = retentionPolicy;
	}

	/**
	 * 获得InfluxDB
	 * 
	 * @return InfluxDB
	 */
	public InfluxDB db() {
		return db;
	}

	/**
	 * 测试连接是否正常
	 * 
	 * @return true 正常
	 */
	public boolean ping() {
		return db.ping().isGood();
	}

	/**
	 * 查询
	 * 
	 * @param command 查询语句
	 * @return
	 */
	public QueryResult query(String command) {
		return db.query(new Query(command, database));
	}

	/**
	 * 插入
	 * 
	 * @param measurement 表
	 * @param tags        标签
	 * @param fields      字段
	 */
	public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
			TimeUnit timeUnit) {
		db.write(database, retentionPolicy,
				Point.measurement(measurement).tag(tags).fields(fields).time(time, timeUnit).build());
	}

	/**
	 * 批量插入
	 * 
	 * @param measurement    表
	 * @param tagsfieldsList 标签字段列表
	 */
	public void insertMany(String measurement, List<Map<Map<String, String>, Map<String, Object>>> tagsfieldsList) {
		// 声明BatchPoints
		BatchPoints batchPoints = BatchPoints.database(database).consistency(ConsistencyLevel.ONE)
				.precision(TimeUnit.MILLISECONDS).build();
		// 设置标签字段.
		tagsfieldsList.forEach(
				tf -> tf.forEach((k, v) -> batchPoints.point(Point.measurement(measurement).tag(k).fields(v).build())));
		// 写数据
		db.write(batchPoints);
	}

	/**
	 * 单条插入
	 * 
	 * @param measurement 表
	 * @param tags        标签
	 * @param fields      字段
	 */
	public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields) {
		db.write(database, retentionPolicy, Point.measurement(measurement).tag(tags).fields(fields).build());
	}
}
