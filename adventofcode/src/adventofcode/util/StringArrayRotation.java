package adventofcode.util;

import java.util.ArrayList;
import java.util.List;

public class StringArrayRotation {

	public static List<String> rotate90clockwise(List<String> lines) {

		List<String> t1 = new ArrayList<>();

		for (int j = 0; j < lines.get(0).length(); j++) {
			StringBuilder sb = new StringBuilder();
			for (int i = lines.size() - 1; i >= 0; i--) {
				sb.append(lines.get(i).charAt(j));
			}
			t1.add(sb.toString());
		}
		return t1;
	}

	public static List<String> rotate90counterClockwise(List<String> lines) {
		List<String> t1 = new ArrayList<>();

		for (int j = lines.get(0).length() - 1; j >= 0; j--) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < lines.size(); i++) {
				sb.append(lines.get(i).charAt(j));
			}
			t1.add(sb.toString());
		}
		return t1;
	}

	public static List<String> rotate180(List<String> lines) {
		List<String> t1 = new ArrayList<>();
		for (int i = lines.size() - 1; i >= 0; i--) {
			String s = lines.get(i);

			StringBuilder sb = new StringBuilder();
			for (int j = s.length() - 1; j >= 0; j--) {
				sb.append(s.charAt(j));
			}
			t1.add(sb.toString());
		}
		return t1;
	}
}
