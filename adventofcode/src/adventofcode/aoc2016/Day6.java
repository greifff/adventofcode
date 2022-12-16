package adventofcode.aoc2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day6
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2016/day06.test");
        List<String> input2 = IOUtil.readFile("2016/day06.data");

        System.out.println("test1: " + consolidate(input1));
        System.out.println("part1: " + consolidate(input2));

        System.out.println("test2: " + consolidate2(input1));
        System.out.println("part2: " + consolidate2(input2));
    }

    private static String consolidate(List<String> input)
    {
        String message = "";

        for (int i = 0; i < input.get(0).length(); i++)
        {
            Map<Character, Integer> occ = new HashMap<>();
            for (String in : input)
            {
                char c = in.charAt(i);
                Integer x = occ.get(c);
                if (x == null)
                {
                    occ.put(c, 1);
                }
                else
                {
                    occ.put(c, x + 1);
                }
            }
            int occ2 = 0;
            char c = 0;
            for (Map.Entry<Character, Integer> e : occ.entrySet())
            {
                if (occ2 < e.getValue())
                {
                    occ2 = e.getValue();
                    c = e.getKey();
                }
            }
            message += c;
        }
        return message;
    }

    private static String consolidate2(List<String> input)
    {
        String message = "";

        for (int i = 0; i < input.get(0).length(); i++)
        {
            Map<Character, Integer> occ = new HashMap<>();
            for (String in : input)
            {
                char c = in.charAt(i);
                Integer x = occ.get(c);
                if (x == null)
                {
                    occ.put(c, 1);
                }
                else
                {
                    occ.put(c, x + 1);
                }
            }
            int occ2 = Integer.MAX_VALUE;
            char c = 0;
            for (Map.Entry<Character, Integer> e : occ.entrySet())
            {
                if (occ2 > e.getValue())
                {
                    occ2 = e.getValue();
                    c = e.getKey();
                }
            }
            message += c;
        }
        return message;
    }
}
