package adventofcode.aoc2018.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {

	public static void main(String[] args) {

		part1(10, 10, 510);
		part1(7, 701, 11394);

		part2(10, 10, 15, 15, 510);
		part2(7, 701, 40, 710, 11394);
	}

	static void part1(int targetX, int targetY, int depth) {
		CaveSystem cs = new CaveSystem(targetX, targetY, targetX, targetY, depth);

		System.out.println("part1: " + cs.sumRiskLevel());
	}

	static void part2(int targetX, int targetY, int rangeX, int rangeY, int depth) {
		CaveSystem cs = new CaveSystem(targetX, targetY, rangeX, rangeY, depth);
		// cs.print();
		Region target = cs.getRegion(targetX, targetY);

		// switch tool (nothing, torch, climbing gear) takes 7 minutes
		// traversing an area takes 1 minute

		// approach:
		// - if a region is reached with the same tool in a shorter time: scrap this path
		// - don't visit regions twice (but track this for each path separately)
		// - do an isochronous simulation (switching gear means skipping 6 rounds)

		Map<Integer, List<Path>> pathsToTraverse = new HashMap<>();
		// start at 0,0 with torch equipped
		List<Path> start = new ArrayList<>();
		start.add(new Path(null, cs.getRegion(0, 0), Tool.TORCH));
		pathsToTraverse.put(0, start);

		int turn = 0;

		while (!pathsToTraverse.isEmpty()) {
			List<Path> paths = pathsToTraverse.remove(turn);
			if (paths != null) {
				for (Path path : paths) {
					if (!path.checkAndSetMarker())
						continue;

					if (path.region == target) {
						// end at maxx, maxy with the torch equipped
						if (path.tool == Tool.TORCH) {
							cs.printHtml(path);
							// 993 was too high, 963 was too low
							int steps = -1;
							Path path1 = path;
							while (path1 != null) {
								steps++;
								path1 = path1.previous;
							}
							System.out.println("part2: Minutes: " + turn + " ; Steps: " + steps);
							return;
						}
						List<Path> toTorch = pathsToTraverse.get(turn + 7);
						if (toTorch == null) {
							toTorch = new ArrayList<>();
							pathsToTraverse.put(turn + 7, toTorch);
						}
						toTorch.add(new Path(path, target, Tool.TORCH));
						continue;
					}

					List<List<Path>> nextPaths = path.createFollowups();

					List<Path> sameTool = pathsToTraverse.get(turn + 1);
					if (sameTool == null) {
						sameTool = new ArrayList<>();
						pathsToTraverse.put(turn + 1, sameTool);
					}
					sameTool.addAll(nextPaths.get(0));
					List<Path> otherTool = pathsToTraverse.get(turn + 8);
					if (otherTool == null) {
						otherTool = new ArrayList<>();
						pathsToTraverse.put(turn + 8, otherTool);
					}
					otherTool.addAll(nextPaths.get(1));
				}
			}
			turn++;
		}
		System.out.println("Ran out of options.");
	}

	/*-
	  The 963 (width=40) step solution seems to be correct, but it is rated as "too low". 
	  969 has been accepted as correct - but I don't really know why
	 */
}
