package adventofcode.aoc2018.day23;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventofcode.util.IOUtil;

public class Day23z {

	public static void main(String[] args) {
		Day23z z = new Day23z();
		z.parse();
		System.out.println("part2: " + z.part2());
	}

	class Node3d {
		int x;
		int y;
		int z;
		long range;

		long getRange() {
			return range;
		}

		Node3d(int x, int y, int z, long range) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.range = range;
		}

		int distance(Node3d other) {
			return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z);
		}

		boolean inRange(Node3d other) {
			return distance(other) <= other.range;
		}
	}

	List<Node3d> point;

	public Object part1() {
		Node3d max = point.stream().max(Comparator.comparing(Node3d::getRange)).get();
		System.out.println(max.range);
		return point.stream().filter(x -> x.inRange(max)).count();
	}

	public Object part2() {
		TreeMap<Integer, Integer> ranges = new TreeMap<>();
		for (Node3d n : point) {
			int distFromZero = n.distance(new Node3d(0, 0, 0, 0));
			ranges.put(Math.max(0, distFromZero - (int) n.range), 1);
			ranges.put(distFromZero + (int) n.range, -1);
		}
		int count = 0;
		int result = 0;
		int maxCount = 0;
		for (Map.Entry<Integer, Integer> each : ranges.entrySet()) {
			count += each.getValue();
			if (count > maxCount) {
				result = each.getKey();
				maxCount = count;
			}
		}
		return result;
	}

	public void parse() {
		point = new ArrayList<>();
		Pattern p = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)");
		List<String> input = IOUtil.readFile("2018/day23.data");
		for (String line : input) {
			Matcher m = p.matcher(line);
			if (m.find()) {
				int x = Integer.parseInt(m.group(1));
				int y = Integer.parseInt(m.group(2));
				int z = Integer.parseInt(m.group(3));
				long r = Long.parseLong(m.group(4));
				Node3d n = new Node3d(x, y, z, r);
				point.add(n);
			}
		}
	}

}
