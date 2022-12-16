package adventofcode.aoc2021.day22;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day22 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2021/day22.test1");
		List<String> test2 = IOUtil.readFile("2021/day22.test2");
		List<String> test3 = IOUtil.readFile("2021/day22.test3");
		List<String> data = IOUtil.readFile("2021/day22.data");

		part1(test1);
		part1(test2);
		part1(test3);
		part1(data);
	}

	private static void part1(List<String> data) {
		List<ActivationStep> steps = data.stream().map(l -> new ActivationStep(l)).collect(Collectors.toList());
		Map<Integer, Map<Integer, Set<Integer>>> cube = new HashMap<>();

		for (ActivationStep step : steps) {
			if (!step.overlapsWithActivationRegion())
				continue;
			int[] coords = step.activationRegionCoordinates();
			for (int x = coords[0]; x <= coords[1]; x++) {
				Map<Integer, Set<Integer>> plane = cube.get(x);
				if (plane == null) {
					plane = new HashMap<>();
					cube.put(x, plane);
				}
				for (int y = coords[2]; y <= coords[3]; y++) {
					Set<Integer> row = plane.get(y);
					if (row == null) {
						row = new HashSet<>();
						plane.put(y, row);
					}
					for (int z = coords[4]; z <= coords[5]; z++) {
						if (step.turnOn) {
							row.add(z);
						} else {
							row.remove(z);
						}
					}
				}
			}
		}

		System.out.println("part1: " + countOn(cube));
	}

	private static long countOn(Map<Integer, Map<Integer, Set<Integer>>> cube) {
		return cube.values().stream().flatMap(m -> m.values().stream()).map(s -> (long) s.size()).reduce((a, b) -> a + b).orElse(0L);
	}

}
