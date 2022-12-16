package adventofcode.aoc2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElfShellProcessor {

	final ElfDir root = new ElfDir(null);
	private ElfDir current = root;

	public void parseShellTranscript(List<String> transcript) {
		for (String t : transcript) {
//			System.out.println(t);
			String[] p = t.split(" ");
			switch (p[0]) {
			case "$":// command
				switch (p[1]) {
				case "cd":
					changeDirectory(p[2]);
					break;
				// ls irrelevant
				}
				break;
			case "dir":
				ElfDir sub = current.subdirs.get(p[1]);
				if (sub == null) {
					current.subdirs.put(p[1], new ElfDir(current));
				}
				break;
			default:
				current.files.put(p[1], Long.parseLong(p[0]));
				break;
			}
		}
	}

	private void changeDirectory(String target) {
		switch (target) {
		case "/":
			current = root;
			break;
		case "..":
			current = current.parent;
			break;
		default:
			ElfDir s = current.subdirs.get(target);
			if (s == null) {
				s = new ElfDir(current);
				current.subdirs.put(target, s);
			}
			current = s;
			break;
		}
	}

	public static class ElfDir {

		final ElfDir parent;

		Map<String, Long> files = new HashMap<>();

		Map<String, ElfDir> subdirs = new HashMap<>();

		long totalSize;

		ElfDir(ElfDir parent) {
			this.parent = parent;
		}

		void calculateSize() {
			subdirs.values().forEach(d -> d.calculateSize());

			totalSize = files.values().stream().collect(Collectors.summingLong(l -> l))
					+ subdirs.values().stream().map(d -> d.totalSize).collect(Collectors.summingLong(l -> l));
		}
	}
}
