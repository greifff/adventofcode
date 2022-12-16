package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day14 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day14/day14.test1");
		List<String> test2 = IOUtil.readFile("2019/day14/day14.test2");
		List<String> test3 = IOUtil.readFile("2019/day14/day14.test3");
		List<String> test4 = IOUtil.readFile("2019/day14/day14.test4");
		List<String> test5 = IOUtil.readFile("2019/day14/day14.test5");
		List<String> data = IOUtil.readFile("2019/day14/day14.data");

		part1(test1);

		part1(test2);

		part1(test3);
		part2(test3);

		part1(test4);
		part2(test4);

		part1(test5);
		part2(test5);

		part1(data);
		part2(data);
	}

	static void part1(List<String> data) {
		Map<String, Reaction> reactions = data.stream().map(s -> new Reaction(s)).collect(Collectors.toMap(r -> r.result.typ, r -> r));

		Map<String, Material> onStock = new HashMap<>();

		List<Material> required = new ArrayList<>();
		required.add(new Material(1, "FUEL"));

		long oreUsed = process(reactions, required, onStock);

		System.out.println("part1: " + oreUsed);

		// long totalOre = 1_000_000_000_000L;
		//
		// long fuelProduced = totalOre / oreUsed;
		//
		// totalOre = totalOre % oreUsed;
		//
		// for (Material o : onStock.values()) {
		// o.count *= fuelProduced;
		// }
		// onStock.put("ORE", new Material(totalOre, "ORE"));
		//
		// fuelProduced += process2(reactions, required, onStock);
		//
		// System.out.println("part2: " + fuelProduced);
	}

	static void part2(List<String> data) {
		Day14 d = new Day14();
		d.reactions = data.stream().map(s -> new Reaction(s)).collect(Collectors.toMap(r -> r.result.typ, r -> r));
		d.onStock.putAll(d.reactions.values().stream().map(r1 -> r1.result).collect(Collectors.toMap(m1 -> m1.typ, m1 -> new Material(0L, m1.typ))));
		d.onStock.put("ORE", new Material(1_000_000_000_000L, "ORE"));

		long fuel = 0;
		long delta = 0;

		Reaction r = d.reactions.get("FUEL");

		// first execution
		Material x = d.transform(r);
		delta = x.count;
		fuel = delta;

		// project into 1_000_000_000L ore

		// System.out.println("available ore after first fuel: " + d.onStock.get("ORE").count);

		// long usedOre1 = 1_000_000_000_000L - d.onStock.get("ORE").count;
		//
		// System.out.println("usedOre for first fuel: " + usedOre1);
		// fuel = 1_000_000_000_000L / usedOre1;
		//
		// for (Material o : d.onStock.values()) {
		// o.count *= fuel;
		// }
		//
		// System.out.println("fuel phase a: " + fuel);
		//
		// d.onStock.get("ORE").count = 1_000_000_000_000L % usedOre1;

		// create remaining fuel

		while (delta > 0) {
			x = d.transform(r);
			delta = x.count;
			fuel += delta;
		}

		System.out.println("part2: " + fuel);
	}

	Map<String, Material> onStock = new HashMap<>();
	Map<String, Reaction> reactions = new HashMap<>();

	Material transform(Reaction r) {
		List<Material> required = r.input.stream().map(m -> new Material(m)).collect(Collectors.toList());
		Material result = new Material(r.result);
		for (Material s : required) {
			Material o = onStock.get(s.typ);
			long d = Math.min(s.count, o.count);
			s.count -= d;
			o.count -= d;

			while (s.count > 0) {
				Reaction r2 = reactions.get(s.typ);
				if (r2 == null) {
					result.count = 0;
					return result;
				}
				Material p = transform(r2);
				if (p.count == 0) {
					result.count = 0;
					return result;
				}
				d = Math.min(p.count, s.count);
				s.count -= d;
				if (d < p.count) {
					o.count += (p.count - d);
				}
			}
		}
		return result;
	}

	static long process(Map<String, Reaction> reactions, List<Material> required, Map<String, Material> onStock) {
		long oreUsed = 0;
		while (!required.isEmpty()) {
			Material m = required.remove(0);

			if ("ORE".equals(m.typ)) {
				oreUsed += m.count;
			} else {
				Material o = onStock.get(m.typ);
				if (o != null) {
					if (m.count > o.count) {
						m.count -= o.count;
						o.count = 0;
					} else {
						o.count -= m.count;
						continue;
					}
				}
				Reaction r = reactions.get(m.typ);
				long factor = m.count / r.result.count;
				long fraction = m.count % r.result.count;
				if (fraction > 0) {
					factor++;
					long n = r.result.count - fraction;
					if (o == null) {
						onStock.put(m.typ, new Material(n, m.typ));
					} else {
						o.count += n;
					}
				}
				for (Material m2 : r.input) {
					required.add(new Material(m2.count * factor, m2.typ));
				}
			}
		}
		return oreUsed;
	}

	static long process2(Map<String, Reaction> reactions, List<Material> required, Map<String, Material> onStock) {
		for (Material o : onStock.values()) {
			System.out.println("" + o.count + " " + o.typ);
		}
		long fuelProduced = 0;
		while (!required.isEmpty()) {
			Material m = required.remove(0);

			Material o = onStock.get(m.typ);
			if (o != null) {
				if (m.count > o.count) {
					m.count -= o.count;
					o.count = 0;
				} else {
					o.count -= m.count;
					continue;
				}
			}
			Reaction r = reactions.get(m.typ);
			long factor = m.count / r.result.count;
			long fraction = m.count % r.result.count;
			if (fraction > 0) {
				factor++;
				long n = r.result.count - fraction;
				if (o == null) {
					onStock.put(m.typ, new Material(n, m.typ));
				} else {
					o.count += n;
				}
			}
			for (Material m2 : r.input) {
				required.add(new Material(m2.count * factor, m2.typ));
			}
		}

		return fuelProduced;
	}

	static class Reaction {

		List<Material> input;

		Material result;

		Reaction(String data) {
			int k = data.indexOf("=>");
			input = Arrays.stream(data.substring(0, k).split(",")).map(s -> new Material(s)).collect(Collectors.toList());
			result = new Material(data.substring(k + 2));
		}
	}

	static class Material {
		long count;
		String typ;

		Material(String m) {
			String[] p = m.trim().split(" ");
			count = Long.parseLong(p[0]);
			typ = p[1];
		}

		public Material(long count, String typ) {
			this.count = count;
			this.typ = typ;
		}

		Material(Material m) {
			this.count = m.count;
			this.typ = m.typ;
		}
	}
}
