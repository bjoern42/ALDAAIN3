package Aufgabe3;

public class TelKnoten {
public final int x,y;

	/**
	 * Legt einen neuen Telefonknoten mit den Koordinaten (x,y) an. 
	 * @param pX - x-Koordinate.
	 * @param pY - y-Koordinate.
	 */
	public TelKnoten(int pX, int pY){
		x = pX;
		y = pY;
	}
	
	public String toString(){
		return "TelKnoten["+x+","+y+"]";
	}
}
