package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day18 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day18.test");
		List<String> input = IOUtil.readFile("2022/day18.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		long start = System.currentTimeMillis();

		System.out.println(MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start, 0));
	}

	private static void part2(List<String> input) {
		long start = System.currentTimeMillis();

		System.out.println(MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start, 0));
	}

}
