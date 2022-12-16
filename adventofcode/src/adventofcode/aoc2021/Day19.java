package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day19 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day19.test");
		List<String> data = IOUtil.readFile("2021/day19.data");

		part1(test);
		part1(data);
	}

	static void part1(List<String> data) {
		List<Scanner> scanners = parse(data);
		List<Set<Beacon>> mapped = new ArrayList<>();

		for (int i = 0; i < scanners.size(); i++) {
			Scanner s1 = scanners.get(i);
			s1.setRotation(0);
			for (int j = i + 1; j < scanners.size(); j++) {
				Scanner s2 = scanners.get(j);
				for (int r = 0; r < 24; r++) {
					s2.setRotation(r);
					List<Set<Beacon>> correlation = s1.findMapping(s2);
					if (correlation.size() >= 12) {
						integrate(mapped, correlation);
						break;
					}
				}
			}
		}

		int effectiveBeaconCount = mapped.size();

		Set<Beacon> alreadyCovered = mapped.stream().flatMap(s -> s.stream()).collect(Collectors.toSet());

		Set<Beacon> any = new HashSet<>(scanners.stream().flatMap(s -> s.beacons.stream()).collect(Collectors.toSet()));
		any.removeAll(alreadyCovered);

		effectiveBeaconCount += any.size();

		System.out.println("part1: " + effectiveBeaconCount);
	}

	private static void integrate(List<Set<Beacon>> mapped, List<Set<Beacon>> correlation) {
		for (Set<Beacon> corr : correlation) {
			List<Set<Beacon>> found = new ArrayList<>();
			for (Beacon b : corr) {
				for (Set<Beacon> m : mapped) {
					if (m.contains(b)) {
						found.add(m);
					}
				}
			}
			if (!found.isEmpty()) {
				mapped.removeAll(found);
				for (Set<Beacon> f1 : found) {
					corr.addAll(f1);
				}
			}
			mapped.add(corr);
		}
	}

	static List<Scanner> parse(List<String> data) {
		List<Scanner> scanners = new ArrayList<>();
		Scanner current = null;
		for (String d : data) {
			if (d.startsWith("---")) {
				// --- scanner 0 ---
				current = new Scanner(Integer.parseInt(d.substring(12, d.length() - 4)));
				scanners.add(current);
			} else if (!"".equals(d)) {
				current.beacons.add(new Beacon(d));
			}
		}
		return scanners;
	}

	static class Scanner {
		int id;

		List<Beacon> beacons = new ArrayList<>();

		Scanner(int id) {
			this.id = id;
		}

		public List<Set<Beacon>> findMapping(Scanner s2) {
			// TODO Auto-generated method stub

			for (int dx = -2000; dx <= 2000; dx++) {
				for (int dy = -2000; dy <= 2000; dy++) {
					for (int dz = -2000; dx <= 2000; dz++) {
						List<Set<Beacon>> hits = new ArrayList<>();

						for (Beacon b1 : beacons) {
							for (Beacon b2 : s2.beacons) {
								if (b1.x == b2.rotX + dx && b1.y == b2.rotY + dy && b1.z == b2.rotZ + dz) {
									hits.add(new HashSet<>(Arrays.asList(b1, b2)));
								}
							}
						}

						if (hits.size() >= 12) {
							return hits;
						}
					}
				}
			}

			return new ArrayList<>();
		}

		public void setRotation(int r) {
			beacons.forEach(b -> b.rotate(r));
		}

	}

	static class Beacon {

		int x;
		int y;
		int z;

		int rotX;
		int rotY;
		int rotZ;

		Beacon(String line) {
			String[] c = line.split(",");
			x = Integer.parseInt(c[0]);
			y = Integer.parseInt(c[1]);
			z = Integer.parseInt(c[2]);
		}

		void rotate(int rotationMode) {
			switch (rotationMode) {
			case 0:
				rotX = x;
				rotY = y;
				rotZ = z;
				break;
			case 1:
				rotX = y;
				rotY = -x;
				rotZ = z;
				break;
			case 2:
				rotX = -x;
				rotY = -y;
				rotZ = z;
				break;
			case 3:
				rotX = -y;
				rotY = x;
				rotZ = z;
				break;

			case 4:
				rotX = y;
				rotY = z;
				rotZ = x;
				break;
			case 5:
				rotX = z;
				rotY = -y;
				rotZ = x;
				break;
			case 6:
				rotX = -y;
				rotY = -z;
				rotZ = x;
				break;
			case 7:
				rotX = -z;
				rotY = y;
				rotZ = x;
				break;

			case 8:
				rotX = x;
				rotY = -z;
				rotZ = y;
				break;
			case 9:
				rotX = -z;
				rotY = -x;
				rotZ = y;
				break;
			case 10:
				rotX = -x;
				rotY = z;
				rotZ = y;
				break;
			case 11:
				rotX = z;
				rotY = x;
				rotZ = y;
				break;

			case 12:
				rotX = x;
				rotY = -y;
				rotZ = -z;
				break;
			case 13:
				rotX = -y;
				rotY = -x;
				rotZ = -z;
				break;
			case 14:
				rotX = -x;
				rotY = y;
				rotZ = -z;
				break;
			case 15:
				rotX = y;
				rotY = x;
				rotZ = -z;
				break;

			case 16:
				rotX = x;
				rotY = z;
				rotZ = -y;
				break;
			case 17:
				rotX = z;
				rotY = -x;
				rotZ = -y;
				break;
			case 18:
				rotX = -x;
				rotY = -z;
				rotZ = -y;
				break;
			case 19:
				rotX = -z;
				rotY = x;
				rotZ = -y;
				break;

			case 20:
				rotX = y;
				rotY = -z;
				rotZ = -x;
				break;
			case 21:
				rotX = -z;
				rotY = -y;
				rotZ = -x;
				break;
			case 22:
				rotX = -y;
				rotY = z;
				rotZ = -x;
				break;
			case 23:
				rotX = z;
				rotY = y;
				rotZ = -x;
				break;
			}
		}
	}
}
