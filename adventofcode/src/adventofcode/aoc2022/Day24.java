package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day24 {

	private static final int FACTOR = 100;

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day24.test");
		List<String> input = IOUtil.readFile("2022/day24.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		int maxx = input.get(0).length() - 2;
		int maxy = input.size() - 2;

		List<Blizzard> blizzards = getBlizzards(input);

		int target = (input.get(0).length() - 2) * FACTOR + input.size() - 1;

		int minutes = run(blizzards, FACTOR, target, maxx, maxy);

		Ansi.foreground(0x00bfff);
		System.out.println(MessageFormat.format("time: {0}, part1: {1}", System.currentTimeMillis() - start, minutes));
	}

	private static int run(List<Blizzard> blizzards, int startElf, int targetElf, int maxx, int maxy) {
		Set<Integer> elves = new HashSet<Integer>();
		elves.add(startElf); // start

		int minutes = 0;

		Set<Integer> blocked;
		Set<Integer> futureBlocked = blizzards.stream().map(b -> b.position).collect(Collectors.toSet());

		while (true) {

//			Ansi.foreground(0xff0000);
//			System.out.println("Minute " + minutes);
//			debug(blizzards, elves, maxx, maxy);

			for (Blizzard b : blizzards) {
				b.move();
			}
			blocked = futureBlocked;
			futureBlocked = blizzards.stream().map(b -> b.position).collect(Collectors.toSet());

			elves.removeAll(blocked); // death

			Set<Integer> futureElves = new HashSet<>();

			for (int elf : elves) {
				if (elf == targetElf - 1 || elf == targetElf + 1) {
					return minutes + 1;
				}
				int x = elf / FACTOR;
				int y = elf % FACTOR;

				if (y <= maxy && x > 1)
					futureElves.add(elf - FACTOR);
				if (y > 0 && x < maxx)
					futureElves.add(elf + FACTOR);
				if (y > 1)
					futureElves.add(elf - 1);
				if (y < maxy)
					futureElves.add(elf + 1);
			}

			futureElves.removeAll(futureBlocked);

			minutes++;

			elves.addAll(futureElves);

			if (elves.contains(targetElf))
				return minutes;
		}
	}

	private static void debug(List<Blizzard> blizzards, Set<Integer> elves, int maxx, int maxy) {
		System.out.println("Blizzards: " + blizzards.size() + " " + elves.size());

		for (int y = 0; y <= maxy + 1; y++) {
			for (int x = 0; x <= maxx + 1; x++) {
				int p = x * FACTOR + y;

				Optional<Character> b = blizzards.parallelStream().filter(b1 -> b1.position == p)
						.map(b2 -> b2.direction).findAny();

				if (b.isPresent()) {
					Ansi.foreground(0xffffff);
					System.out.print(b.get());
				} else if (elves.contains(p)) {
					Ansi.foreground(0x00ff00);
					System.out.print('E');
				} else {
					Ansi.foreground(0x808080);
					System.out.print('.');
				}

			}
			System.out.println();
		}
	}

	private static List<Blizzard> getBlizzards(List<String> input) {
		List<Blizzard> b = new ArrayList<>();
		for (int y = 1; y < input.size() - 1; y++) {
			String row = input.get(y);
			for (int x = 1; x < row.length() - 1; x++) {
				char c = row.charAt(x);
				if (c != '.' && c != '#') {
					b.add(new Blizzard(x * FACTOR + y, c, row.length() - 2, input.size() - 2));
				}
			}
		}
		return b;
	}

	static class Blizzard {
		int position;
		char direction;
		int maxx;
		int maxy;

		Blizzard(int position, char direction, int maxx, int maxy) {
			this.position = position;
			this.direction = direction;
			this.maxx = maxx;
			this.maxy = maxy;
		}

		void move() {
			position = next();
		}

		int next() {
			int x = position / FACTOR;
			int y = position % FACTOR;
			switch (direction) {
			case '^':
				y--;
				if (y < 1)
					y = maxy;
				break;
			case 'v':
				y++;
				if (y > maxy)
					y = 1;
				break;
			case '<':
				x--;
				if (x < 1)
					x = maxx;
				break;
			case '>':
				x++;
				if (x > maxx)
					x = 1;
				break;
			}
			return x * FACTOR + y;
		}
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0x00ff00);
		long start = System.currentTimeMillis();

		int maxx = input.get(0).length() - 2;
		int maxy = input.size() - 2;

		List<Blizzard> blizzards = getBlizzards(input);

		int target = (input.get(0).length() - 2) * FACTOR + input.size() - 1;

		int minutes = run(blizzards, FACTOR, target, maxx, maxy);

		System.out.println("# " + minutes);

		minutes += run(blizzards, target, FACTOR, maxx, maxy);

		System.out.println("# " + minutes);

		minutes += run(blizzards, FACTOR, target, maxx, maxy);

		// 632 too low
		Ansi.foreground(0x00bfff);
		System.out.println(MessageFormat.format("time: {0}, part2: {1}", System.currentTimeMillis() - start, minutes));
	}

}
