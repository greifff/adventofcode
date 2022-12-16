package adventofcode.aoc2018.day22;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaveSystem {

	final int depth;

	int rangeX;
	int rangeY;

	List<List<Region>> regions = new ArrayList<>();

	Region getRegion(int x, int y) {
		if (x < 0 || y < 0 || x > rangeX || y > rangeY) {
			return null;
		}
		return regions.get(y).get(x);
	}

	CaveSystem(int targetX, int targetY, int rangeX, int rangeY, int depth) {
		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.depth = depth;

		for (int i = 0; i <= rangeY; i++) {
			List<Region> row = new ArrayList<>();
			regions.add(row);
			for (int j = 0; j <= rangeX; j++) {
				Region r = new Region(j, i, this);
				row.add(r);
				if (j == targetX && i == targetY) {
					r.geologicIndex = 0;
				} else {
					r.updateGeologicIndex();
				}
			}
		}

	}

	int sumRiskLevel() {
		int risk = 0;
		for (int i = 0; i <= rangeY; i++) {
			List<Region> row = regions.get(i);
			for (int j = 0; j <= rangeX; j++) {
				risk += row.get(j).getType().riskLevel;
			}
		}
		return risk;
	}

	void print() {
		for (int i = 0; i <= rangeY; i++) {
			List<Region> row = regions.get(i);
			for (int j = 0; j <= rangeX; j++) {
				System.out.print(row.get(j).getType().abbrev);
			}
			System.out.println();
		}
	}

	public void printHtml(Path path) {

		Map<Region, Tool> r2t = new HashMap<>();
		Path path1 = path;
		while (path1 != null) {
			r2t.put(path1.region, path1.tool);
			path1 = path1.previous;
		}
		try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("r:/output.html"), StandardCharsets.UTF_8))) {
			w.write("<html><body><p style='font-family:monospace;'>");
			for (int i = 0; i <= rangeY; i++) {
				List<Region> row = regions.get(i);
				for (int j = 0; j <= rangeX; j++) {
					Region r = row.get(j);
					Tool t = r2t.get(r);
					if (t == null) {
						w.write(r.getType().abbrev);
					} else {
						switch (t) {
						case TORCH:
							w.write("<span style='background:red;'>" + r.getType().abbrev + "</span>");
							break;
						case NOTHING:
							w.write("<span style='background:lightgreen;'>" + r.getType().abbrev + "</span>");
							break;
						case CLIMBING_GEAR:
							w.write("<span style='background:lightblue;'>" + r.getType().abbrev + "</span>");
							break;
						}
					}
				}
				w.write("<br>");
			}
			w.write("</p></body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
