package adventofcode.aoc2019.day20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day20 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day20/day20.test1");
		List<String> test2 = IOUtil.readFile("2019/day20/day20.test2");
		List<String> data = IOUtil.readFile("2019/day20/day20.data");

		part1(test1);
		part1(test2);
		part1(data);
	}

	private static void part1(List<String> data) {
		Map<Integer, String> gateLookup = detectGates(data);
		Map<String, Map<String, Integer>> pathLookup = findPaths(data, gateLookup);

		// find shortest way to from AA to ZZ
		Map<String, Node> nodes = new HashMap<>();
		for (String name : pathLookup.keySet()) {
			nodes.put(name, new Node(name));
		}
		nodes.get("AA").cost = 0;

		List<Node> toVisit = new ArrayList<>();
		Set<Node> visited = new HashSet<>();
		toVisit.add(nodes.get("AA"));

		while (!toVisit.isEmpty()) {
			Collections.sort(toVisit, (n1, n2) -> n1.cost - n2.cost);
			Node current = toVisit.remove(0);
			visited.add(current);
			int warpCost = "AA".equals(current.name) ? 0 : 1;
			Map<String, Integer> paths = pathLookup.get(current.name);
			for (Map.Entry<String, Integer> path : paths.entrySet()) {
				Node n2 = nodes.get(path.getKey());
				n2.cost = Math.min(n2.cost, path.getValue() + current.cost + warpCost);
				if (!visited.contains(n2))
					toVisit.add(n2);
			}
		}
		System.out.println("part1: " + nodes.get("ZZ").cost);
	}

	static class Node {
		public Node(String name) {
			this.name = name;
		}

		String name;
		int cost = Integer.MAX_VALUE;
	}

	private static Map<String, Map<String, Integer>> findPaths(List<String> data, Map<Integer, String> gateLookup) {

		Map<String, Map<String, Integer>> pathLookup = new HashMap<>();

		for (Map.Entry<Integer, String> e : gateLookup.entrySet()) {
			String name = e.getValue();
			Map<String, Integer> paths = pathLookup.get(name);
			if (paths == null) {
				paths = new HashMap<>();
				pathLookup.put(name, paths);
			}
			Set<Integer> visited = new HashSet<>();
			List<Integer> probes = new ArrayList<>();
			probes.add(e.getKey());
			int length = 0;
			while (!probes.isEmpty()) {
				List<Integer> probes2 = new ArrayList<>();
				while (!probes.isEmpty()) {
					int current = probes.remove(0);
					visited.add(current);
					String target = gateLookup.get(current);
					if (target != null && !name.equals(target)) {
						paths.put(target, length);
					} else {
						int x = current % 1000;
						int y = current / 1000;
						char k = charAt(data, x, y - 1);
						if (k == '.' && !visited.contains(x + (y - 1) * 1000)) {
							probes2.add(x + (y - 1) * 1000);
						}
						k = charAt(data, x, y + 1);
						if (k == '.' && !visited.contains(x + (y + 1) * 1000)) {
							probes2.add(x + (y + 1) * 1000);
						}
						k = charAt(data, x - 1, y);
						if (k == '.' && !visited.contains(x - 1 + y * 1000)) {
							probes2.add(x - 1 + y * 1000);
						}
						k = charAt(data, x + 1, y);
						if (k == '.' && !visited.contains(x + 1 + y * 1000)) {
							probes2.add(x + 1 + y * 1000);
						}
					}
				}
				probes = probes2;
				length++;
			}
		}

		return pathLookup;
	}

	static Map<Integer, String> detectGates(List<String> data) {
		Map<Integer, String> gateLookup = new HashMap<>();
		int maxX = data.get(0).length();
		int maxY = data.size();

		for (int y = 1; y < maxY - 1; y++) {
			for (int x = 1; x < maxX - 1; x++) {
				char k = charAt(data, x, y);
				if (k >= 'A' && k <= 'Z') {
					char m0 = charAt(data, x, y - 1);
					char m1 = charAt(data, x, y + 1);
					if (m0 == '.' && m1 >= 'A' && m1 <= 'Z') {
						gateLookup.put(x + (y - 1) * 1000, buildName(k, m1));
					} else if (m1 == '.' && m0 >= 'A' && m0 <= 'Z') {
						gateLookup.put(x + (y + 1) * 1000, buildName(k, m0));
					} else {
						m0 = charAt(data, x - 1, y);
						m1 = charAt(data, x + 1, y);
						if (m0 == '.' && m1 >= 'A' && m1 <= 'Z') {
							gateLookup.put(x - 1 + y * 1000, buildName(k, m1));
						} else if (m1 == '.' && m0 >= 'A' && m0 <= 'Z') {
							gateLookup.put(x + 1 + y * 1000, buildName(k, m0));
						}
					}
				}
			}
		}
		return gateLookup;
	}

	static char charAt(List<String> data, int x, int y) {
		return data.get(y).charAt(x);
	}

	static String buildName(char c1, char c2) {
		if (c1 < c2)
			return "" + c1 + c2;
		return "" + c2 + c1;
	}

}
