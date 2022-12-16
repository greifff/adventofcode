package adventofcode.aoc2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day06
{

    public static void main(String[] args)
    {
        List<Point> test1 = IOUtil.readFile("2018/day06.test").stream().map(s -> new Point(s))
                .collect(Collectors.toList());
        List<Point> input = IOUtil.readFile("2018/day06.data").stream().map(s -> new Point(s))
                .collect(Collectors.toList());

        part1(test1);
        part1(input);
        part2(input);
    }

    static void part1(List<Point> points)
    {

        for (int x = 0; x < 500; x++)
        {
            for (int y = 0; y < 500; y++)
            {
                List<int[]> d = new ArrayList<>();
                for (int k = 0; k < points.size(); k++)
                {
                    Point p = points.get(k);
                    d.add(new int[]
                    { k, p.manhattanDistance(x, y) });
                }
                Collections.sort(d, (a, b) -> a[1] - b[1]);
                if (d.get(0)[1] < d.get(1)[1])
                {
                    Point p = points.get(d.get(0)[0]);
                    p.area++;
                    p.infinite |= x == 0 || x == 499 || y == 0 || y == 499;
                }
            }
        }

        // for (int k = 0; k < points.size(); k++)
        // {
        // Point p = points.get(k);
        // System.out.println("# " + k + " " + p.area + " " + p.infinite);
        // }

        int area = points.stream().filter(p -> !p.infinite).map(p -> p.area).reduce(Math::max).orElse(0);
        System.out.println("part1: " + area);
    }

    static void part2(List<Point> points)
    {
        int area = 0;
        for (int x = 0; x < 500; x++)
        {
            for (int y = 0; y < 500; y++)
            {
                int deltaSigma = 0;
                for (int k = 0; k < points.size(); k++)
                {
                    Point p = points.get(k);
                    deltaSigma += p.manhattanDistance(x, y);
                }
                if (deltaSigma < 10000)
                {
                    area++;
                }
            }
        }
        System.out.println("part2: " + area);
    }

    static class Point
    {
        int x;
        int y;
        int area;
        boolean infinite;

        Point(String s)
        {
            String[] f = s.replace(",", "").split(" ");
            x = Integer.parseInt(f[0]);
            y = Integer.parseInt(f[1]);
        }

        int manhattanDistance(int x1, int y1)
        {
            return Math.abs(x - x1) + Math.abs(y - y1);
        }
    }
}
