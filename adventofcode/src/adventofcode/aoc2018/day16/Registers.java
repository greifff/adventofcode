package adventofcode.aoc2018.day16;

public enum Registers {
	INSTANCE;

	int[] registers = new int[4];

	void set(int[] newValues) {
		for (int i = 0; i < 4; i++) {
			registers[i] = newValues[i];
		}
	}

	boolean check(int[] checkValues) {
		boolean result = true;
		for (int i = 0; i < 4; i++) {
			result &= registers[i] == checkValues[i];
		}
		return result;
	}

}
