package adventofcode.aoc2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day01 {

	public static void main(String[] args) {
		List<String> input = IOUtil.readFile("2023/day01.data");

		part1(input);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		int result = input.stream().map(s -> numberFromString(s)).reduce((a, b) -> a + b).orElse(0);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + result);
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		int result = input.stream().map(s -> numberFromString2(s)).reduce((a, b) -> a + b).orElse(0);

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + result);
	}

	private static int numberFromString(String s) {
		List<Integer> numbers = s.chars().filter(c -> c >= '0').filter(c -> c <= '9').map(c -> c - '0').boxed()
				.toList();
		int left = numbers.get(0);
		int right = numbers.get(numbers.size() - 1);
		return left * 10 + right;
	}

	private static int numberFromString2(String s) {

		Map<Integer, Integer> hits = new HashMap<>();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				hits.put(i, c - '0');
			}
		}

		Map<String, Integer> translation = new HashMap<>();

		translation.put("one", 1);
		translation.put("two", 2);
		translation.put("three", 3);
		translation.put("four", 4);
		translation.put("five", 5);
		translation.put("six", 6);
		translation.put("seven", 7);
		translation.put("eight", 8);
		translation.put("nine", 9);

		for (Map.Entry<String, Integer> e : translation.entrySet()) {

			String t = e.getKey();

			int i = s.indexOf(t);
			while (i >= 0) {
				hits.put(i, e.getValue());
				i = s.indexOf(t, i + 1);
			}
		}

		int lowIndex = hits.keySet().stream().reduce(Math::min).orElse(0);
		int highIndex = hits.keySet().stream().reduce(Math::max).orElse(0);

		int left = hits.get(lowIndex);
		int right = hits.get(highIndex);
		return left * 10 + right;
	}
}
