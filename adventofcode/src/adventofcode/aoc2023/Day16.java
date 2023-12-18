package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day16 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day16.test");
		List<String> input = IOUtil.readFile("2023/day16.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long touched = processConfiguration(input, new Photon(0, 0, 1));

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + touched);
	}

	static long processConfiguration(List<String> input, Photon p) {
		Set<Long> visited = new HashSet<>();

		List<Photon> photons = new ArrayList<>();
		photons.add(p);

		int maxx = input.get(0).length() - 1;
		int maxy = input.size() - 1;

		while (!photons.isEmpty()) {
			List<Photon> photons2 = new ArrayList<>();
			for (Photon p1 : photons) {
				if (visited.add(p1.signature())) {
//					System.out.println("$ " + p1.x + " " + p1.y);
					char c = input.get(p1.y).charAt(p1.x);
					photons2.addAll(p1.move(c));
				}
			}

			photons = photons2.stream().filter(p1 -> 0 <= p1.y && p1.y <= maxy).filter(p1 -> 0 <= p1.x && p1.x <= maxx)
					.toList();
		}

		return visited.stream().map(v -> v & 0xF_FFFF).distinct().count();
	}

	static class Photon {
		int x;
		int y;
		int direction; // 0 north, 1 east, 2 south, 3 west

		Photon(int x, int y, int direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}

		long signature() {
			return ((long) direction << 20) + (x << 10) + y;
		}

		List<Photon> move(char c) {
			switch (c) {
			case '.':
				return moveOn();
			case '/':
				return mirror1();
			case '\\':
				return mirror2();
			case '|':
				if (direction == 0 || direction == 2) {
					return moveOn();
				} else {
					return split();
				}
			case '-':
				if (direction == 0 || direction == 2) {
					return split();
				} else {
					return moveOn();
				}
			}
			return Collections.emptyList();
		}

		List<Photon> mirror1() {
			switch (direction) {
			case 0:
				x++;
				direction = 1;
				break;
			case 1:
				y--;
				direction = 0;
				break;
			case 2:
				x--;
				direction = 3;
				break;
			case 3:
				y++;
				direction = 2;
				break;
			}
			return Collections.singletonList(this);
		}

		List<Photon> mirror2() {
			switch (direction) {
			case 0:
				x--;
				direction = 3;
				break;
			case 1:
				y++;
				direction = 2;
				break;
			case 2:
				x++;
				direction = 1;
				break;
			case 3:
				y--;
				direction = 0;
				break;
			}
			return Collections.singletonList(this);
		}

		List<Photon> split() {
			Photon b = null;
			switch (direction) {
			case 0:
			case 2:
				b = new Photon(x - 1, y, 3);
				x++;
				direction = 1;
				break;
			case 1:
			case 3:
				b = new Photon(x, y - 1, 0);
				y++;
				direction = 2;
				break;
			}
			return Arrays.asList(this, b);
		}

		List<Photon> moveOn() {
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
			return Collections.singletonList(this);
		}
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long touched = 0;
		int maxx = input.get(0).length() - 1;
		int maxy = input.size() - 1;
		for (int x = 0; x <= maxx; x++) {
			touched = Math.max(touched, Math.max(processConfigurationPlus(input, new Photon(x, 0, 2)),
					processConfigurationPlus(input, new Photon(x, maxy, 0))));
		}
		for (int i = 0; i <= maxy; i++) {
			touched = Math.max(touched, Math.max(processConfigurationPlus(input, new Photon(0, i, 1)),
					processConfigurationPlus(input, new Photon(maxx, i, 3))));
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + touched);
	}

	static long processConfigurationPlus(List<String> input, Photon p) {

		List<String> inputPlus = new ArrayList<>();
		String topOrBottomBoundary = "X".repeat(input.get(0).length() + 2);
		inputPlus.add(topOrBottomBoundary);
		for (String line : input) {
			inputPlus.add("X" + line + "X");
		}
		inputPlus.add(topOrBottomBoundary);
		boolean[][][] visited = new boolean[input.get(0).length() + 2][input.size() + 2][4];

		List<Photon> photons = new LinkedList<>();
		photons.add(p);

		while (!photons.isEmpty()) {
			Photon p1 = photons.remove(0);
			if (!visited[p1.x + 1][p1.y + 1][p1.direction]) {
				visited[p1.x + 1][p1.y + 1][p1.direction] = true;
//					System.out.println("$ " + p1.x + " " + p1.y);
				char c = inputPlus.get(p1.y + 1).charAt(p1.x + 1);
				photons.addAll(p1.move(c));
			}

		}
		long touched = 0;
		for (int x = 1; x <= input.get(0).length(); x++) {
			boolean[][] visitedLine = visited[x];
			for (int y = 1; y <= input.size(); y++) {
				boolean wasHere = false;
				for (boolean b : visitedLine[y]) {
					wasHere |= b;
				}
				if (wasHere) {
					touched++;
				}
			}
		}
		return touched;
	}
}
