package adventofcode.aoc2018.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day15 {
	public static void main(String[] args) {
		// elfPowerBoost = 12;
		// List<String> test1 = IOUtil.readFile("2018/day15/day15.test1");
		// List<Combatant> test1Combatants = parseCombatants(test1);
		// List<String> test1Field = parseField(test1);
		// part1(test1Combatants, test1Field);

		// List<String> test2 = IOUtil.readFile("2018/day15/day15.test2");
		// List<Combatant> test2Combatants = parseCombatants(test2);
		// List<String> test2Field = parseField(test2);
		// part1(test2Combatants, test2Field);

		// elfPowerBoost = 1;
		// List<String> test3 = IOUtil.readFile("2018/day15/day15.test3");
		// List<Combatant> test3Combatants = parseCombatants(test3);
		// List<String> test3Field = parseField(test3);
		// part1(test3Combatants, test3Field);

		// elfPowerBoost = 12;
		// List<String> test4 = IOUtil.readFile("2018/day15/day15.test4");
		// List<Combatant> test4Combatants = parseCombatants(test4);
		// List<String> test4Field = parseField(test4);
		// part1(test4Combatants, test4Field);
		//
		// elfPowerBoost = 9;
		// List<String> test5 = IOUtil.readFile("2018/day15/day15.test5");
		// List<Combatant> test5Combatants = parseCombatants(test5);
		// List<String> test5Field = parseField(test5);
		// part1(test5Combatants, test5Field);

		// elfPowerBoost = 31;
		// List<String> test6 = IOUtil.readFile("2018/day15/day15.test6");
		// List<Combatant> test6Combatants = parseCombatants(test6);
		// List<String> test6Field = parseField(test6);
		// part1(test6Combatants, test6Field);

		elfPowerBoost = 17;
		List<String> data = IOUtil.readFile("2018/day15/day15.data");
		List<Combatant> dataCombatants = parseCombatants(data);
		List<String> dataField = parseField(data);
		part1(dataCombatants, dataField);
	}

	// 22 -> 41804 is rated as too low
	static int elfPowerBoost = 21;

	static boolean debug = false;

	private static List<String> parseField(List<String> data) {
		return data.stream().map(s -> s.replace('E', '.').replace('G', '.').replace('x', '#')).collect(Collectors.toList());
	}

	private static List<Combatant> parseCombatants(List<String> s) {
		List<Combatant> combatants = new ArrayList<>();
		for (int y = 0; y < s.size(); y++) {
			String line = s.get(y);
			for (int x = 0; x < line.length(); x++) {
				switch (line.charAt(x)) {
				case 'E':
					combatants.add(new Combatant(x, y, true));
					break;
				case 'G':
					combatants.add(new Combatant(x, y, false));
					break;
				}
			}
		}
		return combatants;
	}

	private static void part1(List<Combatant> combatants1, List<String> field) {
		// for (String l : field) {
		// System.out.println(l);
		// }

		List<Combatant> combatants = new ArrayList<>(combatants1);
		long rounds = 0;
		boolean battleContinues = true;
		boolean somebodyHasMovedLastRound = true;
		outer: while (battleContinues) {
			printMapWithCombatants(combatants, field);
			printStatus(rounds, combatants);
			boolean somebodyHasMovedThisRound = false;
			for (Combatant c : combatants) {

				if (c.hp <= 0) {
					continue;
				}

				List<Combatant> enemies = combatants.stream().filter(c2 -> c2.hp > 0 && c2.elf != c.elf).collect(Collectors.toList());
				if (enemies.isEmpty()) {
					break outer;
				}

				// check if in string range with target

				// if (somebodyHasMovedLastRound) {
				List<Combatant> inRange = c.inRange(enemies);
				if (inRange.isEmpty()) {
					somebodyHasMovedThisRound |= c.move(combatants, enemies, field);
				}
				// }
				c.attack(enemies);

			}
			rounds++;

			int combatantsEntering = combatants.size();
			combatants = combatants.stream().filter(c -> c.hp > 0).collect(Collectors.toList());
			somebodyHasMovedLastRound = somebodyHasMovedThisRound || (combatantsEntering > combatants.size());
			Collections.sort(combatants);
		}
		combatants = combatants.stream().filter(c -> c.hp > 0).collect(Collectors.toList());
		printStatus(rounds, combatants);

		System.out.println("# Survivors: " + combatants.size());
		int totalHitpointsRemaining = combatants.stream().map(c -> c.hp).reduce((h1, h2) -> h1 + h2).orElse(0);
		System.out.println("part1: " + rounds + " " + totalHitpointsRemaining + " -> " + (rounds * totalHitpointsRemaining));
	}

	private static void printMapWithCombatants(List<Combatant> combatants, List<String> field) {

		for (int y = 0; y < field.size(); y++) {
			String row = field.get(y);
			for (int x = 0; x < row.length(); x++) {
				Combatant c1 = null;
				for (Combatant c : combatants) {
					if (c.position.x == x && c.position.y == y) {
						c1 = c;
						break;
					}
				}
				if (c1 != null) {
					System.out.print(c1.elf ? 'E' : 'G');
				} else {
					System.out.print(row.charAt(x));
				}
			}
			System.out.println();
		}
	}

	private static void printStatus(long rounds, List<Combatant> combatants) {
		System.out.print("? " + rounds + ": ");
		for (Combatant c : combatants) {
			System.out.print((c.elf ? "E" : "G") + "(" + c.position.x + "," + c.position.y + "," + c.hp + ") ");
		}
		System.out.println();
	}
}
