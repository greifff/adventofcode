package adventofcode.aoc2019.day18.retry;

import java.util.Arrays;
import java.util.List;

public enum TileType {

	WALL("####"),

	TOP(" ###"), BOTTOM("### "), LEFT("# ##"), RIGHT("## #"),

	TOPLEFT("  ##"), TOPRIGHT(" # #"), BOTTOMLEFT("# # "), BOTTOMRIGHT("##  "), TOPBOTTOM(" ## "), LEFTRIGHT("#  #"),

	TOPLEFTRIGHT("   #"), TOPLEFTBOTTOM("  # "), TOPRIGHTBOTTOM(" #  "), LEFTRIGHTBOTTOM("#   "),

	FOURWAY("    ");

	private String identifier;

	TileType(String identifier) {
		this.identifier = identifier;
	}

	public static TileType identifyTile(List<String> maze, int x, int y) {
		if (maze.get(y).charAt(x) == '#')
			return WALL;

		String signature = ("" + maze.get(y - 1).charAt(x) + maze.get(y).charAt(x - 1) + maze.get(y).charAt(x + 1) + maze.get(y + 1).charAt(x))
				.replaceAll("[a-zA-Z@]", " ");

		return Arrays.stream(values()).filter(t -> t.identifier.equals(signature)).findAny().orElse(null);
	}

}
