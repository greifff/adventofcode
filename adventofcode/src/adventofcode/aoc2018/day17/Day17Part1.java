package adventofcode.aoc2018.day17;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day17Part1 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2018/day17.data");

		Grid grid = new Grid();
		for (String d : data) {
			grid.placeClay(d);
		}

		// add water
		grid.setField(500, 0, GroundType.FLOWING_WATER);

		int currentX = 500;
		int currentY = 0;

		grid.setField(currentX, currentY, GroundType.FLOWING_WATER);

		goDown(grid, currentX, currentY);

		grid.print();
		System.out.println(grid.countWaterTiles());
		System.out.println(grid.countStandingWaterTiles()); // Part 2
	}

	private static void goDown(Grid grid, int currentX, int currentY) {
		if (currentY > grid.getMaxY()) {
			return;
		}

		GroundType oneDown = grid.getField(currentX, currentY + 1);

		switch (oneDown) {
		case SAND:
			// go down
			grid.setField(currentX, currentY, GroundType.FLOWING_WATER);
			goDown(grid, currentX, currentY + 1);
			break;
		case FLOWING_WATER:
			// set this field and terminate
			grid.setField(currentX, currentY, GroundType.FLOWING_WATER);
			break;
		default:
			// clay or standing water -> go left and right
			goLeftAndRight(grid, currentX, currentY);
			break;
		}
	}

	private static void goLeftAndRight(Grid grid, int currentX, int currentY) {
		boolean leftDeadEnd = false;
		int leftX = currentX;
		do {
			GroundType nextDown = grid.getField(leftX, currentY + 1);
			if (nextDown == GroundType.SAND || nextDown == GroundType.FLOWING_WATER) {
				break; // go further down
			}
			if (grid.getField(leftX - 1, currentY) == GroundType.CLAY) {
				leftDeadEnd = true;
				break;
			}
			leftX--;
		} while (true);
		boolean rightDeadEnd = false;
		int rightX = currentX;
		do {
			GroundType nextDown = grid.getField(rightX, currentY + 1);
			if (nextDown == GroundType.SAND || nextDown == GroundType.FLOWING_WATER) {
				break;
			}
			if (grid.getField(rightX + 1, currentY) == GroundType.CLAY) {
				rightDeadEnd = true;
				break;
			}
			rightX++;
		} while (true);

		if (leftDeadEnd && rightDeadEnd) {
			for (int x = leftX; x <= rightX; x++) {
				grid.setField(x, currentY, GroundType.STANDING_WATER);
			}
			// go up
			goLeftAndRight(grid, currentX, currentY - 1);
			return;
		}

		for (int x = leftX; x <= rightX; x++) {
			grid.setField(x, currentY, GroundType.FLOWING_WATER);
		}

		if (!leftDeadEnd) {
			goDown(grid, leftX, currentY);
		}
		if (!rightDeadEnd) {
			goDown(grid, rightX, currentY);
		}

	}
}
