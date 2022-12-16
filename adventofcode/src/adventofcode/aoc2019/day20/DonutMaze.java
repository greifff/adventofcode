package adventofcode.aoc2019.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DonutMaze {

	List<String> data;
	int donutwidth;

	Map<Integer, String> gates = new HashMap<>();

	Map<String, Map<String, Integer>> pathLookup = new HashMap<>();

	DonutMaze(List<String> data, int donutwidth) {
		this.data = data;
		this.donutwidth = donutwidth;

		detectGates();
		findPaths();
	}

	private void findPaths() {
		for (Map.Entry<Integer, String> e : gates.entrySet()) {
			findPathFromGate(e.getValue(), e.getKey());
		}
	}

	private void findPathFromGate(String name, int startpoint) {
		Map<String, Integer> paths = pathLookup.get(name);
		if (paths == null) {
			paths = new HashMap<>();
			pathLookup.put(name, paths);
		}
		Set<Integer> visited = new HashSet<>();
		List<Integer> probes = new ArrayList<>();
		probes.add(startpoint);
		int length = 0;
		while (!probes.isEmpty()) {
			List<Integer> probes2 = new ArrayList<>();
			while (!probes.isEmpty()) {
				int current = probes.remove(0);
				visited.add(current);
				String target = gates.get(current);
				if (target != null && !name.equals(target)) {
					paths.put(target, length);
				} else {
					int x = current % 1000;
					int y = current / 1000;
					char k = charAt(x, y - 1);
					if (k == '.' && !visited.contains(x + (y - 1) * 1000)) {
						probes2.add(x + (y - 1) * 1000);
					}
					k = charAt(x, y + 1);
					if (k == '.' && !visited.contains(x + (y + 1) * 1000)) {
						probes2.add(x + (y + 1) * 1000);
					}
					k = charAt(x - 1, y);
					if (k == '.' && !visited.contains(x - 1 + y * 1000)) {
						probes2.add(x - 1 + y * 1000);
					}
					k = charAt(x + 1, y);
					if (k == '.' && !visited.contains(x + 1 + y * 1000)) {
						probes2.add(x + 1 + y * 1000);
					}
				}
			}
			probes = probes2;
			length++;
		}
	}

	private void detectGates() {
		int leftBorder = 1;
		int rightBorder = data.get(0).length() - 2;
		int topBorder = 1;
		int bottomBorder = data.size() - 2;

		for (int x = leftBorder; x <= rightBorder; x++) {
			detectGateAt(x, topBorder, '-');
			detectGateAt(x, bottomBorder, '-');
		}
		for (int y = topBorder; y <= bottomBorder; y++) {
			detectGateAt(leftBorder, y, '-');
			detectGateAt(rightBorder, y, '-');
		}

		leftBorder = donutwidth + 2;
		topBorder = leftBorder;
		rightBorder = data.get(0).length() - 3 - donutwidth;
		bottomBorder = data.size() - 3 - donutwidth;

		for (int x = leftBorder; x <= rightBorder; x++) {
			detectGateAt(x, topBorder, '+');
			detectGateAt(x, bottomBorder, '+');
		}
		for (int y = topBorder; y <= bottomBorder; y++) {
			detectGateAt(leftBorder, y, '+');
			detectGateAt(rightBorder, y, '+');
		}
	}

	private void detectGateAt(int x, int y, char suffix) {
		char k = charAt(x, y);
		if (k >= 'A' && k <= 'Z') {
			char m0 = charAt(x, y - 1);
			char m1 = charAt(x, y + 1);
			if (m0 == '.' && m1 >= 'A' && m1 <= 'Z') {
				gates.put(x + (y - 1) * 1000, buildName(k, m1) + suffix);
			} else if (m1 == '.' && m0 >= 'A' && m0 <= 'Z') {
				gates.put(x + (y + 1) * 1000, buildName(k, m0) + suffix);
			} else {
				m0 = charAt(x - 1, y);
				m1 = charAt(x + 1, y);
				if (m0 == '.' && m1 >= 'A' && m1 <= 'Z') {
					gates.put(x - 1 + y * 1000, buildName(k, m1) + suffix);
				} else if (m1 == '.' && m0 >= 'A' && m0 <= 'Z') {
					gates.put(x + 1 + y * 1000, buildName(k, m0) + suffix);
				}
			}
		}
	}

	private char charAt(int x, int y) {
		return data.get(y).charAt(x);
	}

	static String buildName(char c1, char c2) {
		if (c1 < c2)
			return "" + c1 + c2;
		return "" + c2 + c1;
	}

	boolean isOuter(int x, int y) {
		int leftBorder = 1;
		int rightBorder = data.get(0).length() - 2;
		int topBorder = 1;
		int bottomBorder = data.size() - 2;
		return ((y == topBorder || y == bottomBorder) && x >= leftBorder && x <= rightBorder) // top and bottom row
				|| (y >= topBorder && y <= bottomBorder && (x == leftBorder || x == rightBorder)); // left and right column
	}

	boolean isInner(int x, int y) {
		int leftBorder = donutwidth + 2;
		int topBorder = leftBorder;
		int rightBorder = data.get(0).length() - 3 - donutwidth;
		int bottomBorder = data.size() - 3 - donutwidth;
		return ((y == topBorder || y == bottomBorder) && x >= leftBorder && x <= rightBorder) // top and bottom row
				|| (y >= topBorder && y <= bottomBorder && (x == leftBorder || x == rightBorder)); // left and right column
	}
}
