package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day05 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day05.test");
		List<String> input = IOUtil.readFile("2023/day05.data");

		part1(test);
		part1(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);
		String s = input.get(0);
		List<Long> initialSeeds = Arrays.stream(s.substring(s.indexOf(":") + 1).trim().split(" "))
				.map(x -> Long.parseLong(x)).toList();

		List<String> input2 = new ArrayList<>(input);
		input2.remove(0);

		Seeds seeds = new Seeds(input2);

		long lowestSoil = initialSeeds.stream().map(i -> seeds.mappingAllTheWay(i)).reduce(Math::min).orElse(0L);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + lowestSoil);
		// its not 35205517, 425002936
	}

	static class Seeds {
		List<SeedMap> maps = new ArrayList<>();

		Seeds(List<String> input) {

			SeedMap map = null;
			for (String t : input) {
				if (t.endsWith("map:")) {
					map = new SeedMap();
					maps.add(map);
				} else if ("".equals(t)) {
					// do nothing
				} else {
					map.addEntry(t);
				}
			}
		}

		long mappingAllTheWay(long s) {
			long k = s;
			for (SeedMap map : maps) {
				k = map.get(k);
			}
			return k;
		}

	}

	static class SeedMap {

		// source start, range length, destination start
		List<long[]> entry = new ArrayList<>();

		void addEntry(String line) {
			List<Long> aux = Arrays.stream(line.split(" ")).map(s -> Long.parseLong(s)).toList();
			long[] e = new long[] { aux.get(1), aux.get(2), aux.get(0) };
			entry.add(e);
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
