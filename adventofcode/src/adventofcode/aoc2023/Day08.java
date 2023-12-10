package adventofcode.aoc2023;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day08 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day08.test");
		List<String> test2 = IOUtil.readFile("2023/day08.test2");
//		List<String> test3 = IOUtil.readFile("2023/day08.test3");
		List<String> input = IOUtil.readFile("2023/day08.data");

		part1(test);
		part1(test2);
		part1(input);
//		part2b(test3);
//		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		String moveOrders = input.get(0);

		Map<String, Node> nodes = input.stream().skip(2).map(s -> new Node(s))
				.collect(Collectors.toMap(n -> n.name, n -> n));

		int index = 0;
		Node currentNode = nodes.get("AAA");
		int steps = 0;

		while (!"ZZZ".equals(currentNode.name)) {
			char move = moveOrders.charAt(index);
			currentNode = nodes.get(move == 'L' ? currentNode.left : currentNode.right);
			steps++;
			index = (index + 1) % moveOrders.length();
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + steps);
	}

	static class Node {
		String name, left, right;

		Node(String s) {
			name = s.substring(0, 3);
			left = s.substring(7, 10);
			right = s.substring(12, 15);
		}
	}

}
