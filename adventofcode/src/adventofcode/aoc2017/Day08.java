package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day08
{

    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day08.test");
        List<String> input2 = IOUtil.readFile("2017/day08.data");

        part1(input1);
        part1(input2);
    }

    private static void part1(List<String> input1)
    {
        Map<String, Integer> registers = new HashMap<>();

        int max2 = 0;

        for (String op : input1)
        {
            execute(registers, op);
            max2 = Math.max(max2, registers.values().stream().reduce(Math::max).orElse(0));
        }

        int max1 = registers.values().stream().reduce(Math::max).orElse(0);

        System.out.println("part1: " + max1);
        System.out.println("part2: " + max2);
    }

    private static void execute(Map<String, Integer> registers, String op)
    {
        String[] f = op.split(" ");

        int c1 = get(registers, f[4]);
        int c2 = Integer.parseInt(f[6]);
        boolean check = false;
        switch (f[5])
        {
            case "==":
                check = c1 == c2;
                break;
            case "!=":
                check = c1 != c2;
                break;
            case "<":
                check = c1 < c2;
                break;
            case "<=":
                check = c1 <= c2;
                break;
            case ">":
                check = c1 > c2;
                break;
            case ">=":
                check = c1 >= c2;
                break;
        }

        if (check)
        {
            int d = get(registers, f[0]);
            d += ("inc".equals(f[1]) ? 1 : -1) * Integer.parseInt(f[2]);
            registers.put(f[0], d);
        }
    }

    private static int get(Map<String, Integer> registers, String name)
    {
        Integer value = registers.get(name);
        return value == null ? 0 : value;
    }
}
