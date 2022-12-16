package adventofcode.aoc2018.day23;

import java.util.HashSet;
import java.util.Set;

public class CoveredSpace {

	int[] coordinates = new int[6];
	Set<Integer> coveredBy = new HashSet<>();

	CoveredSpace(Nanobot n, int id) {
		coveredBy.add(id);
		coordinates[0] = n.x - n.range;
		coordinates[1] = n.x + n.range;
		coordinates[2] = n.y - n.range;
		coordinates[3] = n.y + n.range;
		coordinates[4] = n.z - n.range;
		coordinates[5] = n.z + n.range;
	}

	public CoveredSpace() {
		//
	}

	boolean intersects(CoveredSpace other) {
		boolean intersectX = coordinates[0] <= other.coordinates[1] && other.coordinates[0] <= coordinates[1];
		boolean intersectY = coordinates[2] <= other.coordinates[3] && other.coordinates[2] <= coordinates[3];
		boolean intersectZ = coordinates[4] <= other.coordinates[5] && other.coordinates[4] <= coordinates[5];
		return intersectX && intersectY && intersectZ;
	}

	CoveredSpace intersect(CoveredSpace other) {
		CoveredSpace cs = new CoveredSpace();

		cs.coveredBy.addAll(coveredBy);
		cs.coveredBy.addAll(other.coveredBy);

		for (int i = 0; i < 6; i += 2) {
			cs.coordinates[i] = Math.max(coordinates[i], other.coordinates[i]);
			cs.coordinates[i + 1] = Math.min(coordinates[i + 1], other.coordinates[i + 1]);
		}

		return cs;
	}
}
