package adventofcode.aoc2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day3
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day03.data");

        part1(input.get(0));
        part2(input.get(0));
    }

    private static void part1(String input)
    {
        Map<Integer, Map<Integer, Integer>> grid = new HashMap<>();

        int x = 0;
        int y = 0;
        placePresent(grid, x, y);
        for (int i = 0; i < input.length(); i++)
        {
            switch (input.charAt(i))
            {
                case '>':
                    x++;
                    break;
                case '<':
                    x--;
                    break;
                case '^':
                    y--;
                    break;
                case 'v':
                    y++;
                    break;
            }
            placePresent(grid, x, y);
        }

        long houses = grid.values().stream().flatMap(l -> l.values().stream()).count();

        System.out.println("part1: " + houses);
    }

    private static void part2(String input)
    {
        Map<Integer, Map<Integer, Integer>> grid = new HashMap<>();

        int[] x =
        { 0, 0 };
        int[] y =
        { 0, 0 };
        placePresent(grid, x[0], y[0]);
        placePresent(grid, x[0], y[0]);
        for (int i = 0; i < input.length(); i++)
        {
            switch (input.charAt(i))
            {
                case '>':
                    x[i & 1]++;
                    break;
                case '<':
                    x[i & 1]--;
                    break;
                case '^':
                    y[i & 1]--;
                    break;
                case 'v':
                    y[i & 1]++;
                    break;
            }
            placePresent(grid, x[i & 1], y[i & 1]);
        }

        long houses = grid.values().stream().flatMap(l -> l.values().stream()).count();

        System.out.println("part2: " + houses);
    }

    private static void placePresent(Map<Integer, Map<Integer, Integer>> grid, int x, int y)
    {
        Map<Integer, Integer> line = grid.get(x);
        if (line == null)
        {
            line = new HashMap<>();
            grid.put(x, line);
        }
        Integer h = line.get(y);
        line.put(y, h == null ? 1 : h + 1);
    }
}
