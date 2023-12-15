package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day15 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day15.test");
		List<String> input = IOUtil.readFile("2023/day15.data");

		part1(test);
		part1(input);
		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		long sum = Arrays.stream(input.get(0).split(",")).mapToLong(s -> hash(s)).sum();

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + sum);
	}

	static int hash(String s) {
		int currentValue = 0;
		for (int i = 0; i < s.length(); i++) {
			currentValue += s.charAt(i);
			currentValue = (currentValue * 17) & 255;
		}
		return currentValue;
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<List<Map.Entry<String, Integer>>> boxes = new ArrayList<>();
		for (int i = 0; i < 256; i++) {
			boxes.add(new ArrayList<>());
		}

		String[] data = input.get(0).split(",");

		for (String d : data) {

			if (d.endsWith("-")) {
				String lens = d.substring(0, d.length() - 1);
				int address = hash(lens);
				List<Map.Entry<String, Integer>> box = boxes.get(address);
				int j = 0;
//				System.out.println("- " + lens + " #");
				while (j < box.size()) {
					if (box.get(j).getKey().equals(lens)) {
						box.remove(j);
						break;
					}
					j++;
				}
			} else {
				int assign = d.indexOf('=');
				String lens = d.substring(0, assign);
				int address = hash(lens);
				List<Map.Entry<String, Integer>> box = boxes.get(address);
				int value = Integer.parseInt(d.substring(assign + 1));
				int j = 0;
				while (j < box.size()) {
					if (box.get(j).getKey().equals(lens)) {
						break;
					}
					j++;
				}
				if (j < box.size()) {
					box.get(j).setValue(value);
				} else {
					box.add(new Entry<>(lens, value));
				}
			}

//			System.out.println("# " + d);
//			boxes.stream().filter(b -> !b.isEmpty()).forEach(b -> {
//				System.out.println("&  " + b.stream().map(e -> "[" + e.getKey() + " " + e.getValue() + "]")
//						.collect(Collectors.joining(" ")));
//			});
		}

		long sum = 0;
		for (int i = 0; i < 256; i++) {
			List<Map.Entry<String, Integer>> box = boxes.get(i);
			for (int j = 0; j < box.size(); j++) {
				sum += (i + 1) * (j + 1) * box.get(j).getValue();
			}
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + sum);
	}

	static class Entry<X, Y> implements Map.Entry<X, Y> {

		private X key;
		private Y value;

		Entry(X key, Y value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public X getKey() {
			return key;
		}

		@Override
		public Y getValue() {
			return value;
		}

		@Override
		public Y setValue(Y value) {
			Y oldValue = this.value;
			this.value = value;
			return oldValue;
		}

	}
}
