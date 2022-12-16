package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day8 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2021/day8.test1");
		List<String> test2 = IOUtil.readFile("2021/day8.test2");
		List<String> data = IOUtil.readFile("2021/day8.data");

		part1(test1);
		part1(test2);
		part1(data);
		part2(test1);
		part2(test2);
		part2(data);
	}

	private static void part2(List<String> data) {
		int sum = 0;
		for (String d : data) {
			int[] r = decodeLine(d);
			int b = r[10] * 1000 + r[11] * 100 + r[12] * 10 + r[13];
			sum += b;
		}
		System.out.println("part2: " + sum);
	}

	private static int[] decodeLine(String data) {
		List<String> parsed = parseLine(data);
		int[] result = new int[14];
		for (int i = 0; i < 14; i++)
			result[i] = -1;

		Map<Integer, String> mapping = new HashMap<>();
		for (int i = 0; i < 14; i++) {
			String p = parsed.get(i);
			int l = p.length();
			switch (l) {
			case 2:
				result[i] = 1;
				mapping.put(1, p);
				break;
			case 3:
				result[i] = 7;
				mapping.put(7, p);
				break;
			case 4:
				result[i] = 4;
				mapping.put(4, p);
				break;
			case 7:
				result[i] = 8;
				break;
			}
		}

		while (true) {
			for (int i = 0; i < 14; i++) {
				String p = parsed.get(i);
				int l = p.length();
				switch (l) {
				case 5: // 2,3,5
					int k = decide235(mapping, p);
					result[i] = k;
					mapping.put(k, p);
					break;
				case 6: // 0,6,9
					int r = decide069(mapping, p);
					result[i] = r;
					mapping.put(r, p);
					break;
				}
			}

			boolean allWell = true;
			for (int i = 0; i < 14; i++) {
				allWell &= result[i] != -1;
			}
			if (allWell)
				break;
		}

		return result;
	}

	private static int decide235(Map<Integer, String> mapping, String n) {
		if (n.equals(mapping.get(2)))
			return 2;
		if (n.equals(mapping.get(3)))
			return 3;
		if (n.equals(mapping.get(5)))
			return 5;

		String one = mapping.get(1);
		String four = mapping.get(4);

		String fourExtension = reduce(four, one);

		if (hasAllCharactersOf(n, one)) {
			return 3;
		} else if (hasAllCharactersOf(n, fourExtension)) {
			return 5;
		} else {
			return 2;
		}
	}

	private static String reduce(String n, String p) {
		String r = "";
		for (char c : n.toCharArray()) {
			if (p.indexOf(c) == -1)
				r += c;
		}
		return r;
	}

	private static int decide069(Map<Integer, String> mapping, String n) {
		if (n.equals(mapping.get(0)))
			return 0;
		if (n.equals(mapping.get(6)))
			return 6;
		if (n.equals(mapping.get(9)))
			return 9;

		String one = mapping.get(1);
		String four = mapping.get(4);

		if (hasAllCharactersOf(n, four)) {
			return 9;
		} else if (hasAllCharactersOf(n, one)) {
			return 0;
		} else {
			return 6;
		}
	}

	private static boolean hasAllCharactersOf(String n, String p) {
		if (p == null)
			return false;
		boolean result = true;
		for (int i = 0; i < p.length(); i++) {
			result &= n.indexOf(p.charAt(i)) != -1;
		}
		return result;
	}

	private static void part1(List<String> data) {
		int count = 0;
		for (String line : data) {
			List<String> input = parseLine(line);
			for (int i = 10; i < 14; i++) {
				String in = input.get(i);
				if (in.length() <= 4 || in.length() == 7)
					count++;
			}
		}
		System.out.println("part1: " + count);
	}

	private static List<String> parseLine(String line) {
		StringTokenizer st = new StringTokenizer(line, " |");
		List<String> result = new ArrayList<>();
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			List<Character> cs = s.chars().boxed().map(i -> (char) i.intValue()).collect(Collectors.toList());
			Collections.sort(cs);
			result.add(cs.stream().map(c -> "" + c).reduce((a, b) -> a + b).orElse(""));
		}
		return result;
	}

}
