package adventofcode.aoc2021.day24;

import java.util.function.Supplier;

public class ALU {

	long[] registers = new long[4];

	Supplier<Long> input;

	void processInstruction(String instr) {
		String[] p = instr.split(" ");

		switch (p[0]) {
		case "inp":
			input(p[1]);
			break;
		case "add":
			add(p[1], p[2]);
			break;
		case "mul":
			mul(p[1], p[2]);
			break;
		case "div":
			div(p[1], p[2]);
			break;
		case "mod":
			mod(p[1], p[2]);
			break;
		case "eql":
			eql(p[1], p[2]);
			break;
		}
	}

	private void eql(String a, String b) {
		int reg1 = getRegister(a);
		int reg2 = getRegister(b);
		long lit = 0;
		if (reg2 == -1) {
			lit = Long.parseLong(b);
		}
		registers[reg1] = (registers[reg1] == (reg2 == -1 ? lit : registers[reg2])) ? 1 : 0;
	}

	private void mod(String a, String b) {
		int reg1 = getRegister(a);
		int reg2 = getRegister(b);
		long lit = 0;
		if (reg2 == -1) {
			lit = Long.parseLong(b);
		}
		registers[reg1] = registers[reg1] % (reg2 == -1 ? lit : registers[reg2]);
	}

	private void div(String a, String b) {
		int reg1 = getRegister(a);
		int reg2 = getRegister(b);
		long lit = 0;
		if (reg2 == -1) {
			lit = Long.parseLong(b);
		}
		registers[reg1] = registers[reg1] / (reg2 == -1 ? lit : registers[reg2]);
	}

	private void mul(String a, String b) {
		// TODO Auto-generated method stub
		int reg1 = getRegister(a);
		int reg2 = getRegister(b);
		int lit = 0;
		if (reg2 == -1) {
			lit = Integer.parseInt(b);
		}
		registers[reg1] = registers[reg1] * (reg2 == -1 ? lit : registers[reg2]);
	}

	private void add(String a, String b) {
		int reg1 = getRegister(a);
		int reg2 = getRegister(b);
		long lit = 0;
		if (reg2 == -1) {
			lit = Long.parseLong(b);
		}
		registers[reg1] = registers[reg1] + (reg2 == -1 ? lit : registers[reg2]);
	}

	private void input(String a) {
		int reg1 = getRegister(a);

		registers[reg1] = input.get();
	}

	private int getRegister(String a) {
		switch (a) {
		case "w":
			return 0;
		case "x":
			return 1;
		case "y":
			return 2;
		case "z":
			return 3;
		default:
			return -1;
		}
	}
}
