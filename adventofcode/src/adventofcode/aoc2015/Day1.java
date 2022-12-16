package adventofcode.aoc2015;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day1
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day01.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        int floor = 0;
        for (String s : input)
        {
            for (int i = 0; i < s.length(); i++)
            {
                floor += s.charAt(i) == '(' ? 1 : -1;
            }
            System.out.println("# " + floor);
        }
        System.out.println("part1: " + floor);
    }

    private static void part2(List<String> input)
    {
        int floor = 0;
        for (String s : input)
        {
            for (int i = 0; i < s.length(); i++)
            {
                floor += s.charAt(i) == '(' ? 1 : -1;

                if (floor == -1)
                {
                    System.out.println("part2: " + (i + 1));
                    return;
                }
            }
        }

    }
}
