package Aufgabe2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListUndirectedGraph<V> implements UndirectedGraph<V>{
HashMap<V, HashMap<V, Double>> adjacencyList = new HashMap<V, HashMap<V, Double>>();

	@Override
	public boolean addVertex(V v) {
		if(adjacencyList.containsKey(v)){
			return false;
		}
		if(adjacencyList.put(v, new HashMap<V, Double>()) == null){
			return true;
		}
		return false;
	}

	@Override
	public boolean addEdge(V v, V w) {
		return addEdge(v,w,1);
	}

	@Override
	public boolean addEdge(V v, V w, double weight) {
		boolean retVal = false;
		if(adjacencyList.get(w).put(v, weight) == null){
			retVal = true;
		}
		if(adjacencyList.get(v).put(w, weight)==null){
			retVal = true;
		}
		return retVal;
	}

	@Override
	public boolean containsVertex(V v) {
		return adjacencyList.containsKey(v);
	}

	@Override
	public boolean containsEdge(V v, V w) {
		if(containsVertex(v)){
			return adjacencyList.get(v).containsKey(w);
		}
		return false;
	}

	@Override
	public double getWeight(V v, V w) {
		if(containsEdge(v, w)){
			return adjacencyList.get(v).get(w);
		}
		return 0;
	}

	@Override
	public int getNumberOfVertexes() {
		return adjacencyList.size();
	}

	@Override
	public int getNumberOfEdges() {
		int edges=0;
		for(HashMap<V, Double> map:adjacencyList.values()){
			edges+=map.size();
		}
		return edges/2;
	}

	@Override
	public List<V> getVertexList() {
		List<V> list = new LinkedList<V>();
		for(V v:adjacencyList.keySet()){
			list.add(v);
		}
		return list;
	}

	@Override
	public List<Edge<V>> getEdgeList() {
		List<Edge<V>> list = new LinkedList<Edge<V>>();
		for(V v:adjacencyList.keySet()){
			for(V w:adjacencyList.get(v).keySet()){
				list.add(new Edge<V>(v, w, adjacencyList.get(v).get(w)));
			}
		}
		return list;
	}

	@Override
	public List<V> getAdjacentVertexList(V v) {
		List<V> list = new LinkedList<V>();
		for(V w:adjacencyList.get(v).keySet()){
			list.add(w);
		}
		return list;
	}

	@Override
	public List<Edge<V>> getIncidentEdgeList(V v) {
		List<Edge<V>> list = new LinkedList<Edge<V>>();
		for(V w:adjacencyList.get(v).keySet()){
			list.add(new Edge<V>(v, w, adjacencyList.get(v).get(w)));
		}
		return list;
	}

	@Override
	public int getDegree(V v) {
		return adjacencyList.get(v).size();
	}
}
