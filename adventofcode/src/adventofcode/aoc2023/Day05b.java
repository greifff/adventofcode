package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day05b {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day05.test");
		List<String> input = IOUtil.readFile("2023/day05.data");

		part2(test);
		part2(input);
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		String s = input.get(0);
		String[] p = s.substring(s.indexOf(":") + 1).trim().split(" ");

		List<String> input2 = new ArrayList<>(input);
		input2.remove(0);

		Seeds seeds = new Seeds(input2);

		seeds.maps.stream().forEach(m -> {
			System.out.println("---");
			m.checkIntersection();
			m.sort();
		});

		long lowestSoil = Long.MAX_VALUE;

		for (int i = 0; i < p.length; i += 2) {
			long base = Long.parseLong(p[i]);
			long range = Long.parseLong(p[i + 1]);
			long r = seeds.mappingAllTheWay(new long[] { base, base + range - 1 });
			lowestSoil = Math.min(lowestSoil, r);

		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + lowestSoil);
	}

	static class Seeds {
		List<SeedMap2> maps = new ArrayList<>();

		Seeds(List<String> input) {
			SeedMap2 map = null;
			for (String t : input) {
				if (t.endsWith("map:")) {
					map = new SeedMap2();
					maps.add(map);
				} else if ("".equals(t)) {
					// do nothing
				} else {
					map.addEntry(t);
				}
			}
		}

		long mappingAllTheWay(long[] range) {
			List<long[]> ranges1 = new ArrayList<>();
			ranges1.add(range);

			for (SeedMap2 map : maps) {
				List<long[]> ranges2 = new ArrayList<>();
				for (long[] r1 : ranges1) {
					List<long[]> r2 = map.map(r1);
					ranges2.addAll(r2);
				}
				ranges1 = ranges2;
			}
			return ranges1.stream().map(a -> a[0]).reduce(Math::min).orElse(Long.MAX_VALUE);
		}

	}

	static class SeedMap2 {

		// source start1, end1, start2
		List<long[]> entry = new ArrayList<>();

		void addEntry(String line) {
			List<Long> aux = Arrays.stream(line.split(" ")).map(s -> Long.parseLong(s)).toList();
			long[] e = new long[] { aux.get(1), aux.get(1) + aux.get(2) - 1, aux.get(0) };
			entry.add(e);
		}

		void sort() {
			Collections.sort(entry, (a, b) -> Long.compare(a[0], b[0]));
		}

		List<long[]> map(long[] range) {
			List<long[]> result = new ArrayList<>();
			for (int j = 0; j < entry.size(); j++) {
				long[] e2 = entry.get(j);

				if (range[0] > e2[1]) {
					continue; // smallest value is bigger than interval
				}
				if (range[1] < e2[0]) {
					result.add(range); // biggest value is smaller than interval
					return result;
				}

				// range must have an intersection with the interval

				if (range[0] < e2[0]) { // there is a part with values smaller than the interval
					result.add(new long[] { range[0], e2[0] - 1 });
					range[0] = e2[0];
				}

				if (range[1] <= e2[1]) {
					// end of the range is inside the interval
					result.add(new long[] { range[0] - e2[0] + e2[2], range[1] - e2[0] + e2[2] });
					return result;
				}

				// range extends above the interval
				result.add(new long[] { range[0] - e2[0] + e2[2], e2[1] - e2[0] + e2[2] });
				range[0] = e2[1] + 1;
			}
			result.add(range);
			return result;
		}

		void checkIntersection() {
			for (int i = 0; i < entry.size() - 1; i++) {
				long[] e1 = entry.get(i);
				for (int j = i + 1; j < entry.size(); j++) {
					long[] e2 = entry.get(j);

					boolean foot2inRange1 = e1[0] <= e2[0] && e2[0] < e1[1];
					boolean top2inRange1 = e1[0] <= e2[1] && e2[1] < e1[1];

					boolean foot1inRange2 = e2[0] <= e1[0] && e1[0] < e2[1];
					boolean top1inRange2 = e2[0] <= e1[1] && e1[1] < e2[1];

					if (foot2inRange1) {
						if (top2inRange1) {
							System.out.println("#A " + i + " " + j);
						} else {
							System.out.println("#B " + i + " " + j);
						}
					}

					if (foot1inRange2) {
						if (top1inRange2) {
							System.out.println("#C " + i + " " + j);
						} else {
							System.out.println("#D " + i + " " + j);
						}
					}
				}
			}
		}

		public long get(long key) {
			List<Long> m = new LinkedList<>();
			for (long[] e : entry) {
				if (key >= e[0] && key < e[0] + e[1]) {
					m.add(key - e[0] + e[2]);
				}
			}
			return m.stream().reduce(Math::min).orElse(key);
		}

	}
}
