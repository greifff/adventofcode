package adventofcode.aoc2018.day21;

import java.util.HashSet;
import java.util.Set;

public class EqrrMonitor {

	private static Set<Integer> encountered = new HashSet<>();

	public static void call(int value) {
		if (encountered.add(value)) {
			System.out.println("## " + value);
		}
	}

}
