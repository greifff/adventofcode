package adventofcode.aoc2020.day24;

public class Tile
{
    final int x;
    final int y;
    boolean isBlack;

    Tile(int x, int y, boolean isBlack)
    {
        this.x = x;
        this.y = y;
        this.isBlack = isBlack;
    }

    Tile(Tile from, Direction d)
    {
        this.x = from.x + d.getDX();
        this.y = from.y + d.getDY();
    }
}
