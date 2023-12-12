package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day12 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day12.test");
		List<String> input = IOUtil.readFile("2023/day12.data");

		run(test);
		run(input);
	}

	private static void run(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long sum = 0;
		long sum2 = 0;
		for (String s : input) {
			HotSpringRow h = new HotSpringRow(s);

			sum += h.combinationsRecursive(0, 0, 0);

			h.times5();

			sum2 += h.combinationsRecursive(0, 0, 0);
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
		System.out.println("part2: " + sum2);
	}

	static class HotSpringRow {

		List<Integer> groupsDamaged;
		String map;
		Map<Integer, Long> cached = new HashMap<>();

		HotSpringRow(String s) {
			String[] p = s.split(" ");
			map = p[0];
			groupsDamaged = Arrays.stream(p[1].split(",")).map(t -> Integer.parseInt(t)).toList();
		}

		void times5() {
			map = (map + '?').repeat(4) + map;
			List<Integer> groups5 = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				groups5.addAll(groupsDamaged);
			}
			groupsDamaged = groups5;
			cached.clear();
		}

		long combinationsRecursive(int mapIndex, int groupIndex, int processed) {
			Long fromCache = cached.get(mapIndex * 10_000 + groupIndex * 100 + processed);
			if (fromCache != null) {
				return fromCache;
			}
			long result = combinationsRecursive2(mapIndex, groupIndex, processed);
			cached.put(mapIndex * 10_000 + groupIndex * 100 + processed, result);
			return result;
		}

		private long combinationsRecursive2(int mapIndex, int groupIndex, int processed) {
			if (groupIndex >= groupsDamaged.size()) {
				return map.indexOf('#', mapIndex) == -1 ? 1 : 0;
			}

			long result = 0;
			if (mapIndex < map.length()) {

				char c = map.charAt(mapIndex);
				if (c == '#' || c == '?') {
					if (groupsDamaged.get(groupIndex) == processed + 1) {
						if (mapIndex + 1 == map.length() || map.charAt(mapIndex + 1) != '#') {
							result = combinationsRecursive(mapIndex + 2, groupIndex + 1, 0);
						}
					} else {
						result = combinationsRecursive(mapIndex + 1, groupIndex, processed + 1);
					}
				}

				if (c == '?' || c == '.') {
					if (processed == 0) { // skip ahead
						result += combinationsRecursive(mapIndex + 1, groupIndex, processed);
					}
				}
			}
			return result;
		}

		int combinationsBruteforce() {
			// took about three minutes for part 1, probably takes aeons for part 2
			Pattern p = Pattern.compile("\\.*"
					+ groupsDamaged.stream().map(j -> "#{" + j + "}").collect(Collectors.joining("\\.+")) + "\\.*");

			long x = map.chars().filter(c -> c == '?').count();

			int count = 0;
			for (int i = 0; i < 1 << x; i++) {
				String map1 = map;
				for (int k = 0; k < x; k++) {
					map1 = map1.replaceFirst("\\?", (i >> k & 1) == 1 ? "#" : ".");
				}
				if (p.matcher(map1).matches())
					count++;
			}
			return count;
		}

	}

}
