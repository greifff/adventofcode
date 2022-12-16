package adventofcode.aoc2021;

import java.util.Arrays;
import java.util.List;

public class Day21 {

	public static void main(String[] args) {

		part1(new Player(4), new Player(8));
		part1(new Player(6), new Player(2));
	}

	private static void part1(Player player1, Player player2) {
		DeterministicDie die = new DeterministicDie();
		player1.die = die;
		player2.die = die;

		List<Player> players = Arrays.asList(player1, player2);
		int index = 0;
		while (true) {
			Player p = players.get(index);
			p.turn();
			if (p.score >= 1000)
				break;

			index = (index + 1) & 1;
		}

		Player otherPlayer = players.get((index + 1) & 1);

		System.out.println("part1: " + die.count + " * " + otherPlayer.score + " = " + (die.count * otherPlayer.score));
	}

	static interface Die {
		int roll();
	}

	static class DeterministicDie implements Die {
		int value;
		int count;

		@Override
		public int roll() {
			value++;
			count++;
			int v = value;
			value = value % 100;
			return v;
		}

	}

	static class Player {
		int position;
		int score;

		Die die;

		public Player(int i) {
			position = i - 1;
		}

		void turn() {
			int moveBy = die.roll() + die.roll() + die.roll();
			position = (position + moveBy) % 10;
			score += position + 1;
		}

	}
}
