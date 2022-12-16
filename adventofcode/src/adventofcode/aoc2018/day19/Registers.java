package adventofcode.aoc2018.day19;

public enum Registers {
	INSTANCE;

	public int[] registers = new int[6];

	void set(int[] newValues) {
		for (int i = 0; i < 6; i++) {
			registers[i] = newValues[i];
		}
	}

	boolean check(int[] checkValues) {
		boolean result = true;
		for (int i = 0; i < 6; i++) {
			result &= registers[i] == checkValues[i];
		}
		return result;
	}

}
