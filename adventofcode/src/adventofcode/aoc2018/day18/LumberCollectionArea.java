package adventofcode.aoc2018.day18;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class LumberCollectionArea {

	private final int size;

	private final WoodState[][] state;

	public LumberCollectionArea(int size) {
		this.size = size;
		state = new WoodState[size][size];
	}

	public LumberCollectionArea nextStep() {
		LumberCollectionArea next = new LumberCollectionArea(size);
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				next.state[x][y] = nextState(x, y);
			}
		}
		return next;
	}

	private WoodState nextState(int x, int y) {
		WoodState current = state[x][y];
		switch (current) {
		case OPEN:
			if (countAdjacentOfType(x, y, WoodState.WOODED) >= 3) {
				return WoodState.WOODED;
			}
			break;
		case WOODED:
			if (countAdjacentOfType(x, y, WoodState.LUMBERYARD) >= 3) {
				return WoodState.LUMBERYARD;
			}
			break;
		case LUMBERYARD:
			if (countAdjacentOfType(x, y, WoodState.LUMBERYARD) >= 1 && countAdjacentOfType(x, y, WoodState.WOODED) >= 1) {
				return WoodState.LUMBERYARD;
			}
			return WoodState.OPEN;
		}
		return current;
	}

	private int countAdjacentOfType(int x, int y, WoodState expected) {
		List<WoodState> adjacent = Arrays.asList(getState(x - 1, y - 1), getState(x - 1, y), getState(x - 1, y + 1), getState(x + 1, y - 1),
				getState(x + 1, y), getState(x + 1, y + 1), getState(x, y - 1), getState(x, y + 1));
		return (int) adjacent.stream().filter(w -> w == expected).count();
	}

	private WoodState getState(int x, int y) {
		if (x < 0 || x >= size || y < 0 || y >= size) {
			return WoodState.NOTHING;
		}
		return state[x][y];
	}

	public int countType(WoodState expected) {
		int c = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (state[x][y] == expected) {
					c++;
				}
			}
		}
		return c;
	}

	public void init(List<String> data) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				WoodState s = WoodState.NOTHING;
				switch (data.get(y).charAt(x)) {
				case '.':
					s = WoodState.OPEN;
					break;
				case '|':
					s = WoodState.WOODED;
					break;
				case '#':
					s = WoodState.LUMBERYARD;
					break;
				}
				state[x][y] = s;
			}
		}
	}

	public String output() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				switch (state[x][y]) {
				case OPEN:
					sb.append('.');
					break;
				case WOODED:
					sb.append('|');
					break;
				case LUMBERYARD:
					sb.append('#');
					break;
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public String hashOutput() {
		String output = output();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return String.format("%032x", new BigInteger(1, md5.digest(output.getBytes(StandardCharsets.UTF_8))));
	}
}
