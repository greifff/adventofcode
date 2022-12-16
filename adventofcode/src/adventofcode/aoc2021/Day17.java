package adventofcode.aoc2021;

public class Day17 {

	public static void main(String[] args) {
		int[] testArea = new int[] { 20, 30, -10, -5 };
		int[] targetArea = new int[] { 241, 273, -97, -63 };

		part1(testArea);
		part1(targetArea);

		// part2(testArea);
		// part2(targetArea);
	}

	private static void part1(int[] targetArea) {
		int maxY = 0;
		int count = 0;
		for (int x = 0; x < 300; x++) {
			for (int y = -300; y < 300; y++) {
				Probe p = new Probe(x, y, targetArea);
				while (!p.beyondTargetArea()) {
					p.step();
					if (p.inTargetArea()) {
						// System.out.println("# " + x + "," + y + " -> " + p.maxY);
						maxY = Math.max(maxY, p.maxY);
						count++;
						break;
					}
				}
			}
		}
		System.out.println("part1: " + maxY);
		System.out.println("part2: " + count);
	}

	private static void part2(int[] targetArea) {
	}

	static class Probe {

		int x;
		int y;
		int vx;
		int vy;

		int initialVx;
		int initialVy;
		int maxY;

		int[] targetArea;

		Probe(int initialVx, int initialVy, int[] targetArea) {
			this.initialVx = initialVx;
			vx = initialVx;
			this.initialVy = initialVy;
			vy = initialVy;
			this.targetArea = targetArea;
		}

		void step() {
			x += vx;
			y += vy;

			maxY = Math.max(maxY, y);

			if (vx > 0) {
				vx--;
			} else if (vx < 0) {
				vx++;
			}
			vy--;
		}

		boolean inTargetArea() {
			return targetArea[0] <= x && x <= targetArea[1] && targetArea[2] <= y && y <= targetArea[3];
		}

		boolean beyondTargetArea() {
			boolean toTheRight = x > targetArea[1];
			boolean below = y < targetArea[2];

			boolean dropping = vy < 0;
			boolean noMementum = vx == 0;

			return (toTheRight && below) || (toTheRight && noMementum) || (below && dropping);
		}
	}
}
