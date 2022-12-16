package adventofcode.aoc2018.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Nanobot {

	int x;
	int y;
	int z;
	int range;

	List<Nanobot> overlaps = new ArrayList<>();

	Nanobot(String data) {
		StringTokenizer st = new StringTokenizer(data, "pos=<>,r ");
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		z = Integer.parseInt(st.nextToken());
		range = Integer.parseInt(st.nextToken());
	}

	List<Nanobot> inRange(List<Nanobot> input) {
		return input.stream().filter(n -> range >= distance(n)).collect(Collectors.toList());
	}

	int distance(Nanobot bot) {
		return Math.abs(bot.x - x) + Math.abs(bot.y - y) + Math.abs(bot.z - z);
	}

	public boolean intersects(Nanobot b) {
		int d = Math.abs(x - b.x) + Math.abs(y - b.y) + Math.abs(z - b.z);
		return d <= range + b.range;
	}

	public List<Spot> intersection(Nanobot other) {
		List<Spot> result = new ArrayList<>();
		for (int i = 0; i <= range; i++) {
			for (int j = 0; j <= range - i; j++) {
				for (int k = 0; k <= range - i - j; k++) {
					Set<Spot> toCheck = new HashSet<>(Arrays.asList(new Spot(x + i, y + j, z + k), new Spot(x + i, y + j, z - k),
							new Spot(x + i, y - j, z + k), new Spot(x + i, y - j, z - k), new Spot(x - i, y + j, z + k),
							new Spot(x - i, y + j, z - k), new Spot(x - i, y - j, z + k), new Spot(x - i, y - j, z - k)));

					result.addAll(toCheck.stream().filter(spot -> other.checkWithin(spot)).collect(Collectors.toSet()));
				}
			}
		}
		return result;
	}

	public boolean checkWithin(Spot spot) {
		return range >= Math.abs(spot.x - x) + Math.abs(spot.y - y) + Math.abs(spot.z - z);
	}

	public boolean checkWithin(Spot spot, int divisor) {
		int distance = Math.abs(spot.x - x / divisor) + Math.abs(spot.y - y / divisor) + Math.abs(spot.z - z / divisor);

		int rangeModifier = range % divisor > 0 ? 1 : 0;

		return range / divisor + rangeModifier >= distance;
	}
}
