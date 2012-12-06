package Aufgabe2;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		new Test();
	}

	public Test(){
		AdjacencyListDirectedGraph<Integer> graph = new AdjacencyListDirectedGraph<Integer>();
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);
		graph.addVertex(4);
		graph.addEdge(1, 2, 42.0);
		graph.addEdge(1, 3, 5.0);
		graph.addEdge(2, 4, 6.0);
		System.out.println("Kante 1-2: "+graph.containsEdge(1, 2));
		System.out.println("Kante 2-3: "+graph.containsEdge(2, 3));
		System.out.println("Kante 1-3: "+graph.containsEdge(1, 3));
		System.out.println("Knoten 1: "+graph.containsVertex(1));
		System.out.println("Knoten 2: "+graph.containsVertex(2));
		System.out.println("Knoten 3: "+graph.containsVertex(3));
		System.out.println("Knoten 42: "+graph.containsVertex(42));
		System.out.println("Anzahl Knoten: "+graph.getNumberOfVertexes());
		System.out.println("Anzahl Kanten: "+graph.getNumberOfEdges());
		System.out.println("Gewicht 1-2: "+graph.getWeight(1, 2));
		System.out.println("Gewicht 1-3: "+graph.getWeight(1, 3));
		System.out.println("InDegree Knoten 2: "+graph.getInDegree(2));
		System.out.println("OutDegree Knoten 2: "+graph.getOutDegree(2));
		System.out.println("VertexList:");
		for(Integer l:graph.getVertexList()){
			System.out.println("Knoten: "+l);
		}
		System.out.println("EdgeList:");
		for(Edge<Integer> e:graph.getEdgeList()){
			System.out.println("Kanten: "+e);
		}
		System.out.println("AdjacentVertexList 1:");
		for(Integer l:graph.getAdjacentVertexList(1)){
			System.out.println("Benachtbarte Knoten von 1: "+l);
		}
		System.out.println("IncidentEdgeList 1:");
		for(Edge<Integer> e:graph.getIncidentEdgeList(1)){
			System.out.println("Kanten mit Start 1: "+e);
		}
		System.out.println("PredecessorVertexList 2:");
		for(Integer l:graph.getPredecessorVertexList(2)){
			System.out.println("Vorgängerknoten von 2: "+l);
		}
		System.out.println("SuccessorVertexList 2:");
		for(Integer l:graph.getSuccessorVertexList(2)){
			System.out.println("Nachfolgerknoten von 2: "+l);
		}
		
		System.out.println("Tiefensuche von "+graph.getVertexList().get(0));
		for(Integer l:GraphTraversion.depthFirstSearch(graph, graph.getVertexList().get(0))){
			System.out.println("Tiefensuche: "+l);
		}
		System.out.println("Breitensuche von "+graph.getVertexList().get(0));
		for(Integer l:GraphTraversion.breadthFirstSearch(graph, graph.getVertexList().get(0))){
			System.out.println("Breitensuche: "+l);
		}
		System.out.println("Topologische Suche von "+graph.getVertexList().get(0));
		List<Integer> list = GraphTraversion.topologicalSort(graph);
		if(list == null){
			System.out.println("Graph enthält Zyklus");
		}else{
			for(Integer l:list){
				System.out.println("Topologische Suche: "+l);
			}
		}
	}
}
