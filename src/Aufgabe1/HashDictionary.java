package Aufgabe1;

import java.util.LinkedList;

public class HashDictionary<K,V> implements Dictionary<K,V> {
private static final int DEF_CAPACITY = 541;
@SuppressWarnings("unchecked")
private LinkedList<Entry<K,V>> data[] = new LinkedList[DEF_CAPACITY];
	
	private static class Entry<K,V> {
		K key;
		V value;
		Entry(K k, V v) {
			key = k; 
			value = v;
		}
	};
	
	@Override
	public V insert(K key, V value) {
		int index = getIndex(key);
		if(data[index] == null){
			data[index] = new LinkedList<HashDictionary.Entry<K,V>>();
		}
		for(Entry<K,V> entry:data[index]){
			if(entry.key.equals(key)){
				V v = entry.value;
				entry.value = value;
				return v;
			}
		}
		data[index].add(new Entry<K, V>(key, value));
		return null;
	}

	private int getIndex(K key){
		int index = key.hashCode();
		if(index < 0){
			index *= -1;
		}
		return index % DEF_CAPACITY;
	}
	
	@Override
	public V search (K key) {
		int index = getIndex(key);
		if(data[index] == null){
			return null;
		}
		for(Entry<K,V> entry:data[index]){
			if(entry.key.equals(key)){
				return entry.value;
			}
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int index = getIndex(key);
		if(data[index] == null){
			return null;
		}
		for(Entry<K,V> entry:data[index]){
			if(entry.key.equals(key)){
				V value = entry.value;
				data[index].remove(entry);
				return value;
			}
		}
		return null;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<DEF_CAPACITY;i++){
			if(data[i] == null){
				continue;
			}
			for(Entry<K,V> entry:data[i]){
				builder.append(entry.key).append("=").append(entry.value).append("\n");
			}
		}
		return builder.toString();
	}
}