package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day21 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day21.test");
		List<String> input = IOUtil.readFile("2022/day21.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		Map<String, Monkey2> monkeyLookup = input.stream().map(s -> new Monkey2(s))
				.collect(Collectors.toMap(m -> m.name, m -> m));

		long result = monkeyLookup.get("root").getValue(monkeyLookup);

		Ansi.foreground(0x00bfff);
		System.out.println(MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start, result));
	}

	static class Monkey2 {

		String name;
		Long value;
		String left;
		String right;
		String op;

		Monkey2(String in) {
			StringTokenizer st = new StringTokenizer(in, ": ");
			name = st.nextToken();
			if (st.countTokens() == 1) {
				value = Long.parseLong(st.nextToken());
			} else {
				left = st.nextToken();
				op = st.nextToken();
				right = st.nextToken();
			}
		}

		long getValue(Map<String, Monkey2> lookup) {
			if (value != null) {
				return value;
			}
			long a = lookup.get(left).getValue(lookup);
			long b = lookup.get(right).getValue(lookup);

			switch (op) {
			case "+":
				value = a + b;
				break;
			case "-":
				value = a - b;
				break;
			case "*":
				value = a * b;
				break;
			case "/":
				value = a / b;
				break;
			}
			return value;
		}

	}

	private static void part2(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		Map<String, Monkey2> monkeyLookup = input.stream().map(s -> new Monkey2(s))
				.collect(Collectors.toMap(m -> m.name, m -> m));

		Monkey2 root = monkeyLookup.get("root");

		Monkey2 rootLeft = monkeyLookup.get(root.left);
		Monkey2 rootRight = monkeyLookup.get(root.right);

		List<Monkey2> toVisit = new LinkedList<>(monkeyLookup.values());
		Set<String> unprocessable = new HashSet<>();
		unprocessable.add("humn");
		unprocessable.add("root");
		while (!toVisit.isEmpty()) {
			Monkey2 m = toVisit.remove(0);
			if (m.value != null)
				continue;
			if (unprocessable.contains(m.name))
				continue;
			if (unprocessable.contains(m.left) || unprocessable.contains(m.right)) {
				unprocessable.add(m.name);
				continue;
			}
			Long a = monkeyLookup.get(m.left).value;
			Long b = monkeyLookup.get(m.right).value;

			if (a == null || b == null) {
				toVisit.add(m);
				continue;
			}

			switch (m.op) {
			case "+":
				m.value = a + b;
				break;
			case "-":
				m.value = a - b;
				break;
			case "*":
				m.value = a * b;
				break;
			case "/":
				m.value = a / b;
				break;
			}
		}

		System.out.println("left: " + rootLeft.value + " right: " + rootRight.value);

		long targetValue = rootRight.value;

		Monkey2 c = rootLeft;

		monkeyLookup.get("humn").value = null;

		while (!"humn".equals(c.name)) {

//			System.out.println("? " + c.name);

			Monkey2 a = monkeyLookup.get(c.left);
			Monkey2 b = monkeyLookup.get(c.right);

//			System.out.println("§ " + a.name + " " + b.name);

			// c=a/b -> a=b*c , b=a/c
			// c=a-b -> a=b+c , b=a-c

			if (a.value != null) {
				switch (c.op) {
				case "+":
					targetValue -= a.value;
					break;
				case "-":
					targetValue = a.value - targetValue;
					break;
				case "*":
					targetValue /= a.value;
					break;
				case "/":
					targetValue = a.value / targetValue;
					break;
				}
				c = b;
			} else {
				switch (c.op) {
				case "+":
					targetValue -= b.value;
					break;
				case "-":
					targetValue += b.value;
					break;
				case "*":
					targetValue /= b.value;
					break;
				case "/":
					targetValue *= b.value;
					break;
				}
				c = a;
			}

		}

		Ansi.foreground(0x00bfff);
		System.out.println(
				MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start, targetValue));
	}

}
