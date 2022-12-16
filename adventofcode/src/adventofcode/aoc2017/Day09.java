package adventofcode.aoc2017;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day09
{
    public static void main(String[] args)
    {
        // part1("{}");
        // part1("{{{}}}");
        // part1("{{},{}}");
        // part1("{{{},{},{{}}}}");
        // part1("{<{},{},{{}}>}");
        // part1("{<a>,<a>,<a>,<a>}");
        // part1("{{<a>},{<a>},{<a>},{<a>}}");
        // part1("{{<!>},{<!>},{<!>},{<a>}}");
        // part1("{{<ab>},{<ab>},{<ab>},{<ab>}}");
        // part1("{{<!!>},{<!!>},{<!!>},{<!!>}}");
        // part1("{{<a!>},{<a!>},{<a!>},{<ab>}}");
        part1("<>");
        part1("<random characters>");
        part1("<<<<>");
        part1("<{!>}>");
        part1("<!!>");
        part1("<!!!>>");
        part1("<{o\"i!a,<{i<a>");

        List<String> input1 = IOUtil.readFile("2017/day09.data");

        part1(input1.get(0));
    }

    private static void part1(String input)
    {
        int stackdepth = 0;
        int groupcount = 0;
        int score = 0;
        boolean skip = false;
        boolean garbage = false;
        int garbageCharacters = 0;

        for (int index = 0; index < input.length(); index++)
        {
            if (skip)
            {
                skip = false;
            }
            else if (input.charAt(index) == '!')
            {
                skip = true;
            }
            else if (garbage)
            {
                if (input.charAt(index) == '>')
                {
                    garbage = false;
                }
                else
                {
                    garbageCharacters++;
                }
            }
            else if (input.charAt(index) == '<')
            {
                garbage = true;
            }
            else if (input.charAt(index) == '{')
            {
                stackdepth++;
            }
            else if (input.charAt(index) == '}')
            {
                score += stackdepth;
                stackdepth--;
                groupcount++;
            }
        }

        System.out.println("part1: " + score + " (" + groupcount + ", " + stackdepth + ")");
        System.out.println("part2: " + garbageCharacters);
    }
}
