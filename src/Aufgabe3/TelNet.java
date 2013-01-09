package Aufgabe3;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import Aufgabe2.AdjacencyListUndirectedGraph;
import Aufgabe2.Graph;

/**
 * Klasse zur Verwaltung von Telefonknoten mit (x,y)-Koordinaten und zur Berechnung eines minimal aufspannenden Baums
 * mit dem Algorithmus von Prim. Kantengewichte sind durch den Mahattan-Abstand definiert. 
 * @author Bjoern Eschle
 * @version 1.0
 */
public class TelNet {
private HashMap<TelKnoten, Integer> map = new HashMap<TelKnoten, Integer>();
private Graph<TelKnoten> graph = null;
private int lbg = 0;
private int d[];
private TelKnoten p[] = null;

	/**
	 * Anwendung.
	 * @param args - wird nicht benutzt.
	 */
	public static void main(String[] args) {
		aufgabe2();
	}
	
	public static void aufgabe2(){
		TelNet tel = new TelNet(5);
		tel.addTelKnoten(1, 1);
		tel.addTelKnoten(3, 1);
		tel.addTelKnoten(4, 2);
		tel.addTelKnoten(3, 4);
		tel.addTelKnoten(7, 5);
		tel.addTelKnoten(2, 6);
		tel.addTelKnoten(4, 7);
		tel.computeOptTelNet();
		tel.drawOptTelNet(7, 7);
		System.out.println(tel);
		System.out.println("Kosten: "+tel.getOptTelNetKosten());
	}
	
	public static void aufgabe3(){
		TelNet tel = new TelNet(100);
		tel.generateRandomTelNet(1000, 1000, 1000);
		tel.computeOptTelNet();
		tel.drawOptTelNet(1000, 1000);
		System.out.println(tel);
		System.out.println("Kosten: "+tel.getOptTelNetKosten());
	}
	
	/**
	 * Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
	 * @param pLbg - Leitungsbegrenzungswert.
	 */
	public TelNet(int pLbg){
		lbg = pLbg;
		graph = new AdjacencyListUndirectedGraph<TelKnoten>();
	}
	
	/**
	 * Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
	 * @param x - X Koordinate
	 * @param y - Y Koordinate
	 */
	public boolean addTelKnoten(int x, int y) {
		for(TelKnoten k:graph.getVertexList()){
			if(k.x == x && k.y == y){
				return false;
			}
		}
		TelKnoten tmp = new TelKnoten(x, y);
		graph.addVertex(tmp);
		map.put(tmp, graph.getNumberOfVertexes()-1);
		return true;
	}
	
	/**
	 * Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Prim.
	 */
	public void computeOptTelNet(){
		minimumSpanningTree();
	}
	
	/**
	 * Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
	 * @param xMax - Maximale x-Größe.
	 * @param yMax - Maximale y-Größe. 
	 */
	public void drawOptTelNet(int xMax, int yMax){
		double halfSize = 0.5;
		StdDraw.setXscale(1, xMax+1);
		StdDraw.setYscale(1, yMax+1);
		
		if(xMax < 50 && yMax < 50){
			for(int i=1; i<xMax+2; i++){
				StdDraw.line(i, 1, i, yMax+1);
			}
			for(int i=1; i<xMax+2; i++){
				StdDraw.line(1, i, xMax+1, i);
			}
		}
		
		for(TelKnoten k:graph.getVertexList()){
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.filledRectangle(k.x+halfSize, k.y+halfSize, halfSize, halfSize);
			StdDraw.setPenColor(Color.RED);
			StdDraw.filledCircle(k.x+halfSize, k.y+halfSize, halfSize*3/4);
		}

		StdDraw.setPenRadius(halfSize/(xMax*yMax));
		for(TelVerbindung v:getOptTelNet()){
			if(v.v != null){
				StdDraw.line(v.u.x+halfSize, v.u.y+halfSize, v.v.x+halfSize, v.u.y+halfSize);
				StdDraw.line(v.v.x+halfSize, v.u.y+halfSize, v.v.x+halfSize, v.v.y+halfSize);
			}
		}
	}
	
