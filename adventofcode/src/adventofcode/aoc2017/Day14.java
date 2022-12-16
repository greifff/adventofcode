package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Day14
{

    public static void main(String[] args)
    {
        Map<Integer, Set<Integer>> test1 = createMap("flqrgnkx");
        Map<Integer, Set<Integer>> data1 = createMap("nbysizxe");

        part1(test1);
        part1(data1);

        part2(test1);
        part2(data1);
    }

    private static void part2(Map<Integer, Set<Integer>> data)
    {
        int groups = 0;
        for (int x : data.keySet())
        {
            try
            {
                while (true)
                {
                    int y = data.get(x).iterator().next();
                    groups++;
                    eliminate(data, x, y);
                }
            }
            catch (NoSuchElementException e)
            {
                //
            }
        }
        System.out.println("part2: " + groups);
    }

    private static void eliminate(Map<Integer, Set<Integer>> data, int x, int y)
    {
        Set<Integer> row = data.get(x);
        if (row != null && row.remove(y))
        {
            eliminate(data, x - 1, y);
            eliminate(data, x + 1, y);
            eliminate(data, x, y - 1);
            eliminate(data, x, y + 1);
        }
    }

    private static void part1(Map<Integer, Set<Integer>> data)
    {
        int s = data.values().stream().map(r -> r.size()).reduce((a, b) -> a + b).orElse(0);
        System.out.println("part1: " + s);
    }

    private static Map<Integer, Set<Integer>> createMap(String secret)
    {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < 128; i++)
        {
            String hash = Day10.knothash(secret + "-" + i);
            String hash2 = "";
            for (int j = 0; j < hash.length(); j++)
            {
                int c = Integer.parseInt(hash.substring(j, j + 1), 16) + 0x10;
                hash2 += Integer.toBinaryString(c).substring(1);
            }

            Set<Integer> row = new HashSet<>();
            for (int j = 0; j < hash2.length(); j++)
            {
                if (hash2.charAt(j) == '1')
                {
                    row.add(j);
                }
            }
            map.put(i, row);
        }
        return map;
    }
}
