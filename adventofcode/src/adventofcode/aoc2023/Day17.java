package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day17 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day17.test");
		List<String> input = IOUtil.readFile("2023/day17.data");

		part1(test);
//		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		// find path from top-left to bottom-right with minimum heat loss

		List<String> inputPlus = new ArrayList<>();
		String topOrBottom = "X".repeat(input.get(0).length() + 2);
		inputPlus.add(topOrBottom);
		for (String in : input) {
			inputPlus.add("X" + in + "X");
		}
		inputPlus.add(topOrBottom);

		List<Crucible> toMove = new LinkedList<>();
		toMove.add(new Crucible(2, 1, 0, 1, 1, 3));
		toMove.add(new Crucible(1, 2, 0, 2, 1, 3));
		toMove.get(0).consecutiveMovesInDirection = 1;
		toMove.get(1).consecutiveMovesInDirection = 1;

//		toMove.get(0).heatLoss = '0' - inputPlus.get(1).charAt(1);

		Map<Integer, Integer> heatLosses = new HashMap<>();

		while (!toMove.isEmpty()) {

			Crucible c = toMove.remove(0);

			char h = inputPlus.get(c.y).charAt(c.x);

			if (h != 'X') {
				c.heatLoss += h - '0';
				int p = (c.x << 10) + c.y + (c.direction << 20) + (c.consecutiveMovesInDirection << 23);
				Integer oldMin = heatLosses.get(p);
				if (oldMin == null || oldMin > c.heatLoss) {
					heatLosses.put(p, c.heatLoss);
					toMove.addAll(c.move());
				}
			}

//			System.out.println("$ " + toMove.stream().map(c2 -> "(" + c2.x + "," + c2.y + "/" + c.heatLoss + ") ")
//					.collect(Collectors.joining()));
		}

		int result = heatLosses.entrySet().stream()
				.filter(e -> (e.getKey() & 0xFFFFF) == (input.get(0).length() << 10) + input.size())
				.map(e -> e.getValue()).reduce(Math::min).orElse(0);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + result);

		// 842 is too low, 848 is too high
	}

	static class Crucible {
		int x;
		int y;
		int heatLoss;
		int direction;
		int consecutiveMovesInDirection = 1;
		int minimumStraight;
		int maximumStraight;

		Crucible(int x, int y, int heatLoss, int direction, int minimumStraight, int maximumStraight) {
			this.x = x;
			this.y = y;
			this.heatLoss = heatLoss;
			this.direction = direction;
			this.minimumStraight = minimumStraight;
			this.maximumStraight = maximumStraight;
		}

		List<Crucible> move() {
			List<Crucible> next = new ArrayList<>();
			if (consecutiveMovesInDirection >= minimumStraight) {
				next.add(turnLeft());
				next.add(turnRight());
			}
			if (consecutiveMovesInDirection < maximumStraight) {
				moveOn();
				consecutiveMovesInDirection++;
				next.add(this);
			}
			return next;
		}

		Crucible turnLeft() {
			switch (direction) {
			case 0:
				return new Crucible(x - 1, y, heatLoss, 3, minimumStraight, maximumStraight);
			case 1:
				return new Crucible(x, y - 1, heatLoss, 0, minimumStraight, maximumStraight);
			case 2:
				return new Crucible(x + 1, y, heatLoss, 1, minimumStraight, maximumStraight);
			case 3:
				return new Crucible(x, y + 1, heatLoss, 2, minimumStraight, maximumStraight);
			}
			return null;
		}

		Crucible turnRight() {
			switch (direction) {
			case 0:
				return new Crucible(x + 1, y, heatLoss, 1, minimumStraight, maximumStraight);
			case 1:
				return new Crucible(x, y + 1, heatLoss, 2, minimumStraight, maximumStraight);
			case 2:
				return new Crucible(x - 1, y, heatLoss, 3, minimumStraight, maximumStraight);
			case 3:
				return new Crucible(x, y - 1, heatLoss, 0, minimumStraight, maximumStraight);
			}
			return null;
		}

		void moveOn() {
			switch (direction) {
			case 0:
				y--;
				break;
			case 1:
				x++;
				break;
			case 2:
				y++;
				break;
			case 3:
				x--;
				break;
			}
		}
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<String> inputPlus = new ArrayList<>();
		String topOrBottom = "X".repeat(input.get(0).length() + 2);
		inputPlus.add(topOrBottom);
		for (String in : input) {
			inputPlus.add("X" + in + "X");
		}
		inputPlus.add(topOrBottom);

		List<Crucible> toMove = new LinkedList<>();
		toMove.add(new Crucible(2, 1, 0, 1, 4, 10));
		toMove.add(new Crucible(1, 2, 0, 2, 4, 10));
		toMove.get(0).consecutiveMovesInDirection = 1;
		toMove.get(1).consecutiveMovesInDirection = 1;

//		toMove.get(0).heatLoss = '0' - inputPlus.get(1).charAt(1);

		Map<Integer, Integer> heatLosses = new HashMap<>();

		while (!toMove.isEmpty()) {

			Crucible c = toMove.remove(0);

			char h = inputPlus.get(c.y).charAt(c.x);

			if (h != 'X') {
				c.heatLoss += h - '0';
				int p = (c.x << 10) + c.y + (c.direction << 20) + (c.consecutiveMovesInDirection << 23);
				Integer oldMin = heatLosses.get(p);
				if (oldMin == null || oldMin > c.heatLoss) {
					heatLosses.put(p, c.heatLoss);
					toMove.addAll(c.move());
				}
			}

//			System.out.println("$ " + toMove.stream().map(c2 -> "(" + c2.x + "," + c2.y + "/" + c.heatLoss + ") ")
//					.collect(Collectors.joining()));
		}

		int result = heatLosses.entrySet().stream()
				.filter(e -> (e.getKey() & 0xFFFFF) == (input.get(0).length() << 10) + input.size())
				.map(e -> e.getValue()).reduce(Math::min).orElse(0);

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + result);
	}
}
