package edu.fudan.hgmonitor.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BaseCache<K, V> {

	private Map<K, Value> cache;
	
	public BaseCache() {
		cache = new ConcurrentHashMap<K, Value>();
	}
	
	public void insertOrUpdateCache(K key, V value) {
		Value val = new Value(value);
		cache.put(key, val);
	}
	
	public void removeEntry(K key) {
		if (key == null) {
			return;
		}
		cache.remove(key);	
	}
	
	public V get(K key) {
		return cache.get(key).getValue();
	}
	
	public int getSize() {
		return cache.size();
	}
	
	public Set<K> getKeySet() {
		return cache.keySet();
	}
	
	public boolean isEntryValid(K key) {
		return (System.currentTimeMillis() - cache.get(key).getTimestamp()) < 
				ConfigCache.getInstance().getConfigInfo().getTimeout();
	}
	
	class Value {
		private V value;
		private long timestamp;
		
		public Value(V value) {
			this.value = value;
			timestamp = System.currentTimeMillis();
		}
		
		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}	
	}

}
