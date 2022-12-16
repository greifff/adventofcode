package adventofcode.aoc2018.day19;

import adventofcode.aoc2018.day21.EqrrMonitor;

public enum Operation {

	addr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] + r[b];
	}), addi((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] + b;
	}), mulr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] * r[b];
	}), muli((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] * b;
	}), banr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] & r[b];
	}), bani((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] & b;
	}), borr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] | r[b];
	}), bori((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] | b;
	}), setr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a];
	}), seti((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = a;
	}), gtir((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = a > r[b] ? 1 : 0;
	}), gtri((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] > b ? 1 : 0;
	}), gtrr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] > r[b] ? 1 : 0;
	}), eqir((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = a == r[b] ? 1 : 0;
	}), eqri((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		r[c] = r[a] == b ? 1 : 0;
	}), eqrr((a, b, c) -> {
		int[] r = Registers.INSTANCE.registers;
		EqrrMonitor.call(r[1]);
		r[c] = r[a] == r[b] ? 1 : 0;
	});

	public final TriConsumer<Integer, Integer, Integer> triConsumer;

	Operation(TriConsumer<Integer, Integer, Integer> triConsumer) {
		this.triConsumer = triConsumer;
	}

}
