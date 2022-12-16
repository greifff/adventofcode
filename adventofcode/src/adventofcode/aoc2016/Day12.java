package adventofcode.aoc2016;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day12
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day12.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> statements)
    {
        AssembunnyComputer c = new AssembunnyComputer();
        c.execute(statements);

        System.out.println("part1: " + c.getRegister("a"));
    }

    private static void part2(List<String> statements)
    {
        AssembunnyComputer c = new AssembunnyComputer();
        c.setRegister("c", 1);
        c.execute(statements);

        System.out.println("part2: " + c.getRegister("a"));
    }

}
