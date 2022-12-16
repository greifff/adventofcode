package adventofcode.aoc2018.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day23 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2018/day23.test");
		List<String> test2 = IOUtil.readFile("2018/day23.test2");
		List<String> data1 = IOUtil.readFile("2018/day23.data");

		// part1(test1);
		// part1(data1);

		part2(test2);
		// part2(data1);
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

		// int minX = bots.stream().map(n -> n.x).reduce(Math::min).orElse(0);
		// int maxX = bots.stream().map(n -> n.x).reduce(Math::max).orElse(0);
		// int minY = bots.stream().map(n -> n.y).reduce(Math::min).orElse(0);
		// int maxY = bots.stream().map(n -> n.y).reduce(Math::max).orElse(0);
		// int minZ = bots.stream().map(n -> n.z).reduce(Math::min).orElse(0);
		// int maxZ = bots.stream().map(n -> n.z).reduce(Math::max).orElse(0);
		//
		// System.out.println("" + minX + ".." + maxX + "," + minY + ".." + maxY + "," + minZ + ".." + maxZ);

		// int maxContacts = 1;
		// List<Spot> spots = new ArrayList<>();

		// first approach (brute force): at least contact to 64 nodes (out of 1000)

		// second approach: step 1 find pairwise intersections
		for (int i = 0; i < bots.size() - 1; i++) {
			Nanobot a = bots.get(i);
			for (int j = i + 1; j < bots.size(); j++) {
				Nanobot b = bots.get(j);
				if (a.intersects(b)) {
					a.overlaps.add(b);
					b.overlaps.add(a);
				}
			}
		}

		// step 2 find groups of nodes which have pairwise intersections with each other
		List<Set<Nanobot>> result = new ArrayList<>();
		BronKerbosch1(new HashSet<>(), bots, new HashSet<>(), result);

		Collections.sort(result, Comparator.comparing(Set::size));
		Collections.reverse(result);
		Set<Nanobot> x = result.get(0);
		System.out.println(x.size() + " " + result.get(1).size());
		// TODO find points, that are in each range
		Iterator<Nanobot> itn = x.iterator();
		Nanobot n1 = itn.next();
		Nanobot n2 = itn.next();
		List<Spot> spots = new ArrayList<>(n1.intersection(n2));

		while (itn.hasNext() && !spots.isEmpty()) {
			Nanobot n3 = itn.next();
			spots = spots.stream().filter(spot -> n3.checkWithin(spot)).collect(Collectors.toList());
		}

		// find the nearest
		Collections.sort(spots, Comparator.comparing(Spot::distance));
		System.out.println("part2: " + spots.get(0).distance());
	}

	static void BronKerbosch1(Set<Nanobot> r, List<Nanobot> p, Set<Nanobot> x, List<Set<Nanobot>> result) {
		if (p.isEmpty() && x.isEmpty()) {
			result.add(r);
			return;
		}
		while (!p.isEmpty()) {
			Nanobot v = p.get(0);
			Set<Nanobot> r2 = new HashSet<>(r);
			r2.add(v);
			List<Nanobot> p2 = new ArrayList<>(p);
			p2.retainAll(v.overlaps);
			Set<Nanobot> x2 = new HashSet<>(x);
			x2.retainAll(v.overlaps);
			BronKerbosch1(r2, p2, x2, result);
			p.remove(v);
			x.add(v);
		}
	}
}
