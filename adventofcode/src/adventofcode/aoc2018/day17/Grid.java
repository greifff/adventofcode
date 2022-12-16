package adventofcode.aoc2018.day17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Grid {

	private Map<Integer, Map<Integer, GroundType>> groundComposition = new HashMap<>();

	private int minY = Integer.MAX_VALUE;
	private int maxY = 0;

	public void placeClay(String clayData) {
		StringTokenizer st = new StringTokenizer(clayData, " =,.");
		String orientation = st.nextToken();
		int coord1 = Integer.parseInt(st.nextToken());
		st.nextToken();
		int coord2 = Integer.parseInt(st.nextToken());
		int coord3 = Integer.parseInt(st.nextToken());

		if ("x".equals(orientation)) {
			for (int y = coord2; y <= coord3; y++) {
				setField(coord1, y, GroundType.CLAY);
			}
			minY = Math.min(minY, coord2);
			maxY = Math.max(maxY, coord3);
		} else {
			for (int x = coord2; x <= coord3; x++) {
				setField(x, coord1, GroundType.CLAY);
			}
			minY = Math.min(minY, coord1);
			maxY = Math.max(maxY, coord1);
		}
	}

	public void setField(int x, int y, GroundType gt) {
		Map<Integer, GroundType> column = groundComposition.get(x);
		if (column == null) {
			column = new HashMap<>();
			groundComposition.put(x, column);
		}
		column.put(y, gt);
	}

	public GroundType getField(int x, int y) {
		Map<Integer, GroundType> column = groundComposition.get(x);
		if (column == null) {
			return GroundType.SAND;
		}
		GroundType value = column.get(y);
		return value == null ? GroundType.SAND : value;
	}

	public long countWaterTiles() {
		return groundComposition.values().stream().map( //
				column -> column.entrySet().stream().filter(e -> e.getKey() >= minY && e.getKey() <= maxY).map(e -> e.getValue())
						.filter(v -> v == GroundType.FLOWING_WATER || v == GroundType.STANDING_WATER).count()) //
				.reduce((a, b) -> a + b).orElse(0L);
	}

	public long countStandingWaterTiles() {
		return groundComposition.values().stream().map( //
				column -> column.entrySet().stream().filter(e -> e.getKey() >= minY && e.getKey() <= maxY).map(e -> e.getValue())
						.filter(v -> v == GroundType.STANDING_WATER).count()) //
				.reduce((a, b) -> a + b).orElse(0L);
	}

	public int getMaxY() {
		return maxY;
	}

	public void print() {
		List<Map.Entry<Integer, Map<Integer, GroundType>>> l = new ArrayList<>(groundComposition.entrySet());
		Collections.sort(l, (e1, e2) -> e1.getKey() - e2.getKey());

		List<Map<Integer, GroundType>> l2 = l.stream().map(e -> e.getValue()).collect(Collectors.toList());

		for (int y = minY; y <= maxY; y++) {
			for (Map<Integer, GroundType> column : l2) {
				GroundType gt = column.get(y);
				if (gt == null) {
					System.out.print('.');
					continue;
				}
				switch (gt) {
				case SAND:
					System.out.print('.');
					break;
				case CLAY:
					System.out.print('#');
					break;
				case STANDING_WATER:
					System.out.print('~');
					break;
				case FLOWING_WATER:
					System.out.print('|');
					break;
				}
			}
			System.out.println();
		}
	}
}
