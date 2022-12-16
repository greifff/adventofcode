package adventofcode.aoc2019.day18.retry;

public class Path {

	final Location start;
	Location end;
	int currentlyAt;
	int previouslyAt;
	int length = 1;

	public Path(Location start, int currentlyAt) {
		this.currentlyAt = currentlyAt;
		this.start = start;
		previouslyAt = start.coords;
	}
}
