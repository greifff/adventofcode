package adventofcode.aoc2018.day23;

import java.util.HashSet;
import java.util.Set;

public class Area {

	Set<Spot> spots = new HashSet<>();

	Set<Integer> coveredBy = new HashSet<>();

	Area(Nanobot a, Nanobot b, int idA, int idB) {
		coveredBy.add(idA);
		coveredBy.add(idB);

		spots.addAll(a.intersection(b));
	}

	Area(Area a, Area b) {
		coveredBy.addAll(a.coveredBy);
		coveredBy.addAll(b.coveredBy);

		spots.addAll(a.spots);
		spots.retainAll(b.spots);
	}

	@Override
	public String toString() {
		String x1 = coveredBy.stream().map(i -> "" + i).reduce((a, b) -> a + "," + b).orElse("");
		String x2 = spots.stream().map(s -> s.toString()).reduce((c, d) -> c + "," + d).orElse("");
		return "[" + x1 + "] {" + x2 + "}";
	}

	boolean equalCoverage(Area other) {
		return coveredBy.containsAll(other.coveredBy);
	}
}
