package adventofcode.aoc2016;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day8
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2016/day08.test");
        List<String> input2 = IOUtil.readFile("2016/day08.data");

        part1(input1, 7, 3);
        part1(input2, 50, 6);
    }

    private static void part1(List<String> input, int dimx, int dimy)
    {
        boolean[][] display = new boolean[dimx][dimy];

        for (String s : input)
        {
            if (s.startsWith("rect"))
            {
                turnOn(s, display);
            }
            else if (s.startsWith("rotate row"))
            {
                rotateRow(s, display);
            }
            else if (s.startsWith("rotate column"))
            {
                rotateColumn(s, display);
            }
            print(display);
        }

        System.out.println("part1: " + countLit(display));
    }

    private static void rotateColumn(String s, boolean[][] display)
    {
        int i1 = s.indexOf('=');
        int i2 = s.indexOf(" by ");
        int x = Integer.parseInt(s.substring(i1 + 1, i2));
        int r = Integer.parseInt(s.substring(i2 + 4));

        boolean[] column = new boolean[display[0].length];
        for (int y = 0; y < display[0].length; y++)
        {
            column[(y + r) % display[0].length] = display[x][y];
        }
        for (int y = 0; y < display[0].length; y++)
        {
            display[x][y] = column[y];
        }
    }

    private static void rotateRow(String s, boolean[][] display)
    {
        int i1 = s.indexOf('=');
        int i2 = s.indexOf(" by ");
        int y = Integer.parseInt(s.substring(i1 + 1, i2));
        int r = Integer.parseInt(s.substring(i2 + 4));

        boolean[] row = new boolean[display.length];
        for (int x = 0; x < display.length; x++)
        {
            row[(x + r) % display.length] = display[x][y];
        }
        for (int x = 0; x < display.length; x++)
        {
            display[x][y] = row[x];
        }
    }

    private static void turnOn(String s, boolean[][] display)
    {
        String[] coords = s.split(" ")[1].split("x");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        for (int x1 = 0; x1 < x; x1++)
        {
            for (int y1 = 0; y1 < y; y1++)
            {
                display[x1][y1] = true;
            }
        }
    }

    private static int countLit(boolean[][] display)
    {
        int countLit = 0;
        for (int x = 0; x < display.length; x++)
        {
            for (int y = 0; y < display[x].length; y++)
            {
                if (display[x][y])
                    countLit++;
            }
        }
        return countLit;
    }

    private static void print(boolean[][] display)
    {
        for (int y = 0; y < display[0].length; y++)
        {
            for (int x = 0; x < display.length; x++)
            {
                System.out.print(display[x][y] ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }
}
