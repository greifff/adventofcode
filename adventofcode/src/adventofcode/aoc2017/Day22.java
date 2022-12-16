package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day22
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day22.test");
        List<String> input2 = IOUtil.readFile("2017/day22.data");

        part1(parseMap(input1), 1, 1);
        part1(parseMap(input2), 12, 12);
    }

    private static Map<Integer, Map<Integer, Boolean>> parseMap(List<String> input)
    {
        Map<Integer, Map<Integer, Boolean>> map = new HashMap<>();
        for (int j = 0; j < input.size(); j++)
        {
            String l = input.get(j);
            Map<Integer, Boolean> x = new HashMap<>();
            map.put(j, x);
            for (int i = 0; i < l.length(); i++)
            {
                x.put(i, l.charAt(i) == '#');
                // System.out.println("? " + i + " " + j + " " + (l.charAt(i) == '#'));
            }
        }
        return map;
    }

    private static void part1(Map<Integer, Map<Integer, Boolean>> map, int startX, int startY)
    {
        int direction = 0;
        int x = startX;
        int y = startY;

        int infected = 0;

        for (int i = 0; i < 10_000; i++)// 10_000
        {
            boolean isInfected = getStatus(map, x, y);
            // System.out.println("# " + i + " " + x + " " + y + " " + isInfected);
            direction = (direction + (isInfected ? 1 : 3)) % 4;
            if (!isInfected)
            {
                infected++;
            }
            setStatus(map, x, y, !isInfected);
            // move
            switch (direction)
            {
                case 0:
                    y--;
                    break;
                case 2:
                    y++;
                    break;
                case 1:
                    x++;
                    break;
                case 3:
                    x--;
                    break;
            }
            if (i == 6 || i == 69)
            {
                System.out.println("# " + infected);
            }
        }
        System.out.println("part1: " + infected);
    }

    private static boolean getStatus(Map<Integer, Map<Integer, Boolean>> map, int x, int y)
    {
        Map<Integer, Boolean> m1 = map.get(y);
        if (m1 == null)
        {
            return false;
        }
        Boolean m2 = m1.get(x);
        return (m2 == null) ? false : m2;
    }

    private static void setStatus(Map<Integer, Map<Integer, Boolean>> map, int x, int y, boolean status)
    {
        Map<Integer, Boolean> m1 = map.get(y);
        if (m1 == null)
        {
            m1 = new HashMap<>();
            map.put(y, m1);
        }
        m1.put(x, status);
    }

}