	/**
	 * Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinaten aus [0,xMax] und y-Koordinaten aus [0,yMax].
	 * @param n - Anzahl Telefonknoten
	 * @param xMax - Intervallgrenze für x-Koordinate.
	 * @param yMax - Intervallgrenze für y-Koordinate.
	 */
	public void generateRandomTelNet(int n, int xMax, int yMax){
		for(int i=0; i<n; i++){
			while(!addTelKnoten(1+(int)(Math.random()*xMax), 1+(int)(Math.random()*yMax)));
		}
	}
    
	/**
	 * Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
	 * @return Liste von Telefonverbindungen. 
	 */
	public List<TelVerbindung> getOptTelNet(){
		List<TelVerbindung> list = new LinkedList<TelVerbindung>();
		for(TelKnoten k1:graph.getVertexList()){
			TelKnoten k2 = p[map.get(k1)];
			if(k2 != null){
				list.add(new TelVerbindung(k1, k2));
			}
		}
		return list;
	}
	
	/**
	 * Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.<p>
	 * Nicht verbundene Telefonknoten werden dabei ausgelassen!
	 * @return Gesamtkosten eines optimalen Telefonnetzes. 
	 */
	public int getOptTelNetKosten(){
		int retVal = 0;
		for(double x:d){
			if(x != Integer.MAX_VALUE){
				retVal += x;
			}
		}
		return retVal;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(TelVerbindung v:getOptTelNet()){
			builder.append(v).append("\n");
		}
		return builder.toString();
	}
	
	/**
	 * Prim-Algorithmus
	 * @param graph - Graph
	 * @param p - Ergebnisbaum
	 */
	private void minimumSpanningTree() {
		p = new TelKnoten[graph.getNumberOfVertexes()];
		d = new int[graph.getNumberOfVertexes()];
		Queue<TelKnoten> kl = new LinkedList<TelKnoten>();  // Kandidatenliste 
		Set<TelKnoten> baum = new HashSet<TelKnoten>(); // Knoten des aufspannenden Baums 
		for(int i=0;i<graph.getNumberOfVertexes();i++){
			d[i] = Integer.MAX_VALUE;
			p[i] = null;
		}
		TelKnoten s = graph.getVertexList().get(0);
		d[map.get(s)] = 0;  // irgendeinen Startknoten wählen 
		kl.add(s);
		while (!kl.isEmpty()) {
			TelKnoten v = getShortestDistance(kl); //lösche Knoten v aus kl mit d[v] minimal;
			kl.remove(v);
			baum.add(v);
			for (TelKnoten w :graph.getVertexList()) {
				if (!baum.contains(w)) {
					if (d[map.get(w)] == Integer.MAX_VALUE && getDistance(v, w) <= lbg){ // w noch nicht in Kandidatenliste und innerhalb der Leitungsbegrenzung
						kl.add(w); 
					}
					if ( getDistance(v, w) < d[map.get(w)] ) { // d[w] verbessert sich 
						p[map.get(w)] = v;
						d[map.get(w)] = getDistance(v, w); 
					} 
				} 
			} 
		} 
		if(baum.size() < graph.getNumberOfVertexes()){
			//baum enthält nicht alle Knoten von G
			System.out.println("es existiert kein aufspannder Baum");
		} 
	}
	
	/**
	 * Liefert den TelKnoten aus der Kandidatenliste mit der kürzesten Distanz.
	 * @param kl - Kandidatenliste
	 * @return TelKnoten
	 */
	private TelKnoten getShortestDistance(Queue<TelKnoten> kl){
		double shortestDistance = Integer.MAX_VALUE;
		TelKnoten retVal = null;
		for(TelKnoten vertex:kl){
			if(d[map.get(vertex)] < shortestDistance){
				shortestDistance = d[map.get(vertex)];
				retVal = vertex;
			}
		}
		if(retVal == null){
			return kl.poll();
		}
		return retVal;
	}
	
	/**
	 * Liefert die Manhatten-Distanz zweier TelKnoten
	 * @param v - TelKnoten 1
	 * @param w - TelKnoten 2
	 * @return Distanz
	 */
	private int getDistance(TelKnoten v, TelKnoten w){
		int distance = Math.abs(v.x - w.x)+Math.abs(v.y - w.y);
		if(distance > lbg){
			return Integer.MAX_VALUE;
		}
		return distance;
	}
}
