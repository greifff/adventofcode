package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day20 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day20.test");
		List<String> input = IOUtil.readFile("2022/day20.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		long start = System.currentTimeMillis();

		List<NumberEntry> data = new LinkedList<>(input.stream().map(s -> new NumberEntry(s)).toList());

		prepareWorkOrder(data);
		mix(data);

		NumberEntry zero = data.stream().filter(e -> e.value == 0).findAny().get();

		int zeroPosition = data.indexOf(zero);

		long x1000 = data.get((zeroPosition + 1000) % data.size()).value;
		long x2000 = data.get((zeroPosition + 2000) % data.size()).value;
		long x3000 = data.get((zeroPosition + 3000) % data.size()).value;

		System.out.println("# " + x1000 + " " + x2000 + " " + x3000);

		// not -515, 6854 too high, 2713 too small
		System.out.println(MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start,
				x1000 + x2000 + x3000));
	}

	static class NumberEntry {
		long value;
		int workOrder;

		NumberEntry(String s) {
			value = Integer.parseInt(s);
		}

		@Override
		public String toString() {
			return "" + value;
		}
	}

	private static void prepareWorkOrder(List<NumberEntry> data) {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).workOrder = i;
		}
	}

	private static void mix(List<NumberEntry> data) {
		int w = 0;
		while (w < data.size()) {
			final int w1 = w;
			NumberEntry n = data.stream().filter(e -> e.workOrder == w1).findAny().get();

			long d = n.value;

			if (d != 0) {

				int index = data.indexOf(n);

				long targetPosition = index + d;

				if (targetPosition < 0) {
					targetPosition %= (data.size() - 1);
					targetPosition += data.size() - 1;
				}
				if (targetPosition >= data.size()) {
					targetPosition %= (data.size() - 1);
				}

				data.remove(index);

				data.add((int) targetPosition, n);

			}
			w++;
		}
	}

	private static void part2(List<String> input) {
		long start = System.currentTimeMillis();

		List<NumberEntry> data = new LinkedList<>(input.stream().map(s -> new NumberEntry(s)).toList());

		for (NumberEntry n : data) {
			n.value *= 811589153;
		}

		prepareWorkOrder(data);

		for (int i = 0; i < 10; i++) {
			mix(data);
		}

		NumberEntry zero = data.stream().filter(e -> e.value == 0).findAny().get();

		int zeroPosition = data.indexOf(zero);

		long x1000 = data.get((zeroPosition + 1000) % data.size()).value;
		long x2000 = data.get((zeroPosition + 2000) % data.size()).value;
		long x3000 = data.get((zeroPosition + 3000) % data.size()).value;

		System.out.println("# " + x1000 + " " + x2000 + " " + x3000);

		// not -515, 6854 too high, 2713 too small
		System.out.println(MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start,
				x1000 + x2000 + x3000));
	}

}
