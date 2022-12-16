package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day25 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day25.test");
		List<String> data = IOUtil.readFile("2021/day25.data");

		part1(test);
		part1(data);
	}

	private static void part1(List<String> data) {
		boolean haveMoved = true;
		int steps = 0;
		while (haveMoved) {
			steps++;
			List<Integer> mayMove = mayMove(data, '>', 1, 0);
			doMove(data, mayMove, 1, 0, '>');
			haveMoved = !mayMove.isEmpty();

			mayMove = mayMove(data, 'v', 0, 1);
			doMove(data, mayMove, 0, 1, 'v');
			haveMoved |= !mayMove.isEmpty();

		}
		System.out.println("part1: " + steps);
	}

	private static void doMove(List<String> data, List<Integer> mayMove, int dx, int dy, char cucumber) {
		for (int move : mayMove) {
			int x = move % 1000;
			int y = move / 1000;
			setCharAt(data, x, y, '.');
			setCharAt(data, x + dx, y + dy, cucumber);
		}
	}

	private static List<Integer> mayMove(List<String> data, char cucumber, int dx, int dy) {
		List<Integer> mayMove = new ArrayList<>();
		for (int y = 0; y < data.size(); y++) {
			for (int x = 0; x < data.get(0).length(); x++) {
				if (charAt(data, x, y) == cucumber) {
					if (charAt(data, x + dx, y + dy) == '.') {
						mayMove.add(x + y * 1000);
					}
				}
			}
		}
		return mayMove;
	}

	private static char charAt(List<String> data, int x, int y) {
		String r = data.get(y % data.size());
		return r.charAt(x % r.length());
	}

	private static void setCharAt(List<String> data, int x, int y, char c) {
		int effY = y % data.size();
		String r = data.get(effY);
		int effX = x % r.length();
		String s = "";
		if (effX > 0) {
			s += r.substring(0, effX);
		}
		s += c;
		if (effX < r.length() - 1) {
			s += r.substring(effX + 1);
		}
		data.set(effY, s);
	}
}
