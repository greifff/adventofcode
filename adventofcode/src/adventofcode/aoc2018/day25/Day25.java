package adventofcode.aoc2018.day25;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day25 {
	public static void main(String[] args) {
		part1(IOUtil.readFile("2018/day25/day25.test1"));
		part1(IOUtil.readFile("2018/day25/day25.test2"));
		part1(IOUtil.readFile("2018/day25/day25.test3"));
		part1(IOUtil.readFile("2018/day25/day25.test4"));
		part1(IOUtil.readFile("2018/day25/day25.data"));
	}

	static void part1(List<String> data) {
		List<Star> stars = data.stream().map(s -> new Star(s)).collect(Collectors.toList());
		for (int i = 0; i < stars.size() - 1; i++) {
			Star s1 = stars.get(i);
			for (int j = i + 1; j < stars.size(); j++) {
				Star s2 = stars.get(j);
				if (s1.distance(s2) <= 3) {
					s1.neighbors.add(s2);
					s2.neighbors.add(s1);
				}
			}
		}

		int constellations = 0;

		while (!stars.isEmpty()) {
			constellations++;
			Set<Star> inConstellation = new HashSet<>();
			inConstellation.addAll(stars.remove(0).neighbors);
			while (!inConstellation.isEmpty()) {
				Star s2 = inConstellation.iterator().next();
				inConstellation.remove(s2);
				if (stars.remove(s2)) {
					inConstellation.addAll(s2.neighbors);
				}
			}
		}

		System.out.println("Constellations: " + constellations);
	}
}
