package adventofcode.aoc2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day20 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day20.test");
		List<String> data = IOUtil.readFile("2021/day20.data");

		part1test(test);
		part1(data);
		part2test(test);
		part2(data);
	}

	private static void part1test(List<String> data) {
		String alg = data.get(0);
		Map<Integer, Set<Integer>> image = parseImage(data);

		// print(image);

		Map<Integer, Set<Integer>> image2 = enhance(alg, image);
		System.out.println("# " + image2.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
		// print(image2);
		Map<Integer, Set<Integer>> image3 = enhance(alg, image2);

		System.out.println("part1: " + image3.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
		// print(image3);
	}

	private static void part2test(List<String> data) {
		String alg = data.get(0);
		Map<Integer, Set<Integer>> image = parseImage(data);

		// print(image);
		for (int i = 0; i < 25; i++) {
			Map<Integer, Set<Integer>> image2 = enhance(alg, image);
			System.out.println("# " + image2.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
			// print(image2);
			image = enhance(alg, image2);
		}
		System.out.println("part2: " + image.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
		// print(image3);
	}

	private static void part1(List<String> data) {
		String alg = data.get(0);
		Map<Integer, Set<Integer>> image = parseImage(data);

		// print(image);

		Map<Integer, Set<Integer>> image2 = enhance2Negative(alg, image);
		System.out.println("# " + image2.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
		// print(image2);
		Map<Integer, Set<Integer>> image3 = enhanceFromNegative(alg, image2);

		System.out.println("part1: " + image3.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
		// print(image3);
	}

	private static void part2(List<String> data) {
		String alg = data.get(0);
		Map<Integer, Set<Integer>> image = parseImage(data);

		// print(image);

		for (int i = 0; i < 25; i++) {
			Map<Integer, Set<Integer>> image2 = enhance2Negative(alg, image);
			System.out.println("# " + image2.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
			// print(image2);
			image = enhanceFromNegative(alg, image2);
		}

		System.out.println("part2: " + image.values().stream().map(s -> s.size()).reduce((a, b) -> a + b).orElse(0));
		// print(image3);
	}

	private static Map<Integer, Set<Integer>> enhance(String alg, Map<Integer, Set<Integer>> image) {
		Map<Integer, Set<Integer>> image2 = new HashMap<>();

		int x1 = minX(image);
		int x2 = maxX(image);
		int y1 = minY(image);
		int y2 = maxY(image);

		for (int y = y1 - 1; y <= y2 + 1; y++) {
			Set<Integer> lit = new HashSet<>();
			image2.put(y, lit);
			for (int x = x1 - 1; x <= x2 + 1; x++) {
				int info = getPixelInfo(image, x, y, false);
				if (alg.charAt(info) == '#') {
					lit.add(x);
				}
			}
		}

		return image2;
	}

	private static Map<Integer, Set<Integer>> enhance2Negative(String alg, Map<Integer, Set<Integer>> image) {
		Map<Integer, Set<Integer>> image2 = new HashMap<>();

		int x1 = minX(image);
		int x2 = maxX(image);
		int y1 = minY(image);
		int y2 = maxY(image);

		for (int y = y1 - 1; y <= y2 + 1; y++) {
			Set<Integer> lit = new HashSet<>();
			image2.put(y, lit);
			for (int x = x1 - 1; x <= x2 + 1; x++) {
				int info = getPixelInfo(image, x, y, false);
				if (alg.charAt(info) == '.') {
					lit.add(x);
				}
			}
		}

		return image2;
	}

	private static Map<Integer, Set<Integer>> enhanceFromNegative(String alg, Map<Integer, Set<Integer>> image) {
		Map<Integer, Set<Integer>> image2 = new HashMap<>();

		int x1 = minX(image);
		int x2 = maxX(image);
		int y1 = minY(image);
		int y2 = maxY(image);

		for (int y = y1 - 1; y <= y2 + 1; y++) {
			Set<Integer> lit = new HashSet<>();
			image2.put(y, lit);
			for (int x = x1 - 1; x <= x2 + 1; x++) {
				int info = getPixelInfo(image, x, y, true);
				if (alg.charAt(info) == '#') {
					lit.add(x);
				}
			}
		}

		return image2;
	}

	private static void print(Map<Integer, Set<Integer>> image) {
		int x1 = minX(image);
		int x2 = maxX(image);
		int y1 = minY(image);
		int y2 = maxY(image);

		for (int y = y1; y <= y2; y++) {
			for (int x = x1; x <= x2; x++) {
				int p = getPixel(image, x, y, false);
				System.out.print(p == 1 ? '#' : '.');
			}
			System.out.println();
		}
	}

	private static int getPixelInfo(Map<Integer, Set<Integer>> image, int x, int y, boolean negative) {
		int r = getPixel(image, x - 1, y - 1, negative) << 8;
		r += getPixel(image, x, y - 1, negative) << 7;
		r += getPixel(image, x + 1, y - 1, negative) << 6;

		r += getPixel(image, x - 1, y, negative) << 5;
		r += getPixel(image, x, y, negative) << 4;
		r += getPixel(image, x + 1, y, negative) << 3;

		r += getPixel(image, x - 1, y + 1, negative) << 2;
		r += getPixel(image, x, y + 1, negative) << 1;
		r += getPixel(image, x + 1, y + 1, negative);

		return r;
	}

	private static int getPixel(Map<Integer, Set<Integer>> image, int x, int y, boolean negative) {
		Set<Integer> s = image.get(y);
		if (s == null)
			return negative ? 1 : 0;
		return s.contains(x) != negative ? 1 : 0;
	}

	private static Map<Integer, Set<Integer>> parseImage(List<String> data) {
		Map<Integer, Set<Integer>> image = new HashMap<>();
		for (int y = 2; y < data.size(); y++) {
			String row = data.get(y);
			Set<Integer> lit = new HashSet<>();
			for (int x = 0; x < row.length(); x++) {
				if (row.charAt(x) == '#') {
					lit.add(x);
				}
			}
			image.put(y - 2, lit);
		}
		return image;
	}

	private static int minX(Map<Integer, Set<Integer>> image) {
		return image.values().stream().flatMap(s -> s.stream()).reduce(Math::min).orElse(0);
	}

	private static int maxX(Map<Integer, Set<Integer>> image) {
		return image.values().stream().flatMap(s -> s.stream()).reduce(Math::max).orElse(0);
	}

	private static int minY(Map<Integer, Set<Integer>> image) {
		return image.keySet().stream().reduce(Math::min).orElse(0);
	}

	private static int maxY(Map<Integer, Set<Integer>> image) {
		return image.keySet().stream().reduce(Math::max).orElse(0);
	}
}
