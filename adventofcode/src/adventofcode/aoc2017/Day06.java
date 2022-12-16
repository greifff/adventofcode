package adventofcode.aoc2017;

import java.util.HashSet;
import java.util.Set;

public class Day06
{

    public static void main(String[] args)
    {
        int[] test1 =
        { 0, 2, 7, 0 };
        int[] data =
        { 10, 3, 15, 10, 5, 15, 5, 15, 9, 2, 5, 8, 5, 2, 3, 6 };

        part1(test1);
        part1(data);
    }

    private static void part1(int[] data1)
    {
        int[] data = new int[data1.length];
        Set<String> archive = new HashSet<>();

        System.arraycopy(data1, 0, data, 0, data.length);

        // TODO Auto-generated method stub

        String a = toString(data);

        int cycles = 0;
        while (!archive.contains(a))
        {
            System.out.println("# " + cycles + ": " + a);
            archive.add(a);
            cycle(data);
            cycles++;
            a = toString(data);
        }
        System.out.println("# " + cycles + ": " + a);
        System.out.println("part1: " + cycles);
    }

    private static String toString(int[] x)
    {
        String s = "";
        for (int i = 0; i < x.length; i++)
        {
            s += "" + x[i] + ",";
        }
        return s;
    }

    private static void cycle(int[] data)
    {
        int maxValue = 0;
        for (int k : data)
        {
            maxValue = Math.max(maxValue, k);
        }

        int i = 0;
        while (data[i] < maxValue)
        {
            i++;
        }

        data[i] = 0;

        while (maxValue > 0)
        {
            i = (i + 1) % data.length;
            data[i]++;
            maxValue--;
        }
    }

}
