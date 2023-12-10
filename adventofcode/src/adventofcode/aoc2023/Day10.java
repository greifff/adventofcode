package adventofcode.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day10 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day10.test");
		List<String> test2 = IOUtil.readFile("2023/day10.test2");
		List<String> test3 = IOUtil.readFile("2023/day10.test4");
		List<String> test4 = IOUtil.readFile("2023/day10.test3");
		List<String> input = IOUtil.readFile("2023/day10.data");

		part1(test);
		part1(test2);
		part1(test3);
		part1(test4);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		int maxx = input.get(0).length() - 1;
		int maxy = input.size() - 1;

		int start = 0;
		for (int y = 0; y < input.size(); y++) {
			String row = input.get(y);
			int x = row.indexOf('S');
			if (x != -1) {
				start = x * 1000 + y;
				break;
			}
		}

		PipeGrid p = new PipeGrid(input);

		Map<Integer, Integer> visited = new HashMap<>();
//		visited.put(startx * 1000 + starty, 0);

		int length = 0;
		int confirmedLength = 0;
		List<Integer> toVisit = new LinkedList<>();
		toVisit.add(start);
		while (!toVisit.isEmpty()) {
			List<Integer> toVisitNext = new LinkedList<>();
			for (int v : toVisit) {
				if (visited.containsKey(v)) {
					confirmedLength = Math.max(confirmedLength, length - 1);
					continue;
				}
				int x = v / 1000;
				int y = v % 1000;
				if (p.goNorth(x, y)) {
					toVisitNext.add(v - 1);
				}
				if (p.goSouth(x, y)) {
					toVisitNext.add(v + 1);
				}
				if (p.goWest(x, y)) {
					toVisitNext.add(v - 1000);
				}
				if (p.goEast(x, y)) {
					toVisitNext.add(v + 1000);
				}
				visited.put(v, length);
			}
			toVisit = toVisitNext;
			length++;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + confirmedLength);
		Ansi.foreground(0, 255, 0);

