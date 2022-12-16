package adventofcode.aoc2019.day18.retry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MazeParser {

	private Set<Integer> visited = new HashSet<>();
	private List<String> maze;

	private Map<Integer, Location> locations = new HashMap<>();

	private int calculatePosition(int x, int y) {
		return x * 1000 + y;
	}

	private int getPositionX(int pos) {
		return pos / 1000;
	}

	private int getPositionY(int pos) {
		return pos % 1000;
	}

	private void parse1() {
		int maxx = maze.get(0).length();
		int maxy = maze.size();
		for (int y = 1; y < maxy; y++) {
			String line = maze.get(y);
			for (int x = 1; x < maxx; x++) {
				char c = line.charAt(x);
				if ((c >= '@' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
					int pos = calculatePosition(x, y);
					locations.put(pos, new Location(c, pos));
				}
			}
		}
	}

	private void setPossibleExits(Location l) {

		char c = inspectPosition(l.coords + 1);
		if (c != '#') {
			l.paths.put(Direction.EAST, new Path(l, l.coords + 1));
		}
		c = inspectPosition(l.coords - 1);
		if (c != '#') {
			l.paths.put(Direction.WEST, new Path(l, l.coords - 1));
		}
		c = inspectPosition(l.coords - 1000);
		if (c != '#') {
			l.paths.put(Direction.NORTH, new Path(l, l.coords - 1000));
		}
		c = inspectPosition(l.coords + 1000);
		if (c != '#') {
			l.paths.put(Direction.SOUTH, new Path(l, l.coords + -1000));
		}
	}

	private char inspectPosition(int pos) {
		int x = getPositionX(pos);
		int y = getPositionY(pos);
		return maze.get(y).charAt(x);
	}

}
