package adventofcode.aoc2021.day24;

import java.util.function.Supplier;

public class Monad {

	long z;

	Supplier<Long> input;

	void run() {
		step0(1);
		step0(1);
		step0(16);
		stepB(-8, 5);
		stepB(-4, 9);
		step0(3);
		step0(2);
		step0(15);
		stepB(-13, 5);
		stepB(-3, 11);
		stepB(-7, 7);
		step0(1);
		stepB(-6, 10);
		stepB(-8, 3);
	}

	// a==1, b>9
	void step0(long c) {
		long w = input.get();
		z = z * 26 + w + c;
	}

	// a==26
	void stepB(long b, long c) {
		long w = input.get();
		long x = z % 26 + b;
		z = z / 26;
		if (x != w) {
			z = z * 26 + w + c;
		}
	}

	public static void main(String[] args) {
		// for (int j = 2; j <= 10; j++) {
		// for (int i = 1; i <= 9; i++) {
		// Monad m = new Monad();
		// m.z = j;
		// long k = i;
		// m.input = () -> k;
		// // m.calc(1, 15, 16);
		// // if (m.z >= 35 && m.z <= 43) {
		// System.out.println("" + i + "," + j + " -> " + m.z);
		// // }
		// }
		// }

		for (long k = 99_196_999_785_942L; k > 99_146_999_785_942L; k--) {
			Monad m = new Monad();
			m.input = new Day24.X(k);
			m.run();
			if (m.z == 0) {
				System.out.println("" + k);
				return;
			}
		}

	}

}
