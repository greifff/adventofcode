package adventofcode.aoc2019;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day12 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day12/day12.test1");
		List<String> test2 = IOUtil.readFile("2019/day12/day12.test2");
		List<String> data = IOUtil.readFile("2019/day12/day12.data");

		// part1(test1, 10);
		// part1(test2, 100);
		// part1(data, 1000);

		part2(test1);
		part2(test2);
		part2(data);
	}

	static void part1(List<String> data, int turns) {
		List<Moon> moons = data.stream().map(s -> new Moon(s)).collect(Collectors.toList());

		for (int t = 0; t < turns; t++) {
			System.out.println("after " + t);
			moons.forEach(m -> System.out.println("" + m));

			for (int i = 0; i < moons.size() - 1; i++) {
				Moon m1 = moons.get(i);
				for (int j = i + 1; j < moons.size(); j++) {
					Moon m2 = moons.get(j);
					Moon.applyGravity(m1, m2);
				}
			}

			moons.forEach(Moon::move);

		}
		System.out.println("after " + turns);
		moons.forEach(m -> System.out.println("" + m));

		System.out.println("part1: " + moons.stream().mapToInt(Moon::energy).sum());
	}

	static void part2(List<String> data) {
		for (int i = 0; i < 3; i++) {
			getPeriodOfDimension(data, i);
		}
	}

	/*-
	 Test1: 18 | 28 | 44 = 2*3² | 2²*7 | 2²*11 => 2²*3²*7*11 = 2772
	 
	 Test2: 2028 | 5898 | 4702 = 2²*3*13² | 2*3*983 | 2*2351 => 2²*3*13²*983*2351 = 4.686.774.924
	 
	 Data: 186028 | 56344 | 231614 = 2²*46507 | 2³*7043 | 2*115807 => 2³ * 7043 * 46507 * 115807 
	 = 303.459.551.979.256           answer=       303459551979256
	 */

	static long getPeriodOfDimension(List<String> data, int dimension) {
		List<Moon> moons = data.stream().map(s -> new Moon(s)).collect(Collectors.toList());

		Map<String, Long> lookup = new HashMap<>();

		long steps = 0;
		while (true) {

			Long old = lookup.put(moons.stream().map(m -> "" + m.p[dimension] + " " + m.v[dimension] + "\n").reduce((a, b) -> a + b).orElse(""),
					steps);
			if (old != null) {
				System.out.println("" + dimension + ": " + old + "==" + steps);
				break;
			}

			for (int i = 0; i < moons.size() - 1; i++) {
				Moon m1 = moons.get(i);
				for (int j = i + 1; j < moons.size(); j++) {
					Moon m2 = moons.get(j);
					Moon.applyGravity(m1, m2);
				}
			}

			moons.forEach(Moon::move);
			steps++;

		}
		return steps;
	}

	static class Moon {
		int[] p = { 0, 0, 0 };
		int[] v = { 0, 0, 0 };

		// constructor - position data
		Moon(String data) {
			StringTokenizer st = new StringTokenizer(data, "<>xyz=, ");
			p[0] = Integer.parseInt(st.nextToken());
			p[1] = Integer.parseInt(st.nextToken());
			p[2] = Integer.parseInt(st.nextToken());
		}

		void move() {
			for (int axis = 0; axis < 3; axis++) {
				p[axis] = p[axis] + v[axis];
			}
		}

		int energy() {
			int potE = Arrays.stream(p).map(Math::abs).sum();
			int kinE = Arrays.stream(v).map(Math::abs).sum();
			return potE * kinE;
		}

		@Override
		public String toString() {
			return "pos=<x=" + p[0] + ", y=" + p[1] + ", z=" + p[2] + ">, vel=<x=" + v[0] + ", y=" + v[1] + ", z=" + v[2] + ">";
		}

		static void applyGravity(Moon moon1, Moon moon2) {
			for (int axis = 0; axis < 3; axis++) {
				int delta = moon1.p[axis] - moon2.p[axis];

				int d = (int) Math.signum(delta);

				moon1.v[axis] = moon1.v[axis] - d;
				moon2.v[axis] = moon2.v[axis] + d;
			}
		}

		long identifier() {
			return p[0] + p[1] << 8 + p[2] << 16 + (long) v[0] << 24 + (long) v[1] << 32 + (long) v[2] << 40;
		}
	}

}
