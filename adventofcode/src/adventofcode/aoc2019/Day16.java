package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.List;

public class Day16 {

	static final String data = "59766299734185935790261115703620877190381824215209853207763194576128635631359682876612079355215350473577604721555728904226669021629637829323357312523389374096761677612847270499668370808171197765497511969240451494864028712045794776711862275853405465401181390418728996646794501739600928008413106803610665694684578514524327181348469613507611935604098625200707607292339397162640547668982092343405011530889030486280541249694798815457170337648425355693137656149891119757374882957464941514691345812606515925579852852837849497598111512841599959586200247265784368476772959711497363250758706490540128635133116613480058848821257395084976935351858829607105310340";

	public static void main(String[] args) {

		// System.out.println("test1: " + fft1("12345678", 4));
		// System.out.println("test2: " + fft1("80871224585914546619083218645595", 100));
		// System.out.println("test3: " + fft1("19617804207202209144916044189917", 100));
		// System.out.println("test4: " + fft1("69317163492948606335995924319873", 100));
		// System.out.println("part1: " + fft1(data, 100));

		System.out.println("test5: " + fft2("03036732577212944063491565474664", 100));
		System.out.println("test6: " + fft2("02935109699940807407585447034323", 100));
		System.out.println("test7: " + fft2("03081770884921959731165446850517", 100));
		System.out.println("part2: " + fft2(data, 100));
	}

	static String fft2(String input, int rounds) {
		int offset = Integer.parseInt(input.substring(0, 7));
		// if (true) {
		System.out.println("1: " + offset + " " + (input.length() * 10_000));
		// }

		List<Integer> k0 = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			k0.add(Integer.parseInt(input.substring(i, i + 1)));
		}
		List<Integer> k = new ArrayList<>();
		for (int i = 0; i < 10_000; i++) {
			k.addAll(k0);
		}
		System.out.println("2: " + k.size());

		k = k.subList(offset, k.size());
		// for (int i = 0; i < offset; i++) {
		// k.remove(0);
		// }

		System.out.println("3: " + k.size());
		for (int r = 0; r < rounds; r++) {
			if (r % 10 == 0)
				System.out.println();

			for (int i = k.size() - 2; i >= 0; i--) {
				k.set(i, (k.get(i) + k.get(i + 1)) % 10);
			}
			System.out.print('.');
		}

		return k.subList(0, 8).stream().map(v -> "" + v).reduce((a, b) -> a + b).orElse("");
	}

	static String fft1(String input, int rounds) {
		List<Integer> k = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			k.add(Integer.parseInt(input.substring(i, i + 1)));
		}

		for (int r = 0; r < rounds; r++) {
			List<Integer> k2 = new ArrayList<>();
			for (int i = 0; i < k.size(); i++) {
				k2.add(calculateDigit(k, i + 1));
			}
			k = k2;
		}

		return k.subList(0, 8).stream().map(v -> "" + v).reduce((a, b) -> a + b).orElse("");
	}

	static int calculateDigit2(List<Integer> values, int index) {
		int s = 0;
		for (int i = index; i < values.size(); i++) {
			s += values.get(i);
		}
		return s % 10;
	}

	static int calculateDigit(List<Integer> values, int reuseFactor) {

		// starting with its own index, add (index) count values
		// skip (index) count values
		// subtract (index) count values
		// skip (index) count values
		// continue to the end

		// skip first and third group, as they are zero

		// sum second (positive) and fourth group separately
		int group2 = 0;
		int group4 = 0;

		// first loop: repetitions
		for (int i = -1; i < values.size(); i += reuseFactor * 4) {
			for (int j = i + reuseFactor; j < Math.min(i + 2 * reuseFactor, values.size()); j++) {
				group2 += values.get(j);
			}
			for (int j = i + 3 * reuseFactor; j < Math.min(i + 4 * reuseFactor, values.size()); j++) {
				group4 += values.get(j);
			}
		}

		return Math.abs(group2 - group4) % 10;
	}

}
