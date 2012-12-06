// O. Bittel;
// 18.10.2011

package Aufgabe2;

/**
 * Graph mit ungerichteten Kanten.
 * Es ist zu beachten, dass nach erfolgreichem Einfügen der Kante (v,w)
 * sowohl (v,w) als auch (w,v) im Graph enthalten ist.
 * Präziser: Falls g.addEdge(v,w) == true, dann gilt g.contains(v,w) == true und
 * g.contains(w,v) == true.
 * @author Oliver Bittel
 * @since 21.10.2011
 * @param <V> Knotentyp.
 */
public interface UndirectedGraph<V> extends Graph<V> {
    /**
     * Liefert Grad des Knotens v zurück.
     * Das ist die Anzahl der adjazenten Knoten bzw. inzidenten Kanten zu v.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotengrad
     */
    int getDegree(V v);
}

