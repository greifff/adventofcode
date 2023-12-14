package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;
import adventofcode.util.StringArrayRotation;

public class Day14 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day14.test");
		List<String> input = IOUtil.readFile("2023/day14.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		int[][] dishNorthwise = parse(input);

		long sum = 0;
		for (int i = 0; i < dishNorthwise.length; i++) {
			int[] row = dishNorthwise[i];
			int highestAvailable = row.length;

			for (int j = row.length - 1; j >= 0; j--) {
				if (row[j] == 1) {
					highestAvailable = j;
				} else if (row[j] == 2) {
					sum += highestAvailable;
					highestAvailable--;
				}
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
	}

	static int[][] parse(List<String> input) {
		int[][] dishNorthwise = new int[input.get(0).length()][input.size()];

		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				dishNorthwise[j][input.size() - 1 - i] = c == '.' ? 0 : c == '#' ? 1 : 2;
			}
		}
		return dishNorthwise;
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		Map<String, Integer> cycleMap = new HashMap<>();

		int i = 0;
		int cycleStart = 0;
		int cycleRepeat = 0;
		List<String> cycled = input;
		while (true) {
			cycled = moveEast(moveSouth(moveWest(moveNorth(cycled))));
			i++;
			String flat = cycled.stream().collect(Collectors.joining());

			if (cycleMap.containsKey(flat)) {
				cycleRepeat = i;
				cycleStart = cycleMap.get(flat);
				break;
			} else {
				cycleMap.put(flat, i);
			}
		}

		int modCycle = (1_000_000_000 - cycleStart) % (cycleRepeat - cycleStart) + cycleStart;

		String result = cycleMap.entrySet().stream().filter(e -> e.getValue() == modCycle).map(e -> e.getKey())
				.findAny().orElse("");

		long sum = 0;
		for (int j = 0; j < result.length(); j++) {
			char c = result.charAt(j);
			if (c == 'O') {
				int score = input.size() - j / input.size();
				sum += score;
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + sum);
	}

	static List<String> moveNorth(List<String> in) {
		// rotate
		List<String> rotated = StringArrayRotation.rotate90clockwise(in);

		List<String> moved = moveEast(rotated);

		// rotate back

		return StringArrayRotation.rotate90counterClockwise(moved);
	}

	static List<String> moveWest(List<String> in) {
		// rotate
		List<String> rotated = StringArrayRotation.rotate180(in);

		List<String> moved = moveEast(rotated);

		// rotate back

		return StringArrayRotation.rotate180(moved);
	}

	static List<String> moveSouth(List<String> in) {
		// rotate
		List<String> rotated = StringArrayRotation.rotate90counterClockwise(in);

		List<String> moved = moveEast(rotated);

		// rotate back

		return StringArrayRotation.rotate90clockwise(moved);
	}

	static List<String> moveEast(List<String> in) {
		List<String> out = new ArrayList<>();
		for (String i : in) {
			int m = 0;
			int n = i.indexOf("#");
			StringBuilder o = new StringBuilder();
			while (n != -1) {

				int dots = 0;
				int rolling = 0;
				for (int p = m; p < n; p++) {
					if (i.charAt(p) == '.') {
						dots++;
					} else {
						rolling++;
					}
				}
				o.append(".".repeat(dots)).append("O".repeat(rolling)).append('#');
				m = n + 1;
				n = i.indexOf('#', m);
			}
			int dots = 0;
			int rolling = 0;
			for (int p = m; p < i.length(); p++) {
				if (i.charAt(p) == '.') {
					dots++;
				} else {
					rolling++;
				}
			}
			o.append(".".repeat(dots)).append("O".repeat(rolling));
			out.add(o.toString());
		}
		return out;
	}

}
