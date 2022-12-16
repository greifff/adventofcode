package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day2
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day02.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        long sum = 0;
        for (String p : input)
        {
            String[] d = p.trim().split("x");
            int[] l = new int[3];
            for (int i = 0; i < 3; i++)
            {
                l[i] = Integer.parseInt(d[i]);
            }
            List<Integer> s = new ArrayList<>();
            s.add(l[0] * l[1]);
            s.add(l[0] * l[2]);
            s.add(l[1] * l[2]);
            Collections.sort(s);
            int a = 3 * s.get(0) + 2 * s.get(1) + 2 * s.get(2);
            sum += a;
        }
        System.out.println("part1: " + sum);
    }

    private static void part2(List<String> input)
    {
        long sum = 0;
        for (String p : input)
        {
            String[] d = p.trim().split("x");
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < 3; i++)
            {
                l.add(Integer.parseInt(d[i]));
            }
            Collections.sort(l);
            int a = 2 * l.get(0) + 2 * l.get(1) + l.get(0) * l.get(1) * l.get(2);
            sum += a;
        }
        System.out.println("part2: " + sum);
    }
}
