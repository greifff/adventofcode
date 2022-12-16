package adventofcode.aoc2022;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day03 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day03.test");
		List<String> input = IOUtil.readFile("2022/day03.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {

		int sum = 0;

		for (String in : input) {
			String left = in.substring(0, in.length() / 2);
			String right = in.substring(in.length() / 2);

			Set<Integer> l = getCharSet(left);
			Set<Integer> r = getCharSet(right);

			l.retainAll(r);

			sum += priority(l.iterator().next());
		}

		System.out.println("part1: " + sum);
	}

	private static Set<Integer> getCharSet(String in) {
		return in.chars().boxed().collect(Collectors.toSet());
	}

	private static int priority(int character) {
		if (character <= 'Z') {
			return character - 'A' + 27;
		}
		return character - 'a' + 1;
	}

	private static void part2(List<String> input) {
		int sum = 0;
		for (int i = 2; i < input.size(); i += 3) {
			Set<Integer> a = getCharSet(input.get(i - 2));
			Set<Integer> b = getCharSet(input.get(i - 1));
			Set<Integer> c = getCharSet(input.get(i));

			a.retainAll(b);
			a.retainAll(c);

			sum += priority(a.iterator().next());
		}
		System.out.println("part2: " + sum);
	}
}
