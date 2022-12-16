package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day20
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day20.data");
        List<long[]> ranges = input.stream().map(s -> {
            String[] f = s.split("-");
            return new long[]
            { Long.parseLong(f[0]), Long.parseLong(f[1]) };
        }).collect(Collectors.toList());

        part1(ranges);
        part2(ranges);
    }

    private static void part1(List<long[]> ranges1)
    {
        List<long[]> ranges = new ArrayList<>(ranges1);
        Collections.sort(ranges, (la, lb) -> {
            long delta = la[0] - lb[0];
            return (int) Math.signum(delta);
        });

        long[] z = ranges.remove(0);
        System.out.println("/ " + +z[0] + " " + z[1]);
        long end = ranges.remove(0)[1];
        while (!ranges.isEmpty())
        {
            long[] x = ranges.remove(0);
            System.out.println("& " + end + " " + x[0] + " " + x[1]);
            if (x[0] <= end + 1)
            {
                end = Math.max(end, x[1]);
            }
            else
            {
                break;
            }
        }

        System.out.println("part1: " + (end + 1));
    }

    private static void part2(List<long[]> ranges1)
    {
        List<long[]> ranges = new ArrayList<>(ranges1);
        Collections.sort(ranges, (la, lb) -> {
            long delta = la[0] - lb[0];
            return (int) Math.signum(delta);
        });

        long available = 0;
        long[] z = ranges.remove(0);
        System.out.println("/ " + +z[0] + " " + z[1]);
        long end = ranges.remove(0)[1];
        while (!ranges.isEmpty())
        {
            long[] x = ranges.remove(0);
            System.out.println("& " + end + " " + x[0] + " " + x[1]);
            if (x[0] <= end + 1)
            {
                end = Math.max(end, x[1]);
            }
            else
            {
                available += x[0] - end - 1;
                end = x[1];
            }
        }

        System.out.println("part2: " + available);
    }
}
