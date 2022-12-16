package adventofcode.aoc2018;

public class Day11
{

    private static final int GSN = 3214;

    public static void main(String[] args)
    {
        int[][] grid = createGrid();
        part1(grid);
        part2(grid);
    }

    private static void part2(int[][] grid)
    {
        int[] maxPowerLevel = new int[]
        { 32, 21, 42, 3 };

        for (int i = 4; i <= 13; i++)
        {
            int[] powerLevel2 = findMaxPowerLevel(grid, i);
            if (maxPowerLevel[0] < powerLevel2[0])
            {
                maxPowerLevel = powerLevel2;
                System.out.println("# " + i + " " + maxPowerLevel[0]);
            }
        }

        System.out.println("part2: " + maxPowerLevel[0] + "@" + maxPowerLevel[1] + "," + maxPowerLevel[2] + ","
                + maxPowerLevel[3]);
    }

    private static void part1(int[][] grid)
    {
        int[] maxPowerLevel = findMaxPowerLevel(grid, 3);
        System.out.println("part1: " + maxPowerLevel[0] + "@" + maxPowerLevel[1] + "," + maxPowerLevel[2]);
    }

    private static int[] findMaxPowerLevel(int[][] grid, int gridsize)
    {
        int maxPower = Integer.MIN_VALUE;
        int[] result = null;
        for (int x = 1; x <= 300 - gridsize + 1; x++)
        {
            for (int y = 1; y < 300 - gridsize + 1; y++)
            {
                int power = 0;
                for (int x1 = x; x1 < x + gridsize; x1++)
                {
                    for (int y1 = y; y1 < y + gridsize; y1++)
                    {
                        power += grid[x1 - 1][y1 - 1];
                    }
                }
                if (power > maxPower)
                {
                    maxPower = power;
                    result = new int[]
                    { power, x, y, gridsize };
                }
            }
        }
        return result;
    }

    private static int[][] createGrid()
    {
        int[][] grid = new int[300][300];
        for (int x = 1; x <= 300; x++)
        {
            for (int y = 1; y <= 300; y++)
            {
                grid[x - 1][y - 1] = power(x, y);
            }
        }
        return grid;
    }

    private static int power(int x, int y)
    {
        int rackId = x + 10;
        long powerLevel = rackId * y + GSN;
        powerLevel *= rackId;

        powerLevel /= 100;

        return (int) (powerLevel % 10) - 5;
    }
}
