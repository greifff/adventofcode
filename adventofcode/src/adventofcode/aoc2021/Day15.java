package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.dijkstra.Dijkstra;
import adventofcode.dijkstra.DijkstraEdge;
import adventofcode.dijkstra.DijkstraNode;
import adventofcode.util.IOUtil;

public class Day15 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day15.test");
		List<String> data = IOUtil.readFile("2021/day15.data");

		part1(test);
		part1(data);

		part2(test);
		part2(data);
	}

	private static void part2(List<String> data) {
		List<List<Node>> grid = parseGrid5(data);

		Dijkstra dijkstra = new Dijkstra(grid.stream().flatMap(l -> l.stream()).collect(Collectors.toList()), getNode(0, 0, grid));

		dijkstra.visitAll();

		System.out.println("part2: " + getNode(grid.size() - 1, grid.size() - 1, grid).weight());
	}

	private static void part1(List<String> data) {
		List<List<Node>> grid = parseGrid(data);

		Dijkstra dijkstra = new Dijkstra(grid.stream().flatMap(l -> l.stream()).collect(Collectors.toList()), getNode(0, 0, grid));

		dijkstra.visitAll();

		System.out.println("part1: " + getNode(grid.size() - 1, grid.size() - 1, grid).weight());
	}

	private static List<List<Node>> parseGrid(List<String> data) {
		List<List<Node>> result = new ArrayList<>();

		for (int y = 0; y < data.size(); y++) {
			String r = data.get(y);
			List<Node> n = new ArrayList<>();
			result.add(n);
			for (int x = 0; x < r.length(); x++) {
				n.add(new Node(x, y, r.charAt(x) - 0x30, result));
			}
		}

		return result;
	}

	private static List<List<Node>> parseGrid5(List<String> data) {
		List<List<Node>> result = new ArrayList<>();

		for (int m = 0; m < 5; m++) {
			for (int y = 0; y < data.size(); y++) {
				String r = data.get(y);
				List<Node> n = new ArrayList<>();
				result.add(n);
				for (int k = 0; k < 5; k++) {
					for (int x = 0; x < r.length(); x++) {
						int risk = (r.charAt(x) - 0x30 - 1 + k + m) % 9 + 1;
						n.add(new Node(x + k * r.length(), y + m * data.size(), risk, result));
					}
				}
			}
		}

		return result;
	}

	static class Node extends DijkstraNode {

		int x;
		int y;

		int risk;

		List<List<Node>> grid;

		Node(int x, int y, int risk, List<List<Node>> grid) {
			this.x = x;
			this.y = y;
			this.risk = risk;
			this.grid = grid;
		}

		@Override
		public List<DijkstraEdge> edges() {
			List<DijkstraEdge> e = new ArrayList<>();
			Node n = getNode(x - 1, y, grid);
			if (n != null)
				e.add(new Edge(n));
			n = getNode(x + 1, y, grid);
			if (n != null)
				e.add(new Edge(n));
			n = getNode(x, y - 1, grid);
			if (n != null)
				e.add(new Edge(n));
			n = getNode(x, y + 1, grid);
			if (n != null)
				e.add(new Edge(n));
			return e;
		}
	}

	static Node getNode(int x, int y, List<List<Node>> grid) {
		if (y < 0 || y >= grid.size() || x < 0)
			return null;
		List<Node> r = grid.get(y);
		if (x >= r.size())
			return null;
		return r.get(x);
	}

	static class Edge implements DijkstraEdge {

		Node to;

		Edge(Node to) {
			this.to = to;
		}

		@Override
		public DijkstraNode to() {
			return to;
		}

		@Override
		public int weight() {
			return to.risk;
		}
	}

}
