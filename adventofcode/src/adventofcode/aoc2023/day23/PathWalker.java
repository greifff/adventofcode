package adventofcode.aoc2023.day23;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class PathWalker {
	PathWalker previous;
	SlopeNode current;
	int cost;

	PathWalker(SlopeNode start) {
		current = start;
	}

	PathWalker(PathWalker other, SlopeEdge traverse) {
		previous = other;
		cost = other.cost + traverse.weight;
		current = traverse.to;
	}

	List<PathWalker> walk() {
		Set<SlopeNode> visited = new HashSet<>();
		PathWalker p = previous;
		while (p != null) {
			visited.add(p.current);
			p = p.previous;
		}
		List<PathWalker> walkers = new LinkedList<>();
		for (SlopeEdge d : current.edges()) {
			SlopeEdge e = d;
			if (!visited.contains(e.to)) {
				walkers.add(new PathWalker(this, e));
			}
		}
		return walkers;
	}
}