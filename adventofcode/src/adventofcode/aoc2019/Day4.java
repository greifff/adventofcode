package adventofcode.aoc2019;

import java.util.function.Predicate;

public class Day4 {

	public static void main(String[] args) {
		System.out.println("part1: " + countValid(246666, 779999, Day4::isValid1));
		System.out.println("part2: " + countValid(246677, 779999, Day4::isValid2));
	}

	static int[] getPasscode(int passcode) {
		int[] digits = new int[6];
		digits[0] = passcode / 100_000;
		digits[1] = passcode / 10_000 % 10;
		digits[2] = passcode / 1_000 % 10;
		digits[3] = passcode / 100 % 10;
		digits[4] = passcode / 10 % 10;
		digits[5] = passcode % 10;
		return digits;
	}

	static boolean isValid2(int[] passcode) {
		int lastDigit = passcode[0];
		int digitCount = 1;
		boolean hasDouble = false;
		for (int i = 1; i < 6; i++) {
			if (passcode[i] < lastDigit)
				return false;
			if (passcode[i] == lastDigit) {
				digitCount++;
			} else {
				hasDouble |= digitCount == 2;
				lastDigit = passcode[i];
				digitCount = 1;
			}
		}
		return hasDouble || digitCount == 2;
	}

	static boolean isValid1(int[] passcode) {
		int lastDigit = passcode[0];
		int digitCount = 1;
		boolean hasDouble = false;
		for (int i = 1; i < 6; i++) {
			if (passcode[i] < lastDigit)
				return false;
			if (passcode[i] == lastDigit) {
				digitCount++;
			} else {
				hasDouble |= digitCount >= 2;
				lastDigit = passcode[i];
				digitCount = 1;
			}
		}
		return hasDouble || digitCount >= 2;
	}

	static int countValid(int start, int end, Predicate<int[]> isValid) {
		int[] passcode = getPasscode(start);
		int[] lastPasscode = getPasscode(end);

		int count = 0;

		while (isSmaller(passcode, lastPasscode)) {
			if (isValid.test(passcode))
				count++;
			nextPasscode(passcode);
		}
		return count;
	}

	static boolean isSmaller(int[] passcode, int[] lastPasscode) {
		for (int i = 0; i < 6; i++) {
			if (passcode[i] < lastPasscode[i])
				return true;
			if (passcode[i] > lastPasscode[i])
				return false;
		}
		return true;
	}

	static void nextPasscode(int[] passcode) {
		int digit = 5;
		while (digit >= 0) {
			if (passcode[digit] < 9) {
				passcode[digit] = passcode[digit] + 1;
				break;
			}
			digit--;
		}
		while (digit < 5) {
			passcode[digit + 1] = passcode[digit];
			digit++;
		}
	}

	/*-
	 puzzle input: 246540-787419
	 
	 minimum valid value: 246666
	 maximum valid value: 779999
	 
	 246666-246669 (4)
	 246677-246679 (3)
	 246688-246699 (3)
	 246777-246779 (3)
	 246788,246799 (2)
	 246888,246889,246899,246999 (4)   -> 16
	 
	 247777-247779 (3)
	 247788-247799 (3)
	 248888-248899 (3)
	 248999,249999 (2) -> 11
	 
	 255555-255559 (5)
	 255566-255569 (4)
	 255577-255579 (3)
	 255588-255599 (3)
	 255666-255569 (4)
	 255 
	 */
}
