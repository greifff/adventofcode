package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day05
{
    public static void main(String[] args)
    {
        List<Integer> input1 = IOUtil.readFile("2017/day05.test").stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());
        List<Integer> input2 = IOUtil.readFile("2017/day05.data").stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        part1(input1);
        part1(input2);

        part2(input1);
        part2(input2);
    }

    private static void part1(List<Integer> input1)
    {
        List<Integer> data = new ArrayList<>(input1);
        int index = 0;
        int steps = 0;

        while (index >= 0 && index < data.size())
        {
            int instruction = data.get(index);
            data.set(index, instruction + 1);
            index += instruction;
            steps++;
        }

        System.out.println("part1: " + steps);
    }

    private static void part2(List<Integer> input1)
    {
        List<Integer> data = new ArrayList<>(input1);
        int index = 0;
        int steps = 0;

        while (index >= 0 && index < data.size())
        {
            int instruction = data.get(index);
            data.set(index, instruction + (instruction >= 3 ? -1 : 1));
            index += instruction;
            steps++;
        }

        System.out.println("part2: " + steps);
    }

}
