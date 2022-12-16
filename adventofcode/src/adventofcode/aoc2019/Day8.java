package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day8 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2019/day8.data");
		part1(data.get(0), 25, 6);
		part2(data.get(0), 25, 6);
	}

	static void part1(String data, int maxX, int maxY) {
		List<String> layers = separateLayers(data, maxX, maxY);

		List<Integer> zeroes = layers.stream().map( //
				s -> (int) s.chars().filter(c -> c == '0').count() //
		).collect(Collectors.toList());

		int minZeroes = Integer.MAX_VALUE;
		int pos = 0;

		for (int i = 0; i < zeroes.size(); i++) {
			int z = zeroes.get(i);
			if (z < minZeroes) {
				minZeroes = z;
				pos = i;
			}
		}

		String layer = layers.get(pos);

		int ones = (int) layer.chars().filter(c -> c == '1').count();
		int twos = (int) layer.chars().filter(c -> c == '2').count();

		System.out.println("part1: " + (ones * twos));
	}

	static void part2(String data, int maxX, int maxY) {
		List<String> layers = separateLayers(data, maxX, maxY);

		String lower = layers.get(layers.size() - 1);

		for (int i = layers.size() - 2; i >= 0; i--) {
			StringBuilder result = new StringBuilder();
			String upper = layers.get(i);

			for (int j = 0; j < lower.length(); j++) {
				char c = upper.charAt(j);
				if (c == '2') {
					result.append(lower.charAt(j));
				} else {
					result.append(c);
				}
			}

			lower = result.toString();
		}

		System.out.println("part2:");
		for (int k = 0; k < maxY; k++) {
			System.out.println(lower.substring(k * maxX, (k + 1) * maxX));
		}
		// was RUZBP
	}

	private static List<String> separateLayers(String data, int maxX, int maxY) {
		int sectionlength = maxX * maxY;
		List<String> layers = new ArrayList<>();
		String data1 = data;
		while (!"".equals(data1)) {
			layers.add(data1.substring(0, sectionlength));
			data1 = data1.substring(sectionlength);
		}
		return layers;
	}
}
