package adventofcode.aoc2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day18 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day18.test");
		List<String> input = IOUtil.readFile("2023/day18.data");

		part1(test);
		part1(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<DigOrder> digOrders = input.stream().map(s -> new DigOrder(s)).toList();

		Set<Integer> trench = new HashSet<>();
		Set<Integer> left = new HashSet<>();
		Set<Integer> right = new HashSet<>();

		int x = 100;
		int y = 300;
		int minx = x;
		int miny = y;
		int maxx = x;
		int maxy = y;
		for (DigOrder d : digOrders) {
			switch (d.direction) {
			case 'U':
				for (int i = 0; i < d.length; i++) {
					trench.add(x * 1000 + y - i);
					left.add((x - 1) * 1000 + y - i);
					right.add((x + 1) * 1000 + y - i);
				}
				y -= d.length;
				miny = Math.min(y, miny);
				break;
			case 'D':
				for (int i = 0; i < d.length; i++) {
					trench.add(x * 1000 + y + i);
					left.add((x + 1) * 1000 + y + i);
					right.add((x - 1) * 1000 + y + i);
				}
				y += d.length;
				maxy = Math.max(y, maxy);
				break;
			case 'L':
				for (int i = 0; i < d.length; i++) {
					trench.add((x - i) * 1000 + y);
					left.add((x - i) * 1000 + y + 1);
					right.add((x - i) * 1000 + y - 1);
				}
				x -= d.length;
				minx = Math.min(x, minx);
				break;
			case 'R':
				for (int i = 0; i < d.length; i++) {
					trench.add((x + i) * 1000 + y);
					left.add((x + i) * 1000 + y - 1);
					right.add((x + i) * 1000 + y + 1);
				}
				x += d.length;
				maxx = Math.max(x, maxx);
				break;
			}
		}
		right.removeAll(trench);
		left.removeAll(trench);

		System.out.println("% " + trench.size());

		System.out.println("# " + minx + ".." + maxx + "," + miny + ".." + maxy);

		char[][] grid = new char[maxx + 2][maxy + 2];
		for (int y1 = miny - 1; y1 <= maxy + 1; y1++) {
			for (int x1 = minx - 1; x1 <= maxx + 1; x1++) {
				int point = x1 * 1000 + y1;
				if (trench.contains(point)) {
//					System.out.print("#");
					grid[x1][y1] = '#';
				} else if (left.contains(point)) {
//					System.out.print("l");
					grid[x1][y1] = 'l';
				} else if (right.contains(point)) {
//					System.out.print("r");
					grid[x1][y1] = 'r';
				} else {
//					System.out.print(".");
					grid[x1][y1] = '.';
				}
			}
//			System.out.println();
		}

		int k = 0;
		do {
			k = fill(grid, minx, miny, maxx, maxy);
			System.out.println("/ " + k);
		} while (k != 0);

		int rightCount = 0;
		int leftCount = 0;
		for (int y1 = miny - 1; y1 <= maxy; y1++) {
			for (int x1 = minx - 1; x1 <= maxx; x1++) {
				if (grid[x1][y1] == 'r') {
					rightCount++;
				}
				if (grid[x1][y1] == 'l') {
					leftCount++;
				}
			}
		}

		System.out.println("+ " + rightCount + " " + leftCount);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + (trench.size() + rightCount));
	}

	static int fill(char[][] grid, int minx, int miny, int maxx, int maxy) {
		int modified = 0;
		for (int x = minx - 1; x <= maxx; x++) {
			for (int y = miny - 1; y <= maxy; y++) {
				char p = grid[x][y];
				if (p == '.') {
					Set<Character> neighbors = Arrays
							.asList(grid[x - 1][y - 1], grid[x][y - 1], grid[x + 1][y - 1], grid[x - 1][y],
									grid[x + 1][y], grid[x - 1][y + 1], grid[x][y + 1], grid[x + 1][y + 1])
							.stream().collect(Collectors.toSet());
					if (neighbors.contains('r')) {
						grid[x][y] = 'r';
						modified++;
					} else if (neighbors.contains('l')) {
						grid[x][y] = 'l';
						modified++;
					}
				}
			}
		}
		return modified;
	}

	static class DigOrder {
		char direction;
		int length;
		String color;

		DigOrder(String in) {
			StringTokenizer st = new StringTokenizer(in, " ()#");
			direction = st.nextToken().charAt(0);
			length = Integer.parseInt(st.nextToken());
			color = st.nextToken();
		}
	}
}
