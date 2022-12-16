package adventofcode.aoc2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day14 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day14.test");
		List<String> data = IOUtil.readFile("2021/day14.data");

		String testTemplate = getTemplate(test);
		Map<String, String> testModifications = getModifications(test);

		String template = getTemplate(data);
		Map<String, String> modifications = getModifications(data);

		part1(testTemplate, testModifications);
		part1(template, modifications);

		// part2(testTemplate, testModifications);
		// part2(template, modifications);
	}

	private static void part2(String template, Map<String, String> modifications) {
		X x = new X(modifications);
		x.countBase(template);

		for (int i = 1; i < template.length(); i++) {
			x.process(0, template.charAt(i - 1), template.charAt(i));
		}

		long mostCommon = x.occurrences.values().stream().reduce(Math::max).orElse(0L);
		long leastCommon = x.occurrences.values().stream().reduce(Math::min).orElse(0L);

		System.out.println("part2: " + (mostCommon - leastCommon));
	}

	private static class X {
		Map<Character, Long> occurrences = new HashMap<>();
		Map<String, Character> modifications = new HashMap<>();

		X(Map<String, String> mod) {
			for (Map.Entry<String, String> e : mod.entrySet()) {
				modifications.put(e.getKey(), e.getValue().charAt(0));
			}
		}

		void countBase(String template) {
			for (int i = 0; i < template.length(); i++) {
				char c = template.charAt(i);
				Long k = occurrences.get(c);
				if (k == null) {
					k = 0L;
				}
				occurrences.put(c, k + 1);
			}
		}

		void process(int depth, char a, char b) {
			if (depth == 40)
				return;

			char c = modifications.get("" + a + b);

			Long k = occurrences.get(c);
			if (k == null) {
				k = 0L;
			}
			occurrences.put(c, k + 1);

			process(depth + 1, a, c);
			process(depth + 1, c, b);
		}
	}

	private static void part1(String template, Map<String, String> modifications) {
		String s = template;
		for (int i = 0; i < 10; i++) {
			int k = s.length() - 2;
			while (k >= 0) {
				String p = s.substring(k, k + 2);
				String m = modifications.get(p);
				s = s.substring(0, k + 1) + m + s.substring(k + 1);
				k--;
			}
		}
		System.out.println("# " + s.length());

		Map<Character, Long> occurrences = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			Long k = occurrences.get(c);
			if (k == null) {
				k = 0L;
			}
			occurrences.put(c, k + 1);
		}
		long mostCommon = occurrences.values().stream().reduce(Math::max).orElse(0L);
		long leastCommon = occurrences.values().stream().reduce(Math::min).orElse(0L);

		System.out.println("part1: " + (mostCommon - leastCommon));

		Map<String, Map<Character, Long>> subsets = new HashMap<>();

		for (int i = 1; i < s.length(); i++) {
			char s1 = s.charAt(i - 1);
			char s2 = s.charAt(i);

			Map<Character, Long> mcl = subsets.get("" + s1 + s2);
			if (mcl == null) {
				X x = new X(modifications);
				x.process(10, s1, s2);
				mcl = x.occurrences;
				subsets.put("" + s1 + s2, mcl);
				System.out.println("# " + s1 + s2);
			}
			for (Map.Entry<Character, Long> e : mcl.entrySet()) {
				Long count = occurrences.get(e.getKey());
				if (count == null)
					count = 0L;
				occurrences.put(e.getKey(), count + e.getValue());
			}
		}
		mostCommon = occurrences.values().stream().reduce(Math::max).orElse(0L);
		leastCommon = occurrences.values().stream().reduce(Math::min).orElse(0L);

		System.out.println("part2: " + (mostCommon - leastCommon));
	}

	private static String getTemplate(List<String> data) {
		return data.get(0);
	}

	private static Map<String, String> getModifications(List<String> data) {
		Map<String, String> m = new HashMap<>();
		for (int i = 2; i < data.size(); i++) {
			String d = data.get(i);
			m.put(d.substring(0, 2), d.substring(6));
		}
		return m;
	}
}
