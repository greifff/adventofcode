package adventofcode.aoc2021.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class ActivationStep {
	boolean turnOn;
	int[] coordinates = new int[6];

	ActivationStep(String line) {
		turnOn = line.startsWith("on");
		StringTokenizer st = new StringTokenizer(line, "onf xyz=.,");
		for (int i = 0; i < 6; i++) {
			coordinates[i] = Integer.parseInt(st.nextToken());
		}
	}

	public ActivationStep(int[] coords2, boolean turnOn2) {
		coordinates = coords2;
		turnOn = turnOn2;
	}

	boolean overlapsWithActivationRegion() {
		boolean result = true;
		for (int i = 0; i < 6; i += 2) {
			result &= coordinates[i] < 50 && coordinates[i + 1] > -50;
		}
		return result;
	}

	boolean covers(int x, int y, int z) {
		return coordinates[0] <= x && x <= coordinates[1] && coordinates[2] <= y && y <= coordinates[3] && coordinates[4] <= z && z <= coordinates[5];
	}

	int[] activationRegionCoordinates() {
		int[] reduced = new int[6];
		for (int i = 0; i < 6; i += 2) {
			reduced[i] = Math.max(-50, coordinates[i]);
			reduced[i + 1] = Math.min(50, coordinates[i + 1]);
		}
		return reduced;
	}

	boolean intersectedBy(ActivationStep other) {
		boolean intersectX = coordinates[0] <= other.coordinates[1] && other.coordinates[0] <= coordinates[1];
		boolean intersectY = coordinates[2] <= other.coordinates[3] && other.coordinates[2] <= coordinates[3];
		boolean intersectZ = coordinates[4] <= other.coordinates[5] && other.coordinates[4] <= coordinates[5];
		return intersectX && intersectY && intersectZ;
	}

	boolean contains(ActivationStep other) {
		boolean containsX = coordinates[0] <= other.coordinates[0] && other.coordinates[1] <= coordinates[1];
		boolean containsY = coordinates[2] <= other.coordinates[2] && other.coordinates[3] <= coordinates[3];
		boolean containsZ = coordinates[4] <= other.coordinates[4] && other.coordinates[5] <= coordinates[5];
		return containsX && containsY && containsZ;
	}

	List<ActivationStep> splitBy(ActivationStep other) {
		List<ActivationStep> outer = new ArrayList<>();
		for (int i = 0; i < 6; i += 2) {
			if (coordinates[i] < other.coordinates[i]) {
				// "left" part
				int[] coords2 = new int[6];
				System.arraycopy(coordinates, 0, coords2, 0, 6);
				coords2[i + 1] = other.coordinates[i] - 1;
				coordinates[i] = other.coordinates[i];
				outer.add(new ActivationStep(coords2, turnOn));
			}
			if (coordinates[i + 1] > other.coordinates[i + 1]) {
				// "right" part
				int[] coords2 = new int[6];
				System.arraycopy(coordinates, 0, coords2, 0, 6);
				coords2[i] = other.coordinates[i + 1] + 1;
				coordinates[i + 1] = other.coordinates[i + 1];
				outer.add(new ActivationStep(coords2, turnOn));
			}
		}
		// remainder is part of other, thus irrelevant
		return outer;
	}

	long volume() {
		long dx = coordinates[1] - coordinates[0] + 1;
		long dy = coordinates[3] - coordinates[2] + 1;
		long dz = coordinates[5] - coordinates[4] + 1;
		return dx * dy * dz;
	}
}