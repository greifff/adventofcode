package adventofcode.aoc2018.day25;

import java.util.HashSet;
import java.util.Set;

public class Star {

	int x;
	int y;
	int z;
	int t;

	Set<Star> neighbors = new HashSet<>();

	Star(String data) {
		String[] parts = data.split(",");
		x = Integer.parseInt(parts[0]);
		y = Integer.parseInt(parts[1]);
		z = Integer.parseInt(parts[2]);
		t = Integer.parseInt(parts[3]);
	}

	int distance(Star o) {
		return Math.abs(x - o.x) + Math.abs(y - o.y) + Math.abs(z - o.z) + Math.abs(t - o.t);
	}
}
