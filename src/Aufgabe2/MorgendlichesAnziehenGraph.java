package Aufgabe2;

import java.util.List;

public class MorgendlichesAnziehenGraph {
	
	public static void main(String[] args) {
		new MorgendlichesAnziehenGraph();
	}
	
	public MorgendlichesAnziehenGraph(){
		AdjacencyListDirectedGraph<String> graph = new AdjacencyListDirectedGraph<String>();
		graph.addVertex("nix");
		graph.addVertex("Strümpfe");
		graph.addVertex("Schuhe");
		graph.addVertex("Hose");
		graph.addVertex("Unterhose");
		graph.addVertex("Unterhemd");
		graph.addVertex("Hemd");
		graph.addVertex("Gürtel");
		graph.addVertex("Pullover");
		graph.addVertex("Mantel");
		graph.addVertex("Schal");
		graph.addVertex("Handschuhe");
		graph.addVertex("Mütze");
		graph.addEdge("nix", "Strümpfe");
		graph.addEdge("nix", "Unterhose");
		graph.addEdge("nix", "Unterhemd");
		graph.addEdge("nix", "Handschuhe");
		graph.addEdge("nix", "Mütze");
		graph.addEdge("Strümpfe", "Schuhe");
		graph.addEdge("Unterhose", "Hose");
		graph.addEdge("Hose", "Gürtel");
		graph.addEdge("Unterhemd", "Hemd");
		graph.addEdge("Hemd", "Pullover");
		graph.addEdge("Pullover", "Mantel");
		graph.addEdge("Mantel", "Schal");
		
		List<String> list = GraphTraversion.topologicalSort(graph);
		if(list == null){
			System.out.println("Graph enthält Zyklus");
		}else{
			for(String l:list){
				System.out.println("Topologische Suche: "+l);
			}
		}
	}
}
