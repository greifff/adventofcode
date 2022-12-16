package adventofcode.aoc2018.day18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day18b {

	public static void main(String[] args) {

		// 1mrd wiederholungen dauert zu lange, irgendwo muss sich was wiederholen

		Map<String, Integer> memory = new HashMap<>();

		List<String> data = IOUtil.readFile("2018/day18.data");

		LumberCollectionArea area = new LumberCollectionArea(50);

		area.init(data);

		memory.put(area.hashOutput(), -1);

		// found by experiment: 409 and 493 are identical
		int requiredRounds = 409 + (1_000_000_000 - 409) % (493 - 409);

		System.out.println("required rounds: " + requiredRounds);

		for (int i = 0; i < requiredRounds; i++) {
			area = area.nextStep();
			if (i == 409 || i == 493) {
				System.out.println(area.output());
			}
			String hash = area.hashOutput();
			Integer previous = memory.put(hash, i);
			if (previous != null) {
				System.out.println("candidate: " + previous + " vs. " + i);
				break;
			}

		}

		System.out.println(area.countType(WoodState.WOODED) * area.countType(WoodState.LUMBERYARD));
	}
}
