package adventofcode.dijkstra;

import java.util.List;

public abstract class DijkstraNode {

	int weight;

	public abstract List<DijkstraEdge> edges();

	public int weight() {
		return weight;
	}

	public void reset() {
		weight = Integer.MAX_VALUE;
	}

}
