package adventofcode.aoc2015;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day8
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day08.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        int sum = input.stream().map(s -> calculateDelta1(s)).reduce((a, b) -> a + b).orElse(0);

        System.out.println("part1: " + sum);
    }

    private static void part2(List<String> input)
    {
        int sum = input.stream().map(s -> calculateDelta2(s)).reduce((a, b) -> a + b).orElse(0);

        System.out.println("part2: " + sum);
    }

    private static int calculateDelta1(String in)
    {
        int delta = 2;// remove leading and trailing "

        int i = 0;
        while (i < in.length())
        {
            i = in.indexOf('\\', i);
            if (i == -1)
            {
                break;
            }
            char c = in.charAt(i + 1);
            if (c == 'x')
            {
                i += 4;
                delta += 3;
            }
            else
            {
                i += 2;
                delta++;
            }
        }

        return delta;
    }

    private static int calculateDelta2(String in)
    {
        int delta = 4;// leading and trailing "

        int i = 0;
        while (i < in.length())
        {
            i = in.indexOf('\\', i);
            if (i == -1)
            {
                break;
            }
            char c = in.charAt(i + 1);
            if (c == 'x')
            {
                delta++;
            }
            else if (c == '\\')
            {
                i++;
                delta += 2;
            }
            else
            {
                delta += 2;
            }
            i++;
        }

        return delta;
    }
}
