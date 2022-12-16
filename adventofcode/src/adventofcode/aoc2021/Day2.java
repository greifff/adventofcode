package adventofcode.aoc2021;

import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day2 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2021/day2.data");
		part1(data);
		part2(data);
	}

	private static void part2(List<String> data) {
		List<DiveCommand> cmds = data.stream().map(d -> new DiveCommand(d)).collect(Collectors.toList());

		int depth = 0;
		int aim = 0;
		int position = 0;

		for (DiveCommand dc : cmds) {
			if (dc.y != 0) {
				aim += dc.y;
			}
			if (dc.x != 0) {
				position += dc.x;
				depth += aim * dc.x;
			}
		}

		System.out.println("part2: " + (position * depth));
	}

	private static void part1(List<String> data) {
		List<DiveCommand> cmds = data.stream().map(d -> new DiveCommand(d)).collect(Collectors.toList());
		int x = cmds.stream().map(dc -> dc.x).reduce((x1, x2) -> x1 + x2).orElse(0);
		int y = cmds.stream().map(dc -> dc.y).reduce((y1, y2) -> y1 + y2).orElse(0);

		System.out.println("part1: " + (x * y));
	}

	static class DiveCommand {
		int x;
		int y;

		DiveCommand(String data) {
			String[] p = data.split(" ");
			char direction = data.charAt(0);
			int distance = Integer.parseInt(p[1]);

			switch (direction) {
			case 'f':
				x = distance;
				break;
			case 'd':
				y = distance;
				break;
			case 'u':
				y = -distance;
				break;
			}
		}
	}
}
