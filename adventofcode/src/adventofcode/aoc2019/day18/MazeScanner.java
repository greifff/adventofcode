package adventofcode.aoc2019.day18;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.dijkstra.DijkstraEdge;

public class MazeScanner {

	private List<String> maze;
	private Map<Character, Point> points = new HashMap<>();

	// endpoints, gates, length
	private Map<Character, Map<Character, Integer>> paths = new HashMap<>();

	public MazeScanner(List<String> maze) {
		this.maze = maze;
	}

	public void scan() {
		scanItems();
		System.out.println("points: " + points.size());
		scanEdges();
		System.out.println("" + paths.size());
		removeDoublettes();
		printEdges();
	}

	private void printEdges() {
		for (Point p : points.values()) {
			for (DijkstraEdge e : p.edges) {
				System.out.println("" + p.identifier + "-" + ((Point) (e.to())).identifier + " " + e.weight());
			}
		}
	}

	private String createPathIdentifier(char a, char b) {
		return a < b ? "" + a + b : "" + b + a;
	}

	// public void scanPaths() {
	//
	// // simple paths
	// Map<String, Integer> simple = new HashMap<>();
	// for (Point p : points.values()) {
	// for (DijkstraEdge de : p.edges()) {
	// Edge e = (Edge) de;
	// String pathId=createPathIdentifier(p.identifier, e.target.identifier);
	// simple.put(pathId, e.length);
	// Map<Character,Integer> paths2=new HashMap<>();
	// paths2.put("", e.length);
	// paths.put(pathId,paths2);
	// }
	// }
	//
	// List<String> toProcess=new LinkedList<>(simple.keySet());
	// while(!toProcess.isEmpty()) {
	// String edge1=toProcess.remove(0);
	// for(Map.Entry<String,Integer> e:simple.entrySet()) {
	// String edge2=e.getKey();
	// }
	// }
	// // complex paths
	// for (Map.Entry<Character, Map<Character, Map<String, Integer>>> e1 : paths.entrySet()) {
	// char s = e1.getKey();
	// Map<Character, Map<String, Integer>> paths1 = e1.getValue();
	// Map<Character, Map<String, Integer>> paths1n = new HashMap<>();
	// for (Map.Entry<Character, Map<String, Integer>> e2 : paths1.entrySet()) {
	// char t1 = e2.getKey();
	// Map<Character, Map<String, Integer>> paths2 = paths.get(t1);
	// for (Map.Entry<Character, Map<String, Integer>> e3 : paths2.entrySet()) {
	// char t2 = e3.getKey();
	// if (s == t2)
	// continue;
	//
	// Map<String, Integer> paths2n = paths1n.get(t2);
	// if (paths2n == null) {
	// paths2n = new HashMap<>();
	// paths1n.put(t2, paths2n);
	// }
	//
	// String gates=makeGateString(e2.getValue(), null, t1);
	//
	// Integer oldLength=paths2n.get(gates);
	// Integer newLength=
	// if(oldLength!=null) {
	//
	// }
	// paths2n.put(makeGateString(e2.getValue(), null, t1), null);
	// }
	// }
	// }
	// }

	// private String makeGateString(String gateStringA, String gateStringB, char newGate) {
	// Set<Character> c = new HashSet<>();
	// c.add(newGate);
	// c.addAll(gateStringA.chars().boxed().map(i -> (char) i.intValue()).collect(Collectors.toList()));
	// c.addAll(gateStringB.chars().boxed().map(i -> (char) i.intValue()).collect(Collectors.toList()));
	// List<Character> c1 = new ArrayList<>(c);
	// Collections.sort(c1);
	// return c1.stream().map(g -> "" + g).reduce((a, b) -> a + b).orElse("");
	// }

	private void removeDoublettes() {
		for (Point p : points.values()) {
			Collections.sort(p.edges, (e1, e2) -> e1.weight() - e2.weight());
			Collections.sort(p.edges, (e1, e2) -> ((Point) e1.to()).identifier - ((Point) e2.to()).identifier);

			int i = 1;
			while (i < p.edges.size()) {
				DijkstraEdge e1 = p.edges.get(i - 1);
				DijkstraEdge e2 = p.edges.get(i);

				if (e1.to() == e2.to()) {
					p.edges.remove(i);
				} else {
					i++;
				}
			}
		}
	}

	private void scanEdges() {

		for (Point p : points.values()) {
			int position = p.x * 1000 + p.y;
			Set<Integer> visited = new HashSet<>();
			Walker w1 = new Walker(position, 0);
			List<Walker> walkers = new ArrayList<>();
			walkers.addAll(w1.walk());
			visited.add(position);

			while (!walkers.isEmpty()) {
				Walker w = walkers.remove(0);
				if (visited.contains(w.position)) {
					continue;
				}
				char location = locationAt(w.position);
				if (location == '#') {
					// dead end
					continue;
				}

				if (isItem(location)) {
					// other item found
					Point target = points.get(location);

					p.edges.add(new Edge(target, w.steps));
					target.edges.add(new Edge(p, w.steps));

					continue;
				}
				visited.add(w.position);
				walkers.addAll(w.walk());
			}
		}
	}

	private static boolean isItem(char c) {
		return c == '@' || (c >= 'A' && c <= 'Z') || isKey(c) || (c >= '1' && c <= '4');
	}

	private static boolean isKey(char c) {
		return c >= 'a' && c <= 'z';
	}

	private char locationAt(int position) {
		int x = position / 1000;
		int y = position % 1000;
		return maze.get(y).charAt(x);
	}

	private void scanItems() {
		for (int y = 0; y < maze.size(); y++) {
			String l = maze.get(y);
			for (int x = 0; x < l.length(); x++) {
				char c = locationAt(x * 1000 + y);
				if (isItem(c)) {
					points.put(c, new Point(x, y, c));
				}
			}
		}
	}

	public int findShortestPath() {
		int cost = Integer.MAX_VALUE;
		Solution s1 = new Solution(points);
		List<Solution> solutions = new ArrayList<>();
		solutions.add(s1);

		while (!solutions.isEmpty()) {
			Collections.sort(solutions, (s11, s12) -> s11.cost - s12.cost);
			// Collections.reverse(solutions);
			Solution s2 = solutions.remove(0);
			do {
				if (s2.cost >= cost) {
					s2 = null;
				} else {
					List<Solution> next = s2.turn();
					if (next.isEmpty()) {
						cost = Math.min(cost, s2.cost);
						System.out.println("X " + cost);
						s2 = null;
					} else {
						s2 = next.remove(0); // continue on one path
						solutions.addAll(next);
						// System.out.println("# " + solutions.size());
					}
				}
			} while (s2 != null);
		}

		return cost;
	}

}
