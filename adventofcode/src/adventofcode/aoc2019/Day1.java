package adventofcode.aoc2019;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day1 {

	public static void main(String[] args) {
		fuel4fuel(mass2fuel(12));
		fuel4fuel(mass2fuel(14));
		fuel4fuel(mass2fuel(1969));
		fuel4fuel(mass2fuel(100756));

		List<String> data1 = IOUtil.readFile("2019/day1.data");
		// int sumFuel = data1.parallelStream().map(s -> Integer.parseInt(s)).map(m -> mass2fuel(m)).reduce((a, b) -> a + b).orElse(0);
		// System.out.println("part1: " + sumFuel);

		int totalFuel = data1.parallelStream().map(s -> Integer.parseInt(s)).map(m -> mass2fuel(m)).map(m -> fuel4fuel(m)).reduce((a, b) -> a + b)
				.orElse(0);
		System.out.println("part2: " + totalFuel);
	}

	static int mass2fuel(int mass) {
		int fuel = mass / 3 - 2;
		// System.out.println("# " + mass + " -> " + fuel);
		return fuel;
	}

	static int fuel4fuel(int fuel) {
		int totalFuel = 0;
		int fuel1 = fuel;
		while (fuel1 > 0) {
			totalFuel += fuel1;
			fuel1 = mass2fuel(fuel1);
		}
		System.out.println("## " + totalFuel);
		return totalFuel;
	}
}
