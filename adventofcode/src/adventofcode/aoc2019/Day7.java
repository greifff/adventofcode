package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Supplier;

import adventofcode.aoc2019.intcode.IntCodeProcessor;

public class Day7 {

	static final String program = "3,8,1001,8,10,8,105,1,0,0,21,34,55,68,85,106,187,268,349,430,99999,3,9,1001,9,5,9,1002,9,5,9,4,9,99,3,9,1002,9,2,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,101,3,9,9,102,3,9,9,4,9,99,3,9,1002,9,5,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1002,9,4,9,1001,9,2,9,102,3,9,9,101,3,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99";

	static final String test1 = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0";
	static final String test2 = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0";
	static final String test3 = "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0";

	public static final void main(String[] args) throws InterruptedException {
		// part1(test1);
		// part1(test2);
		// part1(test3);
		// part1(program);

		part2(program);
	}

	static void part1(String program) {
		List<long[]> amplifierSettings = permute(new long[] { 0, 1, 2, 3, 4 });

		long max = 0;
		for (long[] ampSet : amplifierSettings) {
			max = Math.max(max, test(program, ampSet));
		}
		System.out.println("part1: " + max);
	}

	static void part2(String program) throws InterruptedException {
		List<long[]> amplifierSettings = permute(new long[] { 5, 6, 7, 8, 9 });

		long max = 0;
		for (long[] ampSet : amplifierSettings) {
			max = Math.max(max, test2(program, ampSet));
			System.out.println("# " + max);
		}
		System.out.println("part2: " + max);
	}

	static List<long[]> permute(long[] input) {
		int n = input.length;
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
			indexes[i] = 0;
		}

		// printArray(elements, delimiter);
		List<long[]> result = new ArrayList<>();
		result.add(input);

		int[] current = new int[n];
		System.arraycopy(input, 0, current, 0, n);

		int i = 0;
		while (i < n) {
			if (indexes[i] < i) {
				swap(current, i % 2 == 0 ? 0 : indexes[i], i);
				long[] next = new long[n];
				System.arraycopy(current, 0, next, 0, n);
				result.add(next);

				indexes[i]++;
				i = 0;
			} else {
				indexes[i] = 0;
				i++;
			}
		}
		return result;
	}

	private static void swap(int[] input, int a, int b) {
		int tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}

	static long test2(String program, long[] amplifiers) throws InterruptedException {

		List<IntCodeProcessor> icps = new ArrayList<>();
		List<Thread> threads = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			IntCodeProcessor icp = new IntCodeProcessor(program);
			icps.add(icp);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					icp.execute();
				}

			});
			threads.add(t);
		}

		for (int i = 0; i < 5; i++) {
			IntCodeProcessor icp1 = icps.get(i);
			IntCodeProcessor icp2 = icps.get((i + 1) % 5);

			InputProvider2 ip = new InputProvider2();
			ip.accept(amplifiers[(i + 1) % 5]);

			icp1.output = ip;
			icp2.input = ip;
		}

		icps.get(4).output.accept(0L);

		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			Thread.sleep(50);
			t.join();
		}
		return ((InputProvider2) (icps.get(4).output)).b;
	}

	static long test(String program, long[] amplifiers) {
		final Carry carry = new Carry();

		for (int i = 0; i < 5; i++) {
			IntCodeProcessor icp = new IntCodeProcessor(program);
			icp.input = new InputProvider(Arrays.asList(amplifiers[i], carry.value).iterator());
			icp.output = v -> carry.value = v;
			icp.execute();
		}

		return carry.value;
	}

	static final class InputProvider implements Supplier<Long> {
		private Iterator<Long> it;

		InputProvider(Iterator<Long> it) {
			this.it = it;
		}

		@Override
		public Long get() {
			return it.next();
		}

	}

	static final class InputProvider2 implements Supplier<Long>, Consumer<Long> {
		private List<Long> it = new Vector<>();
		long b;

		@Override
		public Long get() {
			while (it.isEmpty()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					//
				}
			}
			return it.remove(0);
		}

		@Override
		public void accept(Long t) {
			it.add(t);
			b = t;
		}

	}

	static final class Carry {
		long value;
	}
}
