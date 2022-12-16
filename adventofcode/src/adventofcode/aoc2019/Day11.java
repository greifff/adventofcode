package adventofcode.aoc2019;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import adventofcode.aoc2019.intcode.IntCodeProcessor;

public class Day11 {

	static final String program = "3,8,1005,8,318,1106,0,11,0,0,0,104,1,104,0,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,1,8,10,4,10,1002,8,1,28,1,107,14,10,1,107,18,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,102,1,8,58,1006,0,90,2,1006,20,10,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,88,2,103,2,10,2,4,7,10,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,118,1,1009,14,10,1,1103,9,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,1002,8,1,147,1006,0,59,1,104,4,10,2,106,18,10,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,101,0,8,181,2,4,17,10,1006,0,36,1,107,7,10,2,1008,0,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,101,0,8,217,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,101,0,8,240,1006,0,64,3,8,102,-1,8,10,1001,10,1,10,4,10,108,0,8,10,4,10,1002,8,1,264,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1001,8,0,287,1,1104,15,10,1,102,8,10,1006,0,2,101,1,9,9,1007,9,940,10,1005,10,15,99,109,640,104,0,104,1,21102,932700857236,1,1,21101,335,0,0,1106,0,439,21101,0,387511792424,1,21101,346,0,0,1106,0,439,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21101,46372252675,0,1,21102,393,1,0,1106,0,439,21101,97806162983,0,1,21102,404,1,0,1105,1,439,3,10,104,0,104,0,3,10,104,0,104,0,21102,1,825452438376,1,21101,0,427,0,1106,0,439,21102,709475586836,1,1,21101,0,438,0,1106,0,439,99,109,2,22101,0,-1,1,21101,40,0,2,21102,1,470,3,21102,1,460,0,1106,0,503,109,-2,2106,0,0,0,1,0,0,1,109,2,3,10,204,-1,1001,465,466,481,4,0,1001,465,1,465,108,4,465,10,1006,10,497,1101,0,0,465,109,-2,2105,1,0,0,109,4,2102,1,-1,502,1207,-3,0,10,1006,10,520,21102,1,0,-3,21202,-3,1,1,21202,-2,1,2,21101,0,1,3,21101,0,539,0,1106,0,544,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,567,2207,-4,-2,10,1006,10,567,22101,0,-4,-4,1106,0,635,21202,-4,1,1,21201,-3,-1,2,21202,-2,2,3,21102,586,1,0,1105,1,544,22101,0,1,-4,21102,1,1,-1,2207,-4,-2,10,1006,10,605,21102,0,1,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,627,22101,0,-1,1,21102,1,627,0,106,0,502,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0";

	public static void main(String[] args) {

		// testPaintbot();
		// testPaintbot2();
		// part1(program);
		part2(program);
	}

	static void testPaintbot() {
		Paintbot paintbot = new Paintbot();

		List<Long> input = Arrays.asList(1L, 0L, 0L, 0L, 1L, 0L, 1L, 0L, 0L, 1L, 1L, 0L, 1L, 0L);

		input.forEach(paintbot::accept);

		System.out.println("test1: " + paintbot.painted());

		paintbot.draw();
	}

	static void testPaintbot2() {
		Paintbot paintbot = new Paintbot();

		List<Long> input = Arrays.asList(1L, 0L, 0L, 0L, 1L, 0L, 1L, 0L, 0L, 1L, 1L, 0L, 1L, 0L, //
				0L, 1L, 0L, 1L, 0L, 1L, 0L, 1L);

		input.forEach(paintbot::accept);

		System.out.println("test2: " + paintbot.currentX + " " + paintbot.currentY);
		System.out.println("test2: " + paintbot.painted());

		paintbot.draw();
	}

	static void part1(String program) {
		IntCodeProcessor icp = new IntCodeProcessor(program);

		Paintbot paintbot = new Paintbot();

		icp.input = paintbot;
		icp.output = paintbot;

		icp.execute();

		System.out.println("part1: " + paintbot.painted());

		// System.out.println("" + paintbot.turnsLeft + " " + paintbot.turnsRight);

		// System.out.println("part2:");
		paintbot.draw();
	}

