package adventofcode.aoc2022;

import java.util.List;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day10 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2022/day10.test1");
		List<String> input = IOUtil.readFile("2022/day10.data");

		run(test);
		run(input);
	}

	private static void run(List<String> input) {
		ColorCrt crt = new ColorCrt();
		for (String in : input) {
			crt.execute(in);
		}

		Ansi.foreground(0x8080ff);
		System.out.println("\npart1: " + crt.registers.get('d'));
	}
}
