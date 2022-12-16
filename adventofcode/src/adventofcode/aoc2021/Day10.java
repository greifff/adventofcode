package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import adventofcode.util.IOUtil;

public class Day10 {
	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day10.test");
		List<String> data = IOUtil.readFile("2021/day10.data");

		part1(test);
		part1(data);

		part2(test);
		part2(data);
	}

	private static void part2(List<String> data) {
		List<Long> scores = new ArrayList<>();
		for (String l : data) {
			if (corrupted(l) > 0) {
				continue;
			}
			scores.add(autocomplete(l));
		}
		Collections.sort(scores);
		int middle = scores.size() / 2;
		System.out.println("# " + scores.stream().map(s -> "" + s).reduce((a, b) -> a + ", " + b));
		System.out.println("part2: " + scores.get(middle));
	}

	private static void part1(List<String> data) {
		int score = 0;

		for (String l : data) {
			score += corrupted(l);
		}

		System.out.println("part1: " + score);
	}

	private static long autocomplete(String l) {
		Stack<Character> lc = new Stack<>();

		for (int i = 0; i < l.length(); i++) {
			char c = l.charAt(i);
			if (c == '(' || c == '{' || c == '[' || c == '<') {
				lc.push(c);
			} else {
				char d = lc.pop();
			}
		}
		long score = 0;
		while (!lc.isEmpty()) {
			char c = lc.pop();
			score *= 5;
			switch (c) {
			case '(':
				score++;
				break;
			case '[':
				score += 2;
				break;
			case '{':
				score += 3;
				break;
			case '<':
				score += 4;
				break;
			}
		}
		return score;
	}

	private static int corrupted(String l) {
		Stack<Character> lc = new Stack<>();

		for (int i = 0; i < l.length(); i++) {
			char c = l.charAt(i);
			if (c == '(' || c == '{' || c == '[' || c == '<') {
				lc.push(c);
			} else {
				if (lc.isEmpty()) {
					return 0;
				}
				char d = lc.pop();
				if ((d == '(' && c == ')') || (d == '[' && c == ']') || (d == '{' && c == '}') || (d == '<' && c == '>')) {
					// legit
				} else {
					if (c == ')') {
						return 3;
					} else if (c == ']') {
						return 57;
					} else if (c == '}') {
						return 1197;
					} else {
						return 25137;
					}
				}
			}
		}
		return 0;
	}
}
