package adventofcode.aoc2018.day24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Force {
	String name;
	int strength;
	int hitpoints;
	Set<Element> weakness;
	Set<Element> immunity;

	int damagePoints;
	Element damageType;

	int initiative;

	Force selectedTarget;

	Force(String name, int strength, int hitpoints, Set<Element> weakness, Set<Element> immunity, int damagePoints, Element damageType,
			int initiative) {
		this.name = name;
		this.strength = strength;
		this.hitpoints = hitpoints;
		this.weakness = weakness;
		this.immunity = immunity;
		this.damagePoints = damagePoints;
		this.damageType = damageType;
		this.initiative = initiative;
	}

	void takeDamage(Element element, int damage) {
		if (immunity.contains(element))
			return;
		int dmg = damage;
		if (weakness.contains(element))
			dmg *= 2;
		int killed = dmg / hitpoints;
		strength = Math.max(0, strength - killed);
		// System.out.println(" " + dmg + " " + killed);
	}

	void select(List<Force> forces) {

		List<Force> twice = new ArrayList<>();
		List<Force> normal = new ArrayList<>();
		List<Force> immune = new ArrayList<>();

		for (Force f : forces) {
			if (f.name.charAt(0) != name.charAt(0)) {
				if (f.immunity.contains(damageType)) {
					immune.add(f);
				} else if (f.weakness.contains(damageType)) {
					twice.add(f);
				} else {
					normal.add(f);
				}
			}
		}

		if (!twice.isEmpty()) {
			selectByEffectivePower(twice);
			forces.remove(selectedTarget);
		} else if (!normal.isEmpty()) {
			selectByEffectivePower(normal);
			forces.remove(selectedTarget);
		} else {
			selectedTarget = null;
		}

	}

	private void selectByEffectivePower(List<Force> enemies) {
		Collections.sort(enemies, (e1, e2) -> e1.initiative - e2.initiative);
		Collections.sort(enemies, (e1, e2) -> e1.effectivePower() - e2.effectivePower());
		Collections.reverse(enemies);
		selectedTarget = enemies.get(0);
	}

	void attack() {
		if (selectedTarget != null) {
			// System.out.print(name + " -> " + selectedTarget.name);
			selectedTarget.takeDamage(damageType, effectivePower());
		}
	}

	int effectivePower() {
		return damagePoints * strength;
	}

}
