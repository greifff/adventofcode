package adventofcode.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day4 {

	public static void main(String[] args) {
		List<String> test = IOUtil.readFile("2021/day4.test");
		List<String> data = IOUtil.readFile("2021/day4.data");

		part1(test);
		part1(data);
		part2(test);
		part2(data);
	}

	private static void part1(List<String> data) {
		List<Integer> numbers = Arrays.stream(data.get(0).split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		List<BingoBoard> boards = new ArrayList<>();
		for (int i = 2; i < data.size(); i += 6) {
			boards.add(new BingoBoard(data.subList(i, i + 5)));
		}

		while (true) {
			int n = numbers.remove(0);
			System.out.println("> " + n);
			for (BingoBoard board : boards) {
				board.play(n);
				if (board.hasBingo()) {
					System.out.println("part1: " + (board.score() * n));
					return;
				}
			}
		}
	}

	private static void part2(List<String> data) {
		List<Integer> numbers = Arrays.stream(data.get(0).split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		List<BingoBoard> boards = new ArrayList<>();
		for (int i = 2; i < data.size(); i += 6) {
			boards.add(new BingoBoard(data.subList(i, i + 5)));
		}

		while (true) {
			int n = numbers.remove(0);
			System.out.println("> " + n);
			List<BingoBoard> won = new ArrayList<>();
			for (BingoBoard board : boards) {
				board.play(n);
				if (board.hasBingo()) {
					if (boards.size() == 1) {
						System.out.println("part2: " + (board.score() * n));
						return;
					}
					won.add(board);
				}
			}
			boards.removeAll(won);
		}
	}

	private static class BingoBoard {
		private int[][][] board = new int[5][5][2];

		BingoBoard(List<String> data) {
			for (int i = 0; i < 5; i++) {
				StringTokenizer st = new StringTokenizer(data.get(i), " ");
				for (int k = 0; k < 5; k++) {
					board[i][k][0] = Integer.parseInt(st.nextToken());
					board[i][k][1] = 0;

					// System.out.print(" " + board[i][k][0]);
				}
				// System.out.println();
			}
			// System.out.println("#");
		}

		void play(int n) {
			for (int a = 0; a < 5; a++) {
				for (int b = 0; b < 5; b++) {
					if (board[a][b][0] == n) {
						board[a][b][1] = 1;
						return;
					}
				}
			}
		}

		int score() {
			int score = 0;
			for (int a = 0; a < 5; a++) {
				for (int b = 0; b < 5; b++) {

					if (board[a][b][1] == 0) {
						score += board[a][b][0];
						// System.out.print(" " + board[a][b][0] + " ");
					} else {
						// System.out.print("(" + board[a][b][0] + ")");
					}
				}
				System.out.println();
			}
			return score;
		}

		boolean hasBingo() {
			// horizontal and vertical
			for (int a = 0; a < 5; a++) {
				int hits1 = 0;
				int hits2 = 0;
				for (int b = 0; b < 5; b++) {
					if (board[a][b][1] == 1)
						hits1++;
					if (board[b][a][1] == 1)
						hits2++;
				}
				if (hits1 == 5 || hits2 == 5)
					return true;
			}
			return false;
		}
	}
}
