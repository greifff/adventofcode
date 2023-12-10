package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day02 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2023/day02.test");

		List<String> input = IOUtil.readFile("2023/day02.data");
		part1(test1);
		part1(input);
		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Game> games = input.stream().map(s -> parse(s)).toList();

		long sum = 0;

		outer: for (Game game1 : games) {
			for (Turn t : game1.turns) {
				if (t.red <= 12 && t.green <= 13 && t.blue <= 14) {
//
				} else {
					continue outer;
				}
			}
//			System.out.println("# " + game1.id);
			sum += game1.id;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);
		List<Game> games = input.stream().map(s -> parse(s)).toList();

		long power = 0;

		for (Game game1 : games) {
			long p = game1.turns.stream().map(t -> t.red).reduce(Math::max).orElse(0L)
					* game1.turns.stream().map(t -> t.green).reduce(Math::max).orElse(0L)
					* game1.turns.stream().map(t -> t.blue).reduce(Math::max).orElse(0L);
			power += p;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + power);
	}

	static Game parse(String in) {
		Game g = new Game();
		g.id = Long.parseLong(in.substring(5, in.indexOf(":")));
		String[] turns = in.substring(in.indexOf(":") + 1).split(";");

		for (String t1 : turns) {
			Turn t = new Turn();
			g.turns.add(t);

			String[] t2 = t1.split(",");

			for (String c : t2) {
				String[] c1 = c.trim().split(" ");
				long number = Long.parseLong(c1[0]);
				String color = c1[1];
				switch (color) {
				case "red":
					t.red = number;
					break;
				case "blue":
					t.blue = number;
					break;
				case "green":
					t.green = number;
					break;
				}
			}
		}
		return g;
	}

	static class Game {
		long id;
		List<Turn> turns = new ArrayList<>();
	}

	static class Turn {
		long red;
		long green;
		long blue;
	}
}
