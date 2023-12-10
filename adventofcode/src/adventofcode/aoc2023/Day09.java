package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day09 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day09.test");
		List<String> input = IOUtil.readFile("2023/day09.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long sum = input.stream().map(s -> extrapolate(s)).reduce((a, b) -> a + b).orElse(0L);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
	}

	static long extrapolate(String in) {

		List<Long> row1 = Arrays.stream(in.split(" ")).map(s -> Long.parseLong(s)).toList();

		List<Long> lowest = row1;

		int extrapolate = 0;

		while (lowest.size() > 1 && lowest.stream().anyMatch(l -> l != 0L)) {

			extrapolate += lowest.get(lowest.size() - 1);
			List<Long> row2 = new LinkedList<>();
			for (int i = 1; i < lowest.size(); i++) {
				row2.add(lowest.get(i) - lowest.get(i - 1));
			}
			lowest = row2;
		}
		return extrapolate;
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long sum = input.stream().map(s -> extrapolateBack(s)).reduce((a, b) -> a + b).orElse(0L);

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + sum);
	}

	static long extrapolateBack(String in) {

		List<Long> row1 = Arrays.stream(in.split(" ")).map(s -> Long.parseLong(s)).toList();

		List<Long> lowest = row1;

		List<Long> first = new ArrayList<>();

		while (lowest.size() > 1 && lowest.stream().anyMatch(l -> l != 0L)) {
			first.add(lowest.get(0));

			List<Long> row2 = new LinkedList<>();
			for (int i = 1; i < lowest.size(); i++) {
				row2.add(lowest.get(i) - lowest.get(i - 1));
			}
			lowest = row2;
		}

		long extrapolate = 0;
		for (int i = first.size() - 1; i >= 0; i--) {
			extrapolate = first.get(i) - extrapolate;
		}

		return extrapolate;
	}

}
