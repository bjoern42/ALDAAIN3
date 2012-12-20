package Aufgabe2.ScotlandYard.src.sim;

import Aufgabe2.AdjacencyListUndirectedGraph;
import Aufgabe2.DijkstraShortestPath;
import Aufgabe2.GraphTraversion;
import Aufgabe2.ScotlandYard.src.sim.SYSimulation;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SYDemo {
	public static void main(String[] args) {
		new SYDemo().simSearchShortestPath();
    }
	
	private void simSearchShortestPath(){
		try {
			SYSimulation sim;
			sim = new SYSimulation();
			AdjacencyListUndirectedGraph<Integer> graph = new AdjacencyListUndirectedGraph<Integer>();
			readStationsFromFile(new File(System.getProperty("user.dir")+"/src/Aufgabe2/ScotlandYard.txt"),graph);
			
			DijkstraShortestPath<Integer> dijkstra = new DijkstraShortestPath<Integer>(graph);
			int start = 93;
			int end = 66;
			dijkstra.searchAllShortestPaths(start);
			sim.startSequence("Test2");
			List<Integer> list = dijkstra.getShortestPathTo(end);
			if(list != null){
				Integer last=dijkstra.getIndexOf(graph.getVertexList().get(start));
				Color color;
				for(Integer l:list){
					if(graph.getWeight(l, last) == 5){
						color = Color.RED;
					}else if(graph.getWeight(l, last) == 2){
						color = Color.GREEN;
					}else{
						color = Color.YELLOW;
					}
					sim.drive(last, l, color);
					last = l;
				}
			}
			sim.stopSequence();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void simDepthFirstSearch(){
		try {
			SYSimulation sim;
			sim = new SYSimulation();
			AdjacencyListUndirectedGraph<Integer> graph = new AdjacencyListUndirectedGraph<Integer>();
			readTaxiStationsFromFile(new File(System.getProperty("user.dir")+"/src/Aufgabe2/ScotlandYard.txt"),graph);

			System.out.println(graph.getNumberOfVertexes()+" "+graph.getNumberOfEdges());
			sim.startSequence("Test1");
			List<Integer> list = GraphTraversion.depthFirstSearch(graph, 1);
			if(list != null){
				Integer last=graph.getVertexList().get(0);
				for(Integer l:list){
					sim.drive(last, l, Color.YELLOW);
					last = l;
				}
			}
			sim.stopSequence();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readStationsFromFile(File file,AdjacencyListUndirectedGraph<Integer> g) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String buffer="";
		while((buffer=reader.readLine()) != null){
			String tmp[] = buffer.split(" ");
			int v,w;
			v = Integer.valueOf(tmp[0]);
			w = Integer.valueOf(tmp[1]);
			g.addVertex(v);
			g.addVertex(w);
			
			if(buffer.endsWith("Taxi")){
				g.addEdge(v, w, 3);
			}else if(buffer.endsWith("Bus")){
				g.addEdge(v, w, 2);
			}else{
				g.addEdge(v, w, 5);
			}
		}
		reader.close();
	}
	
	private void readTaxiStationsFromFile(File file,AdjacencyListUndirectedGraph<Integer> g) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String buffer="";
		while((buffer=reader.readLine()) != null){
			if(buffer.endsWith("Taxi")){
				String tmp[] = buffer.split(" ");
				int v,w;
				v = Integer.valueOf(tmp[0]);
				w = Integer.valueOf(tmp[1]);
				g.addVertex(v);
				g.addVertex(w);
				g.addEdge(v, w);
			}
		}
		reader.close();
	}
	
	private void simOriginal(){
		 SYSimulation sim;
			try {
				sim = new SYSimulation();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			sim.startSequence("Test1");
	        sim.visitStation(9);
	        sim.visitStation(20);
	        sim.visitStation(33);
	        sim.visitStation(46);
	        sim.visitStation(79);
	        sim.visitStation(63);
			sim.drive(9, 20, Color.YELLOW);
	        sim.drive(20, 33, Color.YELLOW);
	        sim.drive(33, 46, Color.YELLOW);
	        sim.drive(46, 79, Color.RED.darker());
	        sim.drive(79, 63, Color.GREEN.darker());
	        sim.stopSequence();
	        
	        sim.startSequence("Test2");
	        sim.visitStation(15);
	        sim.visitStation(16);
	        sim.visitStation(28);
	        sim.visitStation(41);
	        sim.visitStation(29);
	        sim.visitStation(55);
	        sim.visitStation(89);
	        sim.visitStation(67);
			sim.drive(15, 16, Color.YELLOW);
	        sim.drive(16, 28, Color.YELLOW);
	        sim.drive(28, 41, Color.YELLOW);
	        sim.drive(41, 29, Color.GREEN.darker());
	        sim.drive(29, 55, Color.GREEN.darker());
	        sim.drive(55, 89, Color.GREEN.darker());
	        sim.drive(89, 67, Color.RED.darker());
	        sim.stopSequence();
	}
}
