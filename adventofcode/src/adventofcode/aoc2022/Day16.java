package adventofcode.aoc2022;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day16 {

	public static void main(String[] args) {
		List<String> test1 = IOUtil.readFile("2022/day16.test");
		List<String> input = IOUtil.readFile("2022/day16.data");

		part1(test1);
		part1(input);

		part2(test1);
		part2(input);
	}

	private static void part1(List<String> input) {

		Map<String, Valve> valves = input.stream().map(s -> new Valve(s))
				.collect(Collectors.toMap(v -> v.name, v -> v));

		Map<String, Integer> tunnelLengths = getTunnelLengths(valves);

		valves = clearupGraph(valves, tunnelLengths);

//		System.out.println("valves: " + valves.size() + " tunnels: " + tunnelLengths.size());

		dijkstra(valves, tunnelLengths);

//		System.out.println("valves: " + valves.size() + " tunnels: " + tunnelLengths.size());

		List<Attempt> attempts = new LinkedList<>();
		attempts.add(new Attempt(valves, tunnelLengths));

		int maxPressure = 0;

		while (!attempts.isEmpty()) {
			Attempt a = attempts.remove(0);

			maxPressure = Math.max(maxPressure, a.pressure);

			attempts.addAll(0, a.go());
		}

		System.out.println(MessageFormat.format("part1: {0}", maxPressure));

	}

	private static void dijkstra(Map<String, Valve> valves, Map<String, Integer> d) {

		List<String> valveNames = valves.keySet().stream().sorted().toList();

		for (int i = 0; i < valveNames.size() - 1; i++) {
			for (int j = i + 1; j < valveNames.size(); j++) {
				String p = valveNames.get(i) + "-" + valveNames.get(j);
				if (d.get(p) == null)
					d.put(p, Integer.MAX_VALUE);
			}
		}

		for (String valveName : valveNames) {
			dijkstra1(valveName, valves, d);
		}
	}

	private static void dijkstra1(String node, Map<String, Valve> valves, Map<String, Integer> d) {
		Set<String> nodes = new HashSet<>(valves.keySet());
		Map<String, Integer> distance = new HashMap<>();
		for (String node2 : nodes) {
			if (!node2.equals(node)) {
				distance.put(node2, d.get(getTunnelName(node, node2)));
			}
		}

		while (!nodes.isEmpty()) {
			Optional<Map.Entry<String, Integer>> x = distance.entrySet().stream()
					.filter(e -> nodes.contains(e.getKey()))
					.reduce((e1, e2) -> e1.getValue() < e2.getValue() ? e1 : e2);
			if (x.isPresent()) {
				String node2 = x.get().getKey();
				nodes.remove(node2);

				for (String node3 : valves.get(node2).tunnels) {
					if (nodes.contains(node3)) {
						dijkstraUpdate(node, node2, node3, distance, d);
					}
				}
			}
		}
	}

	private static void dijkstraUpdate(String node, String node2, String node3, Map<String, Integer> distance,
			Map<String, Integer> d) {
//		System.out.println("# " + node2 + "-" + node3 + "  " + getTunnelName(node2, node3) + " " + distance.get(node2)
//				+ " " + d.get(getTunnelName(node2, node3)));
		int alternative = distance.get(node2) + d.get(getTunnelName(node2, node3));
		if (distance.get(node3) == null || alternative < distance.get(node3)) {
			distance.put(node3, alternative);
			d.put(getTunnelName(node, node3), alternative);
		}
	}

	private static Map<String, Valve> clearupGraph(Map<String, Valve> valves, Map<String, Integer> tunnelLengths) {
		for (Valve valve : valves.values()) {
			valve.tunnels = new ArrayList<>();
		}

		for (String tunnel : tunnelLengths.keySet()) {
			String[] ends = tunnel.split("-");
			valves.get(ends[0]).tunnels.add(ends[1]);
			valves.get(ends[1]).tunnels.add(ends[0]);
		}

		return valves.entrySet().stream().filter(e -> e.getValue().name.equals("AA") || e.getValue().flowrate > 0)
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}

	private static Map<String, Integer> getTunnelLengths(Map<String, Valve> valves) {
		Map<String, Integer> tunnelLengths = new HashMap<>();

		Set<String> visited = new HashSet<>();
		for (Valve valve : valves.values()) {
			if (valve.name.equals("AA") || valve.flowrate > 0) {
				visited.add(valve.name);
				for (String next : valve.tunnels) {
					if (visited.contains(next))
						continue;

					Valve v2 = valves.get(next);
					int length = 1;
					while (v2.flowrate == 0 && !v2.name.equals("AA")) {
						visited.add(v2.name);
						List<String> other = new ArrayList<>(v2.tunnels);
						other.removeAll(visited);
						if (other.isEmpty()) {
							break;
						}
						v2 = valves.get(other.get(0));
						length++;
					}
					tunnelLengths.put(getTunnelName(valve, v2), length);
				}
			}
		}
		return tunnelLengths;
	}

	private static class Attempt {
		private final Map<String, Valve> valves;
		private final Map<String, Integer> tunnelLengths;

		String current = "AA";
		int minutesPast;
		int pressure;

		Set<String> closed = new HashSet<>();

		Attempt(Map<String, Valve> valves, Map<String, Integer> tunnelLengths) {
			this.valves = valves;
			this.tunnelLengths = tunnelLengths;
			closed = new HashSet<>(valves.keySet());
			closed.remove("AA");
		}

		List<Attempt> go() {
			List<Attempt> next = new ArrayList<>();

			for (String v2 : closed) {

				int length = tunnelLengths.get(getTunnelName(current, v2));

				if (minutesPast + length + 1 >= 30)
					continue;

				Attempt a = new Attempt(valves, tunnelLengths);
				a.minutesPast = minutesPast + length + 1;
				a.current = v2;
				a.closed = new HashSet<>(closed);
				a.closed.remove(v2);

				a.pressure = pressure + (30 - a.minutesPast) * valves.get(v2).flowrate;

				next.add(a);
			}

			return next;
		}

	}

	private static class Attempt2 {
		private final Map<String, Valve> valves;
		private final Map<String, Integer> tunnelLengths;

		String[] current = new String[] { "AA", "AA" };

		int[] transiting = new int[] { 0, 0 };
		int minutesPast = 4;
		int pressure;

		Set<String> closed = new HashSet<>();

		Attempt2(Map<String, Valve> valves, Map<String, Integer> tunnelLengths) {
			this.valves = valves;
			this.tunnelLengths = tunnelLengths;
			closed = new HashSet<>(valves.keySet());
			closed.remove("AA");
		}

		void walk() {
			int commonTransitTime = Math.min(transiting[0], transiting[1]);
			minutesPast += commonTransitTime;
			transiting[0] = transiting[0] - commonTransitTime;
			transiting[1] = transiting[1] - commonTransitTime;
		}

		List<Attempt2> go() {

			// work

			List<Attempt2> next = new ArrayList<>();

			if (closed.isEmpty()) {
				return next;
			}
			if (transiting[0] == 0 && transiting[1] == 0 && closed.size() == 1) {
				for (int i = 0; i < 2; i++) {
					Attempt2 a = createAttempt(i, closed.iterator().next());
					if (a != null)
						next.add(a);
				}
				return next;
			}

			if (transiting[0] == 0) {
				next = createAttempts(0);
			} else {
				next.add(this);
			}
			if (transiting[1] == 0) {
				List<Attempt2> n2 = new ArrayList<>();
				for (Attempt2 c : next) {
					List<Attempt2> candidates2 = c.createAttempts(1);
					if (candidates2.isEmpty()) {
						n2.add(c);
					} else {
						n2.addAll(candidates2);
					}
				}
				next = n2;
			}
			next.remove(this);
			return next;
		}

		List<Attempt2> createAttempts(int index) {
			List<Attempt2> next = new ArrayList<>();
			for (String v2 : closed) {
				Attempt2 a = createAttempt(index, v2);
				if (a != null)
					next.add(a);
			}
			return next;
		}

		Attempt2 createAttempt(int index, String v2) {
			int length = tunnelLengths.get(getTunnelName(current[index], v2));

			int arrivalTime = minutesPast + length + 1;

			if (arrivalTime >= 30)
				return null;

			Attempt2 a = new Attempt2(valves, tunnelLengths);
			a.minutesPast = minutesPast;

			System.arraycopy(current, 0, a.current, 0, 2);
			a.current[index] = v2;
			System.arraycopy(transiting, 0, a.transiting, 0, 2);
			a.transiting[index] = length + 1;
			a.closed = new HashSet<>(closed);
			a.closed.remove(v2);

			a.pressure = pressure + (30 - arrivalTime) * valves.get(v2).flowrate;

			return a;
		}

	}

	static String getTunnelName(Valve v1, Valve v2) {
		List<String> n = new ArrayList<>();
		n.add(v1.name);
		n.add(v2.name);
		Collections.sort(n);
		return n.stream().collect(Collectors.joining("-"));
	}

	static String getTunnelName(String v1, String v2) {
		List<String> n = new ArrayList<>();
		n.add(v1);
		n.add(v2);
		Collections.sort(n);
		return n.stream().collect(Collectors.joining("-"));
	}

	private static void part2(List<String> input) {
		long now = System.currentTimeMillis();

		Map<String, Valve> valves = input.stream().map(s -> new Valve(s))
				.collect(Collectors.toMap(v -> v.name, v -> v));

		Map<String, Integer> tunnelLengths = getTunnelLengths(valves);

		valves = clearupGraph(valves, tunnelLengths);

//		System.out.println("valves: " + valves.size() + " tunnels: " + tunnelLengths.size());

		dijkstra(valves, tunnelLengths);

//		System.out.println("valves: " + valves.size() + " tunnels: " + tunnelLengths.size());

		List<Attempt2> attempts = new LinkedList<>();
		attempts.add(new Attempt2(valves, tunnelLengths));

		int maxPressure = 0;

		while (!attempts.isEmpty()) {
			Attempt2 a = attempts.remove(0);

			a.walk();

//			if (a.pressure > maxPressure)
//				System.out.println("= " + a.pressure);
			maxPressure = Math.max(maxPressure, a.pressure);

//			System.out.println("# " + maxPressure);

			attempts.addAll(0, a.go());
		}

		System.out.println(MessageFormat.format("part2: {0}", maxPressure));
		System.out.println("time: " + (System.currentTimeMillis() - now));
	}

	static class Valve {
		String name;
		int flowrate;
		List<String> tunnels;

		Valve(String in) {
			name = in.substring(6, 8);
//			System.out.println("#" + name + "#");
			flowrate = Integer.parseInt(in.substring(in.indexOf("=") + 1, in.indexOf(';')));

			int vx = in.indexOf("valves");
			int vy = in.indexOf("valve");
			int index;
			if (vx != -1) {
				index = vx + 7;
			} else {
				index = vy + 6;
			}
			tunnels = Arrays.asList(in.substring(index).split(", "));

//			System.out.println("?" + tunnels.stream().collect(Collectors.joining(",")) + "?");
		}
	}
}
