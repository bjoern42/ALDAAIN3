package Aufgabe1;

import java.util.Map;

public class MapDictionary<K,V> implements Dictionary<K,V>{
private Map<K,V> map = null;

	public MapDictionary(Map<K,V> pMap){
		map = pMap;
	}
	
	@Override
	public V insert(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public V search(K key) {
		return map.get(key);
	}

	@Override
	public V remove(K key) {
		return map.remove(key);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			builder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
		}
		return builder.toString();
	}
}