	static void part2(String program) {
		IntCodeProcessor icp = new IntCodeProcessor(program);

		Paintbot paintbot = new Paintbot();

		icp.input = paintbot;
		icp.output = paintbot;

		paintbot.setColor(0, 0, 1);

		icp.execute();

		// System.out.println("part1: " + paintbot.painted());

		// System.out.println("" + paintbot.turnsLeft + " " + paintbot.turnsRight);

		System.out.println("part2:");
		paintbot.draw();
	}

	static class Paintbot implements Supplier<Long>, Consumer<Long> {

		static final long BLACK = 0;
		static final long WHITE = 1;

		Status state = Status.PAINT;

		Map<Long, Set<Long>> hull = new Hashtable<>();

		Map<Long, Set<Long>> painted = new Hashtable<>();

		long currentX = 0;
		long currentY = 0;

		int steps = 0;

		int turnsLeft = 0;
		int turnsRight = 0;

		Direction direction = Direction.UP;

		int painted() {
			// System.out.println();
			// painted.entrySet().forEach(
			// es -> System.out.println("" + es.getKey() + ": " + es.getValue().stream().map(l -> "" + l).reduce((a, b) -> a + ", " + b)));
			return painted.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0);
		}

		void setColor(long x, long y, long color) {
			Set<Long> c = hull.get(x);
			if (c == null) {
				c = new HashSet<>();
				hull.put(x, c);
			}
			if (color == 0) {
				c.remove(y);
			} else if (color == 1) {
				c.add(y);
			}
		}

		@Override
		public void accept(Long t) {
			if (state == Status.PAINT) {
				Set<Long> c = hull.get(currentX);
				if (c == null) {
					c = new HashSet<>();
					hull.put(currentX, c);
				}
				if (t == 0) {
					c.remove(currentY);
				} else if (t == 1) {
					c.add(currentY);
				} else {
					System.out.println("unknown color: " + t);
				}

				// System.out.print((t == 0) ? "b" : "w");

				c = painted.get(currentX);
				if (c == null) {
					c = new HashSet<>();
					painted.put(currentX, c);
				}
				c.add(currentY);

				state = Status.MOVE;
			} else if (state == Status.MOVE) {
				if (t == 0) {
					direction = direction.left();
					turnsLeft++;
				} else if (t == 1) {
					direction = direction.right();
					turnsRight++;
				} else {
					System.out.println("unknown turn: " + t);
				}

				// System.out.print(" " + t + ": " + direction);
				currentX += direction.dx;
				currentY += direction.dy;
				// System.out.println(" (" + currentX + "," + currentY + ")");
				state = Status.PAINT;
			}
		}

		@Override
		public Long get() {
			Set<Long> c = hull.get(currentX);
			long k = BLACK;
			if (c != null && c.contains(currentY)) {
				k = WHITE;
			}
			// if (steps % 10 == 0) {
			// System.out.println();
			// }
			// System.out.print("[" + steps + "] (" + currentX + "," + currentY + ") -> " + k + " ");
			// steps++;
			return k;
		}

		void draw() {
			long minX = hull.keySet().stream().reduce(Math::min).orElse(0L);
			long maxX = hull.keySet().stream().reduce(Math::max).orElse(0L);
			long minY = hull.values().stream().flatMap(Set::stream).reduce(Math::min).orElse(0L);
			long maxY = hull.values().stream().flatMap(Set::stream).reduce(Math::max).orElse(0L);

			for (long y = minY; y <= maxY; y++) {
				for (long x = minX; x <= maxX; x++) {
					Set<Long> s = hull.get(x);
					if (s == null) {
						System.out.print(".");
					} else {
						if (s.contains(y)) {
							System.out.print("#");
						} else {
							System.out.print(".");
						}
					}
				}
				System.out.println();
			}
		}

	}

	static enum Status {
		PAINT, MOVE
	}

	static enum Direction {
		UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

		final int dx;
		final int dy;

		Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		Direction left() {
			// int i = Arrays.asList(values()).indexOf(this);
			// i = (i + 3) & 3;
			// return values()[i];
			switch (this) {
			default:
			case UP:
				return LEFT;
			case RIGHT:
				return UP;
			case DOWN:
				return RIGHT;
			case LEFT:
				return DOWN;
			}
		}

		Direction right() {
			// int i = Arrays.asList(values()).indexOf(this);
			// i = (i + 1) & 3;
			// return values()[i];
			switch (this) {
			default:
			case UP:
				return RIGHT;
			case RIGHT:
				return DOWN;
			case DOWN:
				return LEFT;
			case LEFT:
				return UP;
			}
		}
	}
}
