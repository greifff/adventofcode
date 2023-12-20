package adventofcode.aoc2023.day20;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.aoc2023.Day08b;
import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day20 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day20.test");
		List<String> test2 = IOUtil.readFile("2023/day20.test2");
		List<String> input = IOUtil.readFile("2023/day20.data");

		part1(test);
		part1(test2);
		part1(input);
//		part2(test);
		part2(input);
	}

	private static void part1(List<String> input) {
		Ansi.foreground(0, 255, 0);

		Map<String, CommunicationModule> modules = new HashMap<>();
		modules.put("broadcaster", new Broadcaster());
		modules.put("output", new Output());
		Output rx = new Output();
		modules.put("rx", rx);

		parse(input, modules);

		CommunicationModule broadcaster = modules.get("broadcaster");
		CommunicationModule button = new Button();
		for (int i = 0; i < 1000; i++) {
//			System.out.println("  button -low-> broadcaster");
			broadcaster.receive(button, false);
			broadcaster.send();
			List<CommunicationModule> toProcess = new LinkedList<>(broadcaster.getReceivers());

			// process all other nodes
			while (!toProcess.isEmpty()) {
//				System.out.println("? " + toProcess.size());
				toProcess.forEach(c -> c.process());
				List<CommunicationModule> toProcess2 = new LinkedList<>();
				for (CommunicationModule module1 : toProcess) {
					boolean hasSend = module1.send();
					if (hasSend) {
						toProcess2.addAll(module1.getReceivers());
					}
				}
				toProcess = toProcess2;// * .stream().distinct().toList() */);
			}
			if (rx.lowPulses > 0) {
				System.out.println("part2: " + (i + 1));
				return;
			}
		}

		long lowCount = 1000
				+ modules.values().stream().map(m -> m.getLowPulsesSend()).reduce((a, b) -> a + b).orElse(0L);
		long highCount = modules.values().stream().map(m -> m.getHighPulsesSend()).reduce((a, b) -> a + b).orElse(0L);

		System.out.println("# " + lowCount + " " + highCount);

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + (lowCount * highCount));
	}

	private static void parse(List<String> input, Map<String, CommunicationModule> modules) {
		for (String in : input) {
			StringTokenizer st = new StringTokenizer(in, " ->,");
			String start = st.nextToken();
			getModule(start, modules);
		}
		for (String in : input) {
			StringTokenizer st = new StringTokenizer(in, " ->,");
			String start = st.nextToken();
			CommunicationModule sender = getModule(start, modules);
			while (st.hasMoreTokens()) {
				CommunicationModule receiver = getModule(st.nextToken(), modules);
				sender.addReceiver(receiver);
				receiver.addSender(sender);
			}
		}
	}

	private static CommunicationModule getModule(String name, Map<String, CommunicationModule> modules) {
		String realname = name;
		if (name.charAt(0) == '%' || name.charAt(0) == '&') {
			realname = name.substring(1);
		}
		CommunicationModule module = modules.get(realname);
		if (module != null)
			return module;
		if (name.charAt(0) == '%') {
			module = new FlipFlop(realname);
		} else if (name.charAt(0) == '&') {
			module = new Conjunction(realname);
		} else {
			System.out.println("! " + name);
			module = new Output();
		}
		modules.put(realname, module);
		return module;
	}

	private static void part2(List<String> input) {
		Ansi.foreground(0, 255, 0);

		Map<String, CommunicationModule> modules = new HashMap<>();
		modules.put("broadcaster", new Broadcaster());
		modules.put("output", new Output());
		Output rx = new Output();
		modules.put("rx", rx);

		parse(input, modules);

		List<CommunicationModule> specialInterest = new ArrayList<>(
				Arrays.asList(modules.get("sp"), modules.get("cc"), modules.get("jq"), modules.get("nx")));

		Map<CommunicationModule, List<Long>> cycles = new HashMap<>();

		specialInterest.forEach(cm -> cycles.put(cm, new ArrayList<>()));

		CommunicationModule broadcaster = modules.get("broadcaster");
		CommunicationModule button = new Button();
		for (int i = 0;; i++) {
//			System.out.println("  button -low-> broadcaster");
			if (i % 10000 == 0) {
				System.out.println("= " + i);
			}
			broadcaster.receive(button, false);
			broadcaster.send();
			List<CommunicationModule> toProcess = new LinkedList<>(broadcaster.getReceivers());

			// process all other nodes
			while (!toProcess.isEmpty()) {
//				System.out.println("? " + toProcess.size());
				toProcess.forEach(c -> c.process());
				List<CommunicationModule> toProcess2 = new LinkedList<>();
				for (CommunicationModule module1 : toProcess) {
					boolean hasSend = module1.send();
					if (hasSend) {
						toProcess2.addAll(module1.getReceivers());
					}
				}
				toProcess = toProcess2;// * .stream().distinct().toList() */);
			}

			for (CommunicationModule s : specialInterest) {
				if (s.getHighPulsesSend() > 0) {
					cycles.get(s).add(i + 1L);
					s.resetCounter();
				}
			}
			if (cycles.values().stream().allMatch(ll -> ll.size() > 2))
				break;
		}

		System.out.println(
				"ß " + cycles.values().stream().map(m -> m.stream().map(m1 -> "" + m1).collect(Collectors.joining(",")))
						.collect(Collectors.joining(" ")));

		Ansi.foreground(0, 255, 255);
		System.out.println("part2: " + cycles.values().stream().map(l -> BigInteger.valueOf(l.get(0)))
				.reduce((a, b) -> Day08b.lcm(a, b)).orElse(BigInteger.ZERO)

		);

		// 3869180838598288 is too high,
		// 483647604824786 is too high
	}

}
