package adventofcode.aoc2018.day15;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Path {
	final Point step1;

	final Point currentTrace;

	Path(Point step1) {
		this.step1 = step1;
		currentTrace = step1;
	}

	Path(Point step1, Point currentTrace) {
		this.step1 = step1;
		this.currentTrace = currentTrace;
	}

	boolean isOnTarget(Set<Point> adjacentToTarget) {
		return adjacentToTarget.contains(currentTrace);
	}

	List<Path> forward(Set<Point> visited, List<String> field) {

		Set<Point> steps = new HashSet<>();
		steps.add(Point.getPoint(currentTrace.x - 1, currentTrace.y));
		steps.add(Point.getPoint(currentTrace.x + 1, currentTrace.y));
		steps.add(Point.getPoint(currentTrace.x, currentTrace.y - 1));
		steps.add(Point.getPoint(currentTrace.x, currentTrace.y + 1));

		steps.removeAll(visited);

		return steps.stream().filter(c -> field.get(c.y).charAt(c.x) == '.').map(c -> new Path(step1, c)).collect(Collectors.toList());
	}
}