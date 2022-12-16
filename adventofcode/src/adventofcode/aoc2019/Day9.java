package adventofcode.aoc2019;

import java.util.List;

import adventofcode.aoc2019.intcode.IntCodeProcessor;
import adventofcode.util.IOUtil;

public class Day9 {

	public static void main(String[] args) {
		part1("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99");
		part1("1102,34915192,34915192,7,4,7,99,0");
		part1("104,1125899906842624,99");

		List<String> data = IOUtil.readFile("2019/day9.data");
		part1(data.get(0));
		part2(data.get(0));
	}

	static void part1(String program) {

		IntCodeProcessor icp = new IntCodeProcessor(program);

		icp.input = () -> 1L;
		icp.output = v -> System.out.println("part1: " + v);

		icp.execute();

	}

	static void part2(String program) {

		IntCodeProcessor icp = new IntCodeProcessor(program);

		icp.input = () -> 2L;
		icp.output = v -> System.out.println("part2: " + v);

		icp.execute();

	}
}
