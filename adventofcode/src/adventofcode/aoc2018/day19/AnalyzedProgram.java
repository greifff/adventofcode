package adventofcode.aoc2018.day19;

public class AnalyzedProgram {

	static int a = 1;

	static int e;
	static int f;

	static void method2() {
		while (true) {
			int c = 1;
			while (true) {
				if (c * f == e) {
					a += f;
				}
				c++;
				if (c > e) {
					f++;
					break;
				}
			}

			if (f > e) {
				return;
			}
		}
	}

	/*-
	  10_551_261 = 3 * 3_517_087
	  3_517_087  = 7 * 502_441
	  
	  
	  
	  => a = 3 + 7 + 502441 + 21 + 3517087 + 1507323 + 10551261
	  		1
	  		3
	  		7
	  		21
	  		502441
	  		1507323
	  		3517087
	  		10551261
	  		= 16078144
	 */

	public static void main(String[] args) {
		e = 861;
		if (a == 1) {
			e = e + 10_550_400;
			a = 0;
		}
		f = 1;
		method2();

		System.out.println(a);
	}

}
