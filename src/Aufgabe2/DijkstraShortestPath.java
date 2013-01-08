package Aufgabe2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DijkstraShortestPath<V> {
private HashMap<V, Integer> map = new HashMap<V, Integer>();
private Graph<V> graph = null;
private double d[];
private V p[],end;

	/**
	 * Legt eine neue Instanz an zum Finden von kuerzesten Wegen im Graphen g.
	 * @param g
	 */
	@SuppressWarnings("unchecked")
	public DijkstraShortestPath(Graph<V> g){
		graph = g;
		d = new double[graph.getNumberOfVertexes()];
		p = (V[]) new Object[graph.getNumberOfVertexes()];
		List<V> list = graph.getVertexList();
		for(int i=0;i<graph.getNumberOfVertexes();i++){
			map.put(list.get(i), i);
		}
	}
	
	public int getIndexOf(V v){
		return map.get(v);
	}
	
	/**
	 * Sucht einen kuerzesten Weg zwischen Startknoten s und Zielknoten g.
	 * @param s - Startknoten
	 * @param g - Zielknoten
	 * @return
	 */
	public boolean searchShortestPath(V s, V g){
		shortestPath(s, d, p);
//		printTable();
		end = g;
		if(p[map.get(g)] == null){
			return false;
		}
		return true;
	}
	
	private void printTable(){
		System.out.print("v:\t");
		for(V v:graph.getVertexList()){
			System.out.print(v + "\t");
		}
		System.out.print("\nd[]:\t");
		for(double i:d){
			System.out.print((int)i + "\t");
		}
		System.out.print("\np[]:\t");
		for(V vor:p){
			if(vor == null){
				System.out.print("-\t");
			}else{
				System.out.print(vor + "\t");
			}
		}
		System.out.println();
	}

	/**
	 * Liefert einen kuerzesten Weg zurueck. Setzt eine zuvor erfolgreich durchgefuehrte Suche
	 * mit searchShortestPath(s,g) voraus.
	 * @return kuerzesten Weg
	 */
	public List<V> getShortestPath(){
		List<V> list = new LinkedList<V>();
		list.add(end);
		int i = map.get(end);
		while(p[i] != null){
			list.add(0,p[i]);
			i = map.get(p[i]);
		}
		return list;
	}
	
	/**
	 * Liefert die Laenge eines kuerzesten Weges zurueck. Setzt eine zuvor erfolgreich
	 * durchgefuehrte Suche mit searchShortestPath(s,g) voraus.
	 * @return Laenge eines kuerzesten Weges
	 */
	public double getDistance(){
		return d[map.get(end)];
	}

	/**
	 * Sucht alle kuerzesten Wege vom Startknoten s.
	 * @param s - Startknoten
	 * @return
	 */
	public boolean searchAllShortestPaths(V s){
		for(V v:graph.getVertexList()){
			if(searchShortestPath(s,v)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Liefert einen kuerzesten Weg zum Zielknoten g zurueck. Setze eine zuvor erfolgreich
	 * durchgefuehrte Suche mit searchAllShortestPaths(s) voraus.
	 * @param g
	 * @return
	 */
	public List<V> getShortestPathTo(V g){
		List<V> list = new LinkedList<V>();
		list.add(g);
		int i = map.get(g);
		while(p[i] != null){
			list.add(0,p[i]);
			i = map.get(p[i]);
		}
		return list;
	}

	
	/**
	 * Liefert die Laenge einen kuerzesten Weg zum Zielknoten g zurueck. Setze eine zuvor
	 * erfolgreich durchgefuehrte Suche mit searchAllShortestPaths(s) voraus.
	 * @param g - Zielknoten
	 * @return Laenge des kuerzesten Weges
	 */
	public double getDistanceTo(V g){
		return d[map.get(g)];
	}
	
	
	/**
	 * Dijkstra Algorithmus
	 * @param s - Startknoten
	 * @param d - Distanz array
	 * @param p - Vorgaengerknoten array
	 */
	private void shortestPath(V s, double d[], V p[]){
		Queue<V> kl = new LinkedList<V>();
		for(int i=0;i<graph.getNumberOfVertexes();i++){
			d[i] = Integer.MAX_VALUE;
			p[i] = null;
		}
		d[map.get(s)] = 0;

		kl.add(s);
		while(!kl.isEmpty()){
			V v = getShortestDistance(kl);
			kl.remove(v);
			List<V> list = graph.getAdjacentVertexList(v);
			for(V w:list){
				if(d[map.get(w)] == Integer.MAX_VALUE){
					kl.add(w);
				}
				double distance = d[map.get(v)] + c(v,w);

				if(distance < d[map.get(w)]){
					p[map.get(w)] = v;
					d[map.get(w)] = distance;
				}
			}
		}
	}

	private V getShortestDistance(Queue<V> kl){
		double shortestDistance = Integer.MAX_VALUE;
		V retVal = null;
		for(V vertex:kl){
			if(d[map.get(vertex)] < shortestDistance){
				shortestDistance = d[map.get(vertex)];
				retVal = vertex;
			}
		}
		return retVal;
	}
	
	/**
	 * Liefert die Distanz zweier Knoten.
	 * @param v - Knoten 1
	 * @param w - Knoten 2
	 * @return double
	 */
	private double c(V v,V w){
		return graph.getWeight(v, w);
	}
}
