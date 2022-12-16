package adventofcode.aoc2021;

import java.util.HashMap;
import java.util.Map;

public class Day21c {

	public static void main(String[] args) {

		part2(4, 8);
		part2(6, 2);
	}

	private static void part2(int start1, int start2) {
		Map<State, Long> games = new HashMap<>();
		games.put(new State(start1, start2), 1L);
		long[] wins = new long[] { 0, 0 };

		while (!games.isEmpty()) {
			Map<State, Long> games2 = new HashMap<>();
			for (Map.Entry<State, Long> e : games.entrySet()) {
				State state = e.getKey();
				long factor = e.getValue();

				Map<State, Long> newStates = state.turn(factor);

				for (Map.Entry<State, Long> e2 : newStates.entrySet()) {
					State s = e2.getKey();
					long f = e2.getValue();

					int a = (s.active + 1) & 1;

					if (s.score[a] >= 21) {
						wins[a] = wins[a] + f;
						continue;
					}

					Long v = games2.get(s);
					if (v == null) {
						v = 0L;
					}
					games2.put(s, f + v);
				}
			}
			games = games2;
			System.out.println("# " + games.size());
		}
		System.out.println("part2: " + wins[0] + " " + wins[1]);
	}

	static class State {
		int active;
		int[] pos = new int[] { 0, 0 };
		int[] score = new int[] { 0, 0 };

		public State(int start1, int start2) {
			pos[0] = start1 - 1;
			pos[1] = start2 - 1;
		}

		public State(State previous, int steps) {
			for (int i = 0; i < 2; i++) {
				pos[i] = previous.pos[i];
				score[i] = previous.score[i];
			}
			pos[previous.active] = (pos[previous.active] + steps) % 10;
			score[previous.active] = score[previous.active] + pos[previous.active] + 1;
			active = (previous.active + 1) & 1;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof State) {
				State s = (State) o;
				return active == s.active && pos[0] == s.pos[0] && pos[1] == s.pos[1] && score[0] == s.score[0] && score[1] == s.score[1];
			}
			return false;
		}

		@Override
		public int hashCode() {
			return active << 20 + pos[0] << 15 + pos[1] << 10 + score[0] << 5 + score[1];
		}

		Map<State, Long> turn(long factor) {
			Map<State, Long> result = new HashMap<>();

			result.put(new State(this, 3), factor);
			result.put(new State(this, 4), factor * 3);
			result.put(new State(this, 5), factor * 6);
			result.put(new State(this, 6), factor * 7);
			result.put(new State(this, 7), factor * 6);
			result.put(new State(this, 8), factor * 3);
			result.put(new State(this, 9), factor);

			return result;
		}

	}

}
