package adventofcode.aoc2018.day20;

public class MapNode {

	int x;
	int y;
	MapNode north;
	MapNode west;
	MapNode south;
	MapNode east;

	NodeRegistry nodeRegistry;

	public MapNode step(char direction) {
		switch (direction) {
		case 'N':
			north = nodeRegistry.getOrCreateNode(x, y - 1);
			north.south = this;
			return north;
		case 'S':
			south = nodeRegistry.getOrCreateNode(x, y + 1);
			south.north = this;
			return south;
		case 'W':
			west = nodeRegistry.getOrCreateNode(x - 1, y);
			west.east = this;
			return west;
		case 'E':
			east = nodeRegistry.getOrCreateNode(x + 1, y);
			east.west = this;
			return east;
		}
		return null;
	}

}
