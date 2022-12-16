package adventofcode.aoc2019;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.aoc2019.intcode.IntCodeProcessor;

public class Day2 {

	static final String input = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,10,19,1,19,6,23,2,23,13,27,1,27,5,31,2,31,10,35,1,9,35,39,1,39,9,43,2,9,43,47,1,5,47,51,2,13,51,55,1,55,9,59,2,6,59,63,1,63,5,67,1,10,67,71,1,71,10,75,2,75,13,79,2,79,13,83,1,5,83,87,1,87,6,91,2,91,13,95,1,5,95,99,1,99,2,103,1,103,6,0,99,2,14,0,0";

	public static void main(String[] args) {
		test(new IntCodeProcessor("1,9,10,3,2,3,11,0,99,30,40,50"));
		test(new IntCodeProcessor("1,0,0,0,99"));
		test(new IntCodeProcessor("2,3,0,3,99"));
		test(new IntCodeProcessor("2,4,4,5,99,0"));
		test(new IntCodeProcessor("1,1,1,4,99,5,6,0,99"));
		part1(new IntCodeProcessor(input));

		part2(new IntCodeProcessor(input));
	}

	static void part2(IntCodeProcessor program) {
		for (int a = 0; a < program.size(); a++) {
			for (int b = 0; b < program.size(); b++) {
				IntCodeProcessor p2 = new IntCodeProcessor(program);
				p2.modifyValue(1, a);
				p2.modifyValue(2, b);
				try {
					p2.execute();
				} catch (Throwable t) {
					System.err.println("# " + a + " " + b);
					t.printStackTrace();
				}
				if (p2.getValue(0) == 19690720) {
					System.out.println("part2: " + a + " " + b);
					return;
				}
			}
		}
	}

	static List<Integer> parseProgram(String in) {
		return Arrays.stream(in.split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
	}

	static List<Integer> part1modify(List<Integer> program) {
		program.set(1, 12);
		program.set(2, 2);
		return program;
	}

	static void part1(IntCodeProcessor program) {
		program.modifyValue(1, 12);
		program.modifyValue(2, 2);
		program.execute();
		System.out.println("part1: " + program.getValue(0));
	}

	static void test(IntCodeProcessor program) {
		program.execute();
		System.out.println("test: " + program.getValue(0));
	}
}
