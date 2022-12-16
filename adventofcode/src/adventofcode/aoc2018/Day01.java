package adventofcode.aoc2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day01
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2018/day01.data");

        List<Integer> values = input.stream().map(s -> Integer.parseInt(s.replace("+", "")))
                .collect(Collectors.toList());

        System.out.println("part1: " + values.stream().reduce((a, b) -> a + b).orElse(0));

        Set<Integer> frequencies = new HashSet<>();

        frequencies.add(0);
        int last = 0;
        while (true)
        {
            for (int v : values)
            {
                int c = v + last;
                if (frequencies.contains(c))
                {
                    System.out.println("part2: " + c);
                    return;
                }
                frequencies.add(c);
                last = c;
            }
        }
    }
}
