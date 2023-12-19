package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day18b {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day18.test");
		List<String> input = IOUtil.readFile("2023/day18.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<DigOrder1> digOrders = input.stream().map(s -> new DigOrder1(s)).toList();
		List<Trench> trenches = new ArrayList<>();
		long x = 100;
		long y = 300;
		for (DigOrder1 d : digOrders) {
			Trench trench = new Trench(x, y, d);
			trenches.add(trench);
			x = trench.x2;
			y = trench.y2;
		}

		System.out.println("# " + x + " " + y); // has to be at 0,0 again

		long trenchLength = digOrders.stream().map(d -> d.length).reduce((a, b) -> a + b).orElse(0);
		System.out.println("l " + trenchLength);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + (calculateArea(trenches) + trenchLength / 2 + 1));
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<DigOrder2> digOrders = input.stream().map(s -> new DigOrder2(s)).toList();
		List<Trench> trenches = new ArrayList<>();
		long x = 1_000_000;
		long y = 2_200_000;
		for (DigOrder2 d : digOrders) {
			Trench trench = new Trench(x, y, d);
			trenches.add(trench);
			x = trench.x2;
			y = trench.y2;
		}

		System.out.println("# " + x + " " + y); // has to be at 0,0 again

		long trenchLength = digOrders.stream().map(d -> d.length).reduce((a, b) -> a + b).orElse(0);
		System.out.println("l " + trenchLength);

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + (calculateArea(trenches) + trenchLength / 2 + 1));

		// testdata : trench length 6405262, area 952_404_941_483, but shall be 952_408_144_115 - missing 3.202.632
		// 6_405_262 / 2
	}

	static long calculateArea(List<Trench> trenches) {
		long area = 0;
		for (Trench trench : trenches) {
			// simplification of Gauss's area formula
//			area += (trench.x1 - trench.x2) * (trench.y1 + trench.y2) / 2;
			area += (trench.x1 - trench.x2) * trench.y1;
		}

		// debug output min/max-coordinates
		long minx = 0;
		long maxx = 0;
		long miny = 0;
		long maxy = 0;
		for (Trench trench : trenches) {
			if (trench.x1 > trench.x2) {
				long h = trench.x2;
				trench.x2 = trench.x1;
				trench.x1 = h;
			}
			if (trench.y1 > trench.y2) {
				long h = trench.y2;
				trench.y2 = trench.y1;
				trench.y1 = h;
			}
			minx = Math.min(minx, trench.x1);
			maxx = Math.max(maxx, trench.x2);
			miny = Math.min(miny, trench.y1);
			maxy = Math.max(maxy, trench.y2);
		}

		System.out.println("! " + minx + ".." + maxx + " " + miny + ".." + maxy);

		return area;
	}

	static class Trench {
		long x1;
		long y1;
		long x2;
		long y2;

		Trench(long x, long y, DigOrder1 d) {
			x1 = x;
			y1 = y;

			switch (d.direction) {
			case 'U':
				// up
				x2 = x1;
				y2 = y1 - d.length;
				break;
			case 'D':
				// down
				x2 = x1;
				y2 = y1 + d.length;
				break;
			case 'L':
				// left
				y2 = y1;
				x2 = x1 - d.length;
				break;
			case 'R':
				// right
				y2 = y1;
				x2 = x1 + d.length;
				break;
			}
		}

		Trench(long x, long y, DigOrder2 d) {
			x1 = x;
			y1 = y;

			switch (d.direction) {
			case '3':
				// up
				x2 = x1;
				y2 = y1 - d.length;
				break;
			case '1':
				// down
				x2 = x1;
				y2 = y1 + d.length;
				break;
			case '2':
				// left
				y2 = y1;
				x2 = x1 - d.length;
				break;
			case '0':
				// right
				y2 = y1;
				x2 = x1 + d.length;
				break;
			}
		}
	}

	static class DigOrder1 {
		char direction;
		int length;
		String color;

		DigOrder1(String in) {
			StringTokenizer st = new StringTokenizer(in, " ()#");
			direction = st.nextToken().charAt(0);
			length = Integer.parseInt(st.nextToken());
			color = st.nextToken();
		}
	}

	static class DigOrder2 {
		char direction;
		int length;

		DigOrder2(String in) {
			int split = in.indexOf('#');
			direction = in.charAt(split + 6);
			length = Integer.parseInt(in.substring(split + 1, split + 6), 16);
		}
	}
}
