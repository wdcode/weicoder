package com.weicoder.nosql.kafka.consumer;

/**
 * kafka 消费记录
 * @author WD
 */
public class Record<K, V> {
	// topic
	private String	topic;
	// key
	private K		key;
	// value
	private V		value;
	// offset
	private long	offset;
	// 时间戳
	private long	time;

	/**
	 * 构造方法
	 */
	public Record() {}

	/**
	 * 构造方法
	 * @param topic topic
	 * @param key key
	 * @param value value
	 * @param offset offset
	 * @param time time
	 */
	public Record(String topic, K key, V value, long offset, long time) {
		super();
		this.topic = topic;
		this.key = key;
		this.value = value;
		this.offset = offset;
		this.time = time;
	}

	/**
	 * 获得topic
	 * @return topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * 获得key
	 * @return key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * 获得value
	 * @return value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * 获得offset
	 * @return offset
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * 获得时间戳
	 * @return 时间戳
	 */
	public long getTime() {
		return time;
	}
}
