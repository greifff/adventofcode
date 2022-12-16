package adventofcode.aoc2019.intcode;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IntCodeProcessor {

	private static final int MODE_POSITION = 0;
	private static final int MODE_IMMEDIATE = 1;
	private static final int MODE_RELATIVE = 2;

	public Supplier<Long> input;

	public Consumer<Long> output;

	private int relativeBase = 0;

	private final int inputSize;

	private Map<Long, Long> memory = new Hashtable<>();

	private int programCounter;

	private boolean hasStopped;

	public IntCodeProcessor(String in) {
		List<Long> program = Arrays.stream(in.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
		inputSize = program.size();
		for (long i = 0; i < program.size(); i++) {
			memory.put(i, program.get((int) i));
		}
	}

	public IntCodeProcessor(IntCodeProcessor icp) {
		memory = new Hashtable<>(icp.memory);
		inputSize = icp.inputSize;
	}

	public void modifyValue(long index, long value) {
		if (index < 0) {
			throw new RuntimeException("negative address not allowed");
		}
		memory.put(index, value);
	}

	public void execute() {
		while (true) {
			executeSingleStep();
		}
	}

	public void executeUntilNextInput() {
		boolean isInput = false;
		do {
			executeSingleStep();
			isInput = memory.get((long) programCounter) % 100 == 3;
		} while (!isInput);
	}

	public boolean hasStopped() {
		return hasStopped;
	}

	public void executeSingleStep() {
		long opcode = memory.get((long) programCounter);
		int param1mode = (int) (opcode / 100 % 10);
		int param2mode = (int) (opcode / 1000 % 10);
		int param3mode = (int) (opcode / 10_000 % 10);
		switch ((int) (opcode % 100)) {
		// 1,2,99 given on Day 2
		case 1: // add
			long sum = getValue(programCounter + 1, param1mode) + getValue(programCounter + 2, param2mode);
			storeValue(programCounter + 3, param3mode, sum);
			programCounter += 4;
			break;
		case 2: // mul
			long product = getValue(programCounter + 1, param1mode) * getValue(programCounter + 2, param2mode);
			storeValue(programCounter + 3, param3mode, product);
			programCounter += 4;
			break;
		case 99: // exit
			hasStopped = true;
			return;
		// 3,4 given on Day 5 Part 1
		case 3: // input
			storeValue(programCounter + 1, param1mode, input.get());
			programCounter += 2;
			break;
		case 4: // output
			output.accept(getValue(programCounter + 1, param1mode));
			programCounter += 2;
			break;
		// 5..8 given on Day 5 Part 2
		case 5: // jump-if-true
			if (getValue(programCounter + 1, param1mode) != 0) {
				programCounter = (int) getValue(programCounter + 2, param2mode);
			} else {
				programCounter += 3;
			}
			break;
		case 6: // jump-if-false
			if (getValue(programCounter + 1, param1mode) == 0) {
				programCounter = (int) getValue(programCounter + 2, param2mode);
			} else {
				programCounter += 3;
			}
			break;
		case 7: // less than
			storeValue(programCounter + 3, param3mode, getValue(programCounter + 1, param1mode) < getValue(programCounter + 2, param2mode) ? 1 : 0);
			programCounter += 4;
			break;
		case 8: // equals
			storeValue(programCounter + 3, param3mode, getValue(programCounter + 1, param1mode) == getValue(programCounter + 2, param2mode) ? 1 : 0);
			programCounter += 4;
			break;
		// given on day 9 part1
		case 9: // increase relative base
			relativeBase += getValue(programCounter + 1, param1mode);
			programCounter += 2;
			break;
		default:
			programCounter++;
			break;
		}
	}

	private long getValue(long index, int mode) {
		Long value1 = memory.get(index);
		if (value1 == null)
			value1 = 0L;
		Long x = 0L;
		switch (mode) {
		case MODE_IMMEDIATE:
			return value1;
		case MODE_POSITION:
			x = memory.get(value1);
			break;
		case MODE_RELATIVE:
			x = memory.get(relativeBase + value1);
			break;
		}
		return x == null ? 0L : x;
	}

	private void storeValue(long index, int mode, long value) {
		Long address = memory.get(index);
		if (address == null)
			address = 0L;
		switch (mode) {
		case MODE_POSITION:
		case MODE_IMMEDIATE:
			memory.put(address, value);
			break;
		case MODE_RELATIVE:
			memory.put(relativeBase + address, value);
			break;
		}
	}

	public long getValue(long index) {
		return memory.get(index);
	}

	public int size() {
		return inputSize;
	}
}
