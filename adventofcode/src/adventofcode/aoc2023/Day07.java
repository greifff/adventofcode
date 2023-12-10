package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day07 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day07.test");
//		List<String> test2 = IOUtil.readFile("2023/day07.test2");
		List<String> input = IOUtil.readFile("2023/day07.data");

		part1(test);
//		part1(test2);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Hand> hands = new ArrayList<>(input.stream().map(s -> new Hand(s, false)).toList());

		Collections.sort(hands);

		long sum = 0;
		for (int i = 0; i < hands.size(); i++) {
//			System.out.println(
//					"#o " + Arrays.stream(hands.get(i).hand).boxed().map(c -> "" + c).collect(Collectors.joining(",")));
			sum += (i + 1) * hands.get(i).bid;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
		// its not 249373402, 249638266
	}

	static class Hand implements Comparable<Hand> {
		int[] hand;
		long bid;
		int type;
		int[] byValue = new int[15];
		int jokerCards = 0;

		Hand(String s, boolean jokers) {
			bid = Integer.parseInt(s.split(" ")[1]);
			hand = s.split(" ")[0].chars().map(i -> {
				switch (i) {
				case 'A':
					return 14;
				case 'K':
					return 13;
				case 'Q':
					return 12;
				case 'J':
					return jokers ? 1 : 11;
				case 'T':
					return 10;
				default:
					return i - '0';
				}
			}).toArray();

			for (int card : hand) {
				if (card == 1) {
					jokerCards++;
				} else {
					byValue[card]++;
				}
			}
		}

		@Override
		public int compareTo(Hand o) {
			int[] groups = Arrays.stream(byValue).sorted().toArray();
			int[] otherGroups = Arrays.stream(o.byValue).sorted().toArray();
			int biggestGroup = groups[groups.length - 1] + jokerCards;
			int otherBiggestGroup = otherGroups[otherGroups.length - 1] + o.jokerCards;
			int delta = biggestGroup - otherBiggestGroup;
			if (delta != 0)
				return delta;
			if (biggestGroup == 3 || biggestGroup == 2) {
				boolean fullhouse = groups[groups.length - 2] == 2;
				boolean otherFullhouse = otherGroups[otherGroups.length - 2] == 2;
//				System.out.println("#f " + Arrays.stream(hand).boxed().map(c -> "" + c).collect(Collectors.joining(","))
//						+ " X " + Arrays.stream(o.hand).boxed().map(c -> "" + c).collect(Collectors.joining(",")));
				if (fullhouse && !otherFullhouse) {
					return 1;
				}
				if (!fullhouse && otherFullhouse) {
					return -1;
				}
			}

			for (int i = 0; i < 5; i++) {
				delta = hand[i] - o.hand[i];
				if (delta != 0)
					return delta;
			}

			return 0;
		}

	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Hand> hands = new ArrayList<>(input.stream().map(s -> new Hand(s, true)).toList());

		Collections.sort(hands);

		long sum = 0;
		for (int i = 0; i < hands.size(); i++) {
//			System.out.println(
//					"#o " + Arrays.stream(hands.get(i).hand).boxed().map(c -> "" + c).collect(Collectors.joining(","))
//							+ " // " + Arrays.stream(hands.get(i).byValue).reduce(Math::max).orElse(0));
			sum += (i + 1) * hands.get(i).bid;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + sum);
	}
}
