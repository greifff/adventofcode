package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day02
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day02.test");
        List<String> input3 = IOUtil.readFile("2017/day02.test2");
        List<String> input2 = IOUtil.readFile("2017/day02.data");

        List<List<Integer>> data1 = input1.stream().map(Day02::parseRow).collect(Collectors.toList());
        List<List<Integer>> data2 = input2.stream().map(Day02::parseRow).collect(Collectors.toList());
        List<List<Integer>> data3 = input3.stream().map(Day02::parseRow).collect(Collectors.toList());

        part1(data1);
        part1(data2);
        part2(data3);
        part2(data2);
    }

    private static void part1(List<List<Integer>> data)
    {
        // TODO Auto-generated method stub
        long sum = 0;
        for (List<Integer> line : data)
        {
            int max = line.stream().reduce(Math::max).orElse(0);
            int min = line.stream().reduce(Math::min).orElse(0);
            sum += max - min;
        }

        System.out.println("part1: " + sum);
    }

    private static void part2(List<List<Integer>> data)
    {
        // TODO Auto-generated method stub
        long sum = 0;
        for (List<Integer> line : data)
        {
            int lv = 0;
            outer: for (int i = 0; i < line.size() - 1; i++)
            {
                for (int j = i + 1; j < line.size(); j++)
                {
                    int a = line.get(i);
                    int b = line.get(j);
                    if (a % b == 0)
                    {
                        // System.out.println("? " + a + " " + b);
                        lv = a / b;
                        break outer;
                    }
                    if (b % a == 0)
                    {
                        // System.out.println("! " + a + " " + b);
                        lv = b / a;
                        break outer;
                    }
                }
            }
            // System.out.println("# " + lv);
            sum += lv;
        }

        System.out.println("part2: " + sum);
    }

    private static List<Integer> parseRow(String s)
    {
        Matcher m = Patterns.number.matcher(s);
        List<Integer> v = new ArrayList<>();
        while (m.find())
        {
            v.add(Integer.parseInt(m.group()));
        }
        return v;
    }
}
