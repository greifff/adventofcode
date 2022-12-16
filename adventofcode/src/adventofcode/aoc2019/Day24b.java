package adventofcode.aoc2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day24b {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2019/day24/day24.test");
		List<String> data = IOUtil.readFile("2019/day24/day24.data");

		part2(test, 10);
		part2(data, 200);
	}

	private static void part2(List<String> data, int turns) {
		Map<Integer, boolean[][]> grid = initGrid(data);
		System.out.println("s: " + count(grid));
		for (int i = 0; i < turns; i++) {
			grid = step(grid);
			// System.out.println("t-" + i + ": " + count(grid));
			// print(grid);
		}

		System.out.println("part2: " + count(grid));
	}

	private static void print(Map<Integer, boolean[][]> grid) {
		int minLevel = grid.keySet().stream().reduce(Math::min).orElse(0);
		int maxLevel = grid.keySet().stream().reduce(Math::max).orElse(0);

		for (int level = minLevel; level <= maxLevel; level++) {
			boolean[][] current = grid.get(level);
			System.out.println("== " + level + " ==");
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					System.out.print(current[x][y] ? '#' : '.');
				}
				System.out.println();
			}
		}

	}

	private static int count(Map<Integer, boolean[][]> grid) {
		int count = 0;
		for (boolean[][] current : grid.values()) {
			for (int x = 0; x < 5; x++)
				for (int y = 0; y < 5; y++) {
					count += current[x][y] ? 1 : 0;
				}
		}
		return count;
	}

	private static Map<Integer, boolean[][]> step(Map<Integer, boolean[][]> grid1) {

		Map<Integer, boolean[][]> grid2 = new HashMap<Integer, boolean[][]>();

		int minLevel = grid1.keySet().stream().reduce(Math::min).orElse(0);
		int maxLevel = grid1.keySet().stream().reduce(Math::max).orElse(0);

		addLevel(grid1, minLevel - 1);
		addLevel(grid1, maxLevel + 1);

		for (int level = minLevel; level <= maxLevel; level++) {
			boolean[][] upper = grid1.get(level - 1);
			boolean[][] current = grid1.get(level);
			boolean[][] lower = grid1.get(level + 1);
			boolean[][] next = new boolean[5][5];
			for (int y = 0; y < 5; y++) {
				for (int x = 0; x < 5; x++) {
					if (y == 2 && x == 2) {
						next[2][2] = false;
					} else {
						next[x][y] = evalField(current, upper, lower, x, y);
					}
				}
			}
			grid2.put(level, next);
		}

		boolean[][] highest = grid2.get(minLevel);
		boolean highestIsActive = false;
		boolean[][] lowest = grid2.get(maxLevel);
		boolean lowestIsActive = false;
		for (int x = 0; x < 5; x++)
			for (int y = 0; y < 5; y++) {
				highestIsActive |= highest[x][y];
				lowestIsActive |= lowest[x][y];
			}
		if (highestIsActive) {
			addLevel(grid2, minLevel - 1);
		}
		if (lowestIsActive) {
			addLevel(grid2, maxLevel + 1);
		}
		return grid2;
	}

	private static boolean evalField(boolean[][] current, boolean[][] upper, boolean[][] lower, int x, int y) {
		int countActiveNeighbors = 0;
		// left neighbor
		if (x == 0) {
			countActiveNeighbors += upper[1][2] ? 1 : 0;
		} else if (x == 3 && y == 2) {
			for (int yl = 0; yl < 5; yl++) {
				countActiveNeighbors += lower[4][yl] ? 1 : 0;
			}
		} else {
			countActiveNeighbors += current[x - 1][y] ? 1 : 0;
		}
		// right neighbor
		if (x == 4) {
			countActiveNeighbors += upper[3][2] ? 1 : 0;
		} else if (x == 1 && y == 2) {
			for (int yl = 0; yl < 5; yl++) {
				countActiveNeighbors += lower[0][yl] ? 1 : 0;
			}
		} else {
			countActiveNeighbors += current[x + 1][y] ? 1 : 0;
		}
		// top neighbor
		if (y == 0) {
			countActiveNeighbors += upper[2][1] ? 1 : 0;
		} else if (x == 2 && y == 3) {
			for (int xl = 0; xl < 5; xl++) {
				countActiveNeighbors += lower[xl][4] ? 1 : 0;
			}
		} else {
			countActiveNeighbors += current[x][y - 1] ? 1 : 0;
		}
		// bottom neighbor
		if (y == 4) {
			countActiveNeighbors += upper[2][3] ? 1 : 0;
		} else if (x == 2 && y == 1) {
			for (int xl = 0; xl < 5; xl++) {
				countActiveNeighbors += lower[xl][0] ? 1 : 0;
			}
		} else {
			countActiveNeighbors += current[x][y + 1] ? 1 : 0;
		}

		return countActiveNeighbors == 1 || (!current[x][y] && countActiveNeighbors == 2);
	}

	private static void addLevel(Map<Integer, boolean[][]> grid, int level) {
		boolean[][] result = new boolean[5][5];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				result[x][y] = false;
			}
		}
		grid.put(level, result);
	}

	private static Map<Integer, boolean[][]> initGrid(List<String> data) {
		boolean[][] result = new boolean[5][5];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				result[x][y] = data.get(y).charAt(x) == '#';
			}
		}
		Map<Integer, boolean[][]> result2 = new HashMap<>();
		result2.put(0, result);
		addLevel(result2, -1);
		addLevel(result2, 1);
		return result2;
	}
}
