package adventofcode.aoc2022;

import java.util.Arrays;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day04 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day04.test");
		List<String> input = IOUtil.readFile("2022/day04.data");

		run(test1);
		run(input);

	}

	private static void run(List<String> input) {

		int fullCoverage = 0;
		int intersect = 0;

		for (String in : input) {
			List<Integer> p = Arrays.stream(in.split("[,-]")).map(s -> Integer.parseInt(s)).toList();

			if (intersect(p))
				intersect++;

			if (covers(p))
				fullCoverage++;

		}

		System.out.println("part1: " + fullCoverage);
		System.out.println("part2: " + intersect);
	}

	private static boolean intersect(List<Integer> p) {
		return isBetween(p.get(0), p.get(1), p.get(2)) || isBetween(p.get(0), p.get(1), p.get(3))
				|| isBetween(p.get(2), p.get(3), p.get(0)) || isBetween(p.get(2), p.get(3), p.get(1));
	}

	private static boolean isBetween(int a, int b, int x) {
		return a <= x && x <= b;
	}

	private static boolean covers(List<Integer> p) {
		return (isBetween(p.get(0), p.get(1), p.get(2)) && isBetween(p.get(0), p.get(1), p.get(3)))
				|| (isBetween(p.get(2), p.get(3), p.get(0)) && isBetween(p.get(2), p.get(3), p.get(1)));
	}

}
