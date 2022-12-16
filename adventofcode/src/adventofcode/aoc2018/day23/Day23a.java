package adventofcode.aoc2018.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day23a {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2018/day23.test");
		List<String> test2 = IOUtil.readFile("2018/day23.test2");
		List<String> data1 = IOUtil.readFile("2018/day23.data");

		// part1(test1);
		// part1(data1);

		// part2(test2);
		part2(data1);
	}

	static void part1(List<String> data) {
		List<Nanobot> bots = data.stream().map(s -> new Nanobot(s)).collect(Collectors.toList());
		Collections.sort(bots, (bot1, bot2) -> bot1.range - bot2.range);
		Collections.reverse(bots);
		Nanobot b0 = bots.get(0);
		System.out.println("part1: bot 0 range: " + b0.range + "; in Range: " + b0.inRange(bots).size() + "(" + b0.x + "," + b0.y + "," + b0.z + ")");
		System.out.println("part1: bot 1 range: " + bots.get(1).range + "; in Range: " + bots.get(1).inRange(bots).size());
	}

	static void part2(List<String> data) {
		List<Nanobot> bots = data.stream().map(s -> new Nanobot(s)).collect(Collectors.toList());

		int minX = bots.stream().map(n -> n.x).reduce(Math::min).orElse(0);
		int maxX = bots.stream().map(n -> n.x).reduce(Math::max).orElse(0);
		int minY = bots.stream().map(n -> n.y).reduce(Math::min).orElse(0);
		int maxY = bots.stream().map(n -> n.y).reduce(Math::max).orElse(0);
		int minZ = bots.stream().map(n -> n.z).reduce(Math::min).orElse(0);
		int maxZ = bots.stream().map(n -> n.z).reduce(Math::max).orElse(0);

		System.out.println("" + minX + ".." + maxX + "," + minY + ".." + maxY + "," + minZ + ".." + maxZ);

		// int divisor = 1_000_000;
		// int divisorStep = 10;
		// List<Spot> oldSpots = new ArrayList<>();
		// oldSpots.add(new Spot(0, 0, 0));

		// while (divisor >= 1) {
		int maxContacts = 1;

		List<Spot> spots = new ArrayList<>();

		// for (Spot reference : oldSpots) {
		// int refX = reference.x * divisorStep;
		// int refY = reference.y * divisorStep;
		// int refZ = reference.z * divisorStep;
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Spot spot1 = new Spot(x, y, z);
					// final int div1 = divisor;
					int contacts = (int) bots.parallelStream().filter(b -> b.checkWithin(spot1)).count();
					if (contacts > maxContacts) {
						maxContacts = contacts;
						spots.clear();
						spots.add(spot1);
					} else if (contacts == maxContacts) {
						spots.add(spot1);
					}
				}
			}
		}
		// }
		// System.out.println("" + divisor + " " + spots.size() + " " + maxContacts);
		// divisor /= divisorStep;
		// int minDistance = spots.stream().map(Spot::distance).reduce(Math::min).orElse(0);
		// oldSpots = new ArrayList<>(spots.stream().filter(s -> s.distance() == minDistance).collect(Collectors.toList()));
		// Collections.sort(oldSpots);
		// int k = 1;
		// while (k < oldSpots.size()) {
		// Spot s1 = oldSpots.get(k - 1);
		// Spot s2 = oldSpots.get(k);
		// if (s1.equals(s2)) {
		// oldSpots.remove(k);
		// } else {
		// k++;
		// }
		// }
		// System.out.println("" + divisor + " " + spots.size() + " " + maxContacts);
		// }

		// find the nearest
		Collections.sort(spots, Comparator.comparing(Spot::distance));
		System.out.println("part2: " + spots.get(0).distance());

		// 28434455 was too low
	}

}
