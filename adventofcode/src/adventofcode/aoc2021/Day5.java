package adventofcode.aoc2021;

import java.util.List;
import java.util.StringTokenizer;

import adventofcode.grid.Grid;
import adventofcode.util.IOUtil;

public class Day5 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day5.test");
		List<String> data = IOUtil.readFile("2021/day5.data");

		part2(test);
		part2(data);
	}

	private static void part2(List<String> data) {
		Grid<Integer> grid = fillGrid2(data);

		long minX = grid.getMinX();
		long maxX = grid.getMaxX();
		long minY = grid.getMinY();
		long maxY = grid.getMaxY();

		System.out.println("(" + minX + "," + minY + ")-(" + maxX + "," + maxY + ")");

		int c = 0;
		for (long y = minY; y <= maxY; y++) {
			for (long x = minX; x <= maxX; x++) {
				int v = grid.get(x, y);
				System.out.print(v == 0 ? "." : v);
				if (v > 1)
					c++;
			}
			System.out.println();
		}
		System.out.println("part1: " + c);
	}

	private static Grid<Integer> fillGrid(List<String> data) {
		Grid<Integer> grid = new Grid<>(0);

		for (String l : data) {
			StringTokenizer st = new StringTokenizer(l, ", ->");
			int startx = Integer.parseInt(st.nextToken());
			int starty = Integer.parseInt(st.nextToken());
			int endx = Integer.parseInt(st.nextToken());
			int endy = Integer.parseInt(st.nextToken());
			if (startx == endx) {
				if (starty > endy) {
					int h = starty;
					starty = endy;
					endy = h;
				}
				for (int y = starty; y <= endy; y++) {
					grid.put(startx, y, grid.get(startx, y) + 1);
				}
			} else if (starty == endy) {
				if (startx > endx) {
					int h = startx;
					startx = endx;
					endx = h;
				}
				for (int x = startx; x <= endx; x++) {
					grid.put(x, starty, grid.get(x, starty) + 1);
				}
			}
		}
		return grid;
	}

	private static Grid<Integer> fillGrid2(List<String> data) {
		Grid<Integer> grid = new Grid<>(0);

		for (String l : data) {
			StringTokenizer st = new StringTokenizer(l, ", ->");
			int startx = Integer.parseInt(st.nextToken());
			int starty = Integer.parseInt(st.nextToken());
			int endx = Integer.parseInt(st.nextToken());
			int endy = Integer.parseInt(st.nextToken());

			if (startx == endx) {
				if (starty > endy) {
					int h = starty;
					starty = endy;
					endy = h;
				}
				for (int y = starty; y <= endy; y++) {
					grid.put(startx, y, grid.get(startx, y) + 1);
				}
			} else if (starty == endy) {
				if (startx > endx) {
					int h = startx;
					startx = endx;
					endx = h;
				}
				for (int x = startx; x <= endx; x++) {
					grid.put(x, starty, grid.get(x, starty) + 1);
				}
			} else {
				int dx = (startx > endx) ? -1 : 1;
				int dy = (starty > endy) ? -1 : 1;
				int x = startx;
				int y = starty;
				while ((x != endx + dx) && (y != endy + dy)) {
					grid.put(x, y, grid.get(x, y) + 1);
					x += dx;
					y += dy;
				}
			}
		}
		return grid;
	}

}
