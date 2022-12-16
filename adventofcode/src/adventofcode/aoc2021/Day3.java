package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day3 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day3.test");
		List<String> data = IOUtil.readFile("2021/day3.data");
		part1(test);
		part1(data);
		part2(test);
		part2(data);
	}

	private static void part1(List<String> data) {
		int gammaRate = 0;
		int epsilonRate = 0;
		for (int i = 0; i < data.get(1).length(); i++) {
			final int index = i;
			int ones = (int) data.stream().filter(s -> s.charAt(index) == '1').count();
			int zeros = (int) data.stream().filter(s -> s.charAt(index) == '0').count();

			gammaRate *= 2;
			epsilonRate *= 2;
			if (ones > zeros) {
				gammaRate++;
			} else {
				epsilonRate++;
			}
		}
		System.out.println("part1: " + gammaRate + " " + epsilonRate + " -> " + (gammaRate * epsilonRate));
	}

	private static void part2(List<String> data) {

		int oxygen = filter(data, '1', '0');
		int co2 = filter(data, '0', '1');

		System.out.println("part2: " + oxygen + " " + co2 + " -> " + (oxygen * co2));
	}

	private static int filter(List<String> data, char a, char b) {
		List<String> idata = new ArrayList<>(data);

		for (int i = 0; i < data.get(1).length(); i++) {
			final int index = i;
			int ones = (int) idata.stream().filter(s -> s.charAt(index) == '1').count();
			int zeros = (int) idata.stream().filter(s -> s.charAt(index) == '0').count();

			if (ones >= zeros) {
				idata = idata.stream().filter(s -> s.charAt(index) == a).collect(Collectors.toList());
			} else {
				idata = idata.stream().filter(s -> s.charAt(index) == b).collect(Collectors.toList());
			}

			if (idata.size() == 1) {
				return Integer.parseInt(idata.get(0), 2);
			}
		}
		return -1;
	}
}
