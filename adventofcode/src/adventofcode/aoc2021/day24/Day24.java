package adventofcode.aoc2021.day24;

import java.util.List;
import java.util.function.Supplier;

import adventofcode.util.IOUtil;

public class Day24 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2021/day24/day24.test1");
		List<String> test2 = IOUtil.readFile("2021/day24/day24.test2");
		List<String> test3 = IOUtil.readFile("2021/day24/day24.test3");
		List<String> data = IOUtil.readFile("2021/day24/day24.data");

		test1(test1);
		test1(test2);
		test1(test3);
		test1(data);

		part1(data);

		// part2(test);
		// part2(data);
	}

	private static void test1(List<String> data) {
		ALU alu = new ALU();
		alu.input = new Supplier<Long>() {

			String input = "13579246899999";
			int index = 0;

			@Override
			public Long get() {
				long next = index < input.length() ? input.charAt(index) - 0x30 : 0;
				index++;
				return next;
			}

		};
		for (String d : data) {
			alu.processInstruction(d);
		}
		System.out.println("test: " + alu.registers[0] + " " + alu.registers[1] + " " + alu.registers[2] + " " + alu.registers[3]);
	}

	private static void part1(List<String> data) {
		// long value = 99_999_999_999_999L;
		// while (true) {
		X x = new X(99_196_999_785_942L);
		if (!x.invalid()) {
			ALU alu = new ALU();
			alu.input = x;

			for (String d : data) {
				alu.processInstruction(d);
			}

			System.out.println("part1: " + alu.registers[3]);
			// if (alu.registers[3] == 0) {
			// System.out.println("part1: " + value);
			// break;
			// }
		}
		// value--;
		// }

		Monad m = new Monad();

		x = new X(99_196_999_785_942L);
		m.input = x;
		m.run();
		// if (m.z >= 35 && m.z <= 43) {
		System.out.println("" + m.z);
	}

	static class X implements Supplier<Long> {

		String input;
		int index = 0;

		X(long value) {
			input = "" + value;
		}

		boolean invalid() {
			return input.indexOf(0) != -1;
		}

		@Override
		public Long get() {
			long next = index < input.length() ? input.charAt(index) - 0x30 : 0;
			index++;
			return next;
		}

	}
}
