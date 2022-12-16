package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day13 {
	private static final int yMultiplier = 10_000;

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day13.test");
		List<String> data = IOUtil.readFile("2021/day13.data");

		Set<Integer> dotsTest = parseDots(test);
		Set<Integer> dots = parseDots(data);

		List<int[]> instructionsTest = parseInstructions(test);
		List<int[]> instructions = parseInstructions(data);
		part1(dotsTest, instructionsTest);
		part1(dots, instructions);

		part2(dotsTest, instructionsTest);
		part2(dots, instructions);

	}

	private static Set<Integer> parseDots(List<String> input) {
		int i = 0;
		Set<Integer> dots = new HashSet<>();
		String line = input.get(i);
		while (i < input.size() && !"".equals(line)) {
			String[] p = line.split(",");
			dots.add(Integer.parseInt(p[0]) + Integer.parseInt(p[1]) * yMultiplier);
			i++;
			line = input.get(i);
		}
		return dots;
	}

	private static List<int[]> parseInstructions(List<String> input) {
		int i = input.indexOf("") + 1;

		List<int[]> r = new ArrayList<>();
		while (i < input.size()) {
			String s = input.get(i);
			int k = s.indexOf('=');

			r.add(new int[] { s.charAt(k - 1) == 'x' ? 1 : yMultiplier, Integer.parseInt(s.substring(k + 1)) });

			i++;
		}
		return r;
	}

	private static void part2(Set<Integer> dots, List<int[]> instructions) {
		Set<Integer> dots2 = new HashSet<>(dots);
		for (int[] instr : instructions) {
			dots2 = fold(instr[0], instr[1], dots2);
		}
		System.out.println("part2: ");
		print(dots2);
	}

	private static void part1(Set<Integer> dots, List<int[]> instructions) {
		// TODO Auto-generated method stub
		Set<Integer> dots2 = fold(instructions.get(0)[0], instructions.get(0)[1], dots);
		// print(dots2);

		System.out.println("# " + dots.size());
		System.out.println("part1: " + dots2.size()); // it's not 789
	}

	private static void print(Set<Integer> dots) {
		int maxX = dots.stream().map(i -> i % yMultiplier).reduce(Math::max).orElse(0);
		int maxY = dots.stream().map(i -> i / yMultiplier).reduce(Math::max).orElse(0);

		for (int y = 0; y <= maxY; y++) {
			for (int x = 0; x <= maxX; x++) {
				int dot = x + y * yMultiplier;
				System.out.print(dots.contains(dot) ? "#" : ".");
			}
			System.out.println();
		}
	}

	private static Set<Integer> fold(int axis, int index, Set<Integer> dots1) {
		Set<Integer> dots2 = new HashSet<Integer>();

		for (int dot : dots1) {
			int x = dot % yMultiplier;
			int y = dot / yMultiplier;

			if (axis == 1) {
				if (x > index) {
					x = 2 * index - x;
				} else if (x == index) {
					continue;
				}
			} else {
				if (y > index) {
					y = 2 * index - y;
				} else if (y == index) {
					continue;
				}
			}
			dots2.add(x + y * yMultiplier);
		}

		return dots2;
	}
}
