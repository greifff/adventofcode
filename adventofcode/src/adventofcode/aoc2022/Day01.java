package adventofcode.aoc2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day01 {

	public static void main(String[] args) {
		List<String> input = IOUtil.readFile("2022/day01.data");

		Map<Integer, Integer> elves = new HashMap<>();

		int elf = 0;
		elves.put(elf, 0);
		for (String c : input) {
			if ("".equals(c)) {
				elf++;
				elves.put(elf, 0);
			} else {
				elves.put(elf, elves.get(elf) + Integer.parseInt(c));
			}
		}

		System.out.println("part1: " + elves.values().stream().reduce(Math::max).orElse(0));

		List<Integer> l = new ArrayList<>(elves.values());

		Collections.sort(l);
		Collections.reverse(l);

		System.out.println("part2: " + (l.get(0) + l.get(1) + l.get(2)));
	}
}
