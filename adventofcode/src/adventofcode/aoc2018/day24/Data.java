package adventofcode.aoc2018.day24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Data {

	final static List<Force> forces = new ArrayList<>();

	// boost 61 - Immune system wins; 58 - virus wins; 59, 60 - stalemate?
	private static final int boost = 61;

	static {

		forces.add(new Force("I-01", 1193, 4200, new HashSet<>(), new HashSet<>(Arrays.asList(Element.SLASHING, Element.RADIATION, Element.FIRE)),
				33 + boost, Element.BLUDGEONING, 2));
		forces.add(new Force("I-02", 151, 9047, new HashSet<>(Arrays.asList(Element.FIRE)),
				new HashSet<>(Arrays.asList(Element.SLASHING, Element.COLD)), 525 + boost, Element.SLASHING, 1));
		forces.add(new Force("I-03", 218, 4056, new HashSet<>(Arrays.asList(Element.RADIATION)),
				new HashSet<>(Arrays.asList(Element.SLASHING, Element.FIRE)), 176 + boost, Element.FIRE, 9));
		forces.add(new Force("I-04", 5066, 4687, new HashSet<>(Arrays.asList(Element.SLASHING, Element.FIRE)), new HashSet<>(), 8 + boost,
				Element.SLASHING, 8));
		forces.add(new Force("I-05", 2023, 5427, new HashSet<>(Arrays.asList(Element.SLASHING)), new HashSet<>(), 22 + boost, Element.SLASHING, 3));
		forces.add(new Force("I-06", 3427, 5532, new HashSet<>(Arrays.asList(Element.SLASHING)), new HashSet<>(), 15 + boost, Element.COLD, 13));
		forces.add(new Force("I-07", 1524, 8584, new HashSet<>(), new HashSet<>(Arrays.asList(Element.FIRE)), 49 + boost, Element.FIRE, 7));
		forces.add(new Force("I-08", 82, 2975, new HashSet<>(Arrays.asList(Element.COLD, Element.FIRE)), new HashSet<>(), 359 + boost,
				Element.BLUDGEONING, 5));
		forces.add(new Force("I-09", 5628, 9925, new HashSet<>(Arrays.asList(Element.FIRE)), new HashSet<>(Arrays.asList(Element.COLD)), 17 + boost,
				Element.RADIATION, 11));
		forces.add(new Force("I-10", 1410, 9872, new HashSet<>(Arrays.asList(Element.COLD)), new HashSet<>(Arrays.asList(Element.FIRE)), 60 + boost,
				Element.SLASHING, 10));

		forces.add(new Force("V-01", 5184, 12832, new HashSet<>(Arrays.asList(Element.COLD, Element.FIRE)), new HashSet<>(), 4, Element.FIRE, 20));
		forces.add(new Force("V-02", 2267, 13159, new HashSet<>(Arrays.asList(Element.FIRE)), new HashSet<>(Arrays.asList(Element.BLUDGEONING)), 10,
				Element.FIRE, 4));
		forces.add(new Force("V-03", 3927, 50031, new HashSet<>(Arrays.asList(Element.SLASHING, Element.COLD)),
				new HashSet<>(Arrays.asList(Element.FIRE, Element.RADIATION)), 23, Element.COLD, 17));
		forces.add(new Force("V-04", 9435, 23735, new HashSet<>(), new HashSet<>(Arrays.asList(Element.COLD)), 4, Element.COLD, 12));
		forces.add(new Force("V-05", 3263, 26487, new HashSet<>(Arrays.asList(Element.FIRE)), new HashSet<>(), 11, Element.FIRE, 14));
		forces.add(new Force("V-06", 222, 15916, new HashSet<>(Arrays.asList(Element.FIRE)), new HashSet<>(), 135, Element.FIRE, 18));
		forces.add(new Force("V-07", 972, 45332, new HashSet<>(Arrays.asList(Element.BLUDGEONING, Element.SLASHING)), new HashSet<>(), 86,
				Element.COLD, 19));
		forces.add(new Force("V-08", 1456, 39583, new HashSet<>(Arrays.asList(Element.FIRE, Element.COLD)),
				new HashSet<>(Arrays.asList(Element.RADIATION)), 53, Element.BLUDGEONING, 16));
		forces.add(new Force("V-09", 2813, 28251, new HashSet<>(), new HashSet<>(), 17, Element.COLD, 15));
		forces.add(
				new Force("V-10", 1179, 42431, new HashSet<>(), new HashSet<>(Arrays.asList(Element.SLASHING, Element.FIRE)), 55, Element.FIRE, 6));

	}
}
