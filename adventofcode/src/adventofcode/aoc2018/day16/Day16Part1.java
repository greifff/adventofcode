package adventofcode.aoc2018.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day16Part1 {

	public static void main(String[] args) {
		List<String> data = IOUtil.readFile("2018/day16.data1");
		List<Dataset> datasets = parseDatasets(data);

		processDatasets(datasets);

		// for (Map.Entry<Integer, List<Operation>> e : candidates.entrySet()) {
		// System.out.print("" + e.getKey() + ": ");
		// for (Operation op : e.getValue()) {
		// System.out.print("" + op + " ");
		// }
		// System.out.println();
		// }

		System.out.println(hits);
	}

	static Map<Integer, List<Operation>> candidates = new HashMap<>();
	static int hits = 0;

	private static void processDatasets(List<Dataset> datasets) {
		for (Dataset dataset : datasets) {
			List<Operation> ops = null; // candidates.get(dataset.op[0]);
			if (ops == null) {
				ops = new ArrayList<>(Arrays.asList(Operation.values()));
				candidates.put(dataset.op[0], ops);
			}

			List<Operation> failed = new ArrayList<>();

			for (Operation op : ops) {

				Registers.INSTANCE.set(dataset.before);

				try {
					op.triConsumer.accept(dataset.op[1], dataset.op[2], dataset.op[3]);
				} catch (Throwable t) {
					failed.add(op);
					continue;
				}

				if (!Registers.INSTANCE.check(dataset.after)) {
					// System.out.println(Arrays.stream(Registers.INSTANCE.registers).boxed().map(i -> "" + i).reduce((a, b) -> a + " " + b));
					// System.out.println("b " + op);
					failed.add(op);
				}
			}
			ops.removeAll(failed);
			if (ops.size() >= 3) {
				hits++;
			}
		}
	}

	private static List<Dataset> parseDatasets(List<String> data) {
		List<Dataset> datasets = new ArrayList<>();
		for (int i = 0; i < data.size(); i += 4) {
			Dataset d = new Dataset();

			String before = data.get(i);
			before = before.replace(",", "").replace("]", "");
			int k = before.indexOf("[");
			before = before.substring(k + 1);
			d.before = Arrays.stream(before.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();

			String op = data.get(i + 1);
			d.op = Arrays.stream(op.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();

			String after = data.get(i + 2);
			after = after.replace(",", "").replace("]", "");
			k = after.indexOf("[");
			after = after.substring(k + 1);
			d.after = Arrays.stream(after.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();

			datasets.add(d);
		}
		return datasets;
	}

	static class Dataset {
		int[] before;
		int[] after;
		int[] op;
	}

}
