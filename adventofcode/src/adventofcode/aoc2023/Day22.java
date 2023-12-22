package adventofcode.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import adventofcode.util.Ansi;
import adventofcode.util.IOUtil;

public class Day22 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2023/day22.test");
		List<String> input = IOUtil.readFile("2023/day22.data");

		run(test);
		run(input);
	}

	private static void run(List<String> input) {
		Ansi.foreground(0, 255, 0);
		long checkpoint = System.currentTimeMillis();

		List<Brick> bricks = new ArrayList<>(input.stream().map(s -> new Brick(s)).toList());

		tetris(bricks);
		System.out.println("T " + (System.currentTimeMillis() - checkpoint));
		checkpoint = System.currentTimeMillis();

		Ansi.foreground(0, 255, 255);
		System.out.println("part1: " + jengaSaveRemove(bricks) + " // " + (System.currentTimeMillis() - checkpoint));
		checkpoint = System.currentTimeMillis();

		System.out.println("part2: " + jengaFall(bricks) + " // " + (System.currentTimeMillis() - checkpoint));
	}

	static void tetris(List<Brick> bricks) {
		Collections.sort(bricks, (b1, b2) -> b1.z1 - b2.z1);

		List<Brick> processed = new ArrayList<>();
		int maxZ = 0;
		for (Brick b : bricks) {
			Brick v = new Brick(b);

			while (b.z1 > 1) {
				v.z1--;
				v.z2--;
				if (v.z1 <= maxZ) {
					for (Brick p : processed) {
						if (v.intersect(p)) {
							b.restsOn.add(p);
							p.supports.add(b);
						}
					}
					if (b.restsOn.size() > 0) {
						break;
					}
				}
				b.z1--;
				b.z2--;
			}
			processed.add(b);
			maxZ = Math.max(maxZ, b.z2);
		}
	}

	static int jengaSaveRemove(List<Brick> bricks) {
		int removed = 0;

		int i = bricks.size() - 1;

		while (i >= 0) {
			Brick b = bricks.get(i);

			boolean canBeRemoved = true;
			for (Brick upper : b.supports) {
				canBeRemoved &= upper.restsOn.size() > 1;
			}

			if (canBeRemoved) {
				removed++;
			}
			i--;
		}

		return removed;
	}

	static int jengaFall(List<Brick> bricks) {
		int falling = 0;

		int i = bricks.size() - 1;

		while (i >= 0) {
			Brick b = bricks.get(i);

			Set<Brick> willFall = new HashSet<>();
			willFall.add(b);

			List<Brick> toVisit = new LinkedList<>(b.supports);

			while (!toVisit.isEmpty()) {
				Brick c = toVisit.remove(0);
				if (c.willFallOnRemovalOf(willFall)) {
					willFall.add(c);
					toVisit.addAll(c.supports);
				}

			}
			falling += willFall.size() - 1;
//			System.out.println("= " + falling);
			i--;
		}

		return falling;
	}

	static class Brick {
		int x1, x2, y1, y2, z1, z2;

		Set<Brick> restsOn = new HashSet<>();
		Set<Brick> supports = new HashSet<>();

		Brick(String in) {
			List<Integer> l = Arrays.stream(in.split("[,~]")).map(s -> Integer.parseInt(s)).toList();
			x1 = Math.min(l.get(0), l.get(3));
			x2 = Math.max(l.get(0), l.get(3));
			y1 = Math.min(l.get(1), l.get(4));
			y2 = Math.max(l.get(1), l.get(4));
			z1 = Math.min(l.get(2), l.get(5));
			z2 = Math.max(l.get(2), l.get(5));
		}

		Brick(Brick o) {
			x1 = o.x1;
			x2 = o.x2;
			y1 = o.y1;
			y2 = o.y2;
			z1 = o.z1;
			z2 = o.z2;
		}

		boolean intersect(Brick other) {
			return intersect1d(x1, x2, other.x1, other.x2) && intersect1d(y1, y2, other.y1, other.y2)
					&& intersect1d(z1, z2, other.z1, other.z2);
		}

		boolean intersect1d(int a1, int a2, int b1, int b2) {
			return (a1 <= b1 && b1 <= a2) || (a1 <= b2 && b2 <= a2) || (b1 <= a1 && a1 <= b2) || (b1 <= a2 && a2 <= b2);
		}

		boolean willFallOnRemovalOf(Set<Brick> removedOrFalling) {
//			Set<Brick> remaining = new HashSet<>(restsOn);
//			remaining.removeAll(removedOrFalling);
//			return remaining.isEmpty();
			return removedOrFalling.containsAll(restsOn);
		}
	}

}
