package adventofcode.aoc2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day2
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day02.data");

        part1(input);
        part2(input);
    }

    private static void part2(List<String> input)
    {
        String code = "";
        int current = 5;

        for (String s : input)
        {
            System.out.println("# " + code + " ## " + s);
            for (int i = 0; i < s.length(); i++)
            {
                current = nextKey2(current, s.charAt(i));
            }
            code += printKey(current);
        }
        System.out.println("part1: " + code);
    }

    private static void part1(List<String> input)
    {
        String code = "";
        int current = 5;

        for (String s : input)
        {
            System.out.println("# " + code + " ## " + s);
            for (int i = 0; i < s.length(); i++)
            {
                current = nextKey(current, s.charAt(i));
            }
            code += current;
        }
        System.out.println("part1: " + code);
    }

    private static int nextKey(int key, char direction)
    {

        switch (direction)
        {
            case 'U':
                return (key <= 3) ? key : key - 3;
            case 'D':
                return (key >= 7) ? key : key + 3;
            case 'L':
                return ((key % 3) == 1) ? key : key - 1;
            case 'R':
                return ((key % 3) == 0) ? key : key + 1;
        }

        return 0;
    }

    private static String printKey(int key)
    {
        if (key <= 9)
            return "" + key;
        return "" + (char) ('A' + (key - 10));
    }

    private static int nextKey2(int key, char direction)
    {
        Set<Integer> notUp = new HashSet<>(Arrays.asList(1, 2, 4, 5, 9));
        Set<Integer> notLeft = new HashSet<>(Arrays.asList(1, 2, 5, 10, 13));
        Set<Integer> notDown = new HashSet<>(Arrays.asList(5, 9, 10, 12, 13));
        Set<Integer> notRight = new HashSet<>(Arrays.asList(1, 4, 9, 12, 13));
        switch (direction)
        {
            case 'U':
                return (notUp.contains(key)) ? key : ((key == 3 || key == 13) ? key - 2 : key - 4);
            case 'D':
                return (notDown.contains(key)) ? key : ((key == 1 || key == 11) ? key + 2 : key + 4);
            case 'L':
                return (notLeft.contains(key)) ? key : key - 1;
            case 'R':
                return (notRight.contains(key)) ? key : key + 1;
        }

        return 0;
    }
}
