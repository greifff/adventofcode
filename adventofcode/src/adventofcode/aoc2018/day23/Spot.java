package adventofcode.aoc2018.day23;

import java.util.List;

public class Spot implements Comparable<Spot> {
	int x;
	int y;
	int z;

	Spot(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	int inRangeOf(List<Nanobot> input) {
		return (int) input.stream().filter(n -> n.range >= distance(n)).count();
	}

	int distance(Nanobot bot) {
		return Math.abs(bot.x - x) + Math.abs(bot.y - y) + Math.abs(bot.z - z);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")[m=" + (Math.abs(x) + Math.abs(y) + Math.abs(z)) + "]";
	}

	int distance() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	@Override
	public int compareTo(Spot o) {
		int delta = x - o.x;
		if (delta != 0)
			return delta;
		delta = y - o.y;
		if (delta != 0)
			return delta;
		return z - o.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Spot) {
			Spot o = (Spot) obj;
			return x == o.x && y == o.y && z == o.z;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x + y + z;
	}
}
