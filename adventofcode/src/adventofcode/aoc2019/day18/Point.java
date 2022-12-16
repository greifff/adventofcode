package adventofcode.aoc2019.day18;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import adventofcode.dijkstra.DijkstraEdge;
import adventofcode.dijkstra.DijkstraNode;

public class Point extends DijkstraNode {

	int x;
	int y;

	PointType condition;

	char identifier;

	List<DijkstraEdge> edges = new ArrayList<>();

	Set<Character> visited;

	Point(int x, int y, char identifier) {
		this.x = x;
		this.y = y;
		this.identifier = identifier;

		condition = identifier == '@' ? PointType.START : identifier <= 'Z' ? PointType.DOOR : PointType.KEY;
	}

	@Override
	public List<DijkstraEdge> edges() {
		if (!visited.contains(identifier)) {
			return new ArrayList<>(); // closed door, ungrabbed key
		}
		return edges;
	}
}