//		List<String> loopGraph = new ArrayList<>();
//		for (int y = 0; y < input.size(); y++) {
//			StringBuilder row = new StringBuilder();
//			for (int x = 0; x < input.get(0).length(); x++) {
//				row.append(visited.containsKey(x * 1000 + y) ? 'X' : '.');
//			}
//			String row1 = row.toString();
//			System.out.println(row1);
//			loopGraph.add(row1);
//		}

		System.out.println("#find left/right");
		Set<Integer> left = new HashSet<>();
		Set<Integer> right = new HashSet<>();

		int walker = start + 1;
		if (!visited.containsKey(start - 1000)) {
			right.add(start - 1000);
		}
		if (!visited.containsKey(start + 1000)) {
			left.add(start - 1000);
		}
		if (!visited.containsKey(start - 1)) {
			right.add(start - 1);
		}
		int direction = 2; // 0 - north, 1 - east, 2 - south, 3 - west
		while (walker != start) {
			int x = walker / 1000;
			int y = walker % 1000;

			switch (input.get(y).charAt(x)) {
			case '|':
				left.addAll(createNeighbors(x, y, maxx, maxy, direction == 2 ? 1000 : -1000));
				right.addAll(createNeighbors(x, y, maxx, maxy, direction == 2 ? -1000 : +1000));
				break;
			case '-':
				left.addAll(createNeighbors(x, y, maxx, maxy, direction == 1 ? -1 : 1));
				right.addAll(createNeighbors(x, y, maxx, maxy, direction == 1 ? 1 : -1));
				break;
			case 'F':
				if (direction == 0) {
					left.addAll(createNeighbors(x, y, maxx, maxy, -1000, -1001, -1));
					direction = 1;
				} else {
					right.addAll(createNeighbors(x, y, maxx, maxy, -1000, -1001, -1));
					direction = 2;
				}
				break;
			case 'L':
				if (direction == 2) {
					direction = 1;
					right.addAll(createNeighbors(x, y, maxx, maxy, -1000, -999, 1));
				} else {
					left.addAll(createNeighbors(x, y, maxx, maxy, -1000, -999, 1));
					direction = 0;
				}
				break;
			case '7':
				if (direction == 0) {
					right.addAll(createNeighbors(x, y, maxx, maxy, 1000, 999, -1));
					direction = 3;
				} else {
					left.addAll(createNeighbors(x, y, maxx, maxy, 1000, 999, -1));
					direction = 2;
				}
				break;
			case 'J':
				if (direction == 2) {
					left.addAll(createNeighbors(x, y, maxx, maxy, 1000, 1001, 1));
					direction = 3;
				} else {
					right.addAll(createNeighbors(x, y, maxx, maxy, 1000, 1001, 1));
					direction = 0;
				}
				break;
			}
			switch (direction) {// 0 - north, 1 - east, 2 - south, 3 - west
			case 0:
				walker -= 1;
				break;
			case 1:
				walker += 1000;
				break;
			case 2:
				walker += 1;
				break;
			case 3:
				walker -= 1000;
				break;
			}
		}
		left.removeAll(visited.keySet());
		right.removeAll(visited.keySet());

		System.out.println("#grow left");
		grow(left, visited.keySet(), maxx, maxy);

		if (!left.contains(0)) {
			Ansi.foreground(0, 255, 255);
			System.out.println("part2: " + left.size());
			return;
		}

		System.out.println("#grow right");
		grow(right, visited.keySet(), maxx, maxy);

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + (left.contains(0) ? right.size() : left.size()));
	}

	static List<Integer> createNeighbors(int x, int y, int maxx, int maxy, int... deltas) {
		List<Integer> dl = Arrays.stream(deltas).boxed().toList();
		List<Integer> result = new LinkedList<>();
		if (x > 0) {
			if (dl.contains(-1000)) {
				result.add(x * 1000 + y - 1000);
			}
			if (y > 0) {
				if (dl.contains(-1001)) {
					result.add(x * 1000 + y - 1001);
				}
			}
			if (y < maxy) {
				if (dl.contains(-999)) {
					result.add(x * 1000 + y - 999);
				}
			}
		}
		if (x < maxx) {
			if (dl.contains(1000)) {
				result.add(x * 1000 + y + 1000);
			}
			if (y > 0) {
				if (dl.contains(999)) {
					result.add(x * 1000 + y + 999);
				}
			}
			if (y < maxy) {
				if (dl.contains(1001)) {
					result.add(x * 1000 + y + 1001);
				}
			}
		}
		if (y > 0) {
			if (dl.contains(-1)) {
				result.add(x * 1000 + y - 1);
			}
		}
		if (y < maxy) {
			if (dl.contains(1)) {
				result.add(x * 1000 + y + 1);
			}
		}
		return result;
	}

	static void grow(Set<Integer> area, Set<Integer> loop, int maxx, int maxy) {
		List<Integer> toVisit = new LinkedList<>(area);
		while (!toVisit.isEmpty()) {
			List<Integer> toVisit2 = new LinkedList<>();
			for (int v : toVisit) {
				int x = v / 1000;
				int y = v % 1000;

				area.add(v);

				if (x > 0) {
					toVisit2.add(v - 1000);
					if (y > 0) {
						toVisit2.add(v - 1001);
					}
					if (y < maxy) {
						toVisit2.add(v - 999);
					}
				}
				if (x < maxx) {
					toVisit2.add(v + 1000);
					if (y > 0) {
						toVisit2.add(v + 999);
					}
					if (y < maxy) {
						toVisit2.add(v + 1001);
					}
				}
				if (y > 0) {
					toVisit2.add(v - 1);
				}
				if (y < maxy) {
					toVisit2.add(v + 1);
				}
			}
			toVisit2.removeAll(toVisit);
			toVisit2.removeAll(area);
			toVisit2.removeAll(loop);
			toVisit = toVisit2;
		}
	}

	private static void part2(List<String> input) {

	}

	static class PipeGrid {
		List<String> s;

		PipeGrid(List<String> s) {
			this.s = s;
		}

		boolean goNorth(int x, int y) {
			if (y == 0)
				return false;
			switch (s.get(y).charAt(x)) {
			case 'S':
			case '|':
			case 'L':
			case 'J':
				break;
			default:
				return false;
			}
			switch (s.get(y - 1).charAt(x)) {
			case 'S':
			case '|':
			case '7':
			case 'F':
				return true;
			default:
				return false;
			}
		}

		boolean goSouth(int x, int y) {
			if (y == s.size() - 1)
				return false;
			switch (s.get(y).charAt(x)) {
			case 'S':
			case '|':
			case 'F':
			case '7':
				break;
			default:
				return false;
			}
			switch (s.get(y + 1).charAt(x)) {
			case 'S':
			case '|':
			case 'L':
			case 'J':
				return true;
			default:
				return false;
			}
		}

		boolean goWest(int x, int y) {

			if (x == 0)
				return false;
			String row = s.get(y);
			switch (row.charAt(x)) {
			case 'S':
			case '-':
			case 'J':
			case '7':
				break;
			default:
				return false;
			}
			switch (row.charAt(x - 1)) {
			case 'S':
			case '-':
			case 'L':
			case 'F':
				return true;
			default:
				return false;
			}
		}

		boolean goEast(int x, int y) {
			String row = s.get(y);
			if (x == row.length() - 1)
				return false;
			switch (row.charAt(x)) {
			case 'S':
			case '-':
			case 'L':
			case 'F':
				break;
			default:
				return false;
			}
			switch (row.charAt(x + 1)) {
			case 'S':
			case '-':
			case 'J':
			case '7':
				return true;
			default:
				return false;
			}
		}
	}
}
