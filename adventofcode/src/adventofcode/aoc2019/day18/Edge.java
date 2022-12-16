package adventofcode.aoc2019.day18;

import adventofcode.dijkstra.DijkstraEdge;
import adventofcode.dijkstra.DijkstraNode;

public class Edge implements DijkstraEdge {

	int length;
	Point target;

	public Edge(Point target, int steps) {
		this.target = target;
		length = steps;
	}

	@Override
	public DijkstraNode to() {
		return target;
	}

	@Override
	public int weight() {
		return length;
	}
}
