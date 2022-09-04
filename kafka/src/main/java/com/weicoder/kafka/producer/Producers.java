package com.weicoder.kafka.producer;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.weicoder.common.constants.C.A;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.W;
import com.weicoder.common.lang.W.B;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.util.U.S;
import com.weicoder.json.JsonEngine;
import com.weicoder.kafka.params.KafkaParams;
import com.weicoder.protobuf.Protobuf;
import com.weicoder.protobuf.ProtobufEngine;

/**
 * kafka生产者
 * 
 * @author WD
 */
public class Producers {
	// 日志
	private final static Log LOG = LogFactory.getLog(Producers.class);
	// 生产者
	private Producer<byte[], byte[]> producer;

	public Producers(String name) {
		// 设置属性
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaParams.getServers(name));
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 30);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 50);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, KafkaParams.getCompress(name));
		props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 20000);
		props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 20000);
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
	 * 
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
	 * 
	 * @param  topic 节点
	 * @param  value 值
	 * @return       信息
	 */
	public Future<RecordMetadata> send(String topic, Object value) {
		return send(topic, value, (metadata, exception) -> LOG
				.debug("kafka send producer metadata={} exception={} value={}", metadata, exception, value));
	}

	/**
	 * 发送数据
	 * 
	 * @param  topic 节点
	 * @param  key   键
	 * @param  value 值
	 * @return       信息
	 */
	public Future<RecordMetadata> send(String topic, Object key, Object value) {
		return send(topic, key, value,
				(metadata, exception) -> LOG.debug("kafka send producer metadata={} exception={} key={} value={}",
						metadata, exception, key, value));
	}

	/**
	 * 发送数据
	 * 
	 * @param  topic    节点
	 * @param  value    值
	 * @param  callback 回调
	 * @return          信息
	 */
	public Future<RecordMetadata> send(String topic, Object value, Callback callback) {
		return producer.send(newRecord(topic, value), callback);
	}

	/**
	 * 发送数据
	 * 
	 * @param  topic    节点
	 * @param  key      键
	 * @param  value    值
	 * @param  callback 回调
	 * @return          信息
	 */
	public Future<RecordMetadata> send(String topic, Object key, Object value, Callback callback) {
		return producer.send(newRecord(topic, key, value), callback);
	}

	/**
	 * 实例化一个消费数据
	 * 
	 * @param  topic topic
	 * @param  value value
	 * @return       ProducerRecord
	 */
	public ProducerRecord<byte[], byte[]> newRecord(String topic, Object value) {
		return new ProducerRecord<byte[], byte[]>(topic, toBytes(value));
	}

	/**
	 * 实例化一个消费数据
	 * 
	 * @param  topic topic
	 * @param  key   key
	 * @param  value value
	 * @return       ProducerRecord
	 */
	public ProducerRecord<byte[], byte[]> newRecord(String topic, Object key, Object value) {
		return new ProducerRecord<byte[], byte[]>(topic, toBytes(key), toBytes(value));
	}

	/**
	 * 序列号对象为自己数组
	 * 
	 * @param  obj 对象
	 * @return     自己数组
	 */
	private byte[] toBytes(Object obj) {
		// 根据不同类型序列化
		if (obj == null)
			return A.BYTES_EMPTY;
		if (obj instanceof String)
			return W.C.toString(obj).getBytes();
		if (obj.getClass().isAnnotationPresent(Protobuf.class))
			return ProtobufEngine.toBytes(obj);
		if (B.isType(obj.getClass()))
			return Bytes.toBytes(obj);
		// 没有对应类型 转换成json返回
		return S.toBytes(JsonEngine.toJson(obj));
	}
}
