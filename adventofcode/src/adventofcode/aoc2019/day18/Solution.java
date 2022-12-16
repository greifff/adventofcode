package adventofcode.aoc2019.day18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.dijkstra.Dijkstra;

public class Solution {

	int cost;

	Set<Character> visited = new HashSet<>();

	private Map<Character, Point> points;

	char last = '@';

	Solution(Map<Character, Point> points) {
		this.points = points;
		visited.add('@');
	}

	Solution(Solution pred, Point point) {
		this.points = pred.points;
		visited.addAll(pred.visited);
		visited.add(point.identifier);
		visited.add(Character.toUpperCase(point.identifier));
		cost = pred.cost + point.weight();
		last = point.identifier;
	}

	List<Solution> turn() {
		Point p = points.get(last);

		for (Point q : points.values()) {
			q.reset();
			q.visited = visited;
		}

		Dijkstra d = new Dijkstra(new ArrayList<>(points.values()), p);

		d.visitAll();

		List<Solution> solutions = new ArrayList<>();
		for (Point q : points.values()) {
			if (q.weight() != Integer.MAX_VALUE && q.weight() > 0 && q.identifier > 'Z' && !visited.contains(q.identifier)) {
				solutions.add(new Solution(this, q));
			}
		}
		return solutions;
	}
}
