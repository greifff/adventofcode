package adventofcode.aoc2018.day22;

public enum RegionType {
	ROCKY(0, Tool.NOTHING, "."), WET(1, Tool.TORCH, "="), NARROW(2, Tool.CLIMBING_GEAR, "|");

	final int riskLevel;
	final Tool cantUse;
	final String abbrev;

	RegionType(int riskLevel, Tool cantUse, String abbrev) {
		this.riskLevel = riskLevel;
		this.cantUse = cantUse;
		this.abbrev = abbrev;
	}
}
