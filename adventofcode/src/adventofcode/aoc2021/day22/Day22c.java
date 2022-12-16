package adventofcode.aoc2021.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day22c {
	public static void main(String[] args) {
		List<String> test3 = IOUtil.readFile("2021/day22.test3");
		List<String> data = IOUtil.readFile("2021/day22.data");

		part2(test3);
		part2(data);
	}

	private static void part2(List<String> data) {
		List<ActivationStep> steps = data.stream().map(l -> new ActivationStep(l)).collect(Collectors.toList());
		List<ActivationStep> integrated = new ArrayList<>();

		for (ActivationStep step : steps) {
			List<ActivationStep> newFragments = new ArrayList<>();
			int i = 0;
			while (i < integrated.size()) {
				ActivationStep old = integrated.get(i);
				if (old.intersectedBy(step)) {
					newFragments.addAll(old.splitBy(step));
					integrated.remove(i);
				} else {
					i++;
				}
			}
			integrated.add(step);
			integrated.addAll(newFragments);
		}

		System.out.println("part2: " + integrated.stream().filter(s -> s.turnOn).map(s -> s.volume()).reduce((a, b) -> a + b).orElse(0L));
	}

}
