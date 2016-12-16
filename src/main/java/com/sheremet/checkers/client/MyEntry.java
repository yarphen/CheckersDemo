package com.sheremet.checkers.client;

import java.util.Map;

public class MyEntry<K,V> implements Map.Entry<K,V>{
	private final K key;
	private V value;
	public MyEntry(K k, V v) {
		key = k;
		value = v;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V oldValue = getValue();
		this.value = value;
		return oldValue;
	}

}