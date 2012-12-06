package Aufgabe1;

import java.util.HashMap;
import java.util.TreeMap;

public class test {
	public static void main(String[] args) {
		SortedArrayDictionary<String, Integer> array = new SortedArrayDictionary<String, Integer>();
		testDic(array);
		System.out.println("--------------");
		MapDictionary<String, Integer> map = new MapDictionary<String, Integer>(new TreeMap<String,Integer>());
		testDic(map);
		System.out.println("--------------");
		map = new MapDictionary<String, Integer>(new HashMap<String,Integer>());
		testDic(map);
		System.out.println("--------------");
		HashDictionary<String, Integer> hash = new HashDictionary<String, Integer>();
		testDic(hash);
		System.out.println("--------------");
		TreeDictionary<String, Integer> tree = new TreeDictionary<String, Integer>();
		testDic(tree);
	}
	
	private static void testDic(Dictionary<String,Integer> dic){
		dic.insert("blub", 41);
		dic.insert("blab",43);
		dic.insert("asdfasdf", 122);
		System.out.println(dic);
		dic.remove("blub");
		dic.insert("blab", 42);
		System.out.println(dic.search("blab"));
	}
}
