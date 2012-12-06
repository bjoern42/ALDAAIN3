package Aufgabe1;

public class SortedArrayDictionary<K extends Comparable<? super K>,V> implements Dictionary<K,V> {
private static final int DEF_CAPACITY = 16;
private int size;
private Entry<K,V>[] data;

	private static class Entry<K,V> {
		K key;
		V value;
		Entry(K k, V v) {
			key = k; 
			value = v;
		}
	};
	
	@SuppressWarnings("unchecked")
	public SortedArrayDictionary(){
		size = 0;
		data = new Entry[DEF_CAPACITY];
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void ensureCapacity(int newCapacity) {
		if (newCapacity < size){
			return;
		}
		Entry[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public V insert(K key, V value) {
		int i = searchKey(key);
		// Vorhandener Eintrag wird überschrieben:
		if (i != -1) {
			V r = data[i].value;
			data[i].value = value;
			return r;
		}
		// Neueintrag:
		if (data.length == size) {
			ensureCapacity(2*size);
		}
		int j = size-1;
		while (j >= 0 && key.compareTo(data[j].key) < 0) {
			data[j+1] = data[j];
			j--;
		}
		data[j+1] = new Entry(key,value);
		size++;
		return null;
	}

	private int searchKey(K key) {
		int li = 0;
		int re = size-1;
		while (re >= li) {
			int m = (li + re)/2;
			if (key.compareTo(data[m].key) < 0){
				re = m - 1;
			}else if (key.compareTo(data[m].key) > 0){
				li= m + 1;
			}else{
				return m;
			}
		}
		return -1;
	}
	
	@Override
	public V search(K key) {
		int m = searchKey(key);
		if(m != -1){
			return data[m].value;
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int m = searchKey(key);
		if(m == -1){
			return null;
		}
		
		int j = 0;
		while (j < size && key.compareTo(data[j].key) > 0) {
			data[j] = data[j+1];
			j++;
		}
		
		data[j] = null;
		size--;
		return null;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<size; i++){
			builder.append(data[i].key).append("=").append(data[i].value);
			if( i < size-1){
				builder.append("\n");
			}
		}
		return builder.toString();
	}
}