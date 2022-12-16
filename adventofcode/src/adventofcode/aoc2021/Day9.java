package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day9 {
	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day9.test");
		List<String> data = IOUtil.readFile("2021/day9.data");

		part1(test);
		part1(data);

		part2(test);
		part2(data);
	}

	private static void part2(List<String> data) {
		List<List<Integer>> parsed = data.stream().map(s -> s.chars().map(c -> c - 0x30).boxed().collect(Collectors.toList()))
				.collect(Collectors.toList());
		List<Integer> basinSizes = new ArrayList<>();

		Set<Integer> visited = new HashSet<>();

		for (int y = 0; y < parsed.size(); y++) {
			List<Integer> p = parsed.get(y);
			for (int x = 0; x < p.size(); x++) {
				if (p.get(x) == 9) {
					visited.add(x * 1000 + y);
				}
			}
			visited.add(-1000 + y);
			visited.add(p.size() * 1000 + y);
		}
		for (int x = 0; x < parsed.get(0).size(); x++) {
			visited.add(x * 1000 - 1);
			visited.add(x * 1000 + parsed.size());
		}

		System.out.println("# " + visited.size());

		for (int y = 0; y < parsed.size(); y++) {
			List<Integer> p = parsed.get(y);
			for (int x = 0; x < p.size(); x++) {
				findBasin(basinSizes, visited, x, y);
			}
		}

		Collections.sort(basinSizes);
		Collections.reverse(basinSizes);
		System.out.println("part2: " + (basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2)));
	}

	private static void findBasin(List<Integer> basinSizes, Set<Integer> visited, int x, int y) {
		if (visited.contains(x * 1000 + y)) {
			return;
		}
		int size = 0;
		Set<Integer> toVisit = new HashSet<>();
		toVisit.add(x * 1000 + y);
		while (!toVisit.isEmpty()) {
			int v = toVisit.iterator().next();
			toVisit.remove(v);
			if (visited.contains(v))
				continue;
			size++;
			visited.add(v);
			toVisit.addAll(Arrays.asList(v - 1000, v - 1, v + 1, v + 1000));
			toVisit.removeAll(visited);
		}
		basinSizes.add(size);
	}

	private static void part1(List<String> data) {
		int risk = 0;
		List<List<Integer>> parsed = data.stream().map(s -> s.chars().map(c -> c - 0x30).boxed().collect(Collectors.toList()))
				.collect(Collectors.toList());

		for (int y = 0; y < parsed.size(); y++) {
			List<Integer> p = parsed.get(y);
			for (int x = 0; x < p.size(); x++) {
				if (isLowest(parsed, x, y)) {
					risk += parsed.get(y).get(x) + 1;
				}
			}
		}
		System.out.println("part 1: " + risk);
	}

	private static int getPoint(List<List<Integer>> parsed, int x, int y) {
		if (x < 0 || x >= parsed.get(1).size() || y < 0 || y >= parsed.size()) {
			return 9;
		}

		return parsed.get(y).get(x);
	}

	private static boolean isLowest(List<List<Integer>> parsed, int x, int y) {
		// TODO Auto-generated method stub
		int p1 = getPoint(parsed, x, y);

		return p1 < getPoint(parsed, x - 1, y - 1) && p1 < getPoint(parsed, x - 1, y) && p1 < getPoint(parsed, x - 1, y + 1)
				&& p1 < getPoint(parsed, x, y - 1) && p1 < getPoint(parsed, x, y + 1) && p1 < getPoint(parsed, x + 1, y - 1)
				&& p1 < getPoint(parsed, x + 1, y) && p1 < getPoint(parsed, x + 1, y + 1);

	}
}
