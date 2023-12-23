package adventofcode.aoc2023.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day23 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day23.test");
		List<String> input = IOUtil.readFile("2023/day23.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		// start x=1, y=0
		// end x=maxx-1, y=maxy

		SlopeNode start = new SlopeNode(1, 0);
		System.out.println("§0 ");
		List<SlopeNode> nodes = scanInput(input, start, false);

		removeShortEdges(nodes);

		System.out.println("§1 ");
		SlopeNode end = nodes.stream().reduce((n1, n2) -> n1.y > n2.y ? n1 : n2).orElse(null);

//		for (SlopeNode n : nodes) {
//			for (DijkstraEdge e : n.edges) {
//				SlopeNode t = (SlopeNode) e.to();
//				System.out.println(": (" + n.x + "," + n.y + ")->(" + t.x + "," + t.y + ") " + e.weight());
//			}
//		}

//		Dijkstra d = new Dijkstra(nodes, start);
//		d.visitAll();

//		System.out.println("# " + end.weight());

		List<PathWalker> walkers = new LinkedList<>();
		walkers.add(new PathWalker(start));

		int mostExpensive = 0;
		while (!walkers.isEmpty()) {
			PathWalker w = walkers.remove(0);
			if (w.current == end) {

				mostExpensive = Math.max(mostExpensive, w.cost);
				continue;
			}
			List<PathWalker> walkers2 = w.walk();
			walkers2.addAll(walkers);
			walkers = walkers2;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + mostExpensive);
	}

	static void removeShortEdges(List<SlopeNode> nodes) {
		for (SlopeNode node : nodes) {
			Map<SlopeNode, Integer> targets = node.edges.stream()
					.collect(Collectors.toMap(e -> e.to(), e -> e.weight(), Math::max));

			node.edges.clear();
			node.edges.addAll(targets.entrySet().stream().map(e -> new SlopeEdge(e.getKey(), e.getValue())).toList());
		}
	}

	static List<SlopeNode> scanInput(List<String> input, SlopeNode start, boolean part2) {

		Set<Integer> visited = new HashSet<>();

		Map<Integer, SlopeNode> nodes = new HashMap<>();
		List<Walker> toProcess = new LinkedList<>();

		nodes.put(start.x * 1000 + start.y, start);

		Walker w = new Walker(start.x, start.y, input);
		w.from = start;
		toProcess.add(w);

		while (!toProcess.isEmpty()) {
			Walker w1 = toProcess.remove(0);
//			System.out.println("> " + w1.x1 + " " + w1.y1);
			int possible = w1.walk();
			while (possible == 1) {
				possible = w1.walk();
//				System.out.println("= " + w1.x1 + " " + w1.y1 + " " + w1.steps);
			}
//			System.out.println("< " + w1.x1 + " " + w1.y1 + " " + w1.steps);
			visited.add(w1.x2 * 1000 + w1.y2);
			SlopeNode n = nodes.get(w1.x1 * 1000 + w1.y1);
			if (n == null) {
				n = new SlopeNode(w1.x1, w1.y1);
				nodes.put(w1.x1 * 1000 + w1.y1, n);
			}
			if (part2 || w1.direction >= 0) {
				w1.from.edges().add(new SlopeEdge(n, w1.steps));
			}
			if (part2 || w1.direction <= 0) {
				n.edges().add(new SlopeEdge(w1.from, w1.steps));
			}
			if (possible > 0) {
				List<Walker> spawned = w1.spawn().stream().filter(w2 -> !visited.contains(w2.x1 * 1000 + w2.y1))
						.toList();
				for (Walker w2 : spawned) {
					w2.from = n;
				}
				toProcess.addAll(spawned);
			}
		}

		return new ArrayList<>(nodes.values());
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		// start x=1, y=0
		// end x=maxx-1, y=maxy

		SlopeNode start = new SlopeNode(1, 0);
		System.out.println("§0 ");
		List<SlopeNode> nodes = scanInput(input, start, true);

		removeShortEdges(nodes);

		System.out.println("§1 ");
		SlopeNode end = nodes.stream().reduce((n1, n2) -> n1.y > n2.y ? n1 : n2).orElse(null);

		Stack<SlopeNode> visited = new Stack<>();
		Stack<PathWalker> walkers = new Stack<>();
		walkers.add(new PathWalker(start));

		SlopeNode preEnd = end.edges.get(0).to();
		int lastLeg = end.edges.get(0).weight();

		int mostExpensive = 0;
		while (!walkers.isEmpty()) {
			PathWalker w = walkers.pop();
			if (w.current == preEnd) {
				if (w.cost > mostExpensive) {
					System.out.println("c " + w.cost);
					mostExpensive = w.cost;
				}
				continue;
			}

			while (!visited.isEmpty() && w.previous.current != visited.peek()) {
				visited.pop();
			}

			visited.push(w.current);

			for (SlopeEdge e : w.current.edges) {
				if (!visited.contains(e.to)) {
					walkers.push(new PathWalker(w, e));
				}
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + (mostExpensive + lastLeg));

	}
}
