package adventofcode.aoc2018.day24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day24 {

	public static void main(String[] args) {

		part1(Test1.forces);
		part1(Data.forces);
	}

	private static void part1(List<Force> forces1) {
		List<Force> forces = new ArrayList<Force>(forces1);
		boolean battleContinues = true;
		while (battleContinues) {
			Collections.sort(forces, (e1, e2) -> e1.initiative - e2.initiative);
			Collections.sort(forces, (e1, e2) -> e1.effectivePower() - e2.effectivePower());
			Collections.reverse(forces);

			List<Force> selectable = new ArrayList<Force>(forces);
			for (Force f : forces) {
				f.select(selectable);
			}
			Collections.sort(forces, (e1, e2) -> e1.initiative - e2.initiative);
			Collections.reverse(forces);

			for (Force f : forces) {
				f.attack();
			}

			forces = forces.stream().filter(f -> f.strength > 0).collect(Collectors.toList());
			battleContinues = forces.stream().map(f -> f.name.charAt(0)).collect(Collectors.toSet()).size() > 1;

			// for (Force f : forces) {
			// System.out.println(f.name + " " + f.strength);
			// }
			// System.out.println();
		}
		for (Force f : forces) {
			System.out.println(f.name + " " + f.strength);
		}
		System.out.println("part1: " + forces.stream().map(f -> f.strength).reduce((a, b) -> a + b).orElse(0));
	}

}
