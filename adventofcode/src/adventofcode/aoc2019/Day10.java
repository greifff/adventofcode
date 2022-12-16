package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day10 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day10/day10.test1");
		List<String> test2 = IOUtil.readFile("2019/day10/day10.test2");
		List<String> test3 = IOUtil.readFile("2019/day10/day10.test3");
		List<String> test4 = IOUtil.readFile("2019/day10/day10.test4");
		List<String> test5 = IOUtil.readFile("2019/day10/day10.test5");
		List<String> data = IOUtil.readFile("2019/day10/day10.data");

		// part1(parseMap(test1));
		// part1(parseMap(test2));
		// part1(parseMap(test3));
		// part1(parseMap(test4));
		// part1(parseMap(test5));
		// part1(parseMap(data));

		// part2(parseMap(test5), new Point(11, 13));
		part2(parseMap(data), new Point(20, 20));

		// testAngles();
	}

	static class Point {

		static final double THRESHOLD = .0001;

		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Point(Point p1, Point p2) {
			x = p2.x - p1.x;
			y = p2.y - p1.y;
		}

		boolean isSameDirection(Point o) {
			if (Math.abs(Math.signum(x) - Math.signum(o.x)) > THRESHOLD || Math.abs(Math.signum(y) - Math.signum(o.y)) > THRESHOLD) {
				return false;
			}
			if (x == 0) {
				return o.x == 0;
			}
			if (y == 0) {
				return o.y == 0;
			}

			double s1 = (double) x / y;
			double s2 = (double) o.x / o.y;
			return Math.abs(s1 - s2) < THRESHOLD;
		}

		int canSee(List<Point> map) {
			List<Point> vectors = map.stream().filter(p2 -> p2 != this).map(p2 -> new Point(this, p2)).collect(Collectors.toList());

			// System.out.println("# " + vectors.size());

			int i = 0;
			while (i < vectors.size() - 1) {
				Point v1 = vectors.get(i);
				int j = i + 1;
				while (j < vectors.size()) {
					Point v2 = vectors.get(j);
					// System.out.println("* (" + v1.x + "," + v1.y + ")-(" + v2.x + "," + v2.y + ")");
					if (v1.isSameDirection(v2)) {
						// System.out.println("/");
						vectors.remove(j);
					} else {
						j++;
					}
				}
				i++;
			}
			// System.out.println("=" + x + " " + y + " " + vectors.size());
			return vectors.size();
		}

		int distance() {
			return Math.abs(x) + Math.abs(y);
		}

		double angle() {
			if (x == 0) {
				return (y < 0) ? 0 : 200;
			}
			if (x > 0) {
				if (y == 0) {
					return 100;
				}
				if (y < 0) {
					return Math.atan((double) x / -y) / Math.PI * 200;
				}
				return Math.atan((double) y / x) / Math.PI * 200 + 100;
			}
			if (y == 0) {
				return 300;
			}
			if (y > 0) {
				return Math.atan((double) -x / y) / Math.PI * 200 + 200;
			}
			return Math.atan((double) y / x) / Math.PI * 200 + 300;
		}
	}

	static List<Point> parseMap(List<String> data) {
		List<Point> map = new ArrayList<>();
		for (int y = 0; y < data.size(); y++) {
			String line = data.get(y);
			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == '#') {
					map.add(new Point(x, y));
				}
			}
		}
		return map;
	}

	static void part1(List<Point> map) {
		int m = 0;
		Point p1 = null;
		for (Point p : map) {
			// if (p.x == 5 && p.y == 8) {
			int n = p.canSee(map);
			if (n > m) {
				p1 = p;
				m = n;
			}
			// }
		}
		System.out.println("part1: " + m + " (" + p1.x + "," + p1.y + ")");
	}

	static void testAngles() {
		List<Point> ps = Arrays.asList(new Point(0, -3), new Point(1, -3), new Point(2, -2), new Point(3, -1), new Point(3, 0), new Point(3, 1),
				new Point(2, 2), new Point(1, 3), new Point(0, 3), new Point(-1, 3), new Point(-2, 2), new Point(-3, 1), new Point(-3, 0),
				new Point(-3, -1), new Point(-2, -2), new Point(-1, -3));

		ps.forEach(p -> System.out.println("" + p.x + "," + p.y + " " + p.angle()));
	}

	static void part2(List<Point> map, Point base) {
		List<Point> vectors = map.stream().filter(p -> p.x != base.x || p.y != base.y).map(p -> new Point(base, p)).collect(Collectors.toList());

		Map<Double, List<Point>> x = new TreeMap<>();
		for (Point vector : vectors) {
			double angle = vector.angle();
			List<Point> y = x.get(angle);
			if (y == null) {
				y = new ArrayList<>();
				x.put(angle, y);
			}
			y.add(vector);
		}
		x.values().forEach(v1 -> Collections.sort(v1, (va, vb) -> va.distance() - vb.distance()));

		int c = 0;
		while (!x.isEmpty()) {
			List<Double> toDelete = new ArrayList<>();
			for (Map.Entry<Double, List<Point>> e : x.entrySet()) {
				Point k = e.getValue().remove(0);
				c++;
				System.out.println("" + c + " (" + (base.x + k.x) + "," + (base.y + k.y) + ") " + e.getKey());

				if (e.getValue().isEmpty()) {
					toDelete.add(e.getKey());
				}
			}
			x.keySet().removeAll(toDelete);
		}
	}

}
