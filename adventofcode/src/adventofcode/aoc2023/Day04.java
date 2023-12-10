package adventofcode.aoc2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day04 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day04.test");
		List<String> input = IOUtil.readFile("2023/day04.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Card> cards = input.stream().filter(s -> !"".equals(s)).map(s -> new Card(s)).toList();

		int sum = cards.stream().map(c -> c.score()).reduce((a, b) -> a + b).orElse(0);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Card> cards = input.stream().filter(s -> !"".equals(s)).map(s -> new Card(s)).toList();

		for (int i = 0; i < cards.size(); i++) {
			Card c = cards.get(i);
			int m = cards.get(i).copies;
			int f = c.hits();
			for (int j = i + 1; j < Math.min(i + f + 1, cards.size()); j++) {
				cards.get(j).copies += m;
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + cards.stream().map(c -> c.copies).reduce((a, b) -> a + b).orElse(0));
	}

	static class Card {
		int index;
		int copies = 1;

		List<Integer> left;
		List<Integer> right;

		Card(String s) {
			int i1 = s.indexOf(':');
			int i2 = s.indexOf('|');

			index = Integer.parseInt(s.substring(5, i1).trim());
			left = Arrays.stream(s.substring(i1 + 1, i2).split(" ")).filter(t -> !"".equals(t))
					.map(t -> Integer.parseInt(t)).toList();
			right = Arrays.stream(s.substring(i2 + 1).split(" ")).filter(t -> !"".equals(t))
					.map(t -> Integer.parseInt(t)).toList();
		}

		int score() {
			int count = hits();
			if (count == 0)
				return 0;
			return 1 << (count - 1);
		}

		int hits() {

			Set<Integer> hits = new HashSet<>(left);
			hits.retainAll(right);
			return hits.size();
		}
	}

}
