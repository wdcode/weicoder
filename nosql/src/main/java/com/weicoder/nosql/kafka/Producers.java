package com.weicoder.nosql.kafka;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.nosql.params.KafkaParams;

/**
 * kafka生产者
 * @author WD
 */
public class Producers {
	// 日志
	private final static Log			LOG	= LogFactory.getLog(Producers.class);
	// 生产者
	private Producer<byte[], byte[]>	producer;

	public Producers(String name) {
		// 设置属性
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaParams.getServers(name));
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 3);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		LOG.info("KafkaProducers init complete props={}", props);
		// 实例化生产者
		producer = new KafkaProducer<>(props);
	}

	/**
	 * 获得生产者
	 * @return 生产者
	 */
	public Producer<byte[], byte[]> getProducer() {
		return producer;
	}

	/**
	 * 刷新缓存
	 */
	public void flush() {
		producer.flush();
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param value 值
	 * @return 信息
	 */
	public Future<RecordMetadata> send(String topic, Object value) {
		return send(topic, value, (metadata, exception) -> {
			LOG.debug("kafka send producer metadata={} exception={} value={}", metadata, exception, value);
		});
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 * @return 信息
	 */
	public Future<RecordMetadata> send(String topic, Object key, Object value) {
		return send(topic, key, value, (metadata, exception) -> {
			LOG.debug("kafka send producer metadata={} exception={} key={} value={}", metadata, exception, key, value);
		});
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param value 值
	 * @param callback 回调
	 * @return 信息
	 */
	public Future<RecordMetadata> send(String topic, Object value, Callback callback) {
		return producer.send(Kafkas.newRecord(topic, value), callback);
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 * @param callback 回调
	 * @return 信息
	 */
	public Future<RecordMetadata> send(String topic, Object key, Object value, Callback callback) {
		return producer.send(Kafkas.newRecord(topic, key, value), callback);
	}

	/**
	 * 异步发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public void asyn(String topic, Object value) {
		ExecutorUtil.pool().execute(() -> {
			send(topic, value);
		});
	}

	/**
	 * 异步发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public void asyn(String topic, Object key, Object value) {
		ExecutorUtil.pool().execute(() -> {
			send(topic, key, value);
		});
	}
}
