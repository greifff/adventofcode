package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day6 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2019/day6/day6.test");
		List<String> test2 = IOUtil.readFile("2019/day6/day6.test2");
		List<String> data = IOUtil.readFile("2019/day6/day6.data");

		part1(test);
		part1(data);

		part2(test2);
		part2(data);
	}

	private static void part2(List<String> input) {
		Map<String, StellarObject> m = parse(input);

		StellarObject you = m.get("YOU");
		StellarObject san = m.get("SAN");

		StellarObject sanPlanet = san.orbiting;
		StellarObject youPlanet = you.orbiting;

		List<StellarObject> youOrbits = youPlanet.orbits();
		List<StellarObject> sanOrbits = sanPlanet.orbits();
		List<StellarObject> common = new ArrayList<>(youOrbits);
		common.retainAll(sanOrbits);

		youOrbits.removeAll(common);
		sanOrbits.removeAll(common);

		System.out.println(youOrbits.stream().map(so -> so.name).reduce((a, b) -> a + ")" + b));
		System.out.println(sanOrbits.stream().map(so -> so.name).reduce((a, b) -> a + ")" + b));

		int o1 = youOrbits.size();
		int o2 = sanOrbits.size();

		int oTotal = o1 + o2;

		System.out.println("part2: " + oTotal);
	}

	static void part1(List<String> input) {
		Map<String, StellarObject> m = parse(input);
		System.out.println("part1: " + m.values().parallelStream().map(so -> so.countOrbits()).reduce((a, b) -> a + b).orElse(0));
	}

	static Map<String, StellarObject> parse(List<String> input) {
		Map<String, StellarObject> m = new HashMap<>();
		StellarObject com = new StellarObject("COM");
		m.put("COM", com);

		for (String line : input) {
			String[] parts = line.split("\\)");
			StellarObject parent = m.get(parts[0]);
			if (parent == null) {
				parent = new StellarObject(parts[0], com);
				m.put(parts[0], parent);
			}
			StellarObject child = m.get(parts[1]);
			if (child == null) {
				child = new StellarObject(parts[1], parent);
				m.put(parts[1], child);
			} else {
				child.orbiting = parent;
			}
		}
		return m;
	}

	static class StellarObject {
		final String name;
		StellarObject orbiting;

		StellarObject(String name) {
			this.name = name;
			orbiting = null;
		}

		StellarObject(String name, StellarObject so) {
			this.name = name;
			orbiting = so;
		}

		int countOrbits() {
			if (orbiting == null) {
				return 0;
			}
			return 1 + orbiting.countOrbits();
		}

		List<StellarObject> orbits() {
			List<StellarObject> orbits;
			if (orbiting == null) {
				orbits = new ArrayList<>();
			} else {
				orbits = orbiting.orbits();
			}
			orbits.add(this);
			return orbits;
		}
	}

}
