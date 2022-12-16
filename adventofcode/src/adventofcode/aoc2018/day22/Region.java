package adventofcode.aoc2018.day22;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Region {

	int geologicIndex;
	int x;
	int y;
	CaveSystem cs;

	Set<Tool> reachedWith = new HashSet<>();

	Region(int x, int y, CaveSystem cs) {
		this.x = x;
		this.y = y;
		this.cs = cs;
	}

	void updateGeologicIndex() {
		if (x == 0) {
			if (y == 0) {
				geologicIndex = 0;
			} else {
				geologicIndex = y * 7905 % 20183; // 48271 % 20183 = 7905
			}
		} else if (y == 0) {
			geologicIndex = x * 16807 % 20183;
		} else {
			Region left = cs.getRegion(x - 1, y);
			Region up = cs.getRegion(x, y - 1);
			geologicIndex = left.getErosionLevel() * up.getErosionLevel() % 20183;
		}
	}

	int getErosionLevel() {
		return (geologicIndex + cs.depth) % 20183;
	}

	RegionType getType() {
		int i = getErosionLevel() % 3;
		switch (i) {
		case 0:
			return RegionType.ROCKY;
		case 1:
			return RegionType.WET;
		default:
		case 2:
			return RegionType.NARROW;
		}
	}

	List<Region> getNeighbors() {
		return Stream.of(cs.getRegion(x - 1, y), cs.getRegion(x + 1, y), cs.getRegion(x, y - 1), cs.getRegion(x, y + 1)) //
				.filter(r -> r != null).collect(Collectors.toList());
	}
}
