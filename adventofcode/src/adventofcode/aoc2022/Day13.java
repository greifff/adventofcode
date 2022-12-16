package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day13 {
	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2022/day13.test");
		List<String> input = IOUtil.readFile("2022/day13.data");
		List<String> input2 = IOUtil.readFile("2022/day13.data2");

//		part1(test);
//		part1(input);
		part1(input2);

//		part2(test);
//		part2(input);
//		part2(input2);
	}

	private static void part1(List<String> input) {

		int pair = 1;
		int result = 0;

		for (int i = 0; i < input.size(); i += 3) {
			System.out.println("& " + pair);
			TreeNode a = parse(input.get(i));
			TreeNode b = parse(input.get(i + 1));
			int c = a.compareTo(b);
			if (c <= 0) {
				System.out.println("# " + pair + " " + c);
//				System.out.println("#                " + input.get(i));
//				System.out.println("#                " + input.get(i + 1));
				result += pair;
			}
			pair++;
		}

		System.out.println("part1: " + result);
	}

	private static void part2(List<String> input) {

		List<TreeNode> nodes = new ArrayList<>(input.stream().filter(s -> !"".equals(s)).map(s -> parse(s)).toList());

		TreeNode node2 = parse("[[2]]");
		nodes.add(node2);
		TreeNode node6 = parse("[[6]]");
		nodes.add(node6);

		Collections.sort(nodes);
		System.out.println(MessageFormat.format("part2: {0}", (nodes.indexOf(node2) + 1) * (nodes.indexOf(node6) + 1)));
	}

	private static TreeNode parse(String line) {
		StringTokenizer st = new StringTokenizer(line, "[],", true);
		st.nextToken();
		return new TreeNode(st);
	}

	static class TreeNode implements Comparable<TreeNode> {

		int value = -1;
		List<TreeNode> subnodes = new ArrayList<>();

		TreeNode(StringTokenizer st) {
			while (true) {
				String token = st.nextToken();
				switch (token) {
				case "[":
					subnodes.add(new TreeNode(st));
					break;
				case "]":
					return;
				case ",":
					break;
				default:
					subnodes.add(new TreeNode(token));
					break;
				}
			}
		}

		TreeNode(String token) {
			value = Integer.valueOf(token);
		}

		@Override
		public int compareTo(TreeNode o) {
			if (value == -1) {
				if (o.value == -1) {
					int i = 0;
					while (i < Math.min(subnodes.size(), o.subnodes.size())) {
						int c = subnodes.get(i).compareTo(o.subnodes.get(i));
						if (c != 0) {
							return c;
						}
						i++;
					}
					return subnodes.size() - o.subnodes.size();
				}
				return -o.compareTo(this);
			}
			if (o.value == -1) {
				TreeNode s = o;
				boolean moreElements = false;
				while (true) {
					if (s.value != -1) {
						int c = value - s.value;
						if (c == 0 && moreElements) {
							System.out.println("!!!!!!");
							return -1;
						}
						return c;
					}
					if (s.subnodes.isEmpty()) {
						return 1;
					}
					moreElements |= s.subnodes.size() > 1;
					s = s.subnodes.get(0);
				}
			}
			return value - o.value;
		}

		@Override
		public String toString() {
			if (value != -1) {
				return " " + value;
			}
			return "[" + subnodes.stream().map(n -> "" + n).collect(Collectors.joining()) + "]";
		}
	}
}
