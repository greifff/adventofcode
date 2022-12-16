package adventofcode.aoc2018.day21;

public class AnalyzedProgram {

	static int a = 6190977;

	public static void main(String[] args) {
		int b = 123;

		do {
			b &= 456;
		} while (b != 72);

		b = 0;

		while (true) {
			int c = b | 65536;
			b = 7902108;
			while (true) {
				b = (b + (c & 0xFF)) & 0xFF_FFFF;
				b = (b * 65889) & 0xFF_FFFF;
				if (c <= 256) {
					// System.out.println(b);
					// return;
					if (a == b) {
						return;
					}
					break;
				}
				int f = 0;
				int d = 1 << 8;
				while (d <= c) {
					f++;
					d = (f + 1) << 8;
				}
				c = 5;
			}
		}
	}

}
