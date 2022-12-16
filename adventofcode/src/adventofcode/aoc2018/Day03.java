package adventofcode.aoc2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day03
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2018/day03.data");

        List<Claim> c = input1.stream().map(s -> new Claim(s)).collect(Collectors.toList());

        Map<Integer, Map<Integer, Integer>> grid = new HashMap<>();

        for (Claim c1 : c)
        {
            for (int x1 = c1.x; x1 < c1.x + c1.width; x1++)
            {
                for (int y1 = c1.y; y1 < c1.y + c1.height; y1++)
                {
                    Map<Integer, Integer> a = grid.get(x1);
                    if (a == null)
                    {
                        a = new HashMap<>();
                        grid.put(x1, a);
                    }
                    Integer b = a.get(y1);
                    a.put(y1, b == null ? 1 : b + 1);
                }
            }
        }

        long usedTwice = grid.values().stream().flatMap(a -> a.values().stream()).filter(i -> i > 1).count();

        System.out.println("part1: " + usedTwice);

        for (Claim c1 : c)
        {
            boolean hit = true;
            for (int x1 = c1.x; x1 < c1.x + c1.width; x1++)
            {
                for (int y1 = c1.y; y1 < c1.y + c1.height; y1++)
                {
                    Integer a = grid.get(x1).get(y1);
                    hit &= a == 1;
                }
            }
            if (hit)
            {
                System.out.println("part2: " + c1.id);
            }
        }
    }

    static class Claim
    {
        int id;
        int x;
        int y;
        int width;
        int height;

        Claim(String in)
        {
            Matcher m = Patterns.number.matcher(in);
            m.find();
            id = Integer.parseInt(m.group());
            m.find();
            x = Integer.parseInt(m.group());
            m.find();
            y = Integer.parseInt(m.group());
            m.find();
            width = Integer.parseInt(m.group());
            m.find();
            height = Integer.parseInt(m.group());
        }
    }
}
