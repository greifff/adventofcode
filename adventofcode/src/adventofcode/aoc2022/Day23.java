package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day23 {

	private static final long FACTOR = 100_000L;
	private static final long OFFSET = 10_000L;

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day23.test");
		List<String> input = IOUtil.readFile("2022/day23.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		Set<Long> elves = parse(input);
//		output(elves);

		for (int i = 0; i < 10; i++) {
			// consider moving
			Map<Long, Long> moving = new HashMap<>();
			Map<Long, Integer> collision = new HashMap<>();

			for (long elf : elves) {
				long target = proposeMove(elf, elves, i);
				if (target != -1) {
					moving.put(elf, target);
					Integer c = collision.get(target);
					collision.put(target, c == null ? 0 : c + 1);
				}
			}

			// move

			for (Map.Entry<Long, Long> move : moving.entrySet()) {
				if (collision.get(move.getValue()) == 0) {
					elves.remove(move.getKey());
					elves.add(move.getValue());
				}
			}
//			Ansi.foreground(0xff0000);
//			System.out.println("Round " + (i + 1));
//			output(elves);
		}

		Ansi.foreground(0x00bfff);
		System.out.println(MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start,
				calculateArea(elves)));
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		Set<Long> elves = parse(input);
//		output(elves);

		int i = 0;

		while (true) {
			// consider moving
			Map<Long, Long> moving = new HashMap<>();
			Map<Long, Integer> collision = new HashMap<>();

			for (long elf : elves) {
				long target = proposeMove(elf, elves, i);
				if (target != -1) {
					moving.put(elf, target);
					Integer c = collision.get(target);
					collision.put(target, c == null ? 0 : c + 1);
				}
			}

			if (moving.isEmpty())
				break;

			// move

			for (Map.Entry<Long, Long> move : moving.entrySet()) {
				if (collision.get(move.getValue()) == 0) {
					elves.remove(move.getKey());
					elves.add(move.getValue());
				}
			}
//			Ansi.foreground(0xff0000);
//			System.out.println("Round " + (i + 1));
//			output(elves);
			i++;
		}

		Ansi.foreground(0x00bfff);
		System.out.println(MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start, i + 1));
	}

	private static long proposeMove(long elf, Set<Long> elves, int round) {

		boolean north = elves.contains(elf - 1);
		boolean south = elves.contains(elf + 1);
		boolean west = elves.contains(elf - FACTOR);
		boolean east = elves.contains(elf + FACTOR);
		boolean northwest = elves.contains(elf - 1 - FACTOR);
		boolean northeast = elves.contains(elf - 1 + FACTOR);
		boolean southwest = elves.contains(elf + 1 - FACTOR);
		boolean southeast = elves.contains(elf + 1 + FACTOR);

		int neighbors = (east ? 1 : 0) + (west ? 1 : 0) + (south ? 1 : 0) + (north ? 1 : 0) + (southeast ? 1 : 0)
				+ (northeast ? 1 : 0) + (southwest ? 1 : 0) + (northwest ? 1 : 0);

		if (neighbors == 0)
			return -1;

		Supplier<Long> checkNorth = () -> (!north && !northeast && !northwest) ? elf - 1L : -1L;
		Supplier<Long> checkSouth = () -> (!south && !southeast && !southwest) ? elf + 1 : -1L;
		Supplier<Long> checkWest = () -> (!west && !southwest && !northwest) ? elf - FACTOR : -1L;
		Supplier<Long> checkEast = () -> (!east && !southeast && !northeast) ? elf + FACTOR : -1L;

		List<Supplier<Long>> checks = Arrays.asList(checkNorth, checkSouth, checkWest, checkEast);

		int i = 0;
		while (i < 4) {
			long t = checks.get((i + round) % 4).get();
			if (t != -1)
				return t;
			i++;
		}

		return -1;
	}

	private static void output(Set<Long> elves) {
		long minx = Long.MAX_VALUE;
		long maxx = Long.MIN_VALUE;
		long miny = Long.MAX_VALUE;
		long maxy = Long.MIN_VALUE;

		for (long elf : elves) {
			long x = elf / FACTOR;
			long y = elf % FACTOR;
			minx = Math.min(minx, x);
			maxx = Math.max(maxx, x);
			miny = Math.min(miny, y);
			maxy = Math.max(maxy, y);
		}

		for (long y = miny; y <= maxy; y++) {
			for (long x = minx; x <= maxx; x++) {
				if (elves.contains(x * FACTOR + y)) {
					Ansi.foreground(0x00ff00);
					System.out.print("#");
				} else {
					Ansi.foreground(0x808080);
					System.out.print(".");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private static long calculateArea(Set<Long> elves) {
		long minx = Long.MAX_VALUE;
		long maxx = Long.MIN_VALUE;
		long miny = Long.MAX_VALUE;
		long maxy = Long.MIN_VALUE;

		for (long elf : elves) {
			long x = elf / FACTOR;
			long y = elf % FACTOR;
			minx = Math.min(minx, x);
			maxx = Math.max(maxx, x);
			miny = Math.min(miny, y);
			maxy = Math.max(maxy, y);
		}

		return (maxx - minx + 1) * (maxy - miny + 1) - elves.size();
	}

	private static Set<Long> parse(List<String> input) {
		Set<Long> data = new HashSet<>();
		for (int y = 0; y < input.size(); y++) {
			String row = input.get(y);
			for (int x = 0; x < row.length(); x++) {
				if (row.charAt(x) == '#') {
					data.add((x + OFFSET) * FACTOR + y + OFFSET);
				}
			}
		}
		return data;
	}

}
