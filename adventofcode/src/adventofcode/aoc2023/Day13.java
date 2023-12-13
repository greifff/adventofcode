package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day13 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day13.test");
		List<String> input = IOUtil.readFile("2023/day13.data");

		run(test);
		run(input);
	}

	private static void run(List<String> input) {
		Ansi.foreground(0, 255, 0);

		List<Pattern> patterns = new ArrayList<>();
		int i = 0;
		int j = 0;
		while (i < input.size()) {
			while (j < input.size() && !"".equals(input.get(j))) {
				j++;
			}
			patterns.add(new Pattern(new ArrayList<>(input.subList(i, j))));
			i = j + 1;
			j = i;
		}

		long sumh = 0;
		long sumv = 0;
		for (Pattern p : patterns) {
			long h = p.horizontalReflection().stream().findAny().orElse(0);
			long v = p.verticalReflection().stream().findAny().orElse(0);

			sumh += h;
			sumv += v;
		}

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + (sumh * 100 + sumv));
		// its not 24299, 20344, 20330

		long sum2 = 0;
		for (Pattern p : patterns) {
			long x = p.unsmudge();
			sum2 += x;
		}
		System.out.println("part2: " + sum2);
		// 22297 is too low, 36646, 36841 is too high
	}

	static class Pattern {
		List<String> lines;

		List<Integer> detectedFirstPass = new ArrayList<>();

		Pattern(List<String> lines) {
			this.lines = lines;
		}

		List<Integer> horizontalReflection() {
			List<Integer> c = horizontalReflectionCandidates();
			List<Integer> v = verifyHorizontalReflections(c);

			detectedFirstPass.addAll(v);

			return v;
		}

		List<Integer> horizontalReflectionCandidates() {
			List<Integer> result = new ArrayList<>();
			String a = lines.get(0);
			for (int i = 1; i < lines.size(); i++) {
				String b = lines.get(i);
				if (a.equals(b)) {
					result.add(i);
				}
				a = b;
			}
			return result;
		}

		List<Integer> verifyHorizontalReflections(List<Integer> candidates) {
			int size = lines.size();
			List<Integer> verified = new ArrayList<>();
			outer: for (int c : candidates) {
				int deviation = Math.min(c, size - c);
				for (int d = 1; d < deviation; d++) {
					if (!lines.get(c - 1 - d).equals(lines.get(c + d))) {
						continue outer;
					}
				}
				verified.add(c);
			}
			return verified;
		}

		List<Integer> verticalReflection() {
			List<String> t1 = new ArrayList<>();

			for (int j = 0; j < lines.get(0).length(); j++) {
				StringBuilder sb = new StringBuilder();
				for (int i = lines.size() - 1; i >= 0; i--) {
					sb.append(lines.get(i).charAt(j));
				}
				t1.add(sb.toString());
			}

			Pattern tilted = new Pattern(t1);

			List<Integer> result = tilted.horizontalReflection();

			detectedFirstPass.addAll(tilted.detectedFirstPass.stream().map(i -> -i).toList());

			return result;
		}

		int unsmudge() {
			int oldReflectionLine = detectedFirstPass.get(0);
			Set<Integer> horizontal = new HashSet<>();
			Set<Integer> vertical = new HashSet<>();
			for (int i = 0; i < lines.size(); i++) {
				for (int j = 0; j < lines.get(0).length(); j++) {
					List<String> clean = new ArrayList<>(lines);
					String x = lines.get(i);
					String y = x.substring(0, j) //
							+ (x.charAt(j) == '.' ? '#' : '.') //
							+ x.substring(j + 1);
					clean.set(i, y);
					Pattern u = new Pattern(clean);
					List<Integer> rh = u.horizontalReflection();
					List<Integer> rv = u.verticalReflection();

					horizontal.addAll(rh);
					vertical.addAll(rv);
				}
			}
			horizontal.remove(0);
			horizontal.remove(oldReflectionLine);
			vertical.remove(0);
			vertical.remove(-oldReflectionLine);

			return horizontal.stream().findAny().orElse(0) * 100 + vertical.stream().findAny().orElse(0);
		}

	}

}
