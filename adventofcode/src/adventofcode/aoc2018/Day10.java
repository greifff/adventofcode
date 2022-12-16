package adventofcode.aoc2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day10
{
    public static void main(String[] args)
    {
        List<String> test1 = IOUtil.readFile("2018/day10.test");
        List<String> input = IOUtil.readFile("2018/day10.data");

        List<Star> testStars = test1.stream().map(s -> new Star(s)).collect(Collectors.toList());
        List<Star> stars = input.stream().map(s -> new Star(s)).collect(Collectors.toList());

        part1(testStars, 0);
        part1(stars, 0);
        part1b(testStars, 3);
        part1b(stars, 10595);
        part1c(testStars);
        part1c(stars);
    }

    private static void part1(List<Star> stars, int startTime)
    {

        int time = startTime;
        while (true)
        {
            Map<Long, List<Long>> sky = renderSky(stars, time);
            if (countLinked(sky) > stars.size() * .8)
            {
                printSky(sky);
                System.out.println("time: " + time);
                return;
            }
            time++;
        }
    }

    private static void part1c(List<Star> stars)
    {
        long verticalDelta = Long.MAX_VALUE;
        long verticalDeltaNew = Long.MAX_VALUE;
        int time = -1;
        do
        {
            time++;
            verticalDelta = verticalDeltaNew;
            Map<Long, List<Long>> sky = renderSky(stars, time);
            List<Long> ys = new ArrayList<>(sky.keySet());
            Collections.sort(ys);
            verticalDeltaNew = ys.get(ys.size() - 1) - ys.get(0);
        }
        while (verticalDeltaNew < verticalDelta);

        part1b(stars, time - 1);
    }

    private static void part1b(List<Star> stars, int time)
    {

        Map<Long, List<Long>> sky = renderSky(stars, time);
        countLinked(sky);
        printSky(sky);
        System.out.println("time: " + time);
    }

    private static void printSky(Map<Long, List<Long>> sky)
    {
        long minX = Integer.MAX_VALUE;

        for (List<Long> row : sky.values())
        {
            Collections.sort(row);
            minX = Math.min(minX, row.get(0));
        }

        List<Long> ys = new ArrayList<>(sky.keySet());
        Collections.sort(ys);
        for (long y : ys)
        {
            List<Long> xs = sky.get(y);
            long xc = minX;
            // System.out.println("xc=" + xc + " x1=" + xs.get(0));
            for (long x : xs)
            {
                for (long k = xc; k < x; k++)
                {
                    System.out.print(" ");
                }
                System.out.print("#");
                xc = x + 1;
            }
            System.out.println();
        }
    }

    private static int countLinked(Map<Long, List<Long>> sky)
    {
        int linked = 0;
        for (Map.Entry<Long, List<Long>> e : sky.entrySet())
        {
            Collections.sort(e.getValue());
            long y = e.getKey();
            for (long x : e.getValue())
            {
                if (shines(sky, x - 1, y - 1) || shines(sky, x - 1, y) || shines(sky, x - 1, y + 1) //
                        || shines(sky, x, y - 1) || shines(sky, x, y + 1) //
                        || shines(sky, x + 1, y - 1) || shines(sky, x + 1, y) || shines(sky, x + 1, y + 1))
                {
                    linked++;
                }
            }
        }
        return linked;
    }

    private static boolean shines(Map<Long, List<Long>> sky, long x, long y)
    {
        List<Long> row = sky.get(y);
        return row != null ? row.contains(x) : false;
    }

    private static Map<Long, List<Long>> renderSky(List<Star> stars, int time)
    {
        Map<Long, List<Long>> sky = new HashMap<>();
        for (Star s : stars)
        {
            long x = s.getXat(time);
            long y = s.getYat(time);

            List<Long> row = sky.get(y);
            if (row == null)
            {
                row = new ArrayList<>();
                sky.put(y, row);
            }
            row.add(x);
        }

        for (long y : sky.keySet())
        {
            List<Long> xs = new ArrayList<>(new HashSet<>(sky.get(y)));
            Collections.sort(xs);
            sky.put(y, xs);
        }
        return sky;
    }

    static class Star
    {
        long px;
        long py;
        long vx;
        long vy;

        Star(String in)
        {
            Matcher m = Patterns.number.matcher(in);
            m.find();
            px = Long.parseLong(m.group());
            m.find();
            py = Long.parseLong(m.group());
            m.find();
            vx = Long.parseLong(m.group());
            m.find();
            vy = Long.parseLong(m.group());
        }

        long getXat(int t)
        {
            return px + vx * t;
        }

        long getYat(int t)
        {
            return py + vy * t;
        }
    }
}
