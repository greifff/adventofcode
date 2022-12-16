package adventofcode.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import adventofcode.aoc2019.intcode.IntCodeProcessor;

public class Day15 {

	public static void main(String[] args) {
		testRepairbot();
		part1();
	}

	static void testRepairbot() {
		ICPMock mock = new ICPMock();
		RepairBot bot = new RepairBot();
		mock.input = bot;
		mock.output = bot;

		Thread t = new Thread(() -> {
			mock.execute();
		});
		bot.t = t;
		t.start();

		while (!bot.mapComplete) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List<Integer> path = bot.findShortestPathToDevice();
		System.out.println("-> " + path.stream().map(i -> "" + i).reduce((a, b) -> a + "," + b));
		System.out.println("test: " + path.size());
		System.out.println("test2: " + bot.floodWithOxygen());
	}

	static class ICPMock {
		public Supplier<Long> input;

		public Consumer<Long> output;

		long x;
		long y;

		void execute() {
			while (true) {
				long d = input.get();
				// System.out.println("# " + x + "," + y + "/" + d);
				long x2 = x;
				long y2 = y;
				switch ((int) d) {
				case 1:
					y2--;
					break;
				case 2:
					y2++;
					break;
				case 3:
					x2--;
					break;
				case 4:
					x2++;
					break;
				}

				long o = 0;

				if (x2 == -2 && y2 >= -1 && y2 <= 1) {
					o = 1;
				} else if (x2 == -1 && y2 == -1) {
					o = 1;
				} else if (x2 == -1 && y2 == 1) {
					o = 2;
				} else if (x2 == 0 && (y2 == 0 || y2 == 1)) {
					o = 1;
				} else if (x2 == 1 && y2 == 0) {
					o = 1;
				}
				if (o == 1 || o == 2) {
					x = x2;
					y = y2;
				}
				output.accept(o);
				// try {
				// Thread.sleep(50);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// return;
				// }
			}
		}
	}

	// similar to day11

