package adventofcode.aoc2017;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day01
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2017/day01.data");

        part1(input.get(0));
        part2(input.get(0));
    }

    private static void part1(String input)
    {
        long sum = 0;
        for (int i = 1; i < input.length(); i++)
        {
            if (input.charAt(i - 1) == input.charAt(i))
            {
                sum += Integer.parseInt("" + input.charAt(i));
            }
        }

        if (input.charAt(0) == input.charAt(input.length() - 1))
        {
            sum += Integer.parseInt("" + input.charAt(0));
        }

        System.out.println("part1: " + sum);
    }

    private static void part2(String input)
    {
        long sum = 0;
        int m = input.length();
        for (int i = 0; i < m; i++)
        {
            if (input.charAt(i) == input.charAt((i + m / 2) % m))
            {
                sum += Integer.parseInt("" + input.charAt(i));
            }
        }

        System.out.println("part1: " + sum);
    }
}
