package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day05 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2022/day05.test");
		List<String> input = IOUtil.readFile("2022/day05.data");

		part1(test, false);
		part1(input, false);
		part1(test, true);
		part1(input, true);

	}

	private static void part1(List<String> input, boolean crateMover9001) {
		int seperator = 0;
		while (!"".equals(input.get(seperator))) {
			seperator++;
		}

		CrateMover crateMover = new CrateMover();

		crateMover.parse(new ArrayList<>(input.subList(0, seperator)));

		System.out.println("# " + crateMover.topElements());

		List<String> moveOrders = new ArrayList<>(input.subList(seperator + 1, input.size()));
		for (String m : moveOrders) {
			crateMover.move(m, crateMover9001);
		}
		System.out.println(MessageFormat.format("part{0}: {1}", crateMover9001 ? "2" : "1", crateMover.topElements()));
	}

	static class CrateMover {
		List<Stack<Character>> crateStacks = new ArrayList<>();

		void parse(List<String> stacklines) {
			Collections.reverse(stacklines);
			StringTokenizer st = new StringTokenizer(stacklines.remove(0), " ", false);

			for (int i = 0; i < st.countTokens(); i++) {
				crateStacks.add(new Stack<>());
			}

			for (String line : stacklines) {
				for (int i = 0; i < crateStacks.size(); i++) {
					int p = i * 4 + 1;
					if (line.length() > p && line.charAt(p) != ' ')
						crateStacks.get(i).push(line.charAt(p));
				}
			}
		}

		void move(String order, boolean crateMover9001) {
			String[] parts = order.split(" ");
			int count = Integer.parseInt(parts[1]);
			int from = Integer.parseInt(parts[3]) - 1;
			int to = Integer.parseInt(parts[5]) - 1;

			List<Character> toMove = new ArrayList<>();

			for (int i = 0; i < count; i++) {
				toMove.add(crateStacks.get(from).pop());
			}

			if (crateMover9001) {
				Collections.reverse(toMove);
			}

			crateStacks.get(to).addAll(toMove);
		}

		String topElements() {
			return crateStacks.stream().map(s -> s.isEmpty() ? "_" : "" + s.peek()).collect(Collectors.joining());
		}
	}
}
