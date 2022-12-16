package adventofcode.aoc2017;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day11
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2017/day11.data");
        part1("ne,ne,ne".split(","));
        part1("ne,ne,sw,sw".split(","));
        part1("ne,ne,s,s".split(","));
        part1("se,sw,se,sw,sw".split(","));
        part1(input.get(0).split(","));
    }

    private static void part1(String[] directions)
    {
        int x = 0;
        int y = 0;

        int current = 0;
        int farest = 0;

        for (String d : directions)
        {
            switch (d)
            {
                case "n":
                    y -= 2;
                    break;
                case "s":
                    y += 2;
                    break;
                case "ne":
                    x++;
                    y--;
                    break;
                case "nw":
                    x--;
                    y--;
                    break;
                case "se":
                    x++;
                    y++;
                    break;
                case "sw":
                    x--;
                    y++;
                    break;
            }
            current = getDistance(x, y);
            farest = Math.max(farest, current);
        }

        System.out.println("part1: " + current);
        System.out.println("part2: " + farest);
    }

    private static int getDistance(int x, int y)
    {
        int x1 = Math.abs(x);
        return x1 + Math.abs(Math.abs(y) - x1) / 2;
    }

}
