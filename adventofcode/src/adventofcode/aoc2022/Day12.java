package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day12 {

	private static final int FACTOR = 1000;

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day12.test");
		List<String> input = IOUtil.readFile("2022/day12.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {

		int end = findEnd(input);

		System.out.println(MessageFormat.format("part1: {0}", shortestPath(end, input)));
	}

	private static void part2(List<String> input) {

		int end = findEnd(input);

		System.out.println(MessageFormat.format("part2: {0}", shortestPath2(end, input)));
	}

	private static int shortestPath(int start, List<String> grid) {
		Set<Integer> visited = new HashSet<>();
		Set<Integer> toVisit = new HashSet<>();
		toVisit.add(start);

		int steps = 0;

		while (true) {
			Set<Integer> toVisit2 = new HashSet<>();
			for (int p : toVisit) {
				int x = p / FACTOR;
				int y = p % FACTOR;

				if (grid.get(y).charAt(x) == 'S') {
					return steps;
				}
				toVisit2.addAll(nextSteps(p, grid));
			}
			steps++;
			toVisit = toVisit2;
			toVisit.removeAll(visited);
		}
	}

	private static int shortestPath2(int start, List<String> grid) {
		Set<Integer> visited = new HashSet<>();
		Set<Integer> toVisit = new HashSet<>();
		toVisit.add(start);

		int steps = 0;

		while (true) {
			Set<Integer> toVisit2 = new HashSet<>();
			for (int p : toVisit) {

				if (getHeight(grid, p) == 'a') {
					return steps;
				}
				toVisit2.addAll(nextSteps(p, grid));
			}
			steps++;
			toVisit = toVisit2;
			toVisit.removeAll(visited);
		}
	}

	private static List<Integer> nextSteps(int p, List<String> grid) {
		int x = p / FACTOR;
		int y = p % FACTOR;

		List<Integer> next = new ArrayList<>();
		char h1 = getHeight(grid, p);

		if (x > 0) {
			int p2 = p - FACTOR;
			char h2 = getHeight(grid, p2);
			if (h1 - 1 <= h2) {
				next.add(p2);
			}
		}
		if (x < grid.get(0).length() - 1) {
			int p2 = p + FACTOR;
			char h2 = getHeight(grid, p2);
			if (h1 - 1 <= h2) {
				next.add(p2);
			}
		}
		if (y > 0) {
			int p2 = p - 1;
			char h2 = getHeight(grid, p2);
			if (h1 - 1 <= h2) {
				next.add(p2);
			}
		}
		if (y < grid.size() - 1) {
			int p2 = p + 1;
			char h2 = getHeight(grid, p2);
			if (h1 - 1 <= h2) {
				next.add(p2);
			}
		}
		return next;
	}

	private static int findEnd(List<String> grid) {
		for (int y = 0; y < grid.size(); y++) {
			String line = grid.get(y);
			int x = line.indexOf('E');
			if (x != -1) {
				return x * FACTOR + y;
			}
		}
		return 0;
	}

	private static char getHeight(List<String> grid, int pos) {
		int x = pos / FACTOR;
		int y = pos % FACTOR;

		char h = grid.get(y).charAt(x);

		if (h == 'S')
			return 'a';
		if (h == 'E')
			return 'z';
		return h;
	}

}
