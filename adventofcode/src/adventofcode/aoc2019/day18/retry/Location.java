package adventofcode.aoc2019.day18.retry;

import java.util.HashMap;
import java.util.Map;

public class Location {

	final Character identifier;

	final int coords;

	Map<Direction, Path> paths = new HashMap<>();

	public Location(char identifier, int coords) {
		this.identifier = identifier;
		this.coords = coords;
	}

	public Location(int coords) {
		identifier = null;
		this.coords = coords;
	}
}
