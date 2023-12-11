package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day11 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day11.test");
		List<String> input = IOUtil.readFile("2023/day11.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Galaxy> galaxies = expandAndParse(input, 1);

		long sum = 0;

		for (int i = 0; i < galaxies.size() - 1; i++) {
			Galaxy g1 = galaxies.get(i);
			for (int j = i + 1; j < galaxies.size(); j++) {
				Galaxy g2 = galaxies.get(j);

				long d = g1.distance(g2);

				sum += d;
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
	}

	private static List<Galaxy> expandAndParse(List<String> input, int additional) {
		long[] galaxiesInRow = new long[input.size()];
		long[] galaxiesInColumn = new long[input.get(0).length()];

		for (int y = 0; y < input.size(); y++) {
			String row = input.get(y);
			for (int x = 0; x < input.size(); x++) {
				if (row.charAt(x) == '#') {
					galaxiesInRow[y]++;
					galaxiesInColumn[x]++;
				}
			}
		}

		List<Galaxy> galaxies = new ArrayList<>();
		int dy = 0;
		for (int y = 0; y < input.size(); y++) {
			if (galaxiesInRow[y] == 0) {
				dy += additional;
				continue;
			}
			String row = input.get(y);
			int dx = 0;
			for (int x = 0; x < input.size(); x++) {
				if (galaxiesInColumn[x] == 0) {
					dx += additional;
					continue;
				}
				if (row.charAt(x) == '#') {
					Galaxy g = new Galaxy();
					g.x = x + dx;
					g.y = y + dy;
					galaxies.add(g);
				}
			}
		}
		return galaxies;
	}

	static class Galaxy {
		long x;
		long y;

		long distance(Galaxy o) {
			return Math.abs(x - o.x) + Math.abs(y - o.y);
		}
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Galaxy> galaxies = expandAndParse(input, 999_999);

		long sum = 0;

		for (int i = 0; i < galaxies.size() - 1; i++) {
			Galaxy g1 = galaxies.get(i);
			for (int j = i + 1; j < galaxies.size(); j++) {
				Galaxy g2 = galaxies.get(j);

				long d = g1.distance(g2);

				sum += d;
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + sum);
	}
}