	static final String program = "3,1033,1008,1033,1,1032,1005,1032,31,1008,1033,2,1032,1005,1032,58,1008,1033,3,1032,1005,1032,81,1008,1033,4,1032,1005,1032,104,99,101,0,1034,1039,102,1,1036,1041,1001,1035,-1,1040,1008,1038,0,1043,102,-1,1043,1032,1,1037,1032,1042,1106,0,124,101,0,1034,1039,101,0,1036,1041,1001,1035,1,1040,1008,1038,0,1043,1,1037,1038,1042,1105,1,124,1001,1034,-1,1039,1008,1036,0,1041,1002,1035,1,1040,1001,1038,0,1043,1001,1037,0,1042,1106,0,124,1001,1034,1,1039,1008,1036,0,1041,102,1,1035,1040,1002,1038,1,1043,102,1,1037,1042,1006,1039,217,1006,1040,217,1008,1039,40,1032,1005,1032,217,1008,1040,40,1032,1005,1032,217,1008,1039,9,1032,1006,1032,165,1008,1040,33,1032,1006,1032,165,1101,2,0,1044,1106,0,224,2,1041,1043,1032,1006,1032,179,1102,1,1,1044,1106,0,224,1,1041,1043,1032,1006,1032,217,1,1042,1043,1032,1001,1032,-1,1032,1002,1032,39,1032,1,1032,1039,1032,101,-1,1032,1032,101,252,1032,211,1007,0,53,1044,1106,0,224,1101,0,0,1044,1106,0,224,1006,1044,247,1001,1039,0,1034,101,0,1040,1035,102,1,1041,1036,101,0,1043,1038,101,0,1042,1037,4,1044,1105,1,0,64,32,22,30,60,6,82,30,34,77,7,15,17,32,43,96,51,43,27,74,39,14,10,70,32,20,59,35,98,3,81,47,40,23,65,16,1,82,35,35,44,76,93,55,6,40,65,15,62,62,67,7,72,21,92,85,54,71,42,84,80,30,64,88,50,90,16,34,63,20,88,24,64,86,11,64,20,44,23,63,11,26,10,84,75,13,93,39,16,67,2,91,97,22,86,40,69,11,40,58,93,22,82,30,24,40,58,26,75,70,52,20,27,95,57,23,69,9,30,82,87,70,42,32,90,67,36,92,41,97,72,24,3,36,60,96,5,62,13,74,27,21,60,58,90,17,49,27,70,29,59,48,72,30,35,11,21,60,99,35,37,71,9,84,3,22,74,20,48,70,19,58,65,22,14,72,15,7,31,77,61,5,31,60,24,80,33,58,49,78,80,37,79,66,37,83,4,21,50,65,96,23,67,89,44,17,58,60,41,96,96,39,27,62,84,18,74,38,56,9,72,70,32,62,95,6,87,51,96,36,4,3,79,21,21,31,66,93,13,10,77,43,52,68,66,47,42,55,57,23,60,45,63,3,86,96,29,70,81,31,3,48,38,91,34,69,85,18,95,93,96,85,15,38,80,35,17,98,92,14,57,60,25,46,63,60,16,58,25,48,73,59,40,6,72,46,91,39,22,63,79,58,67,84,33,52,78,52,26,21,61,49,78,77,5,95,75,20,56,30,43,67,75,33,84,10,14,60,21,98,14,31,81,97,49,64,19,69,44,3,68,2,66,20,69,48,81,96,22,56,22,25,27,60,59,36,10,45,81,39,46,97,54,49,42,78,89,26,93,55,14,96,48,48,96,57,51,82,94,23,46,64,20,10,56,19,63,41,77,17,26,68,47,37,97,84,6,93,26,99,1,11,84,12,79,74,34,85,25,48,92,69,68,44,59,35,99,33,88,75,29,12,87,79,37,74,24,98,4,68,1,85,43,31,60,2,82,16,51,65,97,4,82,42,52,82,56,58,24,33,60,22,65,29,43,75,10,72,34,97,70,11,36,89,26,69,84,26,50,17,42,83,44,63,1,84,77,22,89,46,72,79,93,22,94,34,79,48,68,55,3,73,91,30,79,37,76,19,24,61,41,98,32,12,6,57,16,44,55,43,63,55,98,11,68,17,50,67,26,86,19,60,14,56,30,59,11,9,41,26,59,39,56,49,48,82,3,83,64,69,48,65,89,42,78,33,25,91,92,50,91,8,64,73,92,16,96,28,40,27,67,22,69,95,7,12,70,56,49,81,22,68,67,40,48,92,43,14,86,60,49,39,74,58,42,43,54,37,2,84,25,41,22,22,65,16,6,67,62,22,25,88,52,76,88,40,20,75,84,24,4,39,99,51,72,73,14,15,81,39,70,15,26,15,32,34,83,33,73,95,14,55,91,65,81,44,3,89,49,80,5,38,56,42,76,68,14,4,76,71,98,65,62,31,6,96,23,77,82,4,59,91,29,14,91,42,80,35,2,58,99,27,40,64,43,86,74,17,58,68,24,71,51,73,35,74,63,50,17,24,5,83,71,12,62,33,19,31,84,41,25,71,75,41,43,55,85,22,11,88,71,49,33,55,50,63,52,64,23,25,42,85,47,49,30,65,42,95,61,15,86,7,61,62,32,79,62,82,13,84,30,69,21,70,22,99,95,71,5,71,11,69,39,85,79,89,41,94,82,86,46,83,96,80,48,74,63,56,8,58,38,66,12,61,33,88,30,92,27,57,0,0,21,21,1,10,1,0,0,0,0,0,0";

	static void part1() {
		RepairBot bot = new RepairBot();

		IntCodeProcessor icp = new IntCodeProcessor(program);

		icp.input = bot;
		icp.output = bot;

		Thread t = new Thread(() -> {
			icp.execute();
		});
		bot.t = t;
		t.start();

		while (!bot.mapComplete) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("part1: " + bot.findShortestPathToDevice().size());

		System.out.println("part2: " + bot.floodWithOxygen());
	}

