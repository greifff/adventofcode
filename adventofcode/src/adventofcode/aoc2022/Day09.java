package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day09 {

	private static final int FACTOR = 1000;

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day09.test");
		List<String> test2 = IOUtil.readFile("2022/day09.test2");
		List<String> input = IOUtil.readFile("2022/day09.data");

		System.out.println("part1");
		run(test1, 2);
		run(input, 2);
		System.out.println("part2");
		run(test1, 10);
		run(test2, 10);
		run(input, 10);
	}

	private static void run(List<String> input, int ropeLength) {

		List<Integer> knots = new ArrayList<>();
		for (int i = 0; i < ropeLength; i++) {
			knots.add(100 * FACTOR + 100);
		}

		Set<Integer> lastElementTrack = new HashSet<>();
		lastElementTrack.add(knots.get(0));

		for (String in : input) {
			String[] mv = in.split(" ");
			int d = Integer.parseInt(mv[1]);
			for (int i = 0; i < d; i++) {
				knots.set(0, moveHead(mv[0], knots.get(0)));

				for (int j = 1; j < ropeLength; j++) {
					if (need2moveTail(knots.get(j - 1), knots.get(j))) {
						int target = moveTail(knots.get(j - 1), knots.get(j));
						knots.set(j, target);
						if (j == ropeLength - 1) {
							lastElementTrack.add(target);
						}
					}
				}
			}
		}

		System.out.println(MessageFormat.format("result: {0}", lastElementTrack.size()));
	}

	private static boolean need2moveTail(int head, int tail) {
		Set<Integer> aroundTail = new HashSet<>(Arrays.asList(tail - FACTOR, tail - FACTOR - 1, tail - FACTOR + 1,
				tail + FACTOR, tail + FACTOR - 1, tail + FACTOR + 1, tail, tail - 1, tail + 1));

		return !aroundTail.contains(head);
	}

	private static int moveTail(int head, int tail) {
		Set<Integer> aroundTail = new HashSet<>(Arrays.asList(tail - FACTOR, tail - FACTOR - 1, tail - FACTOR + 1,
				tail + FACTOR, tail + FACTOR - 1, tail + FACTOR + 1, tail, tail - 1, tail + 1));
		Set<Integer> aroundHead1 = new HashSet<>(Arrays.asList(head, head - FACTOR, head + FACTOR, head - 1, head + 1));

		aroundHead1.retainAll(aroundTail);

		if (!aroundHead1.isEmpty()) {
			return aroundHead1.iterator().next();
		}

		Set<Integer> aroundHead2 = new HashSet<>(
				Arrays.asList(head - FACTOR - 1, head - FACTOR + 1, head + FACTOR - 1, head + FACTOR + 1));

		aroundHead2.retainAll(aroundTail);
		return aroundHead2.iterator().next();

	}

	private static int moveHead(String direction, int head) {
		switch (direction) {
		case "U":
			return head - FACTOR;
		case "D":
			return head + FACTOR;
		case "L":
			return head - 1;
		case "R":
			return head + 1;
		}
		return 0;
	}

}
