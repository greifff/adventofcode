package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day19 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day19.test");
		List<String> input = IOUtil.readFile("2023/day19.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		Map<String, Workflow> workflows = new HashMap<>();
		List<RatedPart> parts = new ArrayList<>();
		int i = 0;
		for (; i < input.size(); i++) {
			String in = input.get(i);
			if ("".equals(in)) {
				break;
			}
			Workflow w = new Workflow(in);
			workflows.put(w.name, w);
		}
		i++;
		for (; i < input.size(); i++) {
			String in = input.get(i);
			if ("".equals(in)) {
				break;
			}
			parts.add(new RatedPart(in));
		}

		long result = 0;
		for (RatedPart part : parts) {
			if (part.accept(workflows)) {
				result += part.attributes.values().stream().reduce((a, b) -> a + b).orElse(0);
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + result);
	}

	static class Workflow {
		String name;

		Node op;

		Workflow(String s1) {
			StringTokenizer st = new StringTokenizer(s1, "{}<>:,", true);
			name = st.nextToken();
			st.nextToken();
			op = new Node(st);
		}
	}

	static class Node {

		String field;
		String op;
		int reference;
		Node left;
		Node right;

		Node(StringTokenizer st) {
			field = st.nextToken();
			op = st.nextToken();
			if (",".equals(op) || "}".equals(op)) {
				return;
			}
			reference = Integer.parseInt(st.nextToken());
			st.nextToken();
			left = new Node(st);
			right = new Node(st);
		}
	}

	static class RatedPart {
		Map<String, Integer> attributes = new HashMap<>();

		RatedPart(String s1) {
//			System.out.print("&");
			String[] p = s1.split("[=,{}]");
			for (int i = 1; i < p.length; i += 2) {
//				System.out.print(" " + p[i] + "->" + p[i + 1]);
				attributes.put(p[i], Integer.parseInt(p[i + 1]));
			}
//			System.out.println();
		}

		boolean accept(Map<String, Workflow> workflows) {
			String workflowName = "in";
//			System.out.println("! " + workflowName);
			outer: while (true) {
				Workflow w = workflows.get(workflowName);
				Node n = w.op;
				while (true) {
					if (",".equals(n.op) || "}".equals(n.op)) {
						if ("A".equals(n.field)) {
//							System.out.println("-> A");
							return true;
						}
						if ("R".equals(n.field)) {
//							System.out.println("-> R");
							return false;
						}
						workflowName = n.field;
//						System.out.println("-> " + workflowName);
						continue outer;
					} else {
						int value = attributes.get(n.field);
						boolean evalResult;
//						System.out.println("/ " + value + " " + n.op + " " + n.reference);
						if ("<".equals(n.op)) {
							evalResult = value < n.reference;
						} else {
							evalResult = value > n.reference;
						}
						n = evalResult ? n.left : n.right;
					}
				}
			}
		}

	}

	static class PartRange {
		Map<String, Integer> attributesMin = new HashMap<>();
		Map<String, Integer> attributesMax = new HashMap<>();

		PartRange() {
			for (String a : Arrays.asList("x", "m", "a", "s")) {
				attributesMin.put(a, 1);
				attributesMax.put(a, 4000);
			}
		}

		PartRange(PartRange o) {
			attributesMin.putAll(o.attributesMin);
			attributesMax.putAll(o.attributesMax);
		}

		PartRange[] split(String attribute, String op, int reference) {
			int min = attributesMin.get(attribute);
			int max = attributesMax.get(attribute);

			int upperMin = reference;
			int lowerMax = reference;
			if ("<".equals(op)) {
				lowerMax--;
				if (min > lowerMax) {
					return new PartRange[] { null, this };
				}
				if (max < upperMin) {
					return new PartRange[] { this, null };
				}

				PartRange rightHalf = new PartRange(this);
				attributesMax.put(attribute, lowerMax); // lower half
				rightHalf.attributesMin.put(attribute, upperMin); // upper half
				return new PartRange[] { this, rightHalf };

			} else /* if (">".equals(op)) */ {
				upperMin++;

				if (min > lowerMax) {
					return new PartRange[] { this, null };
				}
				if (max < upperMin) {
					return new PartRange[] { null, this };
				}

				PartRange leftHalf = new PartRange(this);
				attributesMax.put(attribute, lowerMax); // lower half
				leftHalf.attributesMin.put(attribute, upperMin); // upper half
				return new PartRange[] { leftHalf, this };

//			} else {
//				System.err.println("# " + op);
			}

		}

		boolean valid() {
			for (Map.Entry<String, Integer> entry : attributesMin.entrySet()) {
				if (entry.getValue() > attributesMax.get(entry.getKey()))
					return false;
			}
			return true;
		}

		long size() {
			long size = 1;
			for (Map.Entry<String, Integer> entry : attributesMin.entrySet()) {
				int delta = attributesMax.get(entry.getKey()) - entry.getValue();
				if (delta < 0)
					return 0;
				size *= delta + 1;
			}
			return size;
		}

		boolean contains(int x, int m, int a, int s) {
			return attributesMin.get("x") <= x && x <= attributesMax.get("x") && attributesMin.get("m") <= m
					&& m <= attributesMax.get("m") && attributesMin.get("a") <= a && a <= attributesMax.get("a")
					&& attributesMin.get("s") <= s && s <= attributesMax.get("s");
		}

		@Override
		public String toString() {
			String out = "";
			for (String attr : Arrays.asList("x", "m", "a", "s")) {
				out += attr + "=" + attributesMin.get(attr) + ".." + attributesMax.get(attr) + ",";
			}
			return out;
		}
	}

	static class Intermediate {
		PartRange range;
		String workflowName;

		Intermediate(String workflowName, PartRange range) {
			this.workflowName = workflowName;
			this.range = range;
		}
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		Map<String, Workflow> workflows = new HashMap<>();

		for (int i = 0; i < input.size(); i++) {
			String in = input.get(i);
			if ("".equals(in)) {
				break;
			}
			Workflow w = new Workflow(in);
			workflows.put(w.name, w);
		}

		List<Intermediate> toProcess = new LinkedList<>();
		toProcess.add(new Intermediate("in", new PartRange()));

		long result = 0;

		long rejected = 0;

		List<PartRange> valid = new ArrayList<>();

		while (!toProcess.isEmpty()) {
			Intermediate current = toProcess.remove(0);
			PartRange range = current.range;
			if ("A".equals(current.workflowName)) {
				valid.add(range);
				long s = range.size();
				System.out.println("§ " + range);
//				System.out.println("! " + s + " "
//						+ range.attributesMin.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
//								.collect(Collectors.joining(","))
//						+ " " + range.attributesMax.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
//								.collect(Collectors.joining(",")));
				result += s;
				continue;
			}
			if ("R".equals(current.workflowName)) {
				rejected += range.size();
				continue;
			}
			Workflow w = workflows.get(current.workflowName);

			Node n = w.op;
			while (true) {
				if (",".equals(n.op) || "}".equals(n.op)) {
					toProcess.add(new Intermediate(n.field, range));
					break;
				}

				PartRange[] leftRight = range.split(n.field, n.op, n.reference);

				PartRange left = leftRight[0];

				if (left != null) {
					toProcess.add(new Intermediate(n.left.field, left));
				}
				n = n.right;
				range = leftRight[1];
				if (range == null) {
					break;
				}
			}
		}

		System.out.println("rejected: " + rejected);
		System.out.println("total: " + (result + rejected));
		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + result);

		// 91_750_348_007_874 too low
	}
}
