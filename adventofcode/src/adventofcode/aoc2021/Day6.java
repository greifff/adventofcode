package adventofcode.aoc2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {

	public static void main(String[] args) {
		List<Integer> test = Arrays.asList(3, 4, 3, 1, 2);
		List<Integer> data = Arrays.asList(1, 1, 3, 1, 3, 2, 1, 3, 1, 1, 3, 1, 1, 2, 1, 3, 1, 1, 3, 5, 1, 1, 1, 3, 1, 2, 1, 1, 1, 1, 4, 4, 1, 2, 1, 2,
				1, 1, 1, 5, 3, 2, 1, 5, 2, 5, 3, 3, 2, 2, 5, 4, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 5, 1, 2, 4, 3, 2, 2, 2, 2, 1, 4, 1, 1, 5, 1, 3, 4, 4, 1,
				1, 3, 3, 5, 5, 3, 1, 3, 3, 3, 1, 4, 2, 2, 1, 3, 4, 1, 4, 3, 3, 2, 3, 1, 1, 1, 5, 3, 1, 4, 2, 2, 3, 1, 3, 1, 2, 3, 3, 1, 4, 2, 2, 4, 1,
				3, 1, 1, 1, 1, 1, 2, 1, 3, 3, 1, 2, 1, 1, 3, 4, 1, 1, 1, 1, 5, 1, 1, 5, 1, 1, 1, 4, 1, 5, 3, 1, 1, 3, 2, 1, 1, 3, 1, 1, 1, 5, 4, 3, 3,
				5, 1, 3, 4, 3, 3, 1, 4, 4, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 5, 1, 1, 2, 1, 5, 2, 1, 1, 2, 3, 2, 3, 1, 3, 1, 1, 1, 5, 1, 1, 2,
				1, 1, 1, 1, 3, 4, 5, 3, 1, 4, 1, 1, 4, 1, 4, 1, 1, 1, 4, 5, 1, 1, 1, 4, 1, 3, 2, 2, 1, 1, 2, 3, 1, 4, 3, 5, 1, 5, 1, 1, 4, 5, 5, 1, 1,
				3, 3, 1, 1, 1, 1, 5, 5, 3, 3, 2, 4, 1, 1, 1, 1, 1, 5, 1, 1, 2, 5, 5, 4, 2, 4, 4, 1, 1, 3, 3, 1, 5, 1, 1, 1, 1, 1, 1);
		part1(test);
		part1(data);
	}

	private static void part1(List<Integer> data) {
		// TODO Auto-generated method stub
		Map<Integer, Long> fishes = new HashMap<>();

		for (int d : data) {
			Long c = fishes.get(d);
			if (c == null) {
				fishes.put(d, 1L);
			} else {
				fishes.put(d, c + 1);
			}
		}

		for (int s = 1; s <= 256; s++) {
			fishes = step(fishes);
			if (s == 18 || s == 80 || s == 256)
				System.out.println("" + s + " days: " + fishes.values().stream().reduce((a, b) -> a + b).orElse(0L));
		}
		// System.out.println("part1: " + fishes.values().stream().reduce((a, b) -> a + b).orElse(0));
	}

	private static Map<Integer, Long> step(Map<Integer, Long> fish1) {
		Map<Integer, Long> fish2 = new HashMap<>();

		for (Map.Entry<Integer, Long> f : fish1.entrySet()) {
			int t = f.getKey() - 1;
			long c = f.getValue();

			if (t == -1) {
				fish2.put(8, c);
				t = 6;
			}

			Long c2 = fish2.get(t);
			c2 = c2 == null ? c : c + c2;
			fish2.put(t, c2);
		}
		return fish2;
	}
}
