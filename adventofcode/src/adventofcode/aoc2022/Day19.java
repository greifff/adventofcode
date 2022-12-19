package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import adventofcode.util.IOUtil;

/**
 * This needs a ton of RAM (I used -Xmx24g).
 * 
 * BFS with Pruning
 */
public class Day19 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day19.test");
		List<String> input = IOUtil.readFile("2022/day19.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		long start = System.currentTimeMillis();

		List<Blueprint> blueprints = input.stream().map(s -> new Blueprint(s)).toList();

		System.out.println("# " + blueprints.size());
		int sum = 0;
		for (Blueprint b : blueprints) {

			int geo = b.geodesOpened(24);
			System.out.println("****** " + b.stats[0] + "  " + geo);
			sum += b.stats[0] * geo;
		}

		// 775, 1159 too low
		System.out.println(MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start, sum));
	}

	private static void part2(List<String> input) {
		long start = System.currentTimeMillis();

		List<Blueprint> blueprints = input.stream().map(s -> new Blueprint(s)).toList();

		System.out.println("# " + blueprints.size());
		int product = 1;

		for (int i = 0; i < Math.min(3, blueprints.size()); i++) {
			Blueprint b = blueprints.get(i);

			int geo = b.geodesOpened(32);
			System.out.println("****** " + b.stats[0] + "  " + geo);
			product *= geo;
		}

		System.out.println(MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start, product));
	}

	static class Blueprint {

		/*
		 * int id; int oreRobotOreCost; int clayRobotOreCost; int obsidianRobotOreCost;
		 * int obsidianRobotClayCost; int geodeRobotOreCost; int geodeRobotObsidianCost;
		 */
		int[] stats = new int[7];

		Blueprint(String line) {
			StringTokenizer st = new StringTokenizer(line, " .:");
			int index = 0;
			while (index < 7 && st.hasMoreTokens()) {
				try {
					int k = Integer.parseInt(st.nextToken());
					stats[index] = k;
					index++;
				} catch (NumberFormatException e) {
//
				}
			}
		}

		public int geodesOpened(int maxTime) {
			List<Solution> solutions = new LinkedList<>();
			solutions.add(new Solution(this));

			int maxGeodes = 0;

			while (!solutions.isEmpty()) {
				List<Solution> s2 = new LinkedList<>();
				for (Solution s : solutions) {
					if (s.time == maxTime) {
						maxGeodes = Math.max(maxGeodes, s.ores[3]);
					} else {
						s2.addAll(s.step(maxTime));
					}
				}
				solutions = s2;
				int topScore = solutions.stream().map(s -> s.score()).reduce(Math::max).orElse(0);

				// pruning, as suggested by UncleSnail
				solutions = solutions.stream().filter(s -> s.score() + 50 >= topScore).toList();
			}
			return maxGeodes;
		}

	}

	static class Solution {

		// ore,clay, obsidian, geode

		int[] robots = new int[] { 1, 0, 0, 0 };
		int[] ores = new int[] { 0, 0, 0, 0 };
		int[] oresMined = new int[] { 0, 0, 0, 0 };

		int nextToBuild = -1; // -1 meaning any

		Blueprint blueprint;

		int time = 0;

		Solution(Blueprint blueprint) {
			this.blueprint = blueprint;
		}

		Solution(Solution other) {
			nextToBuild = other.nextToBuild;
			blueprint = other.blueprint;
			System.arraycopy(other.robots, 0, robots, 0, 4);
			System.arraycopy(other.ores, 0, ores, 0, 4);

			time = other.time + 1;
			for (int i = 0; i < 4; i++) {
				ores[i] = ores[i] + robots[i];
				oresMined[i] = oresMined[i] + robots[i];
			}
		}

		int score() {
			// pruning, as suggested by UncleSnail
			return oresMined[0] + oresMined[1] * 4 + oresMined[2] * 9 + oresMined[3] * 16;
		}

		List<Solution> step(int maxTime) {

			List<Solution> s = new ArrayList<>();
			if (time == maxTime - 1) {
				s.add(new Solution(this));
				return s;
			}

//			int buildVariations = 0; // we always want more geode crackers

//			if (robots[2] > 0) {
//			buildVariations++;
			if (robots[2] > 0 && (nextToBuild == -1 || nextToBuild == 3)) {
				Solution s1 = new Solution(this);
				s.add(s1);
				if (blueprint.stats[5] <= ores[0] && blueprint.stats[6] <= ores[2]) {
					s1.ores[0] = s1.ores[0] - blueprint.stats[5];
					s1.ores[2] = s1.ores[2] - blueprint.stats[6];
					s1.robots[3] = s1.robots[3] + 1;
					s1.nextToBuild = -1;
					return s;
				} else {
					s1.nextToBuild = 3;
				}
			}

			if (robots[1] > 0 && (nextToBuild == -1 || nextToBuild == 2) && time <= maxTime - 3) {
				Solution s1 = new Solution(this);
				s.add(s1);
				if (robots[2] < blueprint.stats[6]) {// obsidian
//					buildVariations++;
					if (blueprint.stats[3] <= ores[0] && blueprint.stats[4] <= ores[1]) {
						s1.ores[0] = s1.ores[0] - blueprint.stats[3];
						s1.ores[1] = s1.ores[1] - blueprint.stats[4];
						s1.robots[2] = s1.robots[2] + 1;
						s1.nextToBuild = -1;
					}
				} else {
					s1.nextToBuild = 2;
				}
			}
			if ((nextToBuild == -1 || nextToBuild == 1) && time <= maxTime - 5) {
				Solution s1 = new Solution(this);
				s.add(s1);
				if (robots[1] < blueprint.stats[4]) { // clay
//					buildVariations++;
					if (blueprint.stats[2] <= ores[0]) {
						s1.ores[0] = s1.ores[0] - blueprint.stats[2];
						s1.robots[1] = s1.robots[1] + 1;
						s1.nextToBuild = -1;
					}
				} else {
					s1.nextToBuild = 1;
				}
			}
			if ((time > maxTime - 5 && robots[1] == 0) || time > maxTime - 3 && robots[2] == 0) {
				return Collections.emptyList();
			}

			int usableOreBots = Math.max(Math.max(blueprint.stats[1], blueprint.stats[2]),
					Math.max(blueprint.stats[3], blueprint.stats[5]));
			if (nextToBuild < 1 && robots[0] < usableOreBots) {
//				buildVariations++;
				Solution s1 = new Solution(this);
				s.add(s1);
				if (blueprint.stats[1] <= ores[0]) {
					s1.ores[0] = s1.ores[0] - blueprint.stats[1];
					s1.robots[0] = s1.robots[0] + 1;
					s1.nextToBuild = -1;
				} else {
					s1.nextToBuild = 0;
				}
			}
//			if (buildVariations > s.size()) {
//				s.add(new Solution(this)); // dont build
//			}

			return s;
		}

	}
}
