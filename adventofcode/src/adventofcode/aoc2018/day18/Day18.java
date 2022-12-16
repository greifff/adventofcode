package adventofcode.aoc2018.day18;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day18 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2018/day18.test");

		LumberCollectionArea area = new LumberCollectionArea(10);

		area.init(data);

		for (int i = 0; i < 10; i++) {
			area = area.nextStep();
		}

		System.out.println(area.countType(WoodState.WOODED) * area.countType(WoodState.LUMBERYARD));

		/////////////////

		data = IOUtil.readFile("2018/day18.data");

		area = new LumberCollectionArea(50);

		area.init(data);

		for (int i = 0; i < 10; i++) {
			area = area.nextStep();
		}

		System.out.println(area.countType(WoodState.WOODED) * area.countType(WoodState.LUMBERYARD));

	}
}
