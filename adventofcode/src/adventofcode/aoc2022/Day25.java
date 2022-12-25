package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day25 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day25.test");
		List<String> input = IOUtil.readFile("2022/day25.data");

		run(test1);
		run(input);

	}

	private static void run(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		long result = 0;

		for (String in : input) {
			result += parse(in);
		}

		System.out.println("# " + result);

		Ansi.foreground(0x00bfff);
		System.out.println(
				MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start, convert(result)));
	}

	private static String convert(long value) {
		String n = Long.toString(value, 5);

		System.out.println("? " + n);

		if (n.indexOf('3') == -1 && n.indexOf('4') == -1) {
			return n;
		}

		List<Character> cs = new ArrayList<>();

		for (int i = 0; i < n.length(); i++) {
			cs.add(n.charAt(i));
		}

		int j = 0;

		while (j < cs.size()) {
			char c = cs.get(j);
			switch (c) {
			case '3':
				cs.set(j, '=');
				incrementPrevious(cs, j);
				break;
			case '4':
				cs.set(j, '-');
				incrementPrevious(cs, j);
				break;
			}
			j++;
		}

		return cs.stream().map(c -> "" + c).collect(Collectors.joining());
	}

	private static void incrementPrevious(List<Character> cs, int j) {
		while (j > 0) {
			switch (cs.get(j - 1)) {
			case '=':
				cs.set(j - 1, '-');
				return;
			case '-':
				cs.set(j - 1, '0');
				return;
			case '0':
				cs.set(j - 1, '1');
				return;
			case '1':
				cs.set(j - 1, '2');
				return;
			case '2':
				cs.set(j - 1, '=');
				break;
			}

			j--;
		}
		if (j == 0) {
			cs.add(0, '1');
			return;
		}
	}

	private static long parse(String in) {
		long value = 0;

		for (int k = 0; k < in.length(); k++) {
			value *= 5;
			char c = in.charAt(k);
			switch (c) {
			case '-':
				value -= 1;
				break;
			case '=':
				value -= 2;
				break;
			default:
				value += c - '0';
				break;
			}
		}
		return value;
	}

}
