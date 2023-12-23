package adventofcode.aoc2023.day23;

import java.util.LinkedList;
import java.util.List;

class Walker {
		SlopeNode from;
		List<String> map;
		int steps;
		int x1;
		int y1;
		int x2;
		int y2;

		int direction;

		Walker(int x, int y, List<String> map) {
			x1 = x;
			x2 = x;
			y1 = y;
			y2 = y;
			this.map = map;
		}

		Walker(int x1, int x2, int y1, int y2, List<String> map) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			steps = 1;
			this.map = map;
		}

		int walk() {
			boolean east = test(x1 + 1, y1);
			boolean south = test(x1, y1 + 1);
			boolean west = test(x1 - 1, y1);
			boolean north = test(x1, y1 - 1);

			int count = (east ? 1 : 0) + (west ? 1 : 0) + (south ? 1 : 0) + (north ? 1 : 0);

			if (count != 1) {
				return count; // dead end or multiple possibilities
			}

			x2 = x1;
			y2 = y1;
			if (north) {
				y1--;
				steps++;
			}
			if (south) {
				y1++;
				steps++;
			}
			if (east) {
				x1++;
				steps++;
			}
			if (west) {
				x1--;
				steps++;
			}
			includeDirection();
			return 1;
		}

		void includeDirection() {
			char c = map.get(y1).charAt(x1);
			switch (c) {
			case '>':
				direction = x1 - x2;
				break;
			case '<':
				direction = x2 - x1;
				break;
			case '^':
				direction = y2 - y1;
				break;
			case 'v':
				direction = y1 - y2;
				break;
			}
		}

		List<Walker> spawn() {
			boolean east = test(x1 + 1, y1);
			boolean south = test(x1, y1 + 1);
			boolean west = test(x1 - 1, y1);
			boolean north = test(x1, y1 - 1);

			List<Walker> walkers = new LinkedList<>();

			if (north) {
				walkers.add(new Walker(x1, x1, y1 - 1, y1, map));
			}
			if (south) {
				walkers.add(new Walker(x1, x1, y1 + 1, y1, map));
			}
			if (east) {
				walkers.add(new Walker(x1 + 1, x1, y1, y1, map));
			}
			if (west) {
				walkers.add(new Walker(x1 - 1, x1, y1, y1, map));
			}
			walkers.forEach(w -> w.includeDirection());
			return walkers;
		}

		boolean test(int x, int y) {
			if (x == x2 && y == y2)
				return false;
			if (x < 0 || x >= map.get(0).length() || y < 0 || y >= map.size())
				return false;
			char c = map.get(y).charAt(x);
			switch (c) {
			case '#':
				return false;
//			case '.':
//				return true;
//			case '>':
//				return x == x1 + 1;
//			case '<':
//				return x == x1 - 1;
//			case '^':
//				return y == y1 - 1;
//			case 'v':
//				return y == y1 + 1;
			default:
				return true;
			}
		}
	}