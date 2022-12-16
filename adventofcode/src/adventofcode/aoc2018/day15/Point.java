package adventofcode.aoc2018.day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Point implements Comparable<Point> {
	final int x;
	final int y;

	private static Map<Integer, Map<Integer, Point>> lookup = new HashMap<>();

	static Point getPoint(int x, int y) {
		Map<Integer, Point> row = lookup.get(x);
		if (row == null) {
			row = new HashMap<>();
			lookup.put(x, row);
		}
		Point p = row.get(y);
		if (p == null) {
			p = new Point(x, y);
			row.put(y, p);
		}
		return p;
	}

	private Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			return x == p.x && y == p.y;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x + y * 1000;
	}

	@Override
	public int compareTo(Point o) {
		int value = x + y * 1000;
		int otherValue = o.x + o.y * 1000;
		return value - otherValue;
	}

	public Set<Point> getAdjacent() {
		return new HashSet<>(Arrays.asList(getPoint(x, y + 1), getPoint(x, y - 1), getPoint(x + 1, y), getPoint(x - 1, y)));
	}

	public boolean isWalkable(List<String> field) {
		return field.get(y).charAt(x) == '.';
	}
}
