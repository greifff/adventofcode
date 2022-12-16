package adventofcode.aoc2021;

import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day1 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2021/day1.data");
		List<Integer> dataCnv = data.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

		part1(dataCnv);
		part2(dataCnv);
	}

	private static void part1(List<Integer> dataCnv) {
		int increases = 0;
		for (int i = 1; i < dataCnv.size(); i++) {
			if (dataCnv.get(i) > dataCnv.get(i - 1)) {
				increases++;
			}
		}
		System.out.println("part1: " + increases);
	}

	private static void part2(List<Integer> dataCnv) {
		int increases = 0;
		for (int i = 3; i < dataCnv.size(); i++) {
			if ((dataCnv.get(i) + dataCnv.get(i - 1) + dataCnv.get(i - 2)) > (dataCnv.get(i - 1) + dataCnv.get(i - 2) + dataCnv.get(i - 3))) {
				increases++;
			}
		}
		System.out.println("part2: " + increases);
	}
}
