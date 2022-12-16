package adventofcode.aoc2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13
{

    public static void main(String[] args)
    {
        boolean[][] grid1 = generateGrid(10, 10, 10);
        print(grid1);
        boolean[][] grid2 = generateGrid(1364, 100, 100);
        part1(grid1, 7, 4);
        part1(grid2, 31, 39);
        part2(grid2);
    }

    private static void part2(boolean[][] grid2)
    {
        // TODO Auto-generated method stub
        System.out.println("part1: " + reachFields(grid2, 1, 1, 50).size());
    }

    public static void print(boolean[][] display)
    {
        for (int y = 0; y < display[0].length; y++)
        {
            for (int x = 0; x < display.length; x++)
            {
                System.out.print(display[x][y] ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void part1(boolean[][] grid, int targetX, int targetY)
    {
        System.out.println("part1: " + findWay(grid, 1, 1, targetX, targetY));
    }

    public static int findWay(boolean[][] grid, int startX, int startY, int targetX, int targetY)
    {
        if (startX < 0 || startX >= grid.length || startY < 0 || startY >= grid[0].length)
        {
            return -1;
        }
        if (grid[startX][startY])
        {
            return -1;
        }
        if (startX == targetX && startY == targetY)
        {
            return 0;
        }

        boolean[][] grid1 = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++)
        {
            System.arraycopy(grid[x], 0, grid1[x], 0, grid[0].length);
        }
        grid1[startX][startY] = true;

        List<Integer> results = Arrays.asList(findWay(grid1, startX + 1, startY, targetX, targetY),
                findWay(grid1, startX - 1, startY, targetX, targetY),
                findWay(grid1, startX, startY + 1, targetX, targetY),
                findWay(grid1, startX, startY - 1, targetX, targetY));

        return results.stream().filter(r -> r != -1).map(r -> r + 1).reduce((a, b) -> Math.min(a, b)).orElse(-1);
    }

    private static Set<Integer> reachFields(boolean[][] grid, int startX, int startY, int remainingSteps)
    {
        Set<Integer> reached = new HashSet<>();
        if (startX < 0 || startX >= grid.length || startY < 0 || startY >= grid.length)
        {
            return reached;
        }
        if (grid[startX][startY])
        {
            return reached;
        }

        reached.add(startX * 1000 + startY);

        if (remainingSteps == 0)
        {
            return reached;
        }

        boolean[][] grid1 = new boolean[grid.length][grid.length];
        for (int x = 0; x < grid.length; x++)
        {
            System.arraycopy(grid[x], 0, grid1[x], 0, grid.length);
        }
        grid1[startX][startY] = true;

        reached.addAll(reachFields(grid1, startX + 1, startY, remainingSteps - 1));
        reached.addAll(reachFields(grid1, startX - 1, startY, remainingSteps - 1));
        reached.addAll(reachFields(grid1, startX, startY + 1, remainingSteps - 1));
        reached.addAll(reachFields(grid1, startX, startY - 1, remainingSteps - 1));

        return reached;
    }

    private static boolean[][] generateGrid(int designerNumber, int maxX, int maxY)
    {
        boolean[][] grid = new boolean[maxX][maxY];
        for (int x1 = 0; x1 < maxX; x1++)
        {
            for (int y1 = 0; y1 < maxY; y1++)
            {
                int v = x1 * x1 + 3 * x1 + 2 * x1 * y1 + y1 + y1 * y1 + designerNumber;
                grid[x1][y1] = isWall(v);
            }
        }
        return grid;
    }

    private static boolean isWall(int v)
    {
        return (Integer.toBinaryString(v).replace("0", "").length() & 1) == 1;
    }
}