	static enum TileType {
		UNKNOWN(' '), EMPTY('.'), WALL('#'), DEVICE('+'), OXYGEN('O');

		char p;

		TileType(char p) {
			this.p = p;
		}
	}

	static class RepairBot implements Supplier<Long>, Consumer<Long> {

		public static final int MOVE_NORTH = 1;
		public static final int MOVE_SOUTH = 2;
		public static final int MOVE_WEST = 3;
		public static final int MOVE_EAST = 4;

		public static final int STATUS_WALL_HIT = 0;
		public static final int MOVED = 1;
		public static final int MOVED_FOUND_DEVICE = 2;

		boolean mapComplete = false;

		Thread t;

		protected Map<Long, Map<Long, TileType>> grid = new HashMap<>();

		long x = 0;
		long y = 0;
		int direction;

		RepairBot() {
			put(0, 0, TileType.EMPTY);
		}

		List<Integer> path = null;

		TileType get(long tx, long ty) {
			Map<Long, TileType> t1 = grid.get(tx);
			if (t1 == null)
				return TileType.UNKNOWN;
			TileType tt = t1.get(ty);
			return tt == null ? TileType.UNKNOWN : tt;
		}

		void put(long tx, long ty, TileType tt) {
			Map<Long, TileType> t1 = grid.get(tx);
			if (t1 == null) {
				t1 = new HashMap<>();
				grid.put(tx, t1);
			}
			t1.put(ty, tt);
		}

		public int floodWithOxygen() {
			List<long[]> active = new ArrayList<>();
			long x = 0;
			long y = 0;
			outer: for (Map.Entry<Long, Map<Long, TileType>> e1 : grid.entrySet()) {
				x = e1.getKey();
				for (Map.Entry<Long, TileType> e2 : e1.getValue().entrySet()) {
					if (e2.getValue() == TileType.DEVICE) {
						e2.setValue(TileType.OXYGEN);
						y = e2.getKey();
						break outer;
					}
				}
			}
			active.add(new long[] { x, y });
			List<long[]> deltas = Arrays.asList(new long[] { -1, 0 }, new long[] { 1, 0 }, new long[] { 0, -1 }, new long[] { 0, 1 });
			int step = 0;
			while (!active.isEmpty()) {
				boolean hasSpread = false;
				List<long[]> active2 = new ArrayList<>();
				for (long[] spot : active) {
					for (long[] d : deltas) {
						long x1 = spot[0] + d[0];
						long y1 = spot[1] + d[1];
						if (get(x1, y1) == TileType.EMPTY) {
							hasSpread = true;
							active2.add(new long[] { x1, y1 });
							put(x1, y1, TileType.OXYGEN);
						}
					}
				}
				active = active2;
				if (hasSpread)
					step++;
			}
			return step;
		}

		@Override
		public void accept(Long t) {
			long x2 = x;
			long y2 = y;
			switch ((int) direction) {
			case MOVE_NORTH:
				y2--;
				break;
			case MOVE_SOUTH:
				y2++;
				break;
			case MOVE_WEST:
				x2--;
				break;
			case MOVE_EAST:
				x2++;
				break;
			}
			// System.out.println("+ " + x2 + "," + y2 + "/" + t);
			switch (t.intValue()) {
			case STATUS_WALL_HIT:
				put(x2, y2, TileType.WALL);
				break;
			case MOVED:
				put(x2, y2, TileType.EMPTY);
				x = x2;
				y = y2;
				break;
			case MOVED_FOUND_DEVICE:
				put(x2, y2, TileType.DEVICE);
				x = x2;
				y = y2;
				break;
			}
		}

		@Override
		public Long get() {
			direction = getInternal();
			return (long) direction;
		}

