package adventofcode.aoc2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day24 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2019/day24/day24.test");
		List<String> data = IOUtil.readFile("2019/day24/day24.data");

		part1(test);
		part1(data);
	}

	private static void part1(List<String> data) {
		boolean[][] grid = initGrid(data);
		Map<Integer, Integer> history = new HashMap<>();
		history.put(pack(grid), 0);
		int i = 0;
		while (true) {
			i++;
			grid = step(grid);
			int encoded = pack(grid);
			Integer k = history.put(encoded, i);
			if (k != null) {
				System.out.println("part1: " + k + " " + encoded);
				return;
			}
		}
	}

	private static boolean[][] step(boolean[][] grid1) {
		boolean[][] grid2 = new boolean[5][5];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				int countActiveNeighbors = 0;
				if (x > 0) {
					countActiveNeighbors += grid1[x - 1][y] ? 1 : 0;
				}
				if (x < 4) {
					countActiveNeighbors += grid1[x + 1][y] ? 1 : 0;
				}
				if (y > 0) {
					countActiveNeighbors += grid1[x][y - 1] ? 1 : 0;
				}
				if (y < 4) {
					countActiveNeighbors += grid1[x][y + 1] ? 1 : 0;
				}
				grid2[x][y] = countActiveNeighbors == 1 || (!grid1[x][y] && countActiveNeighbors == 2);
			}
		}
		return grid2;
	}

	private static int pack(boolean[][] grid) {
		int result = 0;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (grid[x][y]) {
					result += 1 << (x + y * 5);
				}
			}
		}
		// System.out.println(": " + result + " : " + Integer.toBinaryString(result));
		return result;
	}

	private static boolean[][] initGrid(List<String> data) {
		boolean[][] result = new boolean[5][5];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				result[x][y] = data.get(y).charAt(x) == '#';
			}
		}
		return result;
	}
}
