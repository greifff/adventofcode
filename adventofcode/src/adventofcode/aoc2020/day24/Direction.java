package adventofcode.aoc2020.day24;

import java.util.ArrayList;
import java.util.List;

public enum Direction
{

    W("w", -2, 0),
    NW("nw", -1, -1),
    SW("sw", -1, 1),
    E("e", 2, 0),
    NE("ne", 1, -1),
    SE("se", 1, 1);

    private String value;
    private int dx;
    private int dy;

    Direction(String representation, int dx, int dy)
    {
        value = representation;
        this.dx = dx;
        this.dy = dy;
    }

    public int getDX()
    {
        return dx;
    }

    public int getDY()
    {
        return dy;
    }

    public static Direction ofValue(String value)
    {
        for (Direction d : values())
        {
            if (d.value.equals(value))
            {
                return d;
            }
        }
        return null;
    }

    public static List<Direction> parseDirections(String input)
    {
        List<Direction> directions = new ArrayList<>();
        String predicate = "";
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            switch (c)
            {
                case 'n':
                case 's':
                    predicate = "" + c;
                    break;
                case 'w':
                case 'e':
                    directions.add(ofValue(predicate + c));
                    predicate = "";
                    break;
            }
        }
        return directions;
    }
}