		private int getInternal() {
			if (path != null && !path.isEmpty()) {
				return path.remove(0);
			}
			// move to direct neighbor
			if (get(x, y - 1) == TileType.UNKNOWN) {
				return MOVE_NORTH;
			}
			if (get(x, y + 1) == TileType.UNKNOWN) {
				return MOVE_SOUTH;
			}
			if (get(x - 1, y) == TileType.UNKNOWN) {
				return MOVE_WEST;
			}
			if (get(x + 1, y) == TileType.UNKNOWN) {
				return MOVE_EAST;
			}
			// print();
			// use path finder

			path = findPath(x, y, TileType.UNKNOWN);
			// System.out.println("w");
			if (path == null) {
				// System.out.println("x");
				mapComplete = true;
				print();
			}
			return path.remove(0);
		}

		void print() {
			long minX = grid.keySet().stream().reduce(Math::min).orElse(0L);
			long maxX = grid.keySet().stream().reduce(Math::max).orElse(0L);
			long minY = grid.values().stream().flatMap(s -> s.keySet().stream()).reduce(Math::min).orElse(0L);
			long maxY = grid.values().stream().flatMap(s -> s.keySet().stream()).reduce(Math::max).orElse(0L);

			for (long y1 = minY; y1 <= maxY; y1++) {
				for (long x1 = minX; x1 <= maxX; x1++) {
					System.out.print(get(x1, y1).p);
				}
				System.out.println();
			}
		}

		List<Integer> findShortestPathToDevice() {
			return findPath(0, 0, TileType.DEVICE);
		}

		List<Integer> findPath(long fromX, long fromY, TileType tileType) {

			Map<Long, Set<Long>> visited = new HashMap<>();

			List<Path> paths = new ArrayList<>();

			paths.add(new Path(fromX, fromY));

			while (!paths.isEmpty()) {
				// System.out.println("c " + paths.size());
				List<Path> paths2 = new ArrayList<>();
				for (Path p : paths) {
					TileType tt = get(p.cx, p.cy);
					if (tt == tileType) {
						return p.directions;
					}
					Set<Long> v = visited.get(p.cx);
					if (v == null) {
						v = new HashSet<>();
						visited.put(p.cx, v);
					}
					v.add(p.cy);
					paths2.addAll(nextStep(p));
				}
				paths = paths2;

				Collections.sort(paths2);
				int i = 1;
				while (i < paths2.size()) {
					Path p1 = paths2.get(i - 1);
					Path p2 = paths2.get(i);
					if (p1.cx == p2.cx && p1.cy == p2.cy) {
						paths.remove(i);
					} else {
						i++;
					}
				}

				paths = paths2.stream().filter(p -> {
					Set<Long> v = visited.get(p.cx);
					return v == null || !v.contains(p.cy);
				}).collect(Collectors.toList());
			}
			return null;
		}

		List<Path> nextStep(Path p1) {
			return Stream.of(new Path(p1, RepairBot.MOVE_NORTH), new Path(p1, RepairBot.MOVE_SOUTH), new Path(p1, RepairBot.MOVE_WEST),
					new Path(p1, RepairBot.MOVE_EAST)).filter(p -> {
						TileType tt = get(p.cx, p.cy);
						return tt != TileType.WALL;
					}).collect(Collectors.toList());
		}

	}

	static class Path implements Comparable<Path> {
		List<Integer> directions = new ArrayList<>();

		long cx;
		long cy;

		Path(long x, long y) {
			cx = x;
			cy = y;
		}

		Path(Path o, int direction) {
			directions.addAll(o.directions);
			directions.add(direction);
			cx = o.cx;
			cy = o.cy;
			switch (direction) {
			case RepairBot.MOVE_NORTH:
				cy--;
				break;
			case RepairBot.MOVE_SOUTH:
				cy++;
				break;
			case RepairBot.MOVE_WEST:
				cx--;
				break;
			case RepairBot.MOVE_EAST:
				cx++;
				break;
			}
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Path) {
				Path p2 = (Path) o;
				return cx == p2.cx && cy == p2.cy;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return (int) ((cx + cy) & 0x7FFF);
		}

		@Override
		public int compareTo(Path o) {
			int delta = (int) (cx - o.cx);
			if (delta != 0)
				return delta;

			return (int) (cy - o.cy);
		}
	}
}
