package adventofcode.aoc2019.day18;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day18 {
	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2019/day18/day18.test1");
		List<String> test2 = IOUtil.readFile("2019/day18/day18.test2");
		List<String> test3 = IOUtil.readFile("2019/day18/day18.test3");
		List<String> test4 = IOUtil.readFile("2019/day18/day18.test4");
		List<String> test5 = IOUtil.readFile("2019/day18/day18.test5");
		List<String> data = IOUtil.readFile("2019/day18/day18.data.simplified2");
		List<String> dataReduced = IOUtil.readFile("2019/day18/day18.data.reduced");

		List<String> part2 = IOUtil.readFile("2019/day18/day18b.data.simplified2");

		// part1(test1);
		// part1(test2);
		// part1(test3);
		// part1(test4); // braucht zu lange, baut tausende Pfade auf
		// part1(test5);
		// part1(data);
		// part1(dataReduced);

		part1(part2);
	}

	static void part1(List<String> data) {
		MazeFiller filler = new MazeFiller(data);
		filler.process();

		for (String l : data) {
			System.out.println(l);
		}

		MazeScanner scanner = new MazeScanner(data);
		scanner.scan();
		// scanner.inventory();
		System.out.println("part1: " + scanner.findShortestPath());
	}

}
