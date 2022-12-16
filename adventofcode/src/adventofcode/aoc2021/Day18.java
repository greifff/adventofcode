package adventofcode.aoc2021;

import java.util.List;
import java.util.StringTokenizer;

import adventofcode.util.IOUtil;

public class Day18 {

	public static void main(String[] args) {

		System.out.println("Explosions");
		testReduce("[[[[[9,8],1],2],3],4]");
		testReduce("[7,[6,[5,[4,[3,2]]]]]");
		testReduce("[[6,[5,[4,[3,2]]]],1]");
		testReduce("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
		testReduce("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
		System.out.println("Complex");
		testReduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
		// testReduce("[[[[[9,8],1],2],3],4]");
		// testReduce("[[[[[9,8],1],2],3],4]");

		List<String> test = IOUtil.readFile("2021/day18.test");
		List<String> data = IOUtil.readFile("2021/day18.data");

		part1(test);
		part1(data);

		part2(test);
		part2(data);
	}

	private static void testReduce(String input) {
		SnailNumber n = new SnailNumber(input);
		System.out.println(">> " + n);
		n.reduce();
		System.out.println(":: " + n);
	}

	private static void part2(List<String> data) {
		int max = 0;

		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.size(); j++) {
				if (i == j)
					continue;

				SnailNumber k = new SnailNumber(data.get(i));
				k.reduce();
				SnailNumber n = new SnailNumber(data.get(j));
				n.reduce();

				SnailNumber m = new SnailNumber(k, n);
				m.reduce();
				max = Math.max(max, m.magnitude());
			}
		}

		System.out.println("part2: " + max);
	}

	private static void part1(List<String> data) {

		SnailNumber k = null;

		for (String d : data) {
			SnailNumber n = new SnailNumber(d);
			n.reduce();

			if (k == null) {
				k = n;
			} else {
				k = new SnailNumber(k, n);
				k.reduce();
			}

		}

		System.out.println("part1: " + k.magnitude());
	}

	static class SnailNumber {

		SnailNumber left;
		SnailNumber right;
		SnailNumber up;
		int value;

		SnailNumber(String input) {
			this(null, new StringTokenizer(input, "[],", true));
		}

		SnailNumber(SnailNumber up, StringTokenizer st) {
			this.up = up;
			String t = st.nextToken();
			if ("[".equals(t)) {
				// pair mode
				left = new SnailNumber(this, st);
				t = st.nextToken(); // ,
				right = new SnailNumber(this, st);
				t = st.nextToken(); // ]
			} else {
				// single value
				value = Integer.parseInt(t);
			}
		}

		void reduce() {
			boolean action = true;
			while (action) {
				action = false;
				// scan for explosion
				SnailNumber e = scanForExplosion();
				if (e != null) {
					e.explode();
					action = true;
					continue;
				}
				// scan for split
				SnailNumber s = scanForSplit();
				if (s != null) {
					s.split();
					action = true;
				}
			}
		}

		SnailNumber scanForExplosion() {
			if (mustExplode())
				return this;

			SnailNumber n = left == null ? null : left.scanForExplosion();
			if (n == null) {
				n = right == null ? null : right.scanForExplosion();
			}
			return n;
		}

		SnailNumber scanForSplit() {
			if (isLeaf()) {
				return value >= 10 ? this : null;
			}

			SnailNumber n = left == null ? null : left.scanForSplit();
			if (n == null) {
				n = right == null ? null : right.scanForSplit();
			}
			return n;
		}

		SnailNumber(SnailNumber up, int value) {
			this.up = up;
			this.value = value;
		}

		public SnailNumber(SnailNumber k, SnailNumber n) {
			left = k;
			right = n;
			k.up = this;
			n.up = this;
		}

		boolean isLeaf() {
			return left == null && right == null;
		}

		boolean mustExplode() {
			if (isLeaf())
				return false;

			int canGoUp = 0;

			SnailNumber n = this;
			while (canGoUp < 4) {
				if (n.up == null)
					break;
				n = n.up;
				canGoUp++;
			}

			return left.isLeaf() && right.isLeaf() && canGoUp == 4;
		}

		void explode() {
			// increase left neighbor
			SnailNumber l = leftNeighbor();
			if (l != null)
				l.value += left.value;
			// TODO increase right neighbor
			SnailNumber r = rightNeighbor();
			if (r != null)
				r.value += right.value;
			// cleanup
			left = null;
			right = null;
			value = 0;
		}

		SnailNumber leftNeighbor() {
			SnailNumber n = up;
			SnailNumber previous = this;

			while (n.left == previous) {
				previous = n;
				n = n.up;
				if (n == null)
					return null;
			}
			n = n.left;
			while (n.right != null) {
				n = n.right;
			}
			return n;
		}

		SnailNumber rightNeighbor() {
			SnailNumber n = up;
			SnailNumber previous = this;

			while (n.right == previous) {
				previous = n;
				n = n.up;
				if (n == null)
					return null;
			}
			n = n.right;
			while (n.left != null) {
				n = n.left;
			}
			return n;
		}

		void split() {
			left = new SnailNumber(this, value / 2);
			right = new SnailNumber(this, (value + 1) / 2);
		}

		int magnitude() {
			if (isLeaf())
				return value;
			return left.magnitude() * 3 + right.magnitude() * 2;
		}

		@Override
		public String toString() {
			if (isLeaf())
				return "" + value;
			return "[" + left + "," + right + "]";
		}
	}
}
