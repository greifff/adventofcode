package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day18 {

	private static final int FACTOR = 100;

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day18.test");
		List<String> input = IOUtil.readFile("2022/day18.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		long start = System.currentTimeMillis();

		Set<Integer> cubicles = input.stream().map(s -> {
			String[] p = s.split(",");
			int x = Integer.parseInt(p[0]);
			int y = Integer.parseInt(p[1]);
			int z = Integer.parseInt(p[2]);
			return x + y * FACTOR + z * FACTOR * FACTOR;
		}).collect(Collectors.toSet());

		Set<Integer> toScan = new HashSet<>(cubicles);

		int surfaceArea = 0;

		for (int c : toScan) {
			int[] neighbors = new int[] { c - 1, c + 1, c - FACTOR, c + FACTOR, c - FACTOR * FACTOR,
					c + FACTOR * FACTOR };

			for (int n : neighbors) {
				if (!cubicles.contains(n))
					surfaceArea++;
			}
		}

		System.out.println(
				MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start, surfaceArea));
	}

	private static void part2(List<String> input) {
		long start = System.currentTimeMillis();

		int minx = Integer.MAX_VALUE;
		int miny = Integer.MAX_VALUE;
		int minz = Integer.MAX_VALUE;
		int maxx = 0;
		int maxy = 0;
		int maxz = 0;

		Set<Integer> cubicles = new HashSet<>();

		for (String s : input) {
			String[] p = s.split(",");
			int x = Integer.parseInt(p[0]) + 1;
			minx = Math.min(minx, x);
			maxx = Math.max(maxx, x);
			int y = Integer.parseInt(p[1]) + 1;
			miny = Math.min(miny, y);
			maxy = Math.max(maxy, y);
			int z = Integer.parseInt(p[2]) + 1;
			minz = Math.min(minz, z);
			maxz = Math.max(maxz, z);
			cubicles.add(x + y * FACTOR + z * FACTOR * FACTOR);
		}

//		System.out.println("# " + minx + " " + miny + " " + minz);
//		System.out.println("# " + maxx + " " + maxy + " " + maxz);

		List<Integer> toVisit = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();
		toVisit.add(0);

		int surfaceArea = 0;

		while (!toVisit.isEmpty()) {
			int c = toVisit.remove(0);
			visited.add(c);
			int x = c % FACTOR;
			int y = (c / FACTOR) % FACTOR;
			int z = c / FACTOR / FACTOR;

			List<Integer> neighbors = new ArrayList<>();
			if (x > 0)
				neighbors.add(c - 1);
			if (x < maxx + 1)
				neighbors.add(c + 1);
			if (y > 0)
				neighbors.add(c - FACTOR);
			if (y < maxy + 1)
				neighbors.add(c + FACTOR);
			if (z > 0)
				neighbors.add(c - FACTOR * FACTOR);
			if (z < maxz + 1)
				neighbors.add(c + FACTOR * FACTOR);

			for (int n : neighbors) {
				if (cubicles.contains(n)) {
					surfaceArea++;
				} else {
					toVisit.add(n);
				}
			}
			toVisit.removeAll(visited);
		}

		// 2442 is too low

		System.out.println(
				MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start, surfaceArea));
	}

}
