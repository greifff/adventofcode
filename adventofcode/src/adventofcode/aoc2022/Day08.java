package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day08 {

	private static final int FACTOR = 1000;

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day08.test");
		List<String> input = IOUtil.readFile("2022/day08.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {

		int treesOnBorder = 2 * (input.size() + input.get(0).length() - 2);

		Set<Integer> visibleInside = new HashSet<>();

		for (int y = 1; y < input.size() - 1; y++) {
			int xinf = input.get(0).length() - 1;
			visibleInside.addAll(look(0, y, 1, 0, xinf, 0, input));
			visibleInside.addAll(look(xinf, y, -1, 0, 0, 0, input));
		}

		for (int x = 1; x < input.get(0).length() - 1; x++) {
			int yinf = input.size() - 1;
			visibleInside.addAll(look(x, 0, 0, 1, 0, yinf, input));
			visibleInside.addAll(look(x, yinf, 0, -1, 0, 0, input));
		}

		System.out.println("# " + visibleInside.stream().map(l -> "" + l).collect(Collectors.joining(", ")));

		System.out.println(MessageFormat.format("part1: {0}", treesOnBorder + visibleInside.size()));
	}

	private static void part2(List<String> input) {

		int xinf = input.get(0).length() - 1;
		int yinf = input.size() - 1;

		int score = 0;

		for (int y = 1; y < yinf; y++) {
			for (int x = 1; x < xinf; x++) {
				int north = countVisibleTrees(x, y, 0, -1, 0, -1, input);
				int south = countVisibleTrees(x, y, 0, 1, 0, yinf + 1, input);
				int west = countVisibleTrees(x, y, -1, 0, -1, 0, input);
				int east = countVisibleTrees(x, y, 1, 0, xinf + 1, 0, input);

				int score1 = north * south * west * east;

				score = Math.max(score, score1);
			}
		}

		System.out.println(MessageFormat.format("part2: {0}", score));
	}

	private static int countVisibleTrees(int sx, int sy, int dx, int dy, int mx, int my, List<String> grid) {
		int cx = sx;
		int cy = sy;

		int visible = 0;

		int currentHeight = getHeight(cx, cy, grid);
		cx += dx;
		cy += dy;

		while (cx != mx && cy != my) {
			int newHeight = getHeight(cx, cy, grid);

			visible++;
			if (newHeight >= currentHeight) {
				break;
			}

			cx += dx;
			cy += dy;
		}
		return visible;
	}

	private static Set<Integer> look(int sx, int sy, int dx, int dy, int mx, int my, List<String> grid) {
		int cx = sx;
		int cy = sy;

		Set<Integer> visible = new HashSet<>();

		int currentHeight = getHeight(cx, cy, grid);
		cx += dx;
		cy += dy;

		while (cx != mx && cy != my) {
			int newHeight = getHeight(cx, cy, grid);

			if (newHeight > currentHeight) {
				visible.add(cx * FACTOR + cy);
				currentHeight = newHeight;
			}

			cx += dx;
			cy += dy;
		}
		return visible;
	}

	private static int getHeight(int x, int y, List<String> grid) {
		return grid.get(y).charAt(x) - '0';
	}

}
