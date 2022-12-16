package adventofcode.aoc2022;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import adventofcode.util.Ansi;

public class ColorCrt {

	Map<Character, Integer> registers = new HashMap<>();

	private Set<Integer> diagnosticCycles = new HashSet<>(Arrays.asList(20, 60, 100, 140, 180, 220));

	private int cycleCount = 0;

	ColorCrt() {
		registers.put('X', 1);
		registers.put('d', 0);
	}

	public void execute(String command) {
		String[] parts = command.split(" ");

		switch (parts[0]) {
		case "addx":
			cycle();
			cycle();
			registers.put('X', registers.get('X') + Integer.parseInt(parts[1]));
			break;
		case "noop":
			cycle();
			break;
		}

	}

	private void cycle() {
		int position = cycleCount % 40;
		if (position == 0) {
			System.out.println();
		}

		int spriteCenter = registers.get('X');

		if (position >= spriteCenter - 1 && position <= spriteCenter + 1) {
			Ansi.foreground(0xFF0000);
			System.out.print("#");
		} else {
			Ansi.foreground(0x808080);
			System.out.print(".");
		}
		cycleCount++;

		if (diagnosticCycles.contains(cycleCount)) {
			registers.put('d', registers.get('d') + cycleCount * spriteCenter);
		}
	}
}
