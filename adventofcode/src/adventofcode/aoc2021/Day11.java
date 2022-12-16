package adventofcode.aoc2021;

import java.util.List;

import adventofcode.grid.Grid;
import adventofcode.util.IOUtil;

public class Day11 {
	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day11.test");
		List<String> data = IOUtil.readFile("2021/day11.data");

		part1(test);
		part1(data);

		part2(test);
		part2(data);
	}

	private static void part2(List<String> data) {
		// TODO Auto-generated method stub

	}

	private static void part1(List<String> data) {
		Grid<Integer> grid = new Grid<>(0);
		for (int y = 0; y < 10; y++) {
			String l = data.get(y);
			for (int x = 0; x < 10; x++) {
				grid.put(x, y, l.charAt(x) - 0x30);
			}
		}

		int flashes = 0;
		for (int i = 0; i < 1000; i++) {
			if (i == 100) {
				System.out.println("part1: " + flashes);
			}
			// increment power
			for (int y = 0; y < 10; y++)
				for (int x = 0; x < 10; x++) {
					grid.put(x, y, grid.get(x, y) + 1);
				}

			// execute flashes
			boolean flashCanHappen = true;
			int flashesThisRound = 0;
			while (flashCanHappen) {
				flashCanHappen = false;
				for (int y = 0; y < 10; y++)
					for (int x = 0; x < 10; x++) {
						if (grid.get(x, y) > 9) {
							flashes++;
							flashesThisRound++;
							grid.put(x, y, -100); // energy spend
							increment(grid, x - 1, y - 1);
							increment(grid, x - 1, y);
							increment(grid, x - 1, y + 1);
							increment(grid, x, y - 1);
							increment(grid, x, y + 1);
							increment(grid, x + 1, y - 1);
							increment(grid, x + 1, y);
							increment(grid, x + 1, y + 1);
							flashCanHappen = true;
						}
					}
			}
			if (flashesThisRound == 100) {
				System.out.println("part2: " + (i + 1));
				return;
			}

			// reset spend nodes to 0
			for (int y = 0; y < 10; y++)
				for (int x = 0; x < 10; x++)
					if (grid.get(x, y) < 0) {
						grid.put(x, y, 0);
					}

		}

	}

	private static void increment(Grid<Integer> grid, int x, int y) {
		grid.put(x, y, grid.get(x, y) + 1);
	}

}
