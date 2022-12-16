package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day11 {

	public static void main(String[] args) {

		part1(test1());
		part1(input());

		part2(test1());
		part2(input());
	}

	private static void part1(List<Monkey> monkeys) {

		for (int round = 0; round < 20; round++) {
			for (Monkey m : monkeys) {
				for (long item : m.items) {
					long worryLevel = m.worryOp.apply(item) / 3;
					boolean isDividable = worryLevel % m.divisor == 0;
					int target = m.targets.get(isDividable);
					monkeys.get(target).items.add(worryLevel);
				}
				m.inspections += m.items.size();
				m.items.clear();
			}
		}

		List<Long> insp = new ArrayList<>(monkeys.stream().map(m -> m.inspections).toList());

		Collections.sort(insp);
		Collections.reverse(insp);

		System.out.println(MessageFormat.format("part1: {0}", insp.get(0) * insp.get(1)));
	}

	private static void part2(List<Monkey> monkeys) {

		int bigDivider = monkeys.stream().map(m -> m.divisor).distinct().reduce((a, b) -> a * b).orElse(0);

		for (int round = 0; round < 10_000; round++) {
			for (Monkey m : monkeys) {
				for (long item : m.items) {
					long worryLevel = m.worryOp.apply(item) % bigDivider;
					boolean isDividable = worryLevel % m.divisor == 0;
					int target = m.targets.get(isDividable);
					monkeys.get(target).items.add(worryLevel);
				}
				m.inspections += m.items.size();
				m.items.clear();
			}
		}

		List<Long> insp = new ArrayList<>(monkeys.stream().map(m -> m.inspections).toList());

		Collections.sort(insp);
		Collections.reverse(insp);

		System.out.println(MessageFormat.format("part2: {0}", insp.get(0) * insp.get(1)));

	}

	static class Monkey {
		List<Long> items = new LinkedList<>();
		Function<Long, Long> worryOp;
		int divisor;
		Map<Boolean, Integer> targets = new HashMap<>();

		long inspections;

		Monkey(List<Long> items, Function<Long, Long> worryOp, int divisor, int left, int right) {
			this.items.addAll(items);
			this.worryOp = worryOp;
			this.divisor = divisor;
			targets.put(true, left);
			targets.put(false, right);
		}
	}

	private static List<Monkey> test1() {
		return Arrays.asList(new Monkey(Arrays.asList(79L, 98L), x -> x * 19L, 23, 2, 3),
				new Monkey(Arrays.asList(54L, 65L, 75L, 74L), x -> x + 6L, 19, 2, 0),
				new Monkey(Arrays.asList(79L, 60L, 97L), x -> x * x, 13, 1, 3),
				new Monkey(Arrays.asList(74L), x -> x + 3L, 17, 0, 1));
	}

	private static List<Monkey> input() {
		return Arrays.asList(new Monkey(Arrays.asList(92L, 73L, 86L, 83L, 65L, 51L, 55L, 93L), x -> x * 5, 11, 3, 4),
				new Monkey(Arrays.asList(99L, 67L, 62L, 61L, 59L, 98L), x -> x * x, 2, 6, 7),
				new Monkey(Arrays.asList(81L, 89L, 56L, 61L, 99L), x -> x * 7, 5, 1, 5),
				new Monkey(Arrays.asList(97L, 74L, 68L), x -> x + 1, 17, 2, 5),
				new Monkey(Arrays.asList(78L, 73L), x -> x + 3, 19, 2, 3),
				new Monkey(Arrays.asList(50L), x -> x + 5, 7, 1, 6),
				new Monkey(Arrays.asList(95L, 88L, 53L, 75L), x -> x + 8, 3, 0, 7),
				new Monkey(Arrays.asList(50L, 77L, 98L, 85L, 94L, 56L, 89L), x -> x + 2, 13, 4, 0));
	}

}
