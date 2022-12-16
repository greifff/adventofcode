package adventofcode.aoc2019.day18;

import java.util.ArrayList;
import java.util.List;

public class Walker {

	public int steps;
	public int position;

	public Walker(int position, int steps) {
		this.position = position;
		this.steps = steps;
	}

	public List<Walker> walk() {
		List<Walker> result = new ArrayList<>();
		result.add(new Walker(position - 1000, steps + 1));
		result.add(new Walker(position + 1000, steps + 1));
		result.add(new Walker(position - 1, steps + 1));
		result.add(new Walker(position + 1, steps + 1));
		return result;
	}

}
