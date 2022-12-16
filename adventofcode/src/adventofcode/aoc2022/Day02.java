package adventofcode.aoc2022;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day02 {

	public static void main(String[] args) {
		List<String> input = IOUtil.readFile("2022/day02.data");
		part1(Arrays.asList("A Y", "B X", "C Z"));
		part1(input);

		part2(Arrays.asList("A Y", "B X", "C Z"));
		part2(input);
	}

	private static void part1(List<String> input) {

		Map<Character, Integer> symbols = new HashMap<>();
		symbols.put('A', 0);
		symbols.put('B', 1);
		symbols.put('C', 2);
		symbols.put('X', 0);
		symbols.put('Y', 1);
		symbols.put('Z', 2);

		int totalScore = 0;
		for (String in : input) {
			totalScore += points(symbols, in);
		}
		System.out.println("part1: " + totalScore);
	}

	private static int points(Map<Character, Integer> symbols, String turn) {
		int elf = symbols.get(turn.charAt(0));
		int me = symbols.get(turn.charAt(2));

		int score = 0;

		if ((elf == 0 && me == 1) || (elf == 1 && me == 2) || (elf == 2 && me == 0)) {
			score = 6; // win
		} else if (elf == me) {
			score = 3; // draw
		}

		score++;
		score += me;
		return score;
	}

	private static void part2(List<String> input) {

		Map<Character, Integer> symbols = new HashMap<>();
		symbols.put('A', 0);
		symbols.put('B', 1);
		symbols.put('C', 2);

		int totalScore = 0;
		for (String in : input) {
			totalScore += points2(symbols, in);
		}
		System.out.println("part2: " + totalScore);
	}

	private static int points2(Map<Character, Integer> symbols, String turn) {
		int elf = symbols.get(turn.charAt(0));
		char move = turn.charAt(2);

		int score = 0;

		int me = 0;

		switch (move) {
		case 'X':
			me = (elf + 2) % 3;
			break;
		case 'Y':
			score += 3;
			me = elf;
			break;
		case 'Z':
			score += 6;
			me = (elf + 1) % 3;
			break;
		}

		score++;
		score += me;
		return score;
	}
}
