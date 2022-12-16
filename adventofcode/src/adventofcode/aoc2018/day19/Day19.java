package adventofcode.aoc2018.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day19 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2018/day19.data");

		int ipRegister = Integer.parseInt(data.get(0).substring(4));
		data.remove(0);

		// part 2: modify register 0 to value 1
		Registers.INSTANCE.registers[0] = 1;

		List<Dataset> datasets = parseDatasets(data);
		runProgram(ipRegister, datasets);

	}

	private static void runProgram(int ipRegister, List<Dataset> datasets) {
		int[] r = Registers.INSTANCE.registers;
		while (r[ipRegister] < datasets.size()) {
			Dataset d = datasets.get(r[ipRegister]);

			d.op.triConsumer.accept(d.a, d.b, d.c);

			r[ipRegister] = r[ipRegister] + 1;

			System.out.println(Arrays.stream(r).boxed().map(v -> "" + v).reduce((a, b) -> a + " " + b));

		}
		System.out.println(Arrays.stream(r).boxed().map(v -> "" + v).reduce((a, b) -> a + " " + b));
	}

	private static List<Dataset> parseDatasets(List<String> data) {
		List<Dataset> datasets = new ArrayList<>();
		for (String d1 : data) {
			Dataset d = new Dataset();

			String[] parts = d1.split(" ");
			d.op = Operation.valueOf(parts[0]);
			d.a = Integer.parseInt(parts[1]);
			d.b = Integer.parseInt(parts[2]);
			d.c = Integer.parseInt(parts[3]);

			datasets.add(d);
		}
		return datasets;
	}

}
