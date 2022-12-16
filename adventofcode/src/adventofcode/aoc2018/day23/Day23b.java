package adventofcode.aoc2018.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day23b {

	public static void main(String[] args) {
		List<String> test2 = IOUtil.readFile("2018/day23.test2");
		List<String> data1 = IOUtil.readFile("2018/day23.data");

		part2(test2);
		part2(data1);
	}

	private static void part2(List<String> data) {
		List<Nanobot> bots = data.stream().map(s -> new Nanobot(s)).collect(Collectors.toList());
		List<Area> areas = new ArrayList<>();
		for (int i = 0; i < bots.size() - 1; i++) {
			Nanobot a = bots.get(i);
			for (int j = i + 1; j < bots.size(); j++) {
				Nanobot b = bots.get(j);
				if (a.intersects(b)) {
					areas.add(new Area(a, b, i, j));
				}
			}
		}

		List<Area> finalAreas = new ArrayList<>();

		while (!areas.isEmpty()) {
			int k = 0;
			while (k < areas.size() - 1) {
				Area a = areas.get(k);
				int l = k + 1;
				while (l < areas.size() - 1) {
					Area b = areas.get(l);
					if (a.equalCoverage(b)) {
						a.coveredBy.addAll(b.coveredBy);
						areas.remove(l);
					} else {
						l++;
					}
				}
				k++;
			}
			List<Area> areas2 = new ArrayList<>();
			boolean noIntersectionOnLast = true;
			for (int i = 0; i < areas.size() - 1; i++) {
				Area a = areas.get(i);
				boolean noIntersection = true;
				for (int j = i + 1; j < areas.size(); j++) {
					Area b = areas.get(j);
					Area c = new Area(a, b);
					if (!c.spots.isEmpty()) {
						areas2.add(c);
						noIntersection = false;
						noIntersectionOnLast &= j < areas.size() - 1;
					}
				}
				if (noIntersection) {
					finalAreas.add(a);
				}
			}
			if (noIntersectionOnLast) {
				finalAreas.add(areas.get(areas.size() - 1));
			}

			areas = areas2;

			// ignore bad solutions
			int maxIntersections = finalAreas.stream().map(a1 -> a1.coveredBy.size()).reduce(Math::max).orElse(0);
			finalAreas = new ArrayList<>(finalAreas.stream().filter(a1 -> a1.coveredBy.size() == maxIntersections).collect(Collectors.toList()));
		}

		System.out.println("# " + finalAreas.size());

		System.out.println("part2: " + finalAreas.stream().flatMap(a1 -> a1.spots.stream()).map(s -> s.distance()).reduce(Math::min).orElse(0));

	}
}
