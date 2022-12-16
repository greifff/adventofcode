package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import adventofcode.util.IOUtil;

public class Day14 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day14.test");
		List<String> input = IOUtil.readFile("2022/day14.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {

		Map<Integer, Set<Integer>> grid = new HashMap<>();

		for (String line : input) {
			List<Integer> coordinates = parseStructure(line);
			for (int i = 2; i < coordinates.size(); i += 2) {
				fillStone(coordinates.get(i - 2), coordinates.get(i - 1), coordinates.get(i), coordinates.get(i + 1),
						grid);
			}
		}

		int borderY = grid.values().stream().flatMap(s -> s.stream()).reduce(Math::max).orElse(0);

		// sand drop sim
//System.out.println("# "+ );
		int sand = -1;

		outer: while (true) {
			int x = 500;
			int y = 0;
			sand++;

			while (true) {
				if (!getSpot(x, y + 1, grid)) {
					y++;
				} else if (!getSpot(x - 1, y + 1, grid)) {
					x--;
					y++;
				} else if (!getSpot(x + 1, y + 1, grid)) {
					x++;
					y++;
				} else {
					setSpot(x, y, grid);
//					debug(grid, borderY);
					break;
				}
				if (y >= borderY) {
					break outer;
				}
			}
		}
// not 270
		System.out.println(MessageFormat.format("part1: {0}", sand));
	}

	private static void part2(List<String> input) {

		Map<Integer, Set<Integer>> grid = new HashMap<>();

		for (String line : input) {
			List<Integer> coordinates = parseStructure(line);
			for (int i = 2; i < coordinates.size(); i += 2) {
				fillStone(coordinates.get(i - 2), coordinates.get(i - 1), coordinates.get(i), coordinates.get(i + 1),
						grid);
			}
		}

		int borderY = grid.values().stream().flatMap(s -> s.stream()).reduce(Math::max).orElse(0);

		borderY++;

		// sand drop sim
//System.out.println("# "+ );
		int sand = 0;

		outer: while (true) {
			int x = 500;
			int y = 0;
			sand++;

			while (true) {
				if (!getSpot(x, y + 1, grid)) {
					y++;
				} else if (!getSpot(x - 1, y + 1, grid)) {
					x--;
					y++;
				} else if (!getSpot(x + 1, y + 1, grid)) {
					x++;
					y++;
				} else {
					setSpot(x, y, grid);
//					debug(grid, borderY);
					if (y == 0) {
						break outer;
					}
					break;
				}
				if (y >= borderY) {
					setSpot(x, y, grid);
					break;
				}
			}
		}

		System.out.println(MessageFormat.format("part2: {0}", sand));
	}

	private static void fillStone(int x1, int y1, int x2, int y2, Map<Integer, Set<Integer>> grid) {
		if (x1 == x2) {
			for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
				setSpot(x1, y, grid);
			}
		} else {
			for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
				setSpot(x, y1, grid);
			}
		}
	}

	private static void setSpot(int x, int y, Map<Integer, Set<Integer>> grid) {
		Set<Integer> ys = grid.get(x);
		if (ys == null) {
			ys = new HashSet<>();
			grid.put(x, ys);
		}
		ys.add(y);
	}

	private static boolean getSpot(int x, int y, Map<Integer, Set<Integer>> grid) {
		Set<Integer> ys = grid.get(x);
		if (ys == null)
			return false;
		return ys.contains(y);
	}

	private static List<Integer> parseStructure(String line) {
		StringTokenizer st = new StringTokenizer(line, " ->,");
		List<Integer> coordinates = new ArrayList<>();
		while (st.hasMoreTokens()) {
			coordinates.add(Integer.parseInt(st.nextToken()));
		}
		return coordinates;
	}

	private static void debug(Map<Integer, Set<Integer>> grid, int borderY) {
		int x1 = grid.keySet().stream().reduce(Math::min).orElse(0);
		int x2 = grid.keySet().stream().reduce(Math::max).orElse(0);

		for (int y = 0; y <= borderY; y++) {
			for (int x = x1; x <= x2; x++) {
				System.out.print(getSpot(x, y, grid) ? '#' : '.');
			}
			System.out.println();
		}
	}

}
