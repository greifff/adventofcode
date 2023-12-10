package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day03 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day03.test");
		List<String> input = IOUtil.readFile("2023/day03.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Long> partnumbers = new ArrayList<>();

		// find symbol character
		// add adjacent part numbers
		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if (c != '.' && (c < '0' || c > '9')) {
					// symbol found
					findPartnumbersAround(input, i, j, partnumbers);
				}
			}
		}

		System.out.println(partnumbers.stream().map(l -> "" + l).sorted().collect(Collectors.joining(",")));

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + partnumbers.stream().collect(Collectors.summarizingLong(l -> l)).getSum());
	}

	private static void findPartnumbersAround(List<String> input, int i, int j, List<Long> partnumbers) {
		if (i > 0) {
			String l = input.get(i - 1);
			searchTopOrBottom(l, j, partnumbers);
		}
		if (i < input.size() - 1) {
			String l = input.get(i + 1);
			searchTopOrBottom(l, j, partnumbers);
		}
		searchLeftAndRight(input.get(i), j, partnumbers);

	}

	private static void searchTopOrBottom(String l, int j, List<Long> partnumbers) {
		char c = l.charAt(j);
		if (c >= '0' && c <= '9') {
			// search left
			int k = j - 1;
			while (k >= 0) {
				char d = l.charAt(k);
				if (d >= '0' && d <= '9') {
					k--;
				} else {
					k++;
					break;
				}
			}
			if (k < 0)
				k = 0;
			// search right
			int m = j + 1;
			while (m < l.length()) {
				char d = l.charAt(m);
				if (d >= '0' && d <= '9') {
					m++;
				} else {
					break;
				}
			}
//			System.out.println("& " + l.substring(k, m));
			partnumbers.add(Long.parseLong(l.substring(k, m)));
		} else {
			searchLeftAndRight(l, j, partnumbers);
		}
	}

	private static void searchLeftAndRight(String l, int j, List<Long> partnumbers) {

//search left
		int k = j - 1;
		while (k >= 0) {
			char d = l.charAt(k);
			if (d >= '0' && d <= '9') {
				k--;
			} else {
				k++;
				break;
			}
		}
		if (k < 0)
			k = 0;
		if (k < j)
			partnumbers.add(Long.parseLong(l.substring(k, j)));
		// search right
		int m = j + 1;
		while (m < l.length()) {
			char d = l.charAt(m);
			if (d >= '0' && d <= '9') {
				m++;
			} else {
				break;
			}
		}

		if (m > j + 1) {
//			System.out.println("# " + l.substring(j + 1, m));
			partnumbers.add(Long.parseLong(l.substring(j + 1, m)));
		}

	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long gearRatios = 0;
		// find symbol character
		// add adjacent part numbers
		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if (c == '*') {
					// symbol found
					List<Long> partnumbers = new ArrayList<>();
					findPartnumbersAround(input, i, j, partnumbers);
					if (partnumbers.size() == 2) {
						long gearRatio = partnumbers.get(0) * partnumbers.get(1);
						gearRatios += gearRatio;
					}
				}
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + gearRatios);
	}

}
