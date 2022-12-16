package adventofcode.aoc2015;

import java.util.List;

import adventofcode.conway.ConwayGrid;
import adventofcode.util.IOUtil;

public class Day18
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day18.data");
        ConwayGrid grid = new ConwayGrid(input.size(), input.size(), (a, b) -> {
            int c = b.stream().map(p -> p ? 1 : 0).reduce((m, n) -> m + n).orElse(0);
            return c == 3 || (a && (c == 2));
        });
        for (int y = 0; y < input.size(); y++)
        {
            for (int x = 0; x < input.size(); x++)
            {
                grid.set(x, y, input.get(y).charAt(x) == '#');
            }
        }

        for (int i = 0; i < 100; i++)
        {
            grid.step();
        }

        System.out.println("part1: " + grid.countActive());

        grid = new ConwayGrid(input.size(), input.size(), (a, b) -> {
            int c = b.stream().map(p -> p ? 1 : 0).reduce((m, n) -> m + n).orElse(0);
            return c == 3 || (a && (c == 2));
        });
        grid.setOverride((v, c) -> {
            if ((c[0] == 0 || c[0] == 99) && (c[1] == 0 || c[1] == 99))
            {
                return true;
            }
            return v;
        });

        for (int y = 0; y < input.size(); y++)
        {
            for (int x = 0; x < input.size(); x++)
            {
                grid.set(x, y, input.get(y).charAt(x) == '#');
            }
        }

        System.out.println("// " + grid.countActive());

        grid.set(0, 0, true);
        grid.set(0, 99, true);
        grid.set(99, 0, true);
        grid.set(99, 99, true);

        System.out.println("// " + grid.countActive());

        for (int i = 0; i < 100; i++)
        {
            grid.step();
        }

        System.out.println("part2: " + grid.countActive());

    }
}
