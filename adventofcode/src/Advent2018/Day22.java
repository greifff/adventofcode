package Advent2018;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Day22 {

	int depth = 11394;
	int targetX = 7;
	int targetY = 701;

	enum Type {
		ROCK(Tool.NONE), WET(Tool.TORCH), NARROW(Tool.CLIMB);

		Tool invalid;

		Type(Tool invalid) {
			this.invalid = invalid;
		}

		static Type getByNum(int n) {
			return Type.values()[n];
		}
	}

	enum Tool {
		NONE(Type.ROCK), TORCH(Type.WET), CLIMB(Type.NARROW);

		Type invalid;

		Tool(Type invalid) {
			this.invalid = invalid;
		}

		static Tool getByNum(int n) {
			return Tool.values()[n];
		}
	}

	class Cave {
		Node pos;
		int minutes;
		Tool tool;
		Type type;

		public Cave(Node pos, int minutes, Tool tool, Type type) {
			this.pos = pos;
			this.minutes = minutes;
			this.tool = tool;
			this.type = type;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Cave cave = (Cave) o;

			if (pos != null ? !pos.equals(cave.pos) : cave.pos != null)
				return false;
			return tool == cave.tool;
		}

		@Override
		public int hashCode() {
			int result = pos != null ? pos.hashCode() : 0;
			result = 31 * result + (tool != null ? tool.hashCode() : 0);
			return result;
		}

		int getMinutes() {
			return minutes;
		}
	}

	int[][] grid = new int[1000][1000];
	int[][] erosion = new int[1000][1000];
	int[][] geolog = new int[1000][1000];

	int erosion(int x, int y) {
		return (geolog[y][x] + depth) % 20183;
	}

	int type(int x, int y) {
		return erosion[y][x] % 3;
	}

	int geologic(int x, int y) {
		if (y == 0) {
			if (x == 0)
				return 0;
			return x * 16807;
		}
		if (x == 0) {
			return y * 48271;
		}
		if (x == targetX && y == targetY) {
			return 0;
		} else {
			return erosion[y - 1][x] * erosion[y][x - 1];
		}
	}

	public Object part1() {
		int sum = 0;
		for (int y = 0; y < 1000; y++) {
			for (int x = 0; x < 1000; x++) {
				geolog[y][x] = geologic(x, y);
				erosion[y][x] = erosion(x, y);
				grid[y][x] = type(x, y);

				if (y <= targetY && x <= targetX) {

					sum += grid[y][x];
				}
			}
		}
		return sum;
	}

	public Object part2() {
		PriorityQueue<Cave> queue = new PriorityQueue<>(Comparator.comparing(Cave::getMinutes));
		queue.add(new Cave(new Node(0, 0), 1, Tool.TORCH, Type.ROCK));
		Map<Cave, Integer> minutes = new HashMap<>();
		Cave target = new Cave(new Node(targetX, targetY), 0, Tool.TORCH, Type.ROCK);

		while (!queue.isEmpty()) {
			Cave current = queue.poll();
			if (minutes.containsKey(current) && minutes.get(current) <= current.getMinutes()) {
				continue;
			}
			minutes.put(current, current.minutes);
			if (current.equals(target)) {
				return current.minutes;
			}

			// find other valid tool
			for (Tool tool : Tool.values()) {
				if (tool != current.tool && tool != current.type.invalid) {
					queue.add(new Cave(current.pos, current.minutes + 7, tool, current.type));
				}
			}

			// check other directions
			for (Direction dir : Direction.values()) {
				int nx = current.pos.getX() + dir.getDx();
				int ny = current.pos.getY() + dir.getDy();
				if (Direction.rangeCheck(nx, ny, 0, 0, 1000, 1000) && Type.getByNum(grid[ny][nx]) != current.tool.invalid) {
					queue.add(new Cave(new Node(nx, ny), current.minutes + 1, current.tool, Type.getByNum(grid[ny][nx])));
				}
			}
		}
		return "";
	}

	static enum Direction {

		N(0, -1), S(0, 1), E(1, 0), W(-1, 0);

		private int dx;
		private int dy;

		private Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		public int getDx() {
			return dx;
		}

		static boolean rangeCheck(int nx, int ny, int i, int j, int k, int l) {
			// TODO Auto-generated method stub
			return false;
		}

		public int getDy() {
			return dy;
		}

	}

	static class Node {

		private int x;
		private int y;

		public Node(int nx, int ny) {
			x = nx;
			y = ny;
		}

		public int getY() {
			return y;
		}

		public int getX() {
			return x;
		}

	}

}