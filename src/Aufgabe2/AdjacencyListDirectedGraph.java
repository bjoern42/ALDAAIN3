package Aufgabe2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V>{
HashMap<V, HashMap<V, Double>> previousVertexes = new HashMap<V, HashMap<V, Double>>();
HashMap<V, HashMap<V, Double>> nextVertexes = new HashMap<V, HashMap<V, Double>>();

	@Override
	public boolean addVertex(V v) {
		nextVertexes.put(v, new HashMap<V, Double>());
		if(previousVertexes.put(v, new HashMap<V, Double>()) == null){
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
		previousVertexes.get(w).put(v, weight);
		if(nextVertexes.get(v).put(w, weight)==null){
			return true;
		}
		return false;
	}

	@Override
	public boolean containsVertex(V v) {
		return nextVertexes.containsKey(v);
	}

	@Override
	public boolean containsEdge(V v, V w) {
		if(containsVertex(v)){
			return nextVertexes.get(v).containsKey(w);
		}
		return false;
	}

	@Override
	public double getWeight(V v, V w) {
		if(containsEdge(v, w)){
			return nextVertexes.get(v).get(w);
		}
		return 0;
	}

	@Override
	public int getNumberOfVertexes() {
		return nextVertexes.size();
	}

	@Override
	public int getNumberOfEdges() {
		int edges=0;
		for(HashMap<V, Double> map:nextVertexes.values()){
			edges+=map.size();
		}
		return edges;
	}

	@Override
	public List<V> getVertexList() {
		List<V> list = new LinkedList<V>();
		for(V v:nextVertexes.keySet()){
			list.add(v);
		}
		return list;
	}

	@Override
	public List<Edge<V>> getEdgeList() {
		List<Edge<V>> list = new LinkedList<Edge<V>>();
		for(V v:nextVertexes.keySet()){
			for(V w:nextVertexes.get(v).keySet()){
				list.add(new Edge<V>(v, w, nextVertexes.get(v).get(w)));
			}
		}
		return list;
	}

	@Override
	public List<V> getAdjacentVertexList(V v) {
		List<V> list = new LinkedList<V>();
		for(V w:nextVertexes.get(v).keySet()){
			list.add(w);
		}
		for(V w:previousVertexes.get(v).keySet()){
			list.add(w);
		}
		return list;
	}

	@Override
	public List<Edge<V>> getIncidentEdgeList(V v) {
		List<Edge<V>> list = new LinkedList<Edge<V>>();
		for(V w:nextVertexes.get(v).keySet()){
			list.add(new Edge<V>(v, w, nextVertexes.get(v).get(w)));
		}
		return list;
	}

	@Override
	public int getInDegree(V v) {
		return nextVertexes.get(v).size();
	}

	@Override
	public int getOutDegree(V v) {
		return previousVertexes.get(v).size();
	}

	@Override
	public List<V> getPredecessorVertexList(V v) {
		List<V> list = new LinkedList<V>();
		for(V w:previousVertexes.get(v).keySet()){
			list.add(w);
		}
		return list;
	}

	@Override
	public List<V> getSuccessorVertexList(V v) {
		List<V> list = new LinkedList<V>();
		for(V w:nextVertexes.get(v).keySet()){
			list.add(w);
		}
		return list;
	}

	@Override
	public List<Edge<V>> getOutgoingEdgeList(V v) {
		List<Edge<V>> list = new LinkedList<Edge<V>>();
		for(V w:nextVertexes.get(v).keySet()){
			list.add(new Edge<V>(v, w, nextVertexes.get(v).get(w)));
		}
		return list;
	}

	@Override
	public List<Edge<V>> getIncomingEdgeList(V v) {
		List<Edge<V>> list = new LinkedList<Edge<V>>();
		for(V w:previousVertexes.get(v).keySet()){
			list.add(new Edge<V>(v, w, previousVertexes.get(v).get(w)));
		}
		return list;
	}
}
