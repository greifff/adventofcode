package adventofcode.aoc2018;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day02
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2018/day02.test");
        List<String> input2 = IOUtil.readFile("2018/day02.data");
        List<String> input3 = IOUtil.readFile("2018/day02.test2");

        part1(input1);
        part1(input2);
        part2(input3);
        part2(input2);
    }

    private static void part2(List<String> input)
    {
        for (int i = 0; i < input.size() - 1; i++)
        {
            for (int j = i + 1; j < input.size(); j++)
            {
                int notMatching = 0;
                String match = "";
                String a = input.get(i);
                String b = input.get(j);
                for (int k = 0; k < a.length(); k++)
                {
                    if (a.charAt(k) == b.charAt(k))
                    {
                        match += a.charAt(k);
                    }
                    else
                    {
                        notMatching++;
                    }
                }
                if (notMatching == 1)
                {
                    System.out.println("part2: " + match);
                    return;
                }
            }
        }

    }

    private static void part1(List<String> input)
    {
        int twos = 0;
        int threes = 0;

        for (String in : input)
        {
            List<Integer> c = in.chars().boxed().collect(Collectors.toList());
            Collections.sort(c);
            int current = 0;
            int count = 0;
            boolean has2 = false;
            boolean has3 = false;
            for (int i : c)
            {

                if (i == current)
                {
                    count++;
                }
                else
                {
                    has2 |= count == 2;
                    has3 |= count == 3;

                    count = 1;
                    current = i;
                }
                // System.out.println("? " + i + " " + count + " " + has2 + " " + has3);
            }
            if (has2 || (count == 2))
            {
                twos++;
            }
            if (has3 || (count == 3))
            {
                threes++;
            }
            // System.out.println("# " + in + " " + twos + " " + threes);
        }

        System.out.println("part1: " + (twos * threes));
    }

}
