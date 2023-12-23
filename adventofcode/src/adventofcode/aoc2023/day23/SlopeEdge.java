package adventofcode.aoc2023.day23;

class SlopeEdge {

	SlopeNode to;
	int weight;

	SlopeEdge(SlopeNode to, int weight) {
		this.to = to;
		this.weight = weight;
	}

	public SlopeNode to() {
		return to;
	}

	public int weight() {
		return weight;
	}

}