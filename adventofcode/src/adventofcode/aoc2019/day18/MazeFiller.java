package adventofcode.aoc2019.day18;

import java.util.List;

public class MazeFiller {

	private List<String> maze;

	public MazeFiller(List<String> maze) {
		this.maze = maze;
	}

	public void process() {
		for (int y = 1; y < maze.size() - 1; y++) {
			String line = maze.get(y);
			for (int x = 1; x < line.length() - 1; x++) {
				if (line.charAt(x) == '.') {
					processOn(x, y);
				}
			}
		}
	}

	private boolean isWall(int x, int y) {
		return maze.get(y).charAt(x) == '#';
	}

	private void processOn(int x1, int y1) {
		int x = x1;
		int y = y1;
		while (maze.get(y).charAt(x) == '.') {
			boolean top = isWall(x, y - 1);
			boolean left = isWall(x - 1, y);
			boolean right = isWall(x + 1, y);
			boolean bottom = isWall(x, y + 1);

			if ((top ? 1 : 0) + (left ? 1 : 0) + (right ? 1 : 0) + (bottom ? 1 : 0) < 3)
				return;

			// fill in new wall piece
			String line = maze.get(y);
			line = line.substring(0, x) + '#' + line.substring(x + 1);
			maze.set(y, line);

			//
			if (!top) {
				y--;
			} else if (!bottom) {
				y++;
			} else if (!left) {
				x--;
			} else {
				x++;
			}
		}
	}

}
