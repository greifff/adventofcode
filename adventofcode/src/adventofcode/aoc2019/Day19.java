package adventofcode.aoc2019;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

import adventofcode.aoc2019.intcode.IntCodeProcessor;

public class Day19 {

	private static final String data = "109,424,203,1,21102,11,1,0,1106,0,282,21102,18,1,0,1105,1,259,1202,1,1,221,203,1,21102,31,1,0,1106,0,282,21101,38,0,0,1106,0,259,21002,23,1,2,22102,1,1,3,21102,1,1,1,21102,1,57,0,1105,1,303,2101,0,1,222,21002,221,1,3,20101,0,221,2,21101,0,259,1,21102,1,80,0,1105,1,225,21102,1,8,2,21101,91,0,0,1106,0,303,1202,1,1,223,21002,222,1,4,21102,1,259,3,21101,225,0,2,21101,225,0,1,21101,0,118,0,1105,1,225,21001,222,0,3,21101,0,48,2,21102,133,1,0,1106,0,303,21202,1,-1,1,22001,223,1,1,21102,1,148,0,1105,1,259,1201,1,0,223,20101,0,221,4,21001,222,0,3,21101,0,6,2,1001,132,-2,224,1002,224,2,224,1001,224,3,224,1002,132,-1,132,1,224,132,224,21001,224,1,1,21102,1,195,0,105,1,108,20207,1,223,2,21001,23,0,1,21101,-1,0,3,21101,0,214,0,1105,1,303,22101,1,1,1,204,1,99,0,0,0,0,109,5,2101,0,-4,249,21201,-3,0,1,22102,1,-2,2,21202,-1,1,3,21102,1,250,0,1106,0,225,21201,1,0,-4,109,-5,2106,0,0,109,3,22107,0,-2,-1,21202,-1,2,-1,21201,-1,-1,-1,22202,-1,-2,-2,109,-3,2106,0,0,109,3,21207,-2,0,-1,1206,-1,294,104,0,99,22101,0,-2,-2,109,-3,2106,0,0,109,5,22207,-3,-4,-1,1206,-1,346,22201,-4,-3,-4,21202,-3,-1,-1,22201,-4,-1,2,21202,2,-1,-1,22201,-4,-1,1,22102,1,-2,3,21101,0,343,0,1105,1,303,1105,1,415,22207,-2,-3,-1,1206,-1,387,22201,-3,-2,-3,21202,-2,-1,-1,22201,-3,-1,3,21202,3,-1,-1,22201,-3,-1,2,22101,0,-4,1,21101,384,0,0,1106,0,303,1106,0,415,21202,-4,-1,-4,22201,-4,-3,-4,22202,-3,-2,-2,22202,-2,-4,-4,22202,-3,-2,-3,21202,-4,-1,-2,22201,-3,-2,1,21201,1,0,-4,109,-5,2106,0,0";

	private static IntCodeProcessor template;

	public static void main(String[] args) {
		template = new IntCodeProcessor(data);

		// part1(data);
		part2();
	}

	private static void part2() {

		long xi = 400;

		// Step 1: find the upper limit at x=500
		long ya = 0;
		do {
			ya++;
			long r = executeTractor(xi + 100, ya);
			if (r == 1)
				break;

		} while (true);

		// Step 2: find the lower limit at x=400
		long yb = 0;
		do {
			yb++;
			long r = executeTractor(xi, yb);
			if (r == 1)
				break;

		} while (true);
		do {
			yb++;
			long r = executeTractor(xi, yb);
			if (r == 0)
				break;

		} while (true);

		// debug: print coordinates
		System.out.println("# (" + (xi + 99) + "," + ya + ") (" + xi + "," + yb + ")");

		// Step 3: follow upper and limit until (xa,ya)==(xi+100,yi) && (xb,yb)==(xi,yi+100)

		while (yb - ya < 100) {
			xi++;

			while (true) {
				long r = executeTractor(xi + 99, ya);
				if (r == 1)
					break;
				ya++;
			}

			while (true) {
				long r = executeTractor(xi, yb);
				if (r == 0)
					break;
				yb++;

			}
		}

		// (1069,410) too low; (1075,412) too high
		System.out.println("# (" + xi + "," + ya + ")" + (xi * 10000 + ya));

		print(xi + 80, ya - 20, xi + 120, ya + 20);
		System.out.println("====================== yb=" + yb);
		print(xi - 20, yb - 20, xi + 20, yb + 20);
	}

	private static void part1() {

		Supplier<Long> s = new Supplier<Long>() {

			long x = 0;
			long y = 0;

			boolean xSend = false;

			@Override
			public Long get() {
				if (xSend) {
					long y1 = y;

					x++;
					if (x == 50) {
						x = 0;
						y++;
					}
					xSend = false;

					return y1;
				}
				xSend = true;
				return x;
			}
		};

		Consumer<Long> c = new Consumer<Long>() {

			int count = 0;
			int totalCount = 0;
			int lit = 0;

			@Override
			public void accept(Long t) {
				System.out.print(t == 1 ? '#' : '.');
				count++;
				totalCount++;
				if (t == 1)
					lit++;
				if (count == 50) {
					System.out.println();
					count = 0;
				}
				if (totalCount == 2500) {
					System.out.println("part1: " + lit);

				}
			}

		};

		for (int i = 0; i < 2500; i++) {
			IntCodeProcessor icp = new IntCodeProcessor(data);
			icp.input = s;
			icp.output = c;
			icp.execute();
		}

	}

	private static void print(long x1, long y1, long x2, long y2) {

		for (long y = y1; y <= y2; y++) {
			for (long x = x1; x <= x2; x++) {
				long xa = x;
				long ya = y;
				IntCodeProcessor icp = new IntCodeProcessor(data);
				icp.input = new Supplier<Long>() {
					Iterator<Long> it = Arrays.asList(xa, ya).iterator();

					@Override
					public Long get() {
						return it.next();
					}
				};
				icp.output = r -> System.out.print(r == 1 ? "#" : ".");
				icp.execute();
			}
			System.out.println();
		}
	}

	static long executeTractor(long x, long y) {
		Tractor t = new Tractor(x, y);
		IntCodeProcessor icp = new IntCodeProcessor(template);
		icp.input = t;
		icp.output = t;
		icp.execute();
		return t.result;
	}

	static class Tractor implements Supplier<Long>, Consumer<Long> {

		long x;
		long y;

		boolean xSend = false;

		long result;

		Tractor(long x, long y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void accept(Long t) {
			result = t;
		}

		@Override
		public Long get() {
			if (xSend) {
				long y1 = y;
				x++;
				if (x == 50) {
					x = 0;
					y++;
				}
				xSend = false;
				return y1;
			}
			xSend = true;
			return x;
		}
	}

}
