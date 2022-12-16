package adventofcode.aoc2018.day16;

import java.util.Arrays;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day16Part2b {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2018/day16.data2");

		for (String d : data) {
			int[] op = Arrays.stream(d.split(" ")).mapToInt(a -> Integer.parseInt(a)).toArray();
			opcodes[op[0]].triConsumer.accept(op[1], op[2], op[3]);
		}

		System.out.println(Registers.INSTANCE.registers[0]);
	}

	static final Operation[] opcodes = { Operation.bori, // 0
			Operation.borr, // 1
			Operation.addi, // 2
			Operation.muli, // 3
			Operation.addr, // 4
			Operation.bani, // 5
			Operation.gtri, // 6
			Operation.setr, // 7
			Operation.gtrr, // 8
			Operation.seti, // 9
			Operation.eqir, // 10
			Operation.eqrr, // 11
			Operation.mulr, // 12
			Operation.eqri, // 13
			Operation.gtir, // 14
			Operation.banr, // 15
	};
}
