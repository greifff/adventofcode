package adventofcode.aoc2018.day22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Path {

	Path previous;
	Region region;
	Tool tool;

	Path(Path previous, Region region, Tool tool) {
		this.previous = previous;
		this.region = region;
		this.tool = tool;
	}

	boolean alreadyVisited(Region region2) {
		return region2 == region || (previous != null && previous.alreadyVisited(region2));
	}

	List<List<Path>> createFollowups() {
		List<Path> withoutToolchange = new ArrayList<>();
		List<Path> withToolchange = new ArrayList<>();

		List<List<Path>> result = Arrays.asList(withoutToolchange, withToolchange);

		List<Region> neighbors = region.getNeighbors();

		Set<Tool> storedTools = Arrays.stream(Tool.values()).filter(t -> t != tool).collect(Collectors.toSet());

		for (Region r : neighbors) {
			if (alreadyVisited(r))
				continue;

			final Tool cantUse = r.getType().cantUse;
			if (cantUse != tool) {
				withoutToolchange.add(new Path(this, r, tool));
			}

			withToolchange.addAll( //
					storedTools.stream().filter(t -> t != cantUse) //
							.map(t -> new Path(this, r, t)) //
							.collect(Collectors.toList()));
		}
		return result;
	}

	public boolean checkAndSetMarker() {
		return region.reachedWith.add(tool);
	}

	public void print() {
		// TODO Auto-generated method stub
		if (previous != null)
			previous.print();
		System.out.println("- (" + region.x + "," + region.y + ") " + tool);
	}

}
