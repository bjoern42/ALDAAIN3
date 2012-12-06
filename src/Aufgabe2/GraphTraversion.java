package Aufgabe2;

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
		int[] inDegree = new int[g.getNumberOfVertexes()]; // Anz. noch nicht besuchter Vorgänger
		Queue<V> q = new LinkedList<V>();
		
		List<V> vertexList = g.getVertexList();
		for (int i=0;i<vertexList.size();i++) {
			inDegree[i] = g.getPredecessorVertexList(vertexList.get(i)).size(); //Anzahl der Vorgänger;
			if (inDegree[i] == 0){
				q.add(vertexList.get(i));
			}
		}
		int k = 0;
		while (! q.isEmpty() ) {
			V v = q.remove();
			list.add(v);
			k++;
			vertexList = g.getSuccessorVertexList(v);
			for (int i=0;i<vertexList.size();i++ ){
				inDegree[i] = g.getPredecessorVertexList(vertexList.get(i)).size();
				if (--inDegree[i] == 0){
					q.add(g.getAdjacentVertexList(v).get(i));
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
