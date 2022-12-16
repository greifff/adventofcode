package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day24
{

    public static void main(String[] args)
    {
        Grid grid1 = new Grid(IOUtil.readFile("2016/day24.test"));
        Grid grid2 = new Grid(IOUtil.readFile("2016/day24.data"));

        part1(grid1);
        part1(grid2);
    }

    private static void part1(Grid grid)
    {
        List<Character> identifiers = new ArrayList<>(grid.controlpoints.keySet());

        // Day13.print(grid.grid);

        Collections.sort(identifiers);
        Map<Character, Map<Character, Integer>> ways = new HashMap<>();
        for (int a = 0; a < identifiers.size() - 1; a++)
        {
            for (int b = a + 1; b < identifiers.size(); b++)
            {
                int x1 = grid.controlpoints.get(identifiers.get(a)) / 1000;
                int y1 = grid.controlpoints.get(identifiers.get(a)) % 1000;
                int x2 = grid.controlpoints.get(identifiers.get(b)) / 1000;
                int y2 = grid.controlpoints.get(identifiers.get(b)) % 1000;

                // System.out.println("? " + x1 + "," + y1 + " " + x2 + "," + y2);
                int length = findWayBreadthSearch(grid.grid, y1, x1, y2, x2);

                Map<Character, Integer> ways1 = ways.get(identifiers.get(a));
                if (ways1 == null)
                {
                    ways1 = new HashMap<>();
                    ways.put(identifiers.get(a), ways1);
                }
                ways1.put(identifiers.get(b), length);
                Map<Character, Integer> ways2 = ways.get(identifiers.get(b));
                if (ways2 == null)
                {
                    ways2 = new HashMap<>();
                    ways.put(identifiers.get(b), ways2);
                }
                ways2.put(identifiers.get(a), length);
                // System.out.println("# " + a + "-" + b + ": " + length);
            }
        }
        identifiers.remove(0);
        System.out.println("part1: " + visit('0', identifiers, ways));
        System.out.println("part2: " + visitAndReturn('0', '0', identifiers, ways));
    }

    public static int findWayBreadthSearch(boolean[][] grid, int startX, int startY, int targetX, int targetY)
    {
        boolean[][] grid1 = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++)
        {
            System.arraycopy(grid[x], 0, grid1[x], 0, grid[0].length);
        }

        List<SearchWay> ways = new ArrayList<>();
        ways.add(new SearchWay(grid1, startX, startY));

        int steps = 0;

        while (!ways.isEmpty())
        {
            List<SearchWay> ways1 = new ArrayList<>();
            for (SearchWay way : ways)
            {
                if (way.isAtReached(targetX, targetY))
                {
                    return steps;
                }
                ways1.addAll(way.step());
            }
            steps++;
            ways = ways1;
            // System.out.println("# " + steps + " " + ways.size());
        }
        return -1;
    }

    static class SearchWay
    {
        private boolean[][] grid;
        private int currentX;
        private int currentY;

        SearchWay(boolean[][] grid, int currentX, int currentY)
        {
            this.grid = grid;
            this.currentX = currentX;
            this.currentY = currentY;
        }

        boolean isAtReached(int targetX, int targetY)
        {
            return currentX == targetX && currentY == targetY;
        }

        boolean visitable(int x, int y)
        {
            return !grid[x][y];
        }

        List<SearchWay> step()
        {
            List<SearchWay> newWays = new ArrayList<>();

            if (grid[currentX][currentY])
            {
                return newWays;
            }

            grid[currentX][currentY] = true;

            if (visitable(currentX + 1, currentY))
            {
                newWays.add(new SearchWay(grid, currentX + 1, currentY));
            }
            if (visitable(currentX - 1, currentY))
            {
                newWays.add(new SearchWay(grid, currentX - 1, currentY));
            }
            if (visitable(currentX, currentY + 1))
            {
                newWays.add(new SearchWay(grid, currentX, currentY + 1));
            }
            if (visitable(currentX, currentY - 1))
            {
                newWays.add(new SearchWay(grid, currentX, currentY - 1));
            }
            return newWays;
        }
    }

    private static int visit(char isAt, List<Character> toVisit, Map<Character, Map<Character, Integer>> ways)
    {
        if (toVisit.isEmpty())
        {
            return 0;
        }
        int result = Integer.MAX_VALUE;
        Map<Character, Integer> ways1 = ways.get(isAt);
        for (Character t : toVisit)
        {
            List<Character> toVisit2 = new ArrayList<>(toVisit);
            toVisit2.remove(t);
            int length = ways1.get(t) + visit(t, toVisit2, ways);
            result = Math.min(result, length);
        }
        return result;
    }

    private static int visitAndReturn(char isAt, char returnTo, List<Character> toVisit,
            Map<Character, Map<Character, Integer>> ways)
    {
        if (toVisit.isEmpty())
        {
            return ways.get(isAt).get(returnTo);
        }
        int result = Integer.MAX_VALUE;
        Map<Character, Integer> ways1 = ways.get(isAt);
        for (Character t : toVisit)
        {
            List<Character> toVisit2 = new ArrayList<>(toVisit);
            toVisit2.remove(t);
            int length = ways1.get(t) + visitAndReturn(t, returnTo, toVisit2, ways);
            result = Math.min(result, length);
        }
        return result;
    }

    static class Grid
    {

        Map<Character, Integer> controlpoints = new HashMap<>();
        boolean[][] grid;

        public Grid(List<String> input)
        {
            grid = new boolean[input.get(0).length()][input.size()];
            for (int i = 0; i < input.size(); i++)
            {
                for (int j = 0; j < input.get(0).length(); j++)
                {
                    char c = input.get(i).charAt(j);
                    grid[j][i] = c == '#';
                    if (c != '.' && c != '#')
                    {
                        controlpoints.put(c, i * 1000 + j);
                    }
                }
            }
        }
    }
}
