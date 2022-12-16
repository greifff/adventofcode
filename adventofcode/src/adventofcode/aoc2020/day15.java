package adventofcode.aoc2020;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class day15
{

    public static void main(String[] args)
    {

        part1(0, 3, 6);
        part1(1, 3, 2);
        part1(2, 1, 3);
        part1(1, 2, 3);
        part1(2, 3, 1);
        part1(3, 2, 1);
        part1(3, 1, 2);
        part1(7, 14, 0, 17, 11, 1, 2);

        part2(0, 3, 6);
        part2(1, 3, 2);
        part2(2, 1, 3);
        part2(1, 2, 3);
        part2(2, 3, 1);
        part2(3, 2, 1);
        part2(3, 1, 2);
        part2(7, 14, 0, 17, 11, 1, 2);
    }

    private static void part1(int... input)
    {
        List<Integer> spoken = new LinkedList<>();
        // starting numbers
        for (int s : input)
        {
            spoken.add(0, s);
        }

        // first counting number is 0

        int c = 0;
        while (spoken.size() < 2020)
        {
            int d = spoken.indexOf(c) + 1;

            spoken.add(0, c);
            c = d;
        }

        System.out.println("part1: " + spoken.get(0));
    }

    private static void part2(int... input)
    {
        Map<Integer, Integer> spoken = new HashMap<>();

        // starting numbers
        int i = 1;
        for (int s : input)
        {
            spoken.put(s, i);
            i++;
        }

        // first counting number is 0

        int c = 0;
        int f = 0;
        while (i <= 30_000_000)
        {
            Integer l = spoken.get(c);
            int d = (l == null) ? 0 : i - l;
            spoken.put(c, i);
            f = c;
            c = d;
            i++;
        }

        System.out.println("part2: " + f);
    }
}
