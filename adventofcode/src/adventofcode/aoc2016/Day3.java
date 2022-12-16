package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day3
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day03.data");

        List<List<Integer>> triangles = input.stream().map(s -> {
            Matcher m = Patterns.number.matcher(s);
            List<Integer> sides = new ArrayList<>();
            while (m.find())
            {
                sides.add(Integer.parseInt(m.group()));
            }
            return sides;
        }).collect(Collectors.toList());

        part1(triangles);
        part2(triangles);
    }

    private static void part1(List<List<Integer>> triangles)
    {
        int count = 0;
        for (List<Integer> triangle : triangles)
        {
            int a = triangle.get(0);
            int b = triangle.get(1);
            int c = triangle.get(2);

            boolean valid = (a + b > c) && (a + c > b) && (b + c > a);
            if (valid)
            {
                count++;
            }
        }
        System.out.println("part1: " + count);
    }

    private static void part2(List<List<Integer>> triangles)
    {
        int count = 0;

        for (int j = 0; j < 3; j++)
        {
            for (int i = 0; i < triangles.size(); i += 3)
            {
                int a = triangles.get(i).get(j);
                int b = triangles.get(i + 1).get(j);
                int c = triangles.get(i + 2).get(j);

                boolean valid = (a + b > c) && (a + c > b) && (b + c > a);
                if (valid)
                {
                    count++;
                }
            }
        }
        System.out.println("part2: " + count);
    }
}
