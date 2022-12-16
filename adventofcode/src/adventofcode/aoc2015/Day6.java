package adventofcode.aoc2015;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day6
{

    static class Command
    {
        String order;
        int x1;
        int x2;
        int y1;
        int y2;

        Command(String in)
        {
            String[] frg = in.split(" ");

            int m = "toggle".equals(frg[0]) ? 0 : 1;

            order = frg[m];

            String[] c1 = frg[m + 1].split(",");
            String[] c2 = frg[m + 3].split(",");

            x1 = Integer.parseInt(c1[0]);
            y1 = Integer.parseInt(c1[1]);
            x2 = Integer.parseInt(c2[0]);
            y2 = Integer.parseInt(c2[1]);
        }

        void execute(boolean[][] lights)
        {
            UnaryOperator<Boolean> switchLights = a -> a;
            switch (order)
            {
                case "toggle":
                    switchLights = a -> !a;
                    break;
                case "on":
                    switchLights = a -> true;
                    break;
                case "off":
                    switchLights = a -> false;
                    break;
            }

            for (int x3 = x1; x3 <= x2; x3++)
            {
                for (int y3 = y1; y3 <= y2; y3++)
                {
                    lights[x3][y3] = switchLights.apply(lights[x3][y3]);
                }
            }
        }

        void execute(int[][] lights)
        {
            UnaryOperator<Integer> switchLights = a -> a;
            switch (order)
            {
                case "toggle":
                    switchLights = a -> a + 2;
                    break;
                case "on":
                    switchLights = a -> a + 1;
                    break;
                case "off":
                    switchLights = a -> Math.max(a - 1, 0);
                    break;
            }

            for (int x3 = x1; x3 <= x2; x3++)
            {
                for (int y3 = y1; y3 <= y2; y3++)
                {
                    lights[x3][y3] = switchLights.apply(lights[x3][y3]);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day06.data");
        List<Command> commands = input.stream().map(s -> new Command(s)).collect(Collectors.toList());
        part1(commands);
        part2(commands);
    }

    private static void part1(List<Command> commands)
    {
        boolean[][] lights = new boolean[1000][1000];

        for (Command c : commands)
        {
            c.execute(lights);
        }

        int shining = 0;
        for (int x = 0; x < 1000; x++)
        {
            for (int y = 0; y < 1000; y++)
            {
                if (lights[x][y])
                {
                    shining++;
                }
            }
        }

        System.out.println("part1: " + shining);
    }

    private static void part2(List<Command> commands)
    {
        int[][] lights = new int[1000][1000];

        for (Command c : commands)
        {
            c.execute(lights);
        }

        int brightness = 0;
        for (int x = 0; x < 1000; x++)
        {
            for (int y = 0; y < 1000; y++)
            {
                brightness += lights[x][y];
            }
        }

        System.out.println("part2: " + brightness);
    }

}
