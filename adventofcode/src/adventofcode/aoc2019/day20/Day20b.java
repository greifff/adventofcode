package adventofcode.aoc2019.day20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day20b {

	/**
	 * Wenn die gestellte Aufgabe keine Lösung hat, terminiert der Algorithmus nicht, weil der Graph eine unbegrenzte Anzahl Knoten hat.
	 */
	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day20/day20.test1");
		List<String> test3 = IOUtil.readFile("2019/day20/day20.test3");
		List<String> data = IOUtil.readFile("2019/day20/day20.data");

		// part2(test1, 5);
		// part2(test3, 7);
		part2(data, 25);
	}

	private static void part2(List<String> data, int donutwidth) {
		DonutMaze maze = new DonutMaze(data, donutwidth);
		List<DonutLevel> levels = new ArrayList<>();
		Map<String, Node> nodes = new HashMap<>();
		addLevel(maze, levels, nodes);
		// addLevel(maze, levels, nodes);
		//
		// if (1 + 2 == 3) {
		// return;
		// }

		// find shortest way to from AA+0 to ZZ+0

		Node startNode = nodes.get("AA-0");
		startNode.cost = 0;

		Node targetNode = nodes.get("ZZ-0");

		List<Node> toVisit = new ArrayList<>();
		Set<Node> visited = new HashSet<>();
		toVisit.add(startNode);

		while (!toVisit.isEmpty()) {
			Collections.sort(toVisit, (n1, n2) -> n1.cost - n2.cost);
			Node current = toVisit.remove(0);
			visited.add(current);

			if (current.cost > targetNode.cost)
				continue;

			Map<String, Integer> paths = levels.get(current.level).pathLookup.get(current.name);

			for (Map.Entry<String, Integer> path : paths.entrySet()) {
				Node n2 = nodes.get(path.getKey() + current.level);
				if (visited.contains(n2))
					continue;
				n2.cost = Math.min(n2.cost, path.getValue() + current.cost);

				System.out.println("# " + targetNode.cost + " " + n2.cost + " " + current.level + " " + levels.size());

				if (n2 == targetNode) {
					continue;
				}
				String opposite;
				if (n2.name.charAt(2) == '-') {
					opposite = n2.name.substring(0, 2) + "+" + (current.level - 1);
				} else {
					opposite = n2.name.substring(0, 2) + "-" + (current.level + 1);
					if (levels.size() == current.level + 1) {
						addLevel(maze, levels, nodes);
					}
				}
				Node n3 = nodes.get(opposite);
				if (n3 == null)
					System.out.println("& " + n2.name + " " + opposite);
				n3.cost = Math.min(n3.cost, n2.cost + 1);
				if (!visited.contains(n3))
					toVisit.add(n3);
			}
			// System.out.println("? " + visited.stream().map(n -> n.name + n.level + "(" + n.cost + ")").reduce((a, b) -> a + "," + b).orElse(""));
			// System.out.println("~ " + toVisit.stream().map(n -> n.name + n.level + "(" + n.cost + ")").reduce((a, b) -> a + "," + b).orElse(""));
		}
		System.out.println("part2: " + targetNode.cost);
	}

	private static void addLevel(DonutMaze maze, List<DonutLevel> levels, Map<String, Node> nodes) {
		int level = levels.size();
		DonutLevel newLevel = new DonutLevel(maze, level);
		levels.add(newLevel);
		for (String name : newLevel.pathLookup.keySet()) {
			nodes.put(name + level, new Node(name, level));
		}
		System.out.println("+ " + level);
		// System.out.println(newLevel.pathLookup.keySet().stream().reduce((a, b) -> a + "," + b).orElse(""));
	}

	static class Node {
		int level;
		String name;
		int cost = Integer.MAX_VALUE;

		public Node(String name, int level) {
			this.name = name;
			this.level = level;
		}

	}

}
