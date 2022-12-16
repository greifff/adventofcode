package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day12 {
	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day12.test");
		List<String> test2 = IOUtil.readFile("2021/day12.test2");
		List<String> test3 = IOUtil.readFile("2021/day12.test3");
		List<String> data = IOUtil.readFile("2021/day12.data");

		part1(test);
		part1(test2);
		part1(test3);
		part1(data);

		part2(test);
		part2(test2);
		part2(test3);
		part2(data);
	}

	private static void part2(List<String> data) {
		Map<String, List<String>> paths = parsePaths(data);

		Set<String> validPaths = new HashSet<>();
		List<List<String>> activePaths = new ArrayList<>();

		activePaths.add(Arrays.asList("start"));

		while (!activePaths.isEmpty()) {
			List<String> path = activePaths.remove(0);
			List<String> targets = paths.get(path.get(path.size() - 1));
			for (String t : targets) {
				if ("end".equals(t)) {
					validPaths.add(path.stream().reduce((a, b) -> a + "," + b).orElse("") + ",end");
					continue;
				}
				if (t.toUpperCase().equals(t) || !path.contains(t) || canVisitTwice(path, t)) {
					List<String> p1 = new ArrayList<>(path);
					p1.add(t);
					activePaths.add(p1);
				}
			}
		}

		System.out.println("part2: " + validPaths.size());
	}

	private static boolean canVisitTwice(List<String> path, String t) {
		if (t.equals("start"))
			return false;
		List<String> p2 = new ArrayList<>(path);
		p2.remove(0); // start node
		p2 = p2.stream().filter(q -> !q.equals(q.toUpperCase())).collect(Collectors.toList());
		Collections.sort(p2);
		int i = 1;
		while (i < p2.size()) {
			if (p2.get(i - 1).equals(p2.get(i))) {
				return false;
			}
			i++;
		}
		return true;
	}

	private static void part1(List<String> data) {
		Map<String, List<String>> paths = parsePaths(data);

		Set<String> validPaths = new HashSet<>();
		List<List<String>> activePaths = new ArrayList<>();

		activePaths.add(Arrays.asList("start"));

		while (!activePaths.isEmpty()) {
			List<String> path = activePaths.remove(0);
			List<String> targets = paths.get(path.get(path.size() - 1));
			for (String t : targets) {
				if ("end".equals(t)) {
					validPaths.add(path.stream().reduce((a, b) -> a + "," + b).orElse("") + ",end");
				} else if (t.toUpperCase().equals(t) || !path.contains(t)) {
					List<String> p1 = new ArrayList<>(path);
					p1.add(t);
					activePaths.add(p1);
				}
			}
		}

		System.out.println("part1: " + validPaths.size());
	}

	private static Map<String, List<String>> parsePaths(List<String> data) {
		Map<String, List<String>> r = new HashMap<>();
		for (String d : data) {
			String[] p = d.split("-");

			List<String> t = r.get(p[0]);
			if (t == null) {
				t = new ArrayList<>();
				r.put(p[0], t);
			}
			t.add(p[1]);

			t = r.get(p[1]);
			if (t == null) {
				t = new ArrayList<>();
				r.put(p[1], t);
			}
			t.add(p[0]);
		}
		return r;
	}
}
