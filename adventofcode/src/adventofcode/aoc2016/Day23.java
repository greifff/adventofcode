package adventofcode.aoc2016;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day23
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day23.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        AssembunnyComputer c = new AssembunnyComputer();
        c.setRegister("a", 7);
        c.execute(input);

        System.out.println("part1: " + c.getRegister("a"));
    }

    private static void part2(List<String> input)
    {
        AssembunnyComputer c = new AssembunnyComputer();
        c.setRegister("a", 12);
        c.execute(input);

        System.out.println("part2: " + c.getRegister("a"));
    }
}
