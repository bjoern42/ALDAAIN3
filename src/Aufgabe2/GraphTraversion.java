package Aufgabe2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class GraphTraversion {
	
	/**
	 * liefert eine vom Knoten s gestartete Tiefensuche als Liste zurück.
	 * @param g -Graph
	 * @param s - Vertex
	 * @return depthSearch List
	 */
	public static <V> List<V> depthFirstSearch(Graph<V> g, V s){
		Set<V> besucht = new TreeSet<V>();
		List<V> list = new LinkedList<V>();
		depthFirstSearch(g,s,besucht,list);
		return list;
	}
	
	private static <V> void depthFirstSearch(Graph<V> g, V s,Set<V> besucht,List<V> list){
		besucht.add(s);
		// Bearbeite v:
		list.add(s);
		for (V w:g.getAdjacentVertexList(s) ){
			if ( ! besucht.contains(w) ){ // w noch nicht besucht
				depthFirstSearch(g, w, besucht,list);
			}
		}
	}
	
	/**
	 * liefert eine vom Knoten s gestartete Breitensuche als Liste zurück.
	 * @param g -Graph
	 * @param s - Vertex
	 * @return breadthSearch List
	 */
	public static <V> List<V> breadthFirstSearch(Graph<V> g, V s){
		Set<V> besucht = new TreeSet<V>();
		List<V> list = new LinkedList<V>();
		breadthFirstSearch(g,s,besucht,list);
		return list;
	}
	
	private static <V> void breadthFirstSearch(Graph<V> g, V s,Set<V> besucht,List<V> list){
		Queue<V> q = new LinkedList<V>();
		q.add(s);
		while( !q.isEmpty() ) {
			s = q.remove();
			if ( besucht.contains(s) ){
				continue;
			}
			besucht.add(s);
			// Bearbeite v:
			list.add(s);
			for (V w:g.getAdjacentVertexList(s) ){
				if ( ! besucht.contains(w) ){
					q.add(w);
				}
			}
		}
	}
	
	/**
	 * liefert eine topologische Sortierung des gerichteten Graphen g als Liste zurück.
	 * @param g -Graph
	 * @param s - Vertex
	 * @return topologicalSort List
	 */
	public static <V> List<V> topologicalSort(DirectedGraph<V> g){
		List<V> list = new LinkedList<V>();
		HashMap<V,Integer> inDegree = new HashMap<V, Integer>(); // Anz. noch nicht besuchter Vorgänger
		Queue<V> q = new LinkedList<V>();
		
		for (V v:g.getVertexList()) {
			inDegree.put(v, g.getPredecessorVertexList(v).size()); //Anzahl der Vorgänger;
			if (inDegree.get(v) == 0){
				q.add(v);
			}
		}
		int k = 0;
		while (! q.isEmpty() ) {
			V v = q.remove();
			list.add(v);
			k++;
			for (V w:g.getSuccessorVertexList(v)){
				inDegree.put(w,inDegree.get(w)-1);
				if (inDegree.get(w) == 0){
					q.add(w);
				}
			}
			
		}
		if (k != g.getNumberOfVertexes()){
			return null; // Graph zyklisch;
		}else{
			return list;
		}
	}
}
