package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day22b
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day22.test");
        List<String> input2 = IOUtil.readFile("2017/day22.data");

        part2(parseMap(input1), 1, 1);
        part2(parseMap(input2), 12, 12);
    }

    private static Map<Integer, Map<Integer, Integer>> parseMap(List<String> input)
    {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for (int j = 0; j < input.size(); j++)
        {
            String l = input.get(j);
            Map<Integer, Integer> x = new HashMap<>();
            map.put(j, x);
            for (int i = 0; i < l.length(); i++)
            {
                x.put(i, l.charAt(i) == '#' ? 2 : 0);
                // System.out.println("? " + i + " " + j + " " + (l.charAt(i) == '#'));
            }
        }
        return map;
    }

    private static void part2(Map<Integer, Map<Integer, Integer>> map, int startX, int startY)
    {
        int direction = 0;
        int x = startX;
        int y = startY;

        int infected = 0;

        for (int i = 0; i < 10_000_000; i++)
        {
            int status = getStatus(map, x, y);

            switch (status)
            {
                case 0:
                    direction = (direction + 3) % 4;
                    break;
                case 2:
                    direction = (direction + 1) % 4;
                    break;
                case 3:
                    direction = (direction + 2) % 4;
                    break;
            }
            // System.out.println("# " + i + " " + x + " " + y + " " + isInfected);
            if (status == 1)
            {
                infected++;
            }
            setStatus(map, x, y, (status + 1) % 4);
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
            if (i == 99)
            {
                System.out.println("# " + infected);
            }
        }
        System.out.println("part2: " + infected);
    }

    private static int getStatus(Map<Integer, Map<Integer, Integer>> map, int x, int y)
    {
        Map<Integer, Integer> m1 = map.get(y);
        if (m1 == null)
        {
            return 0;
        }
        Integer m2 = m1.get(x);
        return (m2 == null) ? 0 : m2;
    }

    private static void setStatus(Map<Integer, Map<Integer, Integer>> map, int x, int y, int status)
    {
        Map<Integer, Integer> m1 = map.get(y);
        if (m1 == null)
        {
            m1 = new HashMap<>();
            map.put(y, m1);
        }
        m1.put(x, status);
    }

}
