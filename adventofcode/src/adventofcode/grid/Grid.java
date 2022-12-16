package adventofcode.grid;

import java.util.HashMap;
import java.util.Map;

public class Grid<T> {

	private Map<Long, Map<Long, T>> grid = new HashMap<>();

	private final T defaultValue;

	public Grid(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public T get(long tx, long ty) {
		Map<Long, T> t1 = grid.get(tx);
		if (t1 == null)
			return defaultValue;
		T tt = t1.get(ty);
		return tt == null ? defaultValue : tt;
	}

	public void put(long tx, long ty, T tt) {
		Map<Long, T> t1 = grid.get(tx);
		if (t1 == null) {
			t1 = new HashMap<>();
			grid.put(tx, t1);
		}
		t1.put(ty, tt);
	}

	public void print() {
		long minX = getMinX();
		long maxX = getMaxX();
		long minY = getMinY();
		long maxY = getMaxY();

		for (long y1 = minY; y1 <= maxY; y1++) {
			for (long x1 = minX; x1 <= maxX; x1++) {
				System.out.print(get(x1, y1));
			}
			System.out.println();
		}
	}

	public long getMinX() {
		return grid.keySet().stream().reduce(Math::min).orElse(0L);
	}

	public long getMaxX() {
		return grid.keySet().stream().reduce(Math::max).orElse(0L);
	}

	public long getMinY() {
		return grid.values().stream().flatMap(s -> s.keySet().stream()).reduce(Math::min).orElse(0L);
	}

	public long getMaxY() {
		return grid.values().stream().flatMap(s -> s.keySet().stream()).reduce(Math::max).orElse(0L);
	}

}
