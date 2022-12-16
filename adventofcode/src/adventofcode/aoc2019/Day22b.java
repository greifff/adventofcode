package adventofcode.aoc2019;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day22b {

	public static void main(String[] args) {
		// List<String> test1 = IOUtil.readFile("2019/day22/day22.test1");
		// List<String> test2 = IOUtil.readFile("2019/day22/day22.test2");
		// List<String> test3 = IOUtil.readFile("2019/day22/day22.test3");
		// List<String> test4 = IOUtil.readFile("2019/day22/day22.test4");
		List<String> data = IOUtil.readFile("2019/day22/day22.data");

		// test(test1);
		// test(test2);
		// test(test3);
		// test(test4);

		part1(data);
		test2(data);
		part2(data);
	}

	private static void part1(List<String> data) {
		long cardCount = 10007;

		long position = 2019;

		// Collections.reverse(data);

		// Map<Long, Long> positions = new HashMap<>();

		for (int j = 0; j < data.size(); j++) {
			position = action(data.get(j), position, cardCount);
		}

		System.out.println("part1: " + position);
	}

	private static void test2(List<String> data) {
		long cardCount = 10007;
		long position = 7096;

		// Collections.reverse(data);

		for (int j = data.size() - 1; j >= 0; j--) {
			// System.out.println("step " + step + " " + position);
			position = reverseAction(data.get(j), position, cardCount);
		}

		System.out.println("test2: " + position);
	}

	private static void part2(List<String> data) {
		long cardCount = 119_315_717_514_047L;
		long repeats = 101_741_582_076_661L;
		long position = 2020;

		// Collections.reverse(data);

		// Map<Long, Long> repetitions = new HashMap<>();

		for (long i = 0; i < repeats; i++) {
			//
			// if (repetitions.containsKey(position)) {
			// long alfa = repetitions.get(position);
			// System.out.println("Cycle detected between steps " + alfa + " and " + i);
			// long repeatsInLoop = repeats - alfa + 1;
			// long loopLength = i - alfa;
			// long stepX = (repeatsInLoop % loopLength) + alfa - 1;
			// repetitions.entrySet().stream().filter(e -> e.getValue() == stepX).forEach(e -> System.out.println("part 2: " + e.getKey()));
			// System.exit(0);
			// }
			//
			// repetitions.put(position, i);

			for (int j = data.size() - 1; j >= 0; j--) {
				// System.out.println("step " + step + " " + position);
				position = reverseAction(data.get(j), position, cardCount);
			}

			// 58_745_525_237_145 "too high"
		}

		System.out.println("part2: " + position);
	}

	private static long reverseAction(String action, long position, long cardCount) {
		if ("deal into new stack".equals(action)) {
			return cardCount - 1 - position;
		}
		if (action.startsWith("deal with increment")) {
			int x = Integer.parseInt(action.substring(20));
			return divideMod(position, x, cardCount);
		}
		if (action.startsWith("cut")) {
			int x = Integer.parseInt(action.substring(4));
			long p = position + x; // invert subtraction to addition
			if (p < 0) {
				return p + cardCount;
			}
			return p % cardCount;
		}
		return position;
	}

	private static long action(String action, long position, long cardCount) {
		if ("deal into new stack".equals(action)) {
			return cardCount - 1 - position;
		}
		if (action.startsWith("deal with increment")) {
			int x = Integer.parseInt(action.substring(20));
			return position * x % cardCount;
		}
		if (action.startsWith("cut")) {
			int x = Integer.parseInt(action.substring(4));
			long p = position - x;
			if (p < 0) {
				return p + cardCount;
			}
			return p % cardCount;
		}
		return position;
	}

	private static long divideMod(long a, long b, long base) {
		long result = a / b;
		long remainder = a % b;
		while (remainder != 0) {
			long c = remainder + base;
			result += c / b;
			remainder = c % b;
		}

		return result;
	}

}
