package adventofcode.aoc2022;

import java.util.List;

import adventofcode.aoc2022.ElfShellProcessor.ElfDir;
import adventofcode.util.IOUtil;

public class Day07 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2022/day07.test");
		List<String> input = IOUtil.readFile("2022/day07.data");

		part1(test);
		part1(input);
	}

	private static void part1(List<String> input) {
		ElfShellProcessor esp = new ElfShellProcessor();
		esp.parseShellTranscript(input);

		esp.root.calculateSize();

		System.out.println("part1: " + sumDirectories(esp.root));

		long disksize = 70_000_000L;

		long free = disksize - esp.root.totalSize;

		long requiredForUpdate = 30_000_000L;

		long deleteMinimum = requiredForUpdate - free;

		System.out.println("part2: " + findSmallestPossibleDirectory(esp.root, deleteMinimum));
	}

	private static long findSmallestPossibleDirectory(ElfDir dir, long deleteMinimum) {
		long candidate = Long.MAX_VALUE;

		if (dir.totalSize >= deleteMinimum) {
			candidate = Math.min(candidate, dir.totalSize);
		}

		for (ElfDir s : dir.subdirs.values()) {
			candidate = Math.min(candidate, findSmallestPossibleDirectory(s, deleteMinimum));
		}

		return candidate;
	}

	private static long sumDirectories(ElfDir dir) {
		long sum = 0;
		if (dir.totalSize <= 100_000L) {
			sum += dir.totalSize;
		}

		for (ElfDir s : dir.subdirs.values()) {
			sum += sumDirectories(s);
		}

		return sum;
	}

}
