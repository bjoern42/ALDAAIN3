package Aufgabe3;

public class TelVerbindung {
public final TelKnoten u, v;

	/**
	 * Legt eine neue Telefonverbindung von u nach v an. 
	 * @param pU - Anfangsknoten.
	 * @param pV - Endknoten.
	 */
	public TelVerbindung(TelKnoten pU, TelKnoten pV){
		u = pU;
		v = pV;
	}
	
	public String toString(){
		return "TelVerbindung von "+u+" nach "+v;
	}
}
