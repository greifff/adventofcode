package adventofcode.aoc2019.intcode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class IntGrid implements Supplier<Long>, Consumer<Long> {

	protected Map<Long, Map<Long, Long>> grid = new HashMap<>();

	public final void put(long x, long y, long color) {
		Map<Long, Long> c = grid.get(x);
		if (c == null) {
			c = new HashMap<>();
			grid.put(x, c);
		}
		c.put(y, color);
	}

	public final long get(long x, long y) {
		Map<Long, Long> c = grid.get(x);
		if (c == null) {
			return 0;
		}
		Long v = c.get(y);
		return v == null ? 0 : v;
	}

	public final void draw() {
		long minX = grid.keySet().stream().reduce(Math::min).orElse(0L);
		long maxX = grid.keySet().stream().reduce(Math::max).orElse(0L);
		long minY = grid.values().stream().flatMap(m -> m.keySet().stream()).reduce(Math::min).orElse(0L);
		long maxY = grid.values().stream().flatMap(m -> m.keySet().stream()).reduce(Math::max).orElse(0L);

		for (long y = minY; y <= maxY; y++) {
			for (long x = minX; x <= maxX; x++) {
				Map<Long, Long> s = grid.get(x);
				if (s == null) {
					System.out.print(forPrint(null));
				} else {
					System.out.print(forPrint(s.get(y)));
				}
			}
			System.out.println();
		}
	}

	protected abstract char forPrint(Long value);
}
