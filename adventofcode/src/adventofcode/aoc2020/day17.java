package adventofcode.aoc2020;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class day17
{

    public static void main(String[] args)
    {
        String[] input =
                // { ".#.", "..#", "###" };
                { ".##.####", ".#.....#", "#.###.##", "#####.##", "#...##.#", "#######.", "##.#####", ".##...#." };

        part1(initGrid(input));
    }

    private static void part1(Map<Integer, Map<Integer, Map<Integer, Cube>>> grid)
    {
        // TODO Auto-generated method stub
        for (int i = 0; i < 6; i++)
        {
            System.out.println("#" + i + " " + countActive(grid));
            determineNewState(grid);
            updateState(grid);
        }

        System.out.println("part1: " + countActive(grid));
    }

    private static final class Cube
    {
        boolean newState;
        boolean oldState;
        int x;
        int y;
        int z;
    }

    private static Map<Integer, Map<Integer, Map<Integer, Cube>>> initGrid(String[] input)
    {
        Map<Integer, Map<Integer, Map<Integer, Cube>>> grid = new HashMap<>();

        for (int y = 0; y < input.length; y++)
        {
            String row = input[y];
            for (int x = 0; x < row.length(); x++)
            {
                Cube c = getCube(grid, x, y, 0);
                c.oldState = row.charAt(x) == '#';
                putCube(grid, c);
            }
        }
        return grid;
    }

    private static void updateState(Map<Integer, Map<Integer, Map<Integer, Cube>>> grid)
    {
        grid.values().forEach(x1 -> x1.values().forEach(y1 -> y1.values().forEach(z1 -> z1.oldState = z1.newState)));
    }

    private static int countActive(Map<Integer, Map<Integer, Map<Integer, Cube>>> grid)
    {
        return grid.values().stream().map( //
                x1 -> x1.values().stream().map( //
                        y1 -> y1.values().stream().map( //
                                z1 -> (z1.oldState ? 1 : 0)).reduce(day17::sum).orElse(0))
                        .reduce(day17::sum).orElse(0))
                .reduce(day17::sum).orElse(0);
    }

    private static int sum(int a, int b)
    {
        return a + b;
    }

    private static Cube getCube(Map<Integer, Map<Integer, Map<Integer, Cube>>> grid, int x, int y, int z)
    {
        Map<Integer, Map<Integer, Cube>> x1 = grid.get(x);
        if (x1 != null)
        {
            Map<Integer, Cube> y1 = x1.get(y);
            if (y1 != null)
            {
                Cube z1 = y1.get(z);
                if (z1 != null)
                {
                    return z1;
                }
            }
        }
        Cube c = new Cube();
        c.x = x;
        c.y = y;
        c.z = z;
        return c;
    }

    private static void putCube(Map<Integer, Map<Integer, Map<Integer, Cube>>> grid, Cube c)
    {
        Map<Integer, Map<Integer, Cube>> x1 = grid.get(c.x);
        if (x1 == null)
        {
            x1 = new HashMap<>();
            grid.put(c.x, x1);
        }
        Map<Integer, Cube> y1 = x1.get(c.y);
        if (y1 == null)
        {
            y1 = new HashMap<>();
            x1.put(c.y, y1);
        }
        y1.put(c.z, c);
    }

    private static void determineNewState(Map<Integer, Map<Integer, Map<Integer, Cube>>> grid)
    {
        Set<Cube> knownCubes = new HashSet<>();
        Set<Cube> newCubes = new HashSet<>();

        grid.values().forEach(x1 -> x1.values().forEach(y1 -> knownCubes.addAll(y1.values())));

        for (Cube k1 : knownCubes)
        {
            int activeAround = 0;
            for (int dx = -1; dx <= 1; dx++)
            {
                for (int dy = -1; dy <= 1; dy++)
                {
                    for (int dz = -1; dz <= 1; dz++)
                    {
                        if (dx != 0 || dy != 0 || dz != 0)
                        {
                            Cube k2 = getCube(grid, k1.x + dx, k1.y + dy, k1.z + dz);
                            activeAround += k2.oldState ? 1 : 0;
                            if (!knownCubes.contains(k2))
                            {
                                newCubes.add(k2);
                            }
                        }
                    }
                }
            }

            k1.newState = (activeAround == 3) || (k1.oldState && activeAround == 2);
        }

        for (Cube k1 : newCubes)
        {
            int activeAround = 0;
            for (int dx = -1; dx <= 1; dx++)
            {
                for (int dy = -1; dy <= 1; dy++)
                {
                    for (int dz = -1; dz <= 1; dz++)
                    {
                        if (dx != 0 || dy != 0 || dz != 0)
                        {
                            Cube k2 = getCube(grid, k1.x + dx, k1.y + dy, k1.z + dz);
                            activeAround += k2.oldState ? 1 : 0;
                        }
                    }
                }
            }
            if (activeAround == 3)
            {
                k1.newState = true;
                putCube(grid, k1);
            }
        }
    }

}
