package adventofcode.aoc2023;

import java.util.Arrays;
import java.util.List;

import adventofcode.util.Ansi;

public class Day06 {

	public static void main(String[] args) {

		List<Integer> testTimes = Arrays.asList(7, 15, 30);
		List<Integer> testDistance = Arrays.asList(9, 40, 200);

		List<Integer> times = Arrays.asList(44, 89, 96, 91);
		List<Integer> distance = Arrays.asList(277, 1136, 1890, 1768);

		part1(testTimes, testDistance);
		part1(times, distance);
		part2(71530, 940200);
		part2(44899691, 277113618901768L);
	}

	private static void part1(List<Integer> times, List<Integer> distance) {
		Ansi.foreground(0, 255, 0);

		long product = 1;
		for (int i = 0; i < times.size(); i++) {
			product *= beatRecord(times.get(i), distance.get(i));
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + product);
	}

	private static void part2(long time, long distance) {
		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + beatRecord(time, distance));
	}

	static long beatRecord(long time, long distance) {
		long min = 0;
		for (long i = 1; i < time; i++) {
			long ownDistance = i * (time - i);
			if (ownDistance > distance) {
				min = i;
				break;
			}
		}
		long max = 0;
		for (long i = time - 1; i > 0; i--) {
			long ownDistance = i * (time - i);
			if (ownDistance > distance) {
				max = i;
				break;
			}
		}

		return max - min + 1;
	}

}
