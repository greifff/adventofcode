package adventofcode.aoc2018.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Combatant implements Comparable<Combatant> {

	int dmg = 3;
	int hp = 200;
	final boolean elf;
	Point position;

	Combatant(int x, int y, boolean elf) {
		position = Point.getPoint(x, y);
		this.elf = elf;
	}

	@Override
	public int compareTo(Combatant o) {
		return position.compareTo(o.position);
	}

	List<Combatant> inRange(List<Combatant> enemies) {
		Set<Point> adjacent = position.getAdjacent();
		return enemies.stream().filter(e -> adjacent.contains(e.position)).collect(Collectors.toList());
	}

	void attack(List<Combatant> enemies) {
		List<Combatant> inRange = inRange(enemies);
		if (!inRange.isEmpty()) {
			Collections.sort(inRange, (e1, e2) -> e1.position.x + 1000 * e1.position.y - (e2.position.x + 1000 * e2.position.y));
			Collections.sort(inRange, (e1, e2) -> e1.hp - e2.hp);
			inRange.get(0).hp -= dmg + (elf ? Day15.elfPowerBoost : 0);
		}
	}

	private static Set<Point> getFreePointsAdjacentToTarget(List<Combatant> combatants, List<Combatant> enemies, final List<String> field) {
		Set<Point> adjacentToTargets = enemies.stream().filter(c -> c.hp > 0).flatMap(e -> e.position.getAdjacent().stream())
				.filter(c -> c.isWalkable(field)).collect(Collectors.toSet());
		// eliminate all positions occupied by combatants
		Set<Point> combatantPoints = combatants.stream().filter(c -> c.hp > 0).map(c -> c.position).collect(Collectors.toSet());
		adjacentToTargets.removeAll(combatantPoints);
		return adjacentToTargets;
	}

	boolean move(List<Combatant> combatants, List<Combatant> enemies, final List<String> field) {
		final Set<Point> visited = new HashSet<>();
		// start point
		visited.add(position);

		// cannot move through combatants
		visited.addAll(combatants.stream().filter(c -> c.hp > 0).map(c -> c.position).collect(Collectors.toList()));

		Set<Point> destinations = getFreePointsAdjacentToTarget(combatants, enemies, field);
		if (destinations.isEmpty()) {
			// no free slots on enemies - not moving
			return false;
		}

		if (Day15.debug)
			System.out.println("" + (elf ? 'E' : 'G') + '(' + position.x + ',' + position.y + ')');

		// find path

		List<Path> current = position.getAdjacent().stream().map(p -> new Path(p)).collect(Collectors.toList());

		// filter for walkability
		current = current.stream().filter(p -> p.currentTrace.isWalkable(field)).filter(p -> !visited.contains(p.currentTrace))
				.collect(Collectors.toList());

		Set<Path> targets = new HashSet<>();
		int it = 0;
		do {
			it++;
			// mark current tracing locations as visited
			current.forEach(c -> visited.add(c.currentTrace));

			// determine whether there are reached targets
			targets = current.stream().filter(p -> p.isOnTarget(destinations)).collect(Collectors.toSet());
			if (!targets.isEmpty()) {
				break;
			}

			current = new ArrayList<>(current.stream().flatMap(p -> p.forward(visited, field).stream()).collect(Collectors.toList()));

			// TODO filter equivalent paths
			Collections.sort(current, (p1, p2) -> p1.currentTrace.compareTo(p2.currentTrace));
			Collections.sort(current, (p1, p2) -> p1.step1.compareTo(p2.step1));
			int k = 1;
			while (k < current.size()) {
				Path p1 = current.get(k - 1);
				Path p2 = current.get(k);
				if (p1.currentTrace.compareTo(p2.currentTrace) == 0) {
					current.remove(k);
				} else {
					k++;
				}
			}

			// System.out.println(" " + current.size());
		} while (!current.isEmpty());
		// System.out.println("+ (" + x + "," + y + ") " + it + " " + targets.size() + " " + current.size());
		if (Day15.debug)
			System.out.println("    " + it + " " + targets.size());
		if (!targets.isEmpty()) {
			List<Path> reachableTargets = new ArrayList<>(targets);
			// sort by first step
			Collections.sort(reachableTargets, (p1, p2) -> p1.step1.x - p2.step1.x + 1000 * (p1.step1.y - p2.step1.y));
			// sort by target
			Collections.sort(reachableTargets, (p1, p2) -> p1.currentTrace.x - p2.currentTrace.x + 1000 * (p1.currentTrace.y - p2.currentTrace.y));

			position = reachableTargets.get(0).step1;
			return true;
		}
		return false;
	}

}
