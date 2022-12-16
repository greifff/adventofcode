package adventofcode.aoc2018.day24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Test1 {

	final static List<Force> forces = new ArrayList<>();

	private static final int boost = 1570;

	static {
		forces.add(new Force("I-11", 17, 5390, new HashSet<>(Arrays.asList(Element.RADIATION, Element.BLUDGEONING)), new HashSet<>(), 4507 + boost,
				Element.FIRE, 2));
		forces.add(new Force("I-12", 989, 1274, new HashSet<>(Arrays.asList(Element.BLUDGEONING, Element.SLASHING)),
				new HashSet<>(Arrays.asList(Element.FIRE)), 25 + boost, Element.SLASHING, 3));

		forces.add(new Force("V-11", 801, 4706, new HashSet<>(Arrays.asList(Element.RADIATION)), new HashSet<>(), 116, Element.BLUDGEONING, 1));
		forces.add(new Force("V-12", 4485, 2961, new HashSet<>(Arrays.asList(Element.FIRE, Element.COLD)),
				new HashSet<>(Arrays.asList(Element.RADIATION)), 12, Element.SLASHING, 4));

	}
}
