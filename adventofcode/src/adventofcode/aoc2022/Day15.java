package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day15 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day15.test");
		List<String> input = IOUtil.readFile("2022/day15.data");

		part1(test1, 10);
		part1(input, 2_000_000);

		// takes way too long
//		part2(test1, 20);
//		part2(input, 4_000_000);

		// acceptable speed (~50s)
//		part2b(test1, 20);
//		part2b(input, 4_000_000);

		// faster (~15s)
		part2c(test1, 20);
		part2c(input, 4_000_000);
	}

	private static void part1(List<String> input, int row) {

		List<int[]> sensors = parse(input);

		Set<Integer> coveredInTargetRow = new HashSet<>();
		List<Integer> beaconsOnTargetRow = sensors.stream().filter(s -> s[3] == row).map(s -> s[2]).toList();

		for (int[] sensor : sensors) {
			int manhattanDistance = Math.abs(sensor[0] - sensor[2]) + Math.abs(sensor[1] - sensor[3]);
//			System.out.println("? " + manhattanDistance);
			int distanceToTargetRow = Math.abs(sensor[1] - row);
			if (distanceToTargetRow > manhattanDistance) {
				continue;
			}
			int x = sensor[0];
			coveredInTargetRow.add(x);
			int dx = 1;
			while (distanceToTargetRow + dx <= manhattanDistance) {
				coveredInTargetRow.add(x - dx);
				coveredInTargetRow.add(x + dx);
				dx++;
			}
		}

		coveredInTargetRow.removeAll(beaconsOnTargetRow);

		System.out.println(MessageFormat.format("part1: {0}", coveredInTargetRow.size()));
	}

	private static List<int[]> parse(List<String> input) {
		List<int[]> sensors = new ArrayList<>();
		for (String in : input) {
			String[] p = in.split("[=,:]");
			int x1 = Integer.parseInt(p[1]);
			int y1 = Integer.parseInt(p[3]);
			int x2 = Integer.parseInt(p[5]);
			int y2 = Integer.parseInt(p[7]);

			sensors.add(new int[] { x1, y1, x2, y2 });
		}
		return sensors;
	}

	private static void part2b(List<String> input, int maxRange) {

		long start = System.currentTimeMillis();
		List<int[]> sensors = parse(input);

		List<Set<Long>> uncovered = new ArrayList<>();

		for (int[] sensor : sensors) {
			Set<Long> uncovered1 = new HashSet<>();
			uncovered.add(uncovered1);
			int x = sensor[0];
			int y = sensor[1];
			int manhattanDistance = Math.abs(sensor[0] - sensor[2]) + Math.abs(sensor[1] - sensor[3]);

			int d = manhattanDistance + 1;

			int[] p = new int[] { x - d, y, x + d, y, x, y - d, x, y + d };

			for (int i = 0; i < 4; i++) {

				int sx = p[i * 2];
				int sy = p[i * 2 + 1];

				int tx = p[(i * 2 + 2) % 8];
				int ty = p[(i * 2 + 3) % 8];

				while (sx != tx && sy != ty) {

					if (insideRange(sx, maxRange) && insideRange(sy, maxRange)) {
						uncovered1.add(4_000_000L * sx + sy);
					}

					sx += (int) Math.signum(tx - sx);
					sy += (int) Math.signum(ty - sy);
				}
			}
//			System.out.println("# " + uncovered1.size());
		}

		System.out.println("* romboids drawn " + (System.currentTimeMillis() - start));

		Set<Long> probableIntersections = new HashSet<>();

		for (int i = 0; i < sensors.size() - 1; i++) {
			Set<Long> uncovered1 = uncovered.get(i);
			for (int j = i + 1; j < sensors.size(); j++) {
				Set<Long> uncovered2 = new HashSet<>(uncovered.get(j));
				uncovered2.retainAll(uncovered1);
				probableIntersections.addAll(uncovered2);
			}
		}

		System.out.println("* intersections processed " + (System.currentTimeMillis() - start));
		System.out.println("# " + probableIntersections.size());

		for (int[] sensor : sensors) {
			int x = sensor[0];
			int y = sensor[1];
			int manhattanDistance = manhattanDistance(x, y, sensor[2], sensor[3]);

			List<Long> remove = new ArrayList<>();

			for (long c : probableIntersections) {
				int cx = (int) (c / 4_000_000);
				int cy = (int) (c % 4_000_000);

				int md2 = manhattanDistance(x, y, cx, cy);
				if (md2 <= manhattanDistance) {
					remove.add(c);
				}
			}

			probableIntersections.removeAll(remove);
		}

		System.out.println("# " + probableIntersections.size());

		System.out.println(MessageFormat.format("part2: {0}", probableIntersections.iterator().next()));
		System.out.println("* total time " + (System.currentTimeMillis() - start));
	}

	private static void part2c(List<String> input, int maxRange) {

		long start = System.currentTimeMillis();
		List<int[]> sensors = parse(input);

		Map<Long, Integer> uncovered = new HashMap<>();

		for (int[] sensor : sensors) {

			int x = sensor[0];
			int y = sensor[1];
			int manhattanDistance = Math.abs(sensor[0] - sensor[2]) + Math.abs(sensor[1] - sensor[3]);

			int d = manhattanDistance + 1;

			int[] p = new int[] { x - d, y, x + d, y, x, y - d, x, y + d };

			for (int i = 0; i < 4; i++) {

				int sx = p[i * 2];
				int sy = p[i * 2 + 1];

				int tx = p[(i * 2 + 2) % 8];
				int ty = p[(i * 2 + 3) % 8];

				while (sx != tx && sy != ty) {

					if (insideRange(sx, maxRange) && insideRange(sy, maxRange)) {
						long c = 4_000_000L * sx + sy;
						Integer score = uncovered.get(c);
						uncovered.put(c, score == null ? 1 : score + 1);
					}

					sx += (int) Math.signum(tx - sx);
					sy += (int) Math.signum(ty - sy);
				}
			}
//			System.out.println("# " + uncovered1.size());
		}

		System.out.println("* romboids drawn " + (System.currentTimeMillis() - start));
		System.out.println("# " + uncovered.size());

		Set<Long> probableIntersections = new HashSet<>(
				uncovered.entrySet().stream().filter(e -> e.getValue() >= 2).map(e -> e.getKey()).toList());

		System.out.println("* intersections processed " + (System.currentTimeMillis() - start));
		System.out.println("# " + probableIntersections.size());

		for (int[] sensor : sensors) {
			int x = sensor[0];
			int y = sensor[1];
			int manhattanDistance = manhattanDistance(x, y, sensor[2], sensor[3]);

			List<Long> remove = new ArrayList<>();

			for (long c : probableIntersections) {
				int cx = (int) (c / 4_000_000);
				int cy = (int) (c % 4_000_000);

				int md2 = manhattanDistance(x, y, cx, cy);
				if (md2 <= manhattanDistance) {
					remove.add(c);
				}
			}

			probableIntersections.removeAll(remove);
		}

		System.out.println("# " + probableIntersections.size());

		System.out.println(MessageFormat.format("part2: {0}", probableIntersections.iterator().next()));
		System.out.println("* total time " + (System.currentTimeMillis() - start));
	}

	private static int manhattanDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	private static boolean insideRange(int c, int maxRange) {
		return 0 <= c && c <= maxRange;
	}

	private static void part2(List<String> input, int maxRange) {
		List<int[]> sensors = parse(input);

		for (int row = 0; row <= maxRange; row++) {

			Set<Integer> coveredInTargetRow = new HashSet<>();

			for (int[] sensor : sensors) {
				int manhattanDistance = Math.abs(sensor[0] - sensor[2]) + Math.abs(sensor[1] - sensor[3]);
				int distanceToTargetRow = Math.abs(sensor[1] - row);
				if (distanceToTargetRow > manhattanDistance) {
					continue;
				}
				int x = sensor[0];
				coveredInTargetRow.add(x);
				int dx = 1;
				while (distanceToTargetRow + dx <= manhattanDistance) {
					coveredInTargetRow.add(x - dx);
					coveredInTargetRow.add(x + dx);
					dx++;
				}
			}

			List<Integer> covered = coveredInTargetRow.stream().filter(x -> x >= 0 && x <= maxRange).sorted().toList();
//			System.out.println("# " + row + " " + covered.size());
			if (covered.size() < maxRange + 1) {

//				System.out.println("# " + covered.stream().map(i -> "" + i).collect(Collectors.joining(",")));
				for (int i = 1; i <= maxRange; i++) {
					if (covered.get(i) - covered.get(i - 1) == 2) {
						long result = 4_000_000L * i + row;
						System.out.println(MessageFormat.format("part2: {0}", result));
						return;
					}
				}

			}
		}

	}

}
