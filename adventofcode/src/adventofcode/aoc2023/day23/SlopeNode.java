package adventofcode.aoc2023.day23;

import java.util.ArrayList;
import java.util.List;

class SlopeNode {

	int x;
	int y;

	List<SlopeEdge> edges = new ArrayList<>();

	SlopeNode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public List<SlopeEdge> edges() {
		return edges;
	}

}