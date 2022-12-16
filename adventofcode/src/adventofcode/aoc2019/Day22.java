package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day22 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day22/day22.test1");
		List<String> test2 = IOUtil.readFile("2019/day22/day22.test2");
		List<String> test3 = IOUtil.readFile("2019/day22/day22.test3");
		List<String> test4 = IOUtil.readFile("2019/day22/day22.test4");
		List<String> data = IOUtil.readFile("2019/day22/day22.data");

		test(test1);
		test(test2);
		test(test3);
		test(test4);

		part1(data);
		// part2(data);
	}

	private static void test(List<String> data) {
		List<Long> cards = new ArrayList<>();
		for (long i = 0; i < 10; i++) {
			cards.add(i);
		}

		cards = shuffle(cards, data);

		System.out.println("test: " + cards.stream().map(c -> "" + c).reduce((a, b) -> a + " " + b).orElse(""));
	}

	private static void part1(List<String> data) {
		List<Long> cards = new ArrayList<>();
		for (long i = 0; i < 10007; i++) {
			cards.add(i);
		}

		cards = shuffle(cards, data);

		System.out.println("part1: " + cards.indexOf(2019L));
	}

	private static List<Long> shuffle(List<Long> deck, List<String> instructions) {
		List<Long> cards = deck;

		for (String instruction : instructions) {
			if ("deal into new stack".equals(instruction)) {
				Collections.reverse(cards);
			} else if (instruction.startsWith("deal with increment")) {
				int x = Integer.parseInt(instruction.substring(20));
				List<Long> a = new ArrayList<>();
				for (int i = 0; i < cards.size(); i++) {
					a.add(0L);
				}
				int j = 0;
				for (int i = 0; i < cards.size(); i++) {
					a.set(j, cards.get(i));
					j = (j + x) % cards.size();
				}
				cards = a;
			} else if (instruction.startsWith("cut")) {
				int x = Integer.parseInt(instruction.substring(4));
				if (x > 0) {
					List<Long> a = cards.subList(0, x);
					cards = cards.subList(x, cards.size());
					cards.addAll(a);
				} else if (x < 0) {
					List<Long> a = cards.subList(0, cards.size() + x);
					cards = cards.subList(cards.size() + x, cards.size());
					cards.addAll(a);
				}
			}
		}

		return cards;
	}

	private static void part2(List<String> data) {
		List<Long> cards = new ArrayList<>();
		for (long i = 0; i < 119_315_717_514_047L; i++) {
			cards.add(i);
		}
		for (long k = 0; k < 101_741_582_076_661L; k++) {
			cards = shuffle(cards, data);
		}

		System.out.println("part2: " + cards.get(2020));
	}
}
