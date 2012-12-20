package Aufgabe1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;

public class FileHandler {
private BufferedReader reader = null;
private FileWriter writer = null;
private GUI gui = null;
private Dictionary<String, String> dic = null;
private long startTime = 0, stopTime = 0;
private boolean evaluate = false;

	public FileHandler(GUI pGUI){
		gui = pGUI;
	}
	
	public void save(File file){
		try {
			writer = new FileWriter(file);
			writer.write(dic.toString().replace("=", " "));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read(File file){
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			switch (gui.getDictType()){
				case DictionaryMenuBar.TREE_MAP:{
					dic = new MapDictionary<String, String>(new TreeMap<String, String>());
					break;
				}
				case DictionaryMenuBar.HASH_MAP:{
					dic = new MapDictionary<String, String>(new HashMap<String, String>());
					break;
				}
				case DictionaryMenuBar.SORTED_ARRAY:{
					dic = new SortedArrayDictionary<String, String>();
					break;
				}
				case DictionaryMenuBar.TREE:{
					dic = new TreeDictionary<String, String>();
					break;
				}
				case DictionaryMenuBar.HASH:{
					dic = new HashDictionary<String, String>();
					break;
				}
			}
			String buffer="";
			startTime();
			while((buffer=reader.readLine()) != null){
				String temp[] = buffer.split(" ");
				if(temp.length == 2){
					dic.insert(temp[0], temp[1]);
				}
			}
			stopTime();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void evaluateTime(boolean pEvaluate){
		evaluate = pEvaluate;
	}
	
	public long getCpuTime(){
		return stopTime-startTime;
	}
	
	public void startTime(){
		if(evaluate){
			startTime = System.nanoTime();
		}else{
			startTime = 0;
		}
	}
	
	public void stopTime(){
		if(evaluate){
			stopTime = System.nanoTime();
		}else{
			stopTime = 0;
		}
	}
	
	public String insert(String key, String value){
		startTime();
		String retVal = dic.insert(key, value);
		stopTime();
		return retVal;
	}
	
	public String remove(String key){
		startTime();
		String retVal = dic.remove(key);
		stopTime();
		return retVal;
	}
	
	public String search(String key){
		startTime();
		String retVal = dic.search(key);
		stopTime();
		return retVal;
	}
	
	public Dictionary<String, String> getDictionary(){
		return dic;
	}
}