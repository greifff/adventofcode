package adventofcode.aoc2023;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day08b {

	public static void main(String[] args) {
		List<String> test3 = IOUtil.readFile("2023/day08.test3");
		List<String> input = IOUtil.readFile("2023/day08.data");

		part2(test3);
		part2(input);
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		String moveOrders = input.get(0);

		Map<String, Node> nodes = input.stream().skip(2).map(s -> new Node(s))
				.collect(Collectors.toMap(n -> n.name, n -> n));

		List<Node> startNodes = nodes.values().stream().filter(n -> n.name.charAt(2) == 'A').toList();

		List<Loop> loops = startNodes.stream().map(n -> findLoopForNode(n, moveOrders, nodes)).toList();

		BigInteger lcm = loops.stream().map(l -> BigInteger.valueOf(l.zIndices.get(0))).reduce((a, b) -> lcm(a, b))
				.orElse(BigInteger.ZERO);

		Ansi.foreground(0, 255, 255);
		System.out.println("part2b: " + lcm);
	}

	static Loop findLoopForNode(Node node, String moveOrders, Map<String, Node> nodes) {
		List<String> visited = new ArrayList<>();

		Node currentNode = node;
		String x = currentNode.name + "-0";
		int index = 0;
		while (!visited.contains(x)) {
			visited.add(x);

			char move = moveOrders.charAt(index);
			currentNode = nodes.get(move == 'L' ? currentNode.left : currentNode.right);
			x = currentNode.name + "-" + index;

			index = (index + 1) % moveOrders.length();
		}

		// 0..y-1: initialization; y..end loop

		Loop loop = new Loop();
		loop.loopStart = visited.indexOf(x);
		loop.size = visited.size() - loop.loopStart;
		for (int i = 0; i < visited.size(); i++) {
			if (visited.get(i).charAt(2) == 'Z') {
				loop.zIndices.add(i);
			}
		}

		System.out.println("# " + node.name + " " + loop.loopStart + " " + visited.size() + " "
				+ loop.zIndices.stream().map(i -> "" + i).collect(Collectors.joining(",")));

		return loop;
	}

	// three-line snippet from Baeldung
	public static BigInteger lcm(BigInteger number1, BigInteger number2) {
		BigInteger gcd = number1.gcd(number2);
		BigInteger absProduct = number1.multiply(number2).abs();
		return absProduct.divide(gcd);
	}

	static class Loop {
		int loopStart;
		int size;
		List<Integer> zIndices = new ArrayList<>();
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
