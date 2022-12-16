package adventofcode.aoc2019.day20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DonutLevel {

	Map<String, Map<String, Integer>> pathLookup = new HashMap<>();
	int level;

	DonutLevel(DonutMaze maze, int level) {
		this.level = level;

		for (Map.Entry<String, Map<String, Integer>> paths : maze.pathLookup.entrySet()) {
			pathLookup.put(paths.getKey(), new HashMap<>(paths.getValue()));
		}

		if (level > 0) {
			pathLookup.remove("AA-");
			pathLookup.remove("ZZ-");
			for (Map<String, Integer> pL2 : pathLookup.values()) {
				pL2.remove("AA-");
				pL2.remove("ZZ-");
			}
		} else {
			List<String> toRemove = pathLookup.keySet().stream().filter(s -> s.charAt(2) == '-').filter(s -> !s.equals("AA-") && !s.equals("ZZ-"))
					.collect(Collectors.toList());
			pathLookup.keySet().removeAll(toRemove);
			for (Map<String, Integer> pL2 : pathLookup.values()) {
				pL2.keySet().removeAll(toRemove);
			}
		}
	}
}
