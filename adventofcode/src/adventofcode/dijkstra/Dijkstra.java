package adventofcode.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

	/*-	
	 1.   Weise allen Knoten die beiden Eigenschaften (Attribute) „Distanz“ und „Vorgänger“ zu. 
	      Initialisiere die Distanz im Startknoten mit 0 und in allen anderen Knoten mit ∞.
	 2.   Solange es noch unbesuchte Knoten gibt, wähle darunter denjenigen mit minimaler (aufsummierter) Distanz aus 
	      und
	 2.1  Speichere, dass dieser Knoten schon besucht wurde.
	 2.2  Berechne für alle noch unbesuchten Nachbarknoten die Gesamtdistanz über die Summe des jeweiligen Kantengewichtes 
	      und der bereits berechneten Distanz vom Startknoten zum aktuellen Knoten.
	 2.3  Ist dieser Wert für einen Knoten kleiner als die dort gespeicherte Distanz, aktualisiere sie und setze den 
	      aktuellen Knoten als Vorgänger.
	      Dieser Schritt wird auch als Update oder Relaxation/Relaxierung bezeichnet.
	        
	*/

	private List<DijkstraNode> toVisit;

	public Dijkstra(List<DijkstraNode> nodes, DijkstraNode startNode) {
		toVisit = new ArrayList<>(nodes);

		nodes.forEach(n -> n.weight = Integer.MAX_VALUE);
		startNode.weight = 0;
	}

	public void visitAll() {
		while (!toVisit.isEmpty()) {
			Collections.sort(toVisit, (a, b) -> a.weight - b.weight);
			DijkstraNode n = toVisit.remove(0);

			for (DijkstraEdge e : n.edges()) {
				if (toVisit.contains(e.to())) {
					DijkstraNode n2 = e.to();
					n2.weight = Math.min(n2.weight, n.weight + e.weight());
				}
			}
		}
	}

}
