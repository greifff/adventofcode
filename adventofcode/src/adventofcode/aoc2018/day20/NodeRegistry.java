package adventofcode.aoc2018.day20;

import java.util.HashMap;
import java.util.Map;

public class NodeRegistry {

	Map<Integer, Map<Integer, MapNode>> nodes = new HashMap<>();

	MapNode getOrCreateNode(int x, int y) {
		Map<Integer, MapNode> column = nodes.get(x);
		if (column == null) {
			column = new HashMap<>();
			nodes.put(x, column);
		}
		MapNode node = column.get(y);
		if (node == null) {
			node = new MapNode();
			node.x = x;
			node.y = y;
			node.nodeRegistry = this;
			column.put(y, node);
		}
		return node;
	}
}